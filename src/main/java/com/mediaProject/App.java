package com.mediaProject;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
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
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 */
	public static void main( String[] args ) throws IOException, UnsupportedAudioFileException,
	LineUnavailableException, InterruptedException {
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
				Playback.playback(args[1]);
			} else {
				System.out.println("Unknown Operation.");
			}
		}	else {
			System.out.println("Unknown Operation.");
		}
	}




}
