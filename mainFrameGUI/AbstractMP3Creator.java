package mainFrameGUI;

import java.io.File;
import java.io.IOException;
import javax.swing.JMenuItem;
import main.MediaPlayerJFrame;

/**
 * Abstract subclass of JMenuItem which has the functionality to use a bash terminal command
 * as well as create an mp3 file given input text and an output file name.
 * 
 * SaveTextLabel is the only concrete subclass that extends this class.
 * AbstractMediaLabel extends this but it is an abstract class.
 *
 */
public abstract class AbstractMP3Creator extends JMenuItem {
	
	public AbstractMP3Creator() {
		super();
	}
	
	/**
	 * Function to create an mp3 from a string of text by: 
	 * 1. creating a wav file using text2wave 
	 * 2. creating an mp3 from the wav file using ffmpeg 
	 * 3. removing the wav file NB: this overwrites an existing mp3 with the same name
	 * 
	 * @param textToSynth - the input string
	 * @param outputName - name of the output file
	 */
	public void createMP3(String textToSynth, String outputName) {
		useTerminalCommand("echo " + textToSynth + "|text2wave -o \"" + outputName + ".wav\";" 
		+ "ffmpeg -y -i \"" + outputName + ".wav\" -f mp3 \"" + MediaPlayerJFrame.MP3_DIR_RELATIVE_PATH+ File.separator + outputName + ".mp3\";" 
		+ "rm \"" + outputName + ".wav\"");
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
