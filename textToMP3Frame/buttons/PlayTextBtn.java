package textToMP3Frame.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import doInBackground.BackgroundUse;
import doInBackground.UseTerminalCommands;
import doInBackground.WriteSchemeFiles;
import textToMP3Frame.TextToSpeechFrame;

public class PlayTextBtn extends JButton {
	TextToSpeechFrame parentFrame;

	public PlayTextBtn(TextToSpeechFrame frame) {
		super();
		parentFrame = frame;
		setText("Play Text");
		setToolTipText("Play Text From Textbox");
	}

	/**
	 * Uses festival to speak the input text by creating a bash process
	 * @param text
	 */
	public void sayWithFestival(float speed, int startPitch, int endPitch, String text) {
		WriteSchemeFiles write = new WriteSchemeFiles(parentFrame);
		File playScm = write.sayText(speed, startPitch, endPitch, text);
		
		//Festival to speak in the background
		String cmd = ("festival -b "+playScm.getAbsolutePath().toString());		
		BackgroundUse backGrd = new BackgroundUse(cmd);
		backGrd.execute();
		//TODO get pid so can stop
	}
	
}
