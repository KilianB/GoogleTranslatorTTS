package com.github.kilianB.apis.googleTextToSpeech.examples;

import java.io.File;

import com.github.kilianB.apis.googleTextToSpeech.GLanguage;
import com.github.kilianB.apis.googleTextToSpeech.GoogleTextToSpeech;

/**
 * Basic example demonstrating conversion of a text with multiple different languages 
 * 
 * @author Kilian
 *
 */
public class TextToSpeechMultiLanguage {
	public static void main(String[] args) {

		// Path to output mp3 directory
		String outputPath = "mpFiles/";

		// Create directory
		File outputDirectory = new File(outputPath);
		outputDirectory.mkdirs();

		// Convert the text and retrieve an mp3 file
		var tts = new GoogleTextToSpeech(outputPath);

		// Request an mp3 file with multiple different pronounciations
		String text[] = new String[] { "The author", "Immanuel Kant",
				"argued that the human mind creates the structure of human experience, that reason is the source of morality." };

		GLanguage[] language = new GLanguage[] { GLanguage.English_GB, GLanguage.German, GLanguage.English_GB };

		// Asynch is available as well
		File convertedTextMP3 = tts.convertTextMultiLanguage(text, language, "FileName");

	}
}
