# Google-Text-To-Speech-Java-API
Convert long strings of text into .mp3 files in real time utilizing googles translator text to speech service. Supports multi-language- requests and multi-threading resulting in usually responds times of < .75 seconds. 

Lightweight, no dependencies.

## Example usage 

```java
public class TextToSpeechSample{
    
    public static void main(String[] args) {
        
        // 1. Simple TTS Request
        	
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
        
        
        
        // 2. Asnch non blocking TTS
        
        //Non blocking method. Call event handler in the observer upon finishing
        boolean deleteTemporaryFiles = true;
        
        GoogleTextToSpeechObserver callback = new GoogleTextToSpeechAdapter() {
        	@Override
        	public void mergeCompleted(File f,int id) {
        		System.out.println("File available for playback: " + f.getAbsolutePath());
        	}
        };
        
        tts.convertTextAsynch(text,GLanguage.German, "FileNameAsynch",deleteTemporaryFiles,callback);
        
        
        
        // 3. Multi language query
         
        //Request an mp3 file with multiple different pronounciations
        String textMult[] = new String[]{
            "The author",
            "Immanuel Kant",
            "argued that the human mind creates the structure of human experience, that reason is the source of morality."
        };
        
        GLanguage[] language = new GLanguage[] {
                GLanguage.English_GB,
                GLanguage.German,
                GLanguage.English_GB
        };
        
        convertedTextMP3 = tts.convertTextMultiLanguage(textMult,language,"FileNameMulti");
    }
}
```


