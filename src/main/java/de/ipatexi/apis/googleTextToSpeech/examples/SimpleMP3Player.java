package de.ipatexi.apis.googleTextToSpeech.examples;


import java.io.File;

import de.ipatexi.apis.googleTextToSpeech.GoogleTextToSpeechAdapter;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SimpleMP3Player extends GoogleTextToSpeechAdapter {

	private MediaPlayer player;
	private float volume = 0.5f;
	boolean isPlaying = false;

	public SimpleMP3Player() {

		Platform.startup(() -> {
		});

		// By calling the player we keep the fx application thread receiving updates
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(10);
					if (player != null)
						player.getStatus();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void stop() {
		if (player != null) {
			player.stop();
		}
		isPlaying = false;
	}

	/**
	 * Volume of the lore player between 0 and 1
	 * 
	 * @param volume
	 */
	public void setVolume(float volume) {
		this.volume = volume;
		if (player != null)
			player.setVolume(volume);
	}

	/**
	 * Recursive play back of all files added to the playlist
	 */
	private void startPlayback(File mediaFile) {
		// See if we got files to work with.
		player = new MediaPlayer(new Media(mediaFile.toURI().toString()));
		player.setVolume(volume);
		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				isPlaying = false;
				System.exit(0);
			}
		});
		player.play();
	}

	/**
	 * @return true if we currently play back a file
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	// Google text to speech observer
	@Override
	public void mergeCompleted(int id, File f) {
		startPlayback(f);
	}

}
