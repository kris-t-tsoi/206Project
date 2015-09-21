package guiComponents;

import javax.swing.JButton;

public class PlayTextButton extends JButton{
	
	public PlayTextButton() {
		super("Play Text");
		setToolTipText("Listen to the text");
	}
	
	/**
	 * Uses festival to speak the input text by creating a bash process
	 * 
	 * @param text
	 */
	public void sayWithFestival(String text) {
		String cmd = "echo " + text + " | festival --tts&";
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		try {
			builder.start();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
}
