package com.mediaProject;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.sound.sampled.AudioFormat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RecordTest {
	/**
	 * Temporary folder to test file creation.
	 */
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	/**
	 * Testing Save functionality (saving the byte array).
	 */
	@Test
	public void saveCaptureTest() {
		AudioFormat soundFormat = new AudioFormat(Constants.sampleRate, Constants.sampleSize,
				Constants.channels, Constants.signed, Constants.bigEndian);
		byte[] buf = new byte[10];
		Record.saveCapture(temporaryFolder.getRoot() + "\\junittest", buf, soundFormat);
		File file = new File(temporaryFolder.getRoot() + "\\junittest.wav");
		assertTrue(file.exists());
	}

}
