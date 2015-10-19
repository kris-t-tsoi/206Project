package textToMP3Frame.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import terminal.UseTerminalCommands;

public class PlayTextBtn extends JButton {

	public PlayTextBtn() {
		super();
		setText("Play Text");
		setToolTipText("Play Text From Textbox");
	}

	/**
	 * Uses festival to speak the input text by creating a bash process
	 * @param text
	 */
	public void sayWithFestival(String text) {
		UseTerminalCommands term = new UseTerminalCommands();
		term.terminalCommandVoid("echo " + text + " | festival --tts&");
		//TODO get pid so can stop
		
		

		//TODO change once add pitch and speed
		
	}
	
}
