package de.ipatexi.apis.googleTextToSpeech.examples;

import java.io.File;

import de.ipatexi.apis.googleTextToSpeech.GLanguage;
import de.ipatexi.apis.googleTextToSpeech.GoogleTextToSpeech;

public class TextToSpeechWithPlayback{
	
	public static void main(String[] args) {
		
		//Path to output mp3 directory
		String outputPath = "mpFiles/";

		//Text to convert to mp3
		String text = "When in the Course of human events it becomes necessary for one people to dissolve the political bands which have connected them with another and to assume among the powers of the earth, the separate and equal station to which the Laws of Nature and of Nature's God entitle them, a decent respect to the opinions of mankind requires that they should declare the causes which impel them to the separation.";
		
		//Create directory
		File outputDirectory = new File(outputPath);
		outputDirectory.mkdirs();
		
		//Create an mp3 player to playback the final result
		SimpleMP3Player mp3Player = new SimpleMP3Player();
		
		//Finally convert the text and play back the mp3 files
		GoogleTextToSpeech tts = new GoogleTextToSpeech(outputPath);
		tts.convertTextAsynch(text,GLanguage.English_GB,"Independence",true,mp3Player);
	} 
	
}