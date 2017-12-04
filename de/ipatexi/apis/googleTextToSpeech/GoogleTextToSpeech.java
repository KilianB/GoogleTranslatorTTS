package de.ipatexi.apis.googleTextToSpeech;

import static tts.GLanguage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Convert long strings of text into .mp3 files in real time utilizing googles translator text to speech service.
 * Due to the nature of the google api we end up with multiple mp3 files which get merge together. 
 * Multi threaded and multi language support.
 *  
 * If you want to convert larger volumes of data please consider using the official cloud spech api https://cloud.google.com/speech/
 * @author Kilian
 */
public class GoogleTextToSpeech {

	private final String GOOGLE_URL = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&q=__TEXT__&tl=";
	private GLanguage defaultLanguage = English_GB;  //all allowed language codes can be found here https://cloud.google.com/speech/docs/languages
	
	/* The Google API only allows strings to be converted with a limited length which lays somewhere between 200 - 300 characters per request 
	 * truncating the sentence (or returning a 420 http failure). To solve this issue we split up our request in multiple requests.
	 * Increasing the characterLimit will result in increased performance and fewer generated mp3 files. 
	 */
	private int characterLimit = 200;
	private String outputDirectoryPath;	//The directory of the saved files
	
	/*
	 * The end of each file is padded with silence. Remove it to ensures the merged files to play back seamlessly .
	 *  Alternative 1: Inspect the bytes and figure out where exactly the silence starts. This will hit 
	 *  the merge performance as we have to inspect the individual bytes before writing them.
	 * 	The signature of the to be removed block looks like the following: 
	 * 
	 * 1: MP3 Header:
	 * 	ff f3 44 c   
	 * 2: MP3 Data Block:
	 *   4 ac 20 20 03 48 20 20 20 20 39 39 2e
	 *  35 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 
	 *  55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55
	 *  55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 
	 *  55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 
	 *  55 55 4c 41 4d 45 33 2e  
	 *  
	 * Alternative 2: Take a good guess and simply truncate the last x Bytes.
	 */
	private final int BITRATE = 32000; 				//32kbit Bitrate of the files 
	private final int BITS_PER_DATA_BLOCK = 736;	//MP3 Data block lenght
	private final int BITS_PER_HEADER_BLOCK = 32;	//MP3 Header block length
	double secsToTruncate = 0.3;					//How many seconds of the end of the file do we want to truncate?
	long bytesToTruncate = 0;						//The actual count of bytes we truncate. Gets calculated by the seconds
	/*
	 * If you bombard google with to many requests at the same time they will eventually blacklist you. If you multi thread a large request it's better to add a small delay
	 * between each request. 
	 */
	long msSleepBetweenRequests = 50;
	
	/**
	 * @param outFilePath The directory where the .mp3 files will be stored
	 */
	public GoogleTextToSpeech(String outputDirectoryPath){
		this.outputDirectoryPath = outputDirectoryPath;

		bytesToTruncate = (long) ((BITRATE/8)*secsToTruncate);
		long bytePerBlock = (BITS_PER_DATA_BLOCK + BITS_PER_HEADER_BLOCK)/8;
		
		//We only want to truncate an entire header + block or our file might get corrupted
		if(bytesToTruncate%bytePerBlock != 0)
		{
			bytesToTruncate = findClosestDivisibleInteger(bytesToTruncate,bytePerBlock);
		}
		return;
	}
	
	/**
	 * Convert the supplied text to a .mp3 file using the default language. Blocks and returns the generated mp3 file once it is ready.
	 * @param text The string which will be converted to speech. Special characters might mess up the pronunciation.
	 * @param outputFileName The name of the outputfile without extension. The file is created by appending the outputname to the output directory supplied by the constructor of this object
	 * 			which in turns allows you to specify sub directories using the file name. e.g. "MyFile" or "Foo/Bar"
	 * @return a single mp3 file. Null if an error occurred during conversion (e.g. no internet access).
	 */
	public File convertText(String text ,String outputFileName){
		return convertText(text,defaultLanguage,outputFileName);
	}
	
