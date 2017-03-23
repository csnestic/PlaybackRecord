package com.mediaProject;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Playback {
	/**
	 * Find the file specified and play it back.
	 *
	 * @param fileName
	 */
	public static void playback(String fileName) {
		// Get file and convert it to the appropriate clip format.
		// This works for the audio clips recorded from this same application.
		File file = new File(fileName);
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			clip.open(audioInputStream);
			// Ensures one playthrough of the clip before terminating.
			clip.start();
			while(!clip.isRunning()){
				Thread.sleep(10);
			}
			while(clip.isRunning()){
				Thread.sleep(10);
			}
			clip.close();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
