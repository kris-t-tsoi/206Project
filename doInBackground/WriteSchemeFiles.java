package doInBackground;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mediaMainFrame.MediaPlayerJFrame;

public class WriteSchemeFiles {
	private JFrame parentFrame;
	
	public WriteSchemeFiles(JFrame frame) {
		parentFrame = frame;
	}

	/**
	 * Write scheme file for playing text
	 * @param speed - speed of voice
	 * @param startPitch - starting pitch of voice
	 * @param endPitch - ending pitch of voice
	 * @param text - text user wishes to turn into text
	 * @return
	 */
	public File sayText(float speed, int startPitch, int endPitch, String text) {
		File playScm = new File(System.getProperty("user.dir")+ File.separator +".PlayText.scm");

		FileWriter file;
		try {
			file = new FileWriter(playScm, false);
			BufferedWriter buffwrite = new BufferedWriter(file);
			writeSpeedPitch(buffwrite, speed, startPitch, endPitch);
			buffwrite.write("(SayText \""+text+"\")");
			buffwrite.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parentFrame, "Sorry, Text Could Not Be Played");
			e.printStackTrace();
		}
	return playScm;
		//TODO get pid so can stop
	}
	
	/**
	 * Write scheme file for creating mp3
	 * @param speed - speed of voice
	 * @param startPitch - starting pitch of voice
	 * @param endPitch - ending pitch of voice
	 * @return
	 */
	public File createMP3(float speed, int startPitch, int endPitch){
		File scm = new File(System.getProperty("user.dir")+ File.separator +".CreateMP3.scm");

		FileWriter file;
		try {
			file = new FileWriter(scm, false);
			BufferedWriter buffwrite = new BufferedWriter(file);
			writeSpeedPitch(buffwrite, speed, startPitch, endPitch);
			buffwrite.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parentFrame, "Sorry, MP3 Could Not Be Created");
			e.printStackTrace();
		}
	return scm;
	}
	
	/**
	 * Create a text file for text to be turned into speech
	 * @param text
	 * @return textfile that is created
	 */
	public File createTxt(String text){
		File txtFile = new File(System.getProperty("user.dir")+ File.separator +".toSpeech.txt");
		FileWriter file;
		try {
			file = new FileWriter(txtFile, false);
			BufferedWriter buffwrite = new BufferedWriter(file);
			buffwrite.write(text);
			buffwrite.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parentFrame, "Sorry, MP3 Could Not Be Created");
			e.printStackTrace();
		}
		return txtFile;
	}
	
	
	/**
	 * Write the speed and pitch of text in file
	 * @param buffwrite - current writing stream
	 * @param speed - speed of voice
	 * @param startPitch - starting pitch of voice
	 * @param endPitch - ending pitch of voice
	 */
	private void writeSpeedPitch(BufferedWriter buffwrite, float speed, int startPitch, int endPitch){
		
		try {
			buffwrite.write("(Parameter.set 'Duration_Stretch "+speed+")");
			buffwrite.write("(set! duffint_params '((start "+startPitch+") (end "+endPitch+")))");
			buffwrite.write("(Parameter.set 'Int_Method 'DuffInt)");
			buffwrite.write("(Parameter.set 'Int_Target_Method Int_Targets_Default)");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
