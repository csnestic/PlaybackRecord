package com.mediaProject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class Record {
	static ByteArrayOutputStream out;

	/**
	 * Takes the recorded audio and saves it to disk in wav format.
	 * File will be called recording.wav if no filename was specified on execution.
	 *
	 * @param fileName
	 * @param recordedAudio
	 * @param soundFormat
	 */
	public static void saveCapture(String fileName, byte[] recordedAudio, AudioFormat soundFormat) {
		String fName = "";
		if (fileName.equals("")) {
			fName = "recording";
		} else {
			fName = fileName;
		}
		// Convert to AudioInputStream to allow for file writing and save.
		// File will be located in the directory the program was run in.
		AudioInputStream audioInputStream = new AudioInputStream(
				new ByteArrayInputStream(recordedAudio), soundFormat,
				recordedAudio.length);
		try {
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
					new File(fName + ".wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Record a 10 second .wav file.
	 * Filename will be recording.wav if no filename was specified.
	 *
	 * @param fileName
	 */
	public static void record(String fileName, Mixer.Info mixerInfo) {
		final AudioFormat soundFormat = new AudioFormat(Constants.sampleRate, Constants.sampleSize,
				Constants.channels, Constants.signed, Constants.bigEndian);
		// Create the line to begin recording and try/catch for errors.
		Mixer selectedMixer = AudioSystem.getMixer(mixerInfo);
		Line.Info dataInfo = new Line.Info(TargetDataLine.class);
		if (selectedMixer.isLineSupported(dataInfo)) {
			try {
				final TargetDataLine line = (TargetDataLine) selectedMixer.getLine(dataInfo);
				line.open(soundFormat);
				line.start();
				// The audio is recorded into a byte array output stream.
				Runnable runner = new Runnable() {
					int bufferSize = (int)soundFormat.getSampleRate()
							* soundFormat.getFrameSize();
					byte buffer[] = new byte[bufferSize];

					@Override
					public void run() {
						out = new ByteArrayOutputStream();
						try {
							// 10 Second timer for recording.
							long t = System.currentTimeMillis();
							long timer = t+10000;
							System.out.println("Recording (10 seconds)");
							while (System.currentTimeMillis() < timer) {
								int counter = line.read(buffer, 0, buffer.length);
								if (counter > 0){
									out.write(buffer, 0, counter);
								}
							}
							System.out.println("Recording Complete!");
							out.close();
							byte[] tempIs = out.toByteArray();
							// Send the byte array to the save method.
							saveCapture(fileName, tempIs, soundFormat);

						}  catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				Thread recordingThread = new Thread(runner);
				recordingThread.start();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
}
