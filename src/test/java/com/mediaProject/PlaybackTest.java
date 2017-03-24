package com.mediaProject;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PlaybackTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setup() { }

	@After
	public void tearDown() { }

	/**
	 * Testing playback method with empty file. IOException should not occur.
	 *
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 */
	@Test
	public void testPlaybackSuccess() throws IOException, UnsupportedAudioFileException,
	LineUnavailableException, InterruptedException {
		final File temp = temporaryFolder.newFile("testfile.wav");
		byte[] buf = new byte[10];
		AudioFormat soundFormat = new AudioFormat(Constants.sampleRate, Constants.sampleSize,
				Constants.channels, Constants.signed, Constants.bigEndian);

		AudioInputStream audioInputStream = new AudioInputStream(
				new ByteArrayInputStream(buf), soundFormat ,
				buf.length);
		AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, temp);
		Playback.playback(temp.getPath());
		// Playback success
		assertTrue(true);
	}

	/**
	 * Testing playback method with a file that does not support audio.
	 * (I.E. user inputs the path to a text file instead of a supported audio file)
	 *
	 * @throws InterruptedException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	@Test(expected = UnsupportedAudioFileException.class)
	public void testPlaybackFileTypeFail() throws UnsupportedAudioFileException,
	LineUnavailableException, InterruptedException {
		File temp;
		try {
			temp = temporaryFolder.newFile("testfile.txt");

			PrintWriter writer = new PrintWriter(temp, "UTF-8");
			writer.println("testtesttest");
			writer.close();
			Playback.playback(temp.getPath());
			assertTrue(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
