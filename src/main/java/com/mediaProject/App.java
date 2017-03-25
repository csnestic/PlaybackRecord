package com.mediaProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer.Info;
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

		// Sort devices by playback and capture types.
		// TODO: fix VM to test again in linux.
		for(int i = 0; i < mixerInfo.length; i++) {
			if(mixerInfo[i].getDescription().contains("Playback")){
				playbackInfo.add(mixerInfo[i]);
			} else if(mixerInfo[i].getDescription().contains("Capture")){
				recordInfo.add(mixerInfo[i]);
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
				Record.record(fileName, recordInfo.get(0));
			} else {
				System.out.println("Unknown Operation.");
			}
		} else if (args.length == 2) {
			// Two arguments, record or play using default audio devices.
			if (args[0].equals("record")) {
				Record.record(args[1], recordInfo.get(0));
			} else if (args[0].equals("play")) {
				Playback.playback(args[1], null);
			} else {
				System.out.println("Unknown Operation.");
			}
		} else if (args.length == 3) {
			// Three arguments, check for record or play commands
			// and check that the specified device exists.
			if (args[0].equals("record")) {
				int record = Integer.parseInt(args[2]);
				if (record > recordInfo.size() || record < 0) {
					System.out.println("The requested recording device does not exist.");
				} else {
					Record.record(args[1], recordInfo.get(record));
				}
			} else if (args[0].equals("play")){
				int playback = Integer.parseInt(args[2]);
				if (playback > playbackInfo.size() || playback  < 0) {
					System.out.println("The requested playback device does not exist.");
				} else {
					Playback.playback(args[1], playbackInfo.get(playback));
				}
			} else {
				System.out.println("Unknown Operation.");
			}
		} else {
			System.out.println("Unknown Operation.");
		}
	}




}
