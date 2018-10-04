package com.github.kilianB.apis.googleTextToSpeech.examples;


import java.io.File;

import com.github.kilianB.apis.googleTextToSpeech.GoogleTextToSpeechAdapter;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SimpleMP3Player extends GoogleTextToSpeechAdapter {

	private MediaPlayer player;
	private float volume = 0.5f;
	ReadOnlyBooleanWrapper isPlaying = new ReadOnlyBooleanWrapper(false);

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
		isPlaying.set(false);
	}

	/**
	 * Volume of the lore player between 0 and 1
	 * 
	 * @param volume the volume
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
				isPlaying.set(false);
				System.exit(0);
			}
		});
		player.play();
		isPlaying.set(true);
	}

	/**
	 * @return true if we currently play back a file
	 */
	public boolean isPlaying() {
		return isPlaying.get();
	}
	
	public ReadOnlyBooleanProperty isPlayingProperty() {
		return isPlaying.getReadOnlyProperty();
	}

	// Google text to speech observer
	@Override
	public void mergeCompleted(File f,int id) {
		startPlayback(f);
	}

}