	/**
	 * Convert the supplied text to a .mp3 file using the supplied language. Blocks and returns the generated mp3 file once it is ready.
	 * @param text The string which will be converted to speech. Special characters might mess up the pronunciation.
	 * @param language The language and accent which will be used to pronounce the text.
	 * @param The name of the outputfile without extension. The file is created by appending the outputname to the output directory supplied by the constructor of this object
	 * 			which in turns allows you to specify sub directories using the file name. e.g. "MyFile" or "Foo/Bar"
	 * @return a single mp3 file. Null if an error occurred during conversion (e.g. no internet access).
	 */
	public File convertText(String text,GLanguage language,String outputFileName){
		int hash = text.hashCode();
		ArrayList<File> tempFiles = new ArrayList<File>();
		convertText(new String[] {text},new GLanguage[] {language},outputFileName,hash,tempFiles,null);
		return mergeFiles(outputFileName,true,tempFiles.toArray(new File[tempFiles.size()]));
	}
	

	/**
	 * Convert the supplied text to a single .mp3 file using the submitted languages. 
	 * Each string in the text-array will be converted using the corresponding language supplied in the languages array.
	 * Blocks and returns the generated mp3 file once it is ready.
	 * @param text The string which will be converted to speech. Special characters might mess up the pronunciation. 
	 * @param language The language and accent which will be used to pronounce the text.
	 * @param The name of the outputfile without extension. The file is created by appending the outputname to the output directory supplied by the constructor of this object
	 * 			which in turns allows you to specify sub directories using the file name. e.g. "MyFile" or "Foo/Bar"
	 * @return a single mp3 file. Null if an error occurred during conversion (e.g. no internet access).
	 */
	public File convertTextMultiLanguage(String[] text,GLanguage language[], String outputFileName) {
		int hash = text.hashCode();
		ArrayList<File> tempFiles = new ArrayList<File>();
		convertText(text,language,outputFileName,hash,tempFiles,null);
		
		return mergeFiles(outputFileName,true,tempFiles.toArray(new File[tempFiles.size()]));
	}
	
	
	/**
	 * Asynchronously convert the supplied text to a single .mp3 file using the submitted languages.
	 * This function is not faster than the synchronous call. If an observer is supplied you get notified once the files are ready. 
	 * The time conversion takes depends on the specified character limit your internet connection and the length of the text. 
	 * @param text
	 * @param language
	 * @param outputFilePath
	 * @param deleteTempFiles  
	 * @param observer
	 */
	public void convertTextAsynch(String text,GLanguage language,String outputFilePath,boolean deleteTempFiles,GoogleTextToSpeechObserver observer) {
		new Thread(()-> {
			int hash = text.hashCode();
			ArrayList<File> tempFiles = new ArrayList<File>();
			convertText(new String[] {text},new GLanguage[]{language},outputFilePath,hash,tempFiles,observer);
			File mergedFile = mergeFiles(outputFilePath,deleteTempFiles,tempFiles.toArray(new File[tempFiles.size()]));
			if(observer!=null)
				observer.mergeCompleted(hash, mergedFile);
		}).start();
		return;
	}
	
	
	
	public void convertTextMultiLanguageAsynch(String[] text,GLanguage language[], String outoutFilePath, boolean deleteTempFiles, GoogleTextToSpeechObserver observer) {
		new Thread(()->{
			int hash = text.hashCode();
			ArrayList<File> tempFiles = new ArrayList<File>();
			convertText(text,language,outoutFilePath,hash,tempFiles,observer);
			File mergedFile = mergeFiles(outoutFilePath,deleteTempFiles,tempFiles.toArray(new File[tempFiles.size()]));
			if(observer!=null)
				observer.mergeCompleted(hash, mergedFile);
			
		}).start();
	}
	
