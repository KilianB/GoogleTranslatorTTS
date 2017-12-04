# Google-Text-To-Speech-Java-API
Convert long strings of text into .mp3 files in real time utilizing googles translator text to speech service. Supports multi language requests and multi threading resulting in usually responds times of .75 seconds. 

(TODO: be aware that the example package is outdated at the moment)

## Example usage 

```java
public class TextToSpeechSample{
	
	public static void main(String[] args) {
		
		//Path to output mp3 directory
		String outputPath = "mpFiles/";

		//Text to convert to mp3
		String text = "When in the Course of human events it becomes necessary for one people to dissolve the political bands which have connected them with another and to assume among the powers of the earth, the separate and equal station to which the Laws of Nature and of Nature's God entitle them, a decent respect to the opinions of mankind requires that they should declare the causes which impel them to the separation.";
		
		//Create directory
		File outputDirectory = new File(outputPath);
		outputDirectory.mkdirs();
	
		//Convert the text and retrieve an mp3 file
		GoogleTextToSpeech tts = new GoogleTextToSpeech(outputPath);
		File convertedTextMP3 = tts.convertText(text, GLanguage.English_US, "FileName");
		
		//Non blocking method. Call event handler in the observer upon finishing
		boolean deleteTemporaryFiles = true;
		gtts.convertTextAsynch(text,GLanguage.German, "FileName",deleteTemporaryFiles, <? extends GoogleTextToSpeechObserver>);
	
		//Request an mp3 file with multiple different pronounciations
		String text[] = new String[]{
			"The author",
			"Immanuel Kant",
			"argued that the human mind creates the structure of human experience, that reason is the source of morality."
		};
		
		GLanguage[] language = new GLanguage[] {
				GLanguage.English_GB,
				GLanguage.German,
				GLanguage.English_GB
		};
		
		//Asynch is available as well
		convertedTextMP3 = File gtts.convertTextMultiLanguage(text,language,"FileName");
	
	}
}
```


