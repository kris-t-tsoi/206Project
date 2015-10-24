package textToMP3Frame.buttons;

import java.io.File;

import javax.swing.JButton;

import textToMP3Frame.TextToSpeechFrame;
import doInBackground.BackgroundUse;
import doInBackground.CreateInBackground;
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

	public void createAudio(float speed, int startPitch, int endPitch,
			String text, String audioName) {
		// create scm file
		WriteSchemeFiles write = new WriteSchemeFiles(parentFrame);
		File playScm = write.createMP3(speed, startPitch, endPitch);
		
		//create text file
		File textFile = write.createTxt(text);
		
		//String mp3Path = MediaPlayerJFrame.MP3_DIR_RELATIVE_PATH+ File.separator +"";
		
		// MP3 created in the background
		CreateInBackground backGrd = new CreateInBackground("text2wave -o \""+audioName+".wav\" "
				+textFile.getAbsolutePath().toString()+" -eval "+playScm.getAbsolutePath().toString()+ ";"
				+ "ffmpeg -y -i \"" + audioName + ".wav\" -f mp3 \""+ audioName + ".mp3\";" 
				+ "rm \"" +audioName + ".wav\"");
	
		backGrd.execute();

	}
	

}
