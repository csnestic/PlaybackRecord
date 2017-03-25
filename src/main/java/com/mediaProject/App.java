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
		Info[] mixerInfo = AudioSystem.getMixerInfo();
		List<Info> playbackInfo = new ArrayList<Info>();
		List<Info> recordInfo = new ArrayList<Info>();
		for(int i = 0; i < mixerInfo.length; i++) {
			if(mixerInfo[i].getDescription().contains("Playback")){
				playbackInfo.add(mixerInfo[i]);
			} else if(mixerInfo[i].getDescription().contains("Capture")){
				recordInfo.add(mixerInfo[i]);
			}
			//System.out.println(mixerInfo[i].getDescription() + " | " + mixerInfo[i].getName());
		}

		//		for(int i = 0; i < playbackInfo.size(); i++) {
		//			System.out.println(playbackInfo.get(i).getDescription() + " | " + playbackInfo.get(i).getName());
		//		}
		//		for(int i = 0; i < recordInfo.size(); i++) {
		//			System.out.println(recordInfo.get(i).getDescription() + " | " + recordInfo.get(i).getName());
		//		}
		String fileName = "";
		if (args.length == 0){
			System.out.println("Please specify an operation.");
		} else if (args.length == 1) {
			if(args[0].equals("play")){
				System.out.println("Please specify a filename you wish to playback.");
			} else if (args[0].equals("record")) {
				Record.record(fileName);
			} else {
				System.out.println("Unknown Operation.");
			}
		} else if (args.length == 2) {
			if (args[0].equals("record")) {
				Record.record(args[1]);
			} else if (args[0].equals("play")) {
				Playback.playback(args[1], null);
			} else {
				System.out.println("Unknown Operation.");
			}
		} else if (args.length == 3) {
			if (args[0].equals("record")) {
				int record = Integer.parseInt(args[2]);
				if (record > recordInfo.size() || record < 0) {
					System.out.println("The requested recording device does not exist.");
				} else {
					Record.record(args[1]);
				}
			}
			if (args[0].equals("play")){
				int playback = Integer.parseInt(args[2]);
				if (playback > playbackInfo.size() || playback  < 0) {
					System.out.println("The requested playback device does not exist.");
				} else {
					Playback.playback(args[1], playbackInfo.get(playback));
				}
			}

		} else {
			System.out.println("Unknown Operation.");
		}
	}




}
