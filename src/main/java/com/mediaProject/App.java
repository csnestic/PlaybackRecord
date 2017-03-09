package com.mediaProject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * Simple application that takes arguments from command line to
 * play or record specified WAV files.
 *
 */
public class App {
	// Static variables for testing recording
	// Single channel 8kHz
	// TODO: try to improve quality
	private static float sampleRate = 8000;
	private static int sampleSize = 8;
	private static int channels = 1;
	private static boolean signed = true;
	private static boolean bigEndian = true;
	static ByteArrayOutputStream out;

	/**
	 * Main method for running record or playback functionality.
	 * If the arguments are sufficient the program will perform the specified task.
	 * If there are arguments missing the program will return a message stating
	 * something was missing and close execution.
	 *
	 * @param args
	 */
	public static void main( String[] args ) {
		String fileName = "";
		if (args.length == 0){
			System.out.println("Please specify an operation.");
		} else if (args.length == 1) {
			if(args[0].equals("play")){
				System.out.println("Please specify a filename you wish to playback.");
			} else if (args[0].equals("record")) {
				record(fileName);
			}
		} else if (args.length == 2) {
			if (args[0].equals("record")) {
				record(args[1]);
			} else if (args[0].equals("play")) {
				// playback(fileName);
			}
		}
		// Uncomment when testing functionality in eclipse.
		//record(fileName);
	}

	/**
	 * Takes the recorded audio and saves it to disk in wav format.
	 * File will be called recording.wav if no filename was specified on execution.
	 *
	 * @param fileName
	 * @param recordedAudio
	 * @param soundFormat
	 */
	private static void saveCapture(String fileName, byte[] recordedAudio, AudioFormat soundFormat) {
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
				out.toByteArray().length);
		try {
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
					new File(fName + ".wav"));
		} catch (IOException e) {
			System.err.println("I/O SAVE EXCEPTION" + e);
			System.exit(-2);
		}

	}

	/**
	 * Record a 10 second .wav file.
	 * Filename will be recording.wav if no filename was specified.
	 *
	 * @param fileName
	 */
	public static void record(String fileName) {
		final AudioFormat soundFormat = new AudioFormat(sampleRate, sampleSize,
				channels, signed, bigEndian);
		// Create the line to begin recording and try/catch for errors.
		DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, soundFormat);
		try {
			final TargetDataLine line = (TargetDataLine)AudioSystem.getLine(dataInfo);
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
						// TODO: change to an escape or shutdown command.
						long t = System.currentTimeMillis();
						long timer = t+10000;
						System.out.println("Recording (10 seconds)");
						while (System.currentTimeMillis() < timer) {
							int count = line.read(buffer, 0, buffer.length);
							if (count > 0){
								out.write(buffer, 0, count);
							}
						}
						System.out.println("Recording Complete!");
						out.close();
						byte[] tempIs = out.toByteArray();
						// Send the byte array to the save method.
						saveCapture(fileName, tempIs, soundFormat);

					}  catch (IOException f) {
						System.err.println("I/O CAPTURE EXCEPTION" + f);
						System.exit(-2);
					}
				}
			};
			Thread recordingThread = new Thread(runner);
			recordingThread.start();

		} catch (LineUnavailableException e) {
			System.err.println("LINE CAPTURE EXCEPTION" + e);
			System.exit(-1);
		}

	}
}
