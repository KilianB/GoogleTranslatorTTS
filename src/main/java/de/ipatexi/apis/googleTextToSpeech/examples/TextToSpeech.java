package de.ipatexi.apis.googleTextToSpeech.examples;

import java.io.File;

import de.ipatexi.apis.googleTextToSpeech.GLanguage;
import de.ipatexi.apis.googleTextToSpeech.GoogleTextToSpeech;

/**
 * @author Kilian
 *
 */
public class TextToSpeech {

	public static void main(String[] args) {

		// Path to output mp3 directory
		String outputPath = "mpFiles/";

		// Text to convert to mp3
		String text = "When in the Course of human events it becomes necessary for one people to dissolve the political bands which have connected them with another and to assume among the powers of the earth, the separate and equal station to which the Laws of Nature and of Nature's God entitle them, a decent respect to the opinions of mankind requires that they should declare the causes which impel them to the separation.";

		// Create directory
		File outputDirectory = new File(outputPath);
		outputDirectory.mkdirs();

		// Convert the text and retrieve an mp3 file
		GoogleTextToSpeech tts = new GoogleTextToSpeech(outputPath);
		File convertedTextMP3 = tts.convertText(text, GLanguage.English_US, "FileName");


		System.out.println("Path: " + convertedTextMP3.getAbsolutePath());
		System.out.println("Bytes: " + convertedTextMP3.length());
	}

}
