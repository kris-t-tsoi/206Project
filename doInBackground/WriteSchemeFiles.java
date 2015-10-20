package doInBackground;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WriteSchemeFiles {
	private JFrame parentFrame;
	
	public WriteSchemeFiles(JFrame frame) {
		parentFrame = frame;
	}

	/**
	 * Write scm file for playing text
	 */
	public File sayText(float speed, int startPitch, int endPitch, String text) {
		File playScm = new File(System.getProperty("user.dir")+ File.separator +"PlayText.scm");

		FileWriter file;
		try {
			file = new FileWriter(playScm, false);
			BufferedWriter buffwrite = new BufferedWriter(file);
			writeSpeedPitch(buffwrite, speed, startPitch, endPitch, text);
			buffwrite.write("(SayText \""+text+"\")");
			buffwrite.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parentFrame, "Sorry, Text Could Not be Played");
			e.printStackTrace();
		}
	return playScm;
		//TODO get pid so can stop
	}
	
	
	public File createMP3(float speed, int startPitch, int endPitch, String text){
		File playScm = new File(System.getProperty("user.dir")+ File.separator +".PlayText.scm");

		FileWriter file;
		try {
			file = new FileWriter(playScm, false);
			BufferedWriter buffwrite = new BufferedWriter(file);
			writeSpeedPitch(buffwrite, speed, startPitch, endPitch, text);
			buffwrite.write("(SayText \""+text+"\")");
			buffwrite.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parentFrame, "Sorry, Text Could Not be Played");
			e.printStackTrace();
		}
	return playScm;
	}
	
	/**
	 * Write the speed and pitch of text in file
	 * @param buffwrite
	 * @param speed
	 * @param startPitch
	 * @param endPitch
	 * @param text
	 */
	private void writeSpeedPitch(BufferedWriter buffwrite, float speed, int startPitch, int endPitch, String text){
		
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
