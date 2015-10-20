package textToMP3Frame.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import textToMP3Frame.TextToSpeechFrame;
import doInBackground.BackgroundUse;
import doInBackground.UseTerminalCommands;
import doInBackground.WriteSchemeFiles;
import main.MediaPlayerJFrame;
import mainFrameGUI.AbstractMP3Creator;
import mainFrameGUI.ReplaceTextLabel;

@SuppressWarnings("serial")
public class CreateMP3Btn extends JButton {

	private TextToSpeechFrame parentFrame;

	public CreateMP3Btn(TextToSpeechFrame frame) {
		super();
		parentFrame = frame;
		setText("Create MP3");
		setToolTipText("Create MP3 File From Text");
	}

	public void createAudio(float speed, int startPitch, int endPitch,
			String text, String audioName) {
		// create scm file
		WriteSchemeFiles write = new WriteSchemeFiles(parentFrame);
		File playScm = write.createMP3(speed, startPitch, endPitch, text,
				audioName);
		
		String mp3Path = MediaPlayerJFrame.MP3_DIR_RELATIVE_PATH+ File.separator +"";

		// MP3 created in the background
		// String cmd = ("festival -b "+playScm.getAbsolutePath().toString());
		BackgroundUse backGrd = new BackgroundUse("festival -b "+ playScm.getAbsolutePath().toString() + ";"
				+ "ffmpeg -y -i \"" + mp3Path + audioName + ".wav\" -f mp3 \""+ mp3Path+ audioName + ".mp3\";" 
				+ "rm \"" + mp3Path+audioName + ".wav\"");
		backGrd.execute();
		
		// TODO get pid so can stop
	}

}
