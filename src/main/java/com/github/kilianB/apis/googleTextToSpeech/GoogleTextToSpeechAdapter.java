package com.github.kilianB.apis.googleTextToSpeech;

import java.io.File;

/**
 * @author Kilian
 *
 */
public class GoogleTextToSpeechAdapter implements GoogleTextToSpeechObserver {

	@Override
	public void firstFileDownloaded(File f, int id) {}
	
	@Override
	public void fileDownloaded(File f, int id) {}
	
	@Override
	public void fileDownloadCompleted(int id) {}

	@Override
	public void mergeCompleted(File f,int id) {}
}
