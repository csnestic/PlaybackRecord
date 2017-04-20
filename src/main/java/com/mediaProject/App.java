package com.mediaProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Simple application that takes arguments from command line to
 * play or record specified WAV files.
 *
 */
public class App {

	/**
	 * Main method for running record or playback functionality.
	 * If the arguments are sufficient the program will perform the specified task.
	 * If there are arguments missing the program will return a message stating
	 * something was missing and close execution.
	 *
	 * @param args
	 *
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws NumberFormatException
	 */
	public static void main( String[] args ) throws IOException, UnsupportedAudioFileException,
	LineUnavailableException, InterruptedException, NumberFormatException {
		// Get all audio devices.
		Info[] mixerInfo = AudioSystem.getMixerInfo();
		List<Info> playbackInfo = new ArrayList<Info>();
		List<Info> recordInfo = new ArrayList<Info>();

		Line.Info targetDataLineInfo = new Line.Info(TargetDataLine.class);
		// Sort devices by playback and capture types.
		for(int i = 0; i < mixerInfo.length; i++) {
			Mixer currentMixer = AudioSystem.getMixer(mixerInfo[i]);
			if(currentMixer.isLineSupported(targetDataLineInfo)) {
				recordInfo.add(mixerInfo[i]);
			} else {
				playbackInfo.add(mixerInfo[i]);
			}
		}

		String fileName = "";
		if (args.length == 0){
			// Empty call.
			System.out.println("Please specify an operation.");
		} else if (args.length == 1) {
			// One argument, record should be the only one that works.
			if(args[0].equals("play")){
				System.out.println("Please specify a filename you wish to playback.");
			} else if (args[0].equals("record")) {
				// One argument record uses default filename and default recording device.
				Record.record(fileName, 10, recordInfo.get(0));
			} else {
				System.out.println("Unknown Operation. [1 argument]");
			}
		} else if (args.length == 2) {
			// Two arguments, record or play using default audio devices.
			if (args[0].equals("record")) {
				Record.record(args[1], 10, recordInfo.get(0));
			} else if (args[0].equals("play")) {
				Playback.playback(args[1], null);
			} else {
				System.out.println("Unknown Operation. [2 arguments]");
			}
		} else if (args.length == 3) {
			// Three arguments, check for record or play commands
			// and check that the specified device exists.
			if (args[0].equals("record")) {
				int recordingLength = Integer.parseInt(args[2]);
				if (recordingLength <= 0) {
					System.out.println("The recording length must be greater than 0");
				} else {
					Record.record(args[1], recordingLength, recordInfo.get(0));
				}
			} else if (args[0].equals("play")){
				int playback = Integer.parseInt(args[2]);
				if (playback > playbackInfo.size() || playback  < 0) {
					System.out.println("The requested playback device does not exist.");
				} else {
					Playback.playback(args[1], playbackInfo.get(playback));
				}
			} else {
				System.out.println("Unknown Operation. [3 arguments]");
			}
		} else if (args.length == 4) {
			// Four Arguments for specifying recording length in seconds + recording device.
			if(args[0].equals("record")){
				int record = Integer.parseInt(args[3]);
				int recordingLength = Integer.parseInt(args[2]);
				if (record > recordInfo.size() || record < 0) {
					System.out.println("The requested recording device does not exist.");
				} else if (recordingLength <= 0) {
					System.out.println("The recording length must be greater than 0");
				} else {
					Record.record(args[1], recordingLength, recordInfo.get(record));
				}
			} else {
				System.out.println("Unknown Operation. [4 arguments]");
			}

		} else {
			System.out.println("Unknown Operation.");
		}
	}




}
