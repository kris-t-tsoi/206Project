package textToMP3Frame.buttons;

import java.io.File;

import javax.swing.JButton;

import textToMP3Frame.TextToSpeechFrame;
import doInBackground.CreateInBackground;
import doInBackground.WriteSchemeFiles;

@SuppressWarnings("serial")
/**
 * button creates MP3 files from text
 * @author kristy
 *
 */
public class CreateMP3Btn extends JButton {

	private TextToSpeechFrame parentFrame;

	/**
	 * class where mp3 files are created
	 * @param frame
	 */
	public CreateMP3Btn(TextToSpeechFrame frame) {
		super();
		parentFrame = frame;
		setText("Create MP3");
		setToolTipText("Create MP3 File From Text");
	}

	/**
	 * Create mp3 from text
	 * @param speed - of voice
	 * @param startPitch
	 * @param endPitch
	 * @param text - to turn into audio
	 * @param audioName -m3 name
	 */
	public void createAudio(float speed, int startPitch, int endPitch,
			String text, String audioName) {
		// create scm file
		WriteSchemeFiles write = new WriteSchemeFiles(parentFrame);
		File playScm = write.createMP3(speed, startPitch, endPitch);
		
		//create text file
		File textFile = write.createTxt(text);
		
		// MP3 created in the background
		CreateInBackground backGrd = new CreateInBackground("text2wave -o \""+audioName+".wav\" "
				+textFile.getAbsolutePath().toString()+" -eval "+playScm.getAbsolutePath().toString()+ ";"
				+ "ffmpeg -y -i \"" + audioName + ".wav\" -f mp3 \""+ audioName + ".mp3\";" 
				+ "rm \"" +audioName + ".wav\"");
	
		backGrd.execute();

	}
	

}
