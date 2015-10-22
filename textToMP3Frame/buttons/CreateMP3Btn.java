package textToMP3Frame.buttons;

import java.io.File;

import javax.swing.JButton;

import textToMP3Frame.TextToSpeechFrame;
import doInBackground.BackgroundUse;
import doInBackground.WriteSchemeFiles;
import mediaMainFrame.MediaPlayerJFrame;

@SuppressWarnings("serial")
public class CreateMP3Btn extends JButton {

	private TextToSpeechFrame parentFrame;

	public CreateMP3Btn(TextToSpeechFrame frame) {
		super();
		parentFrame = frame;
		setText("Create MP3");
		setToolTipText("Create MP3 File From Text");
	}

	public String createAudio(float speed, int startPitch, int endPitch,
			String text, String audioName) {
		// create scm file
		WriteSchemeFiles write = new WriteSchemeFiles(parentFrame);
		File playScm = write.createMP3(speed, startPitch, endPitch, text,
				audioName);
		
		File textFile = write.createTxt(text, audioName);
		
		String mp3Path = MediaPlayerJFrame.MP3_DIR_RELATIVE_PATH+ File.separator +"";

		// MP3 created in the background
		BackgroundUse backGrd = new BackgroundUse("text2wave -o \""+mp3Path +audioName+".wav\" "
				+textFile.getAbsolutePath().toString()+" -eval "+playScm.getAbsolutePath().toString()+ ";"
				+ "ffmpeg -y -i \"" + mp3Path + audioName + ".wav\" -f mp3 \""+ mp3Path+ audioName + ".mp3\";" 
				+ "rm \"" + mp3Path+audioName + ".wav\"");
	
		backGrd.execute();
		
		//TODO Progress bar
		
		
		// TODO get pid so can stop
		
		return mp3Path+ audioName + ".mp3";
		
	}

}
