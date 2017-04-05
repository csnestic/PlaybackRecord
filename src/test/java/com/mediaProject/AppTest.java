package com.mediaProject;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Output stream for testing console outputs.
	 */
	private final ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();

	/**
	 * Setting up outputs for reading and junit tests.
	 */
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(consoleOut));
	}

	/**
	 * Clearing the output stream.
	 */
	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}

	/**
	 * Test system console output for 0 arguments.
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void testEmptyInput() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = new String[0];
		App.main(test);
		assertEquals("Please specify an operation.", consoleOut.toString().trim());
	}

	/**
	 * Test system console output for one play argument.
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void testPlaySingleInput() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"play"};
		App.main(test);
		assertEquals("Please specify a filename you wish to playback.", consoleOut.toString().trim());
	}

	/**
	 * Test system console output for one unknown argument.
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void testUnknownSingleInput() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"dfsdf"};
		App.main(test);
		assertEquals("Unknown Operation. [1 argument]", consoleOut.toString().trim());
	}

	/**
	 * Test system console output for 5 unknown arguments.
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void testUnknownFiveInput() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"dfsdf", "ok", "this", "error", "yep"};
		App.main(test);
		assertEquals("Unknown Operation.", consoleOut.toString().trim());
	}

	/**
	 * Test system console output for four invalid arugments.
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsuppoBrtedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void testFourInvalidInput() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"play", "test", "oops", "false"};
		App.main(test);
		assertEquals("Unknown Operation. [4 arguments]", consoleOut.toString().trim());
	}

	/**
	 * Test system console output for three invalid arguments.
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsuppoBrtedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void testThreeInvalidInput() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"erere", "test", "3"};
		App.main(test);
		assertEquals("Unknown Operation. [3 arguments]", consoleOut.toString().trim());
	}


	/**
	 * Test system console output for two invalid arguments.
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsuppoBrtedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void testTwoInvalidInput() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"podcast", "test"};
		App.main(test);
		assertEquals("Unknown Operation. [2 arguments]", consoleOut.toString().trim());
	}


	/*	@Test
	public void recordTest() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"record", "test", "0", "1"};
		App.main(test);
	}*/

	/**
	 * Test recording 4 argument device invalid .
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsuppoBrtedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void record4ArgNoDeviceTest() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"record", "test", "1550", "1"};
		App.main(test);
		assertEquals("The requested recording device does not exist.", consoleOut.toString().trim());
	}

	/**
	 * Test recording 3 argument device invalid .
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsuppoBrtedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void record3ArgNoDeviceTest() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"record", "test", "1550"};
		App.main(test);
		assertEquals("The requested recording device does not exist.", consoleOut.toString().trim());
	}

	/**
	 * Test playback 3 argument device invalid .
	 *
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsuppoBrtedAudioFileException
	 * @throws IOException
	 */
	@Test
	public void play3ArgNoDeviceTest() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		final String[] test = {"play", "test.wav", "1550"};
		App.main(test);
		assertEquals("The requested playback device does not exist.", consoleOut.toString().trim());
	}
}
