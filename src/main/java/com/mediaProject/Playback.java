package com.mediaProject;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Playback {
	/**
	 * Find the file specified and play it back.
	 *
	 * @param fileName
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws InterruptedException
	 */
	public static void playback(String fileName, Info mixerInfo) throws UnsupportedAudioFileException,
	IOException, LineUnavailableException, InterruptedException {
		// Get file and convert it to the appropriate clip format.
		// This works for the audio clips recorded from this same application.
		File file = new File(fileName);
		try {
			Clip clip = AudioSystem.getClip(mixerInfo);
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
			throw new LineUnavailableException();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new UnsupportedAudioFileException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new InterruptedException();
		}
	}
}