	/**
	 * Convert the text to mp3 files by first splitting the request into suitable chunks which comply with the character limit set at the beginning. 
	 * Afterwards the url is queried and the mp3 files are downloaded. Multi threaded. 
	 * @param text
	 * @param language
	 * @param outputFilePrefix
	 * @param hashIdentifier
	 * @param temporaryFiles
	 * @param observer
	 */
	private void convertText(String[] text,GLanguage[] language,String outputFilePrefix,int hashIdentifier,ArrayList<File> temporaryFiles,GoogleTextToSpeechObserver observer){
	
		ArrayList<RequestData> requestData = new ArrayList<RequestData>();
		
		for(int j = 0; j < text.length; j++) {
			//Prepare the text and split it up into suitable chunks to guarantee that no request is > character limit
			String sentences[] = text[j].split("\\.");	//Split it into sentences
			ArrayList<String> requestStrings = new ArrayList<String>();
			
			String tempRequest = "";
			for(int i = 0; i < sentences.length; i++){
				
				if( (tempRequest.length() + sentences[i].length()) > characterLimit){;
					//If it's not empty add it to the queue. This can happen at the very beginning.
					if(!tempRequest.isEmpty())
						requestStrings.add(tempRequest); //Add the old request to the queue
					tempRequest = sentences[i] +"."; //And fill the current line to the next reuqest.
				}else{
					tempRequest += sentences[i] + ".";
				}
				
				//If the current sentence is to long, we need to cut it somewhere after a word.
				while(tempRequest.length() > characterLimit){
					int wordEndIndex = tempRequest.lastIndexOf(" ",characterLimit);
					String subsentence = tempRequest.substring(0, wordEndIndex);
					requestStrings.add(subsentence);
					tempRequest = tempRequest.substring(wordEndIndex);
				}
			}
			
			//and at the very end simply add the remainder to the request
			if(tempRequest.length() > 0){
				requestStrings.add(tempRequest);
			}
			if(j >= language.length) {
				//TODO how do enums behave in multi threaded enviroments? 
				requestData.add(new RequestData(defaultLanguage,requestStrings));
			}else {
				requestData.add(new RequestData(language[j],requestStrings));
			}
			
		}
		
	
		//Download the files
		
		for(RequestData data: requestData) {
			System.out.println("Req Data: " + data.language);
			for(String s : data.requests) {
				System.out.println(s);
			}
		}
		
		try{
		//Build request
			final int requestWrapperCount = requestData.size();
			
			//Threads
			//IO heavy operation therefore we can increase our thread pool?
			ExecutorService threadPool = Executors.newFixedThreadPool(10);
			ArrayList<Future<File>> callback = new ArrayList<Future<File>>();
			int fileNamingOffset = 0;
			
			for(int j = 0; j < requestWrapperCount; j++) {
				ArrayList<String> requests = requestData.get(j).requests;
				GLanguage requestLanguage = requestData.get(j).language;
				
				int requestCount = requests.size();
				for(int i = 0; i < requestCount; i++){
					
					//Create the task
					Callable<File> task = new Callable<File>() {
						int i;
						int fileNamingOffset;
						private Callable<File> setParameters(int i,int fileNamingOffset) {
							this.i = i;
							this.fileNamingOffset = fileNamingOffset;
							return this;
						}
						
						@Override
						public File call() throws Exception {
							String query = 	URLEncoder.encode(requests.get(i), "UTF-8");
							String request = GOOGLE_URL.replace("__TEXT__", query) + requestLanguage.getCountryCode();
							URLConnection conn = new URL(request).openConnection();
							conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
							InputStream is = conn.getInputStream();
							File outputFile = new File(outputDirectoryPath + outputFilePrefix+(i+fileNamingOffset)+".mp3");
							OutputStream outstream = new FileOutputStream(outputFile);
							byte[] buffer = new byte[4096];
							int len;
							while ((len = is.read(buffer)) > 0) {
								outstream.write(buffer, 0, len);
							}
							outstream.close();
							return outputFile;
						}
						
					}.setParameters(i,fileNamingOffset);
					
					//Start the task in one of the threads and save it for later reference.
					Future<File> future = threadPool.submit(task);
					callback.add(future);
				
					Thread.sleep(msSleepBetweenRequests);
					
				}	
				fileNamingOffset += requestCount;
			}
			
			//Block until all threads are ready
			for(int i = 0; i < callback.size(); i++) {
				File tempFile = callback.get(i).get();
				if(observer != null) {
					if(i == 0 && fileNamingOffset == 0) {
						observer.firstFileDownloaded(tempFile, hashIdentifier);
					}
					observer.fileDownloaded(tempFile, hashIdentifier);
				}
				temporaryFiles.add(tempFile);
			}
			
			threadPool.shutdown();
			observer.fileDownloadCompleted(hashIdentifier);
			return;
			
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
		
	}
	/**
	 * Due to the nature of the google api we end up with multiple mp3 files. Merge those together to get one playable file.
	 * This method may not produce suitable results for arbitrary mp3 files as you might need to strip id3 tags beforehand which are 
	 * not present here. We also truncate parts of the file to account for silence added at the end of each downloaded .mp3 track.
	 * @param targetName The name of the new file
	 * @param deleteOldFiles Delete the temporary files created by the api 
	 * @param filesToMerge Mp3 files which will be merged in order
	 * @return
	 */
	private File mergeFiles(String targetName,boolean deleteOldFiles,File... filesToMerge){
		
		//Delete the final file if it should be present
		File targetFile = new File(outputDirectoryPath+targetName+".mp3");
		if(targetFile.exists()) {
			targetFile.delete();
		}

		try {
			targetFile.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
			
			int SIZE = 4000; // 4 kb byte buffer used to read bytes in a block to save IO operations.
			byte[] barray;
			int nGet;	//Number of bytes read at a time

			//Append files byte by byte
			for(int i = 0; i < filesToMerge.length; i++) {
				
				/* By using file channels & mapped byte buffers we gain gain a magnitude of performance compared to appending every byte individually.
				 * (13 ms instead of 560 in a small example with 3 files).
				 * Interesting benchmarking article: http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly */
				FileInputStream fileInputStream = new FileInputStream(filesToMerge[i]);
				FileChannel fileChannel = fileInputStream.getChannel();
				MappedByteBuffer mb = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
				barray = new byte[SIZE];
				
				//Strip id3 tags 
				/* - Ignore skip for first file
				 * Apparently those files have no id3 tags therefore we don't need to skip any bytes
				 * Get ID3 Tag size: http://www.gigamonkeys.com/book/practical-an-id3-parser.html
				 * mb.position(32);
				 */
				
				boolean done = false;	//Flag to indicate that we truncated the remaining bytes and can move to the next available file.
				
				while (!done && mb.hasRemaining()) {
					nGet = Math.min(mb.remaining(), SIZE);
					
					/*
					 * TODO The end of each file is padded with silence. We should remove it to ensure a better sounde quality.
					 * Alternative 1: Inspect the bytes and figure out where exactly the silence starts. This will hit 
					 * the merge performance as we have to inspect the individual bytes before writing them.
					 * 	1: MP3 Header:
					 * 		ff f3 44 c   
					 *  2: MP3 Data Block:
					 *  	4 ac 20 20 03 48 20 20 20 20 39 39 2e
					 *  	35 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 
					 *  	55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55
					 *  	55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 
					 *  	55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 55 
					 *  	55 55 4c 41 4d 45 33 2e  
					 *  
					 * Alternative 2: Take a good guess and simply truncate the last x Bytes.
					 */
					System.out.println(nGet + " " + mb.remaining() + " " + bytesToTruncate + " " + (mb.remaining()-bytesToTruncate) );
					
					
					if(nGet > mb.remaining()-bytesToTruncate) {
						
						nGet =  (int) (mb.remaining()-bytesToTruncate);
						//if(nGet < 0)
						//	nGet = 0;
						done = true;
					};
					
					barray = new byte[nGet];
					
					//Alternative 1 here with a linear search approach for xff followed by xf3
					mb.get(barray,0, nGet);
					fileOutputStream.write(barray);
				}
			
				fileInputStream.close();
			}
			
			fileOutputStream.close();
			
			//Delete temporary files if applicable
			if(deleteOldFiles) {
				for(File f : filesToMerge) {
					f.delete();
				}
			}
			
			return targetFile;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Find the nearest integer which will divide the given number so number%x == 0
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	private long findClosestDivisibleInteger(long dividend, long divisor) {
		long lowerBound = dividend - (dividend % divisor);
		long upperBound = (dividend + divisor) - (dividend % divisor);
		if (dividend - lowerBound > upperBound - dividend) {
			return upperBound;
		} else {
			return lowerBound;
		}
	}
	
	
	/**
	 * Data class to handle multi language requests.
	 * @author Kilian
	 *
	 */
	private class RequestData{
		GLanguage language;
		ArrayList<String> requests;
		
		public RequestData(GLanguage language, ArrayList<String> requests) {
			this.language = language;
			this.requests = requests;
		}
	}
}
