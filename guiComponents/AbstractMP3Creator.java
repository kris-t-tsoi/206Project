package guiComponents;

import java.io.File;
import java.io.IOException;

import javax.swing.JButton;

import main.MediaPlayerJFrame;

public abstract class AbstractMP3Creator extends JButton {
	
	public AbstractMP3Creator() {
	}
	/**
	 * Function to create an mp3 from a string of text by: 1. creating a wav
	 * file using text2wave 2. creating an mp3 from the wav file using ffmpeg 3.
	 * removing the wav file NB: this overwrites an existing mp3 with the same
	 * name
	 * 
	 * @param outputName
	 *            - the input string
	 * 
	 */
	public void createMP3(String textToSynth, String outputName) {
		useTerminalCommand("echo " + textToSynth + "|text2wave -o " + outputName + ".wav;" + "ffmpeg -y -i " + outputName
				+ ".wav -f mp3 " + MediaPlayerJFrame.MP3_DIR_RELATIVE_PATH + File.separator + outputName + ".mp3;" + "rm " + outputName + ".wav");
	}
	
	/**
	 * Executes a given terminal command as-is, where we don't do anything with
	 * different return values. This function waits for the process to finish,
	 * so can freeze the GUI if a swingworker is not used.
	 * 
	 * @param cmd
	 */
	protected void useTerminalCommand(String cmd) {
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		Process process;
		try {
			process = builder.start();
			process.waitFor();
		} catch (IOException | SecurityException | IllegalArgumentException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
