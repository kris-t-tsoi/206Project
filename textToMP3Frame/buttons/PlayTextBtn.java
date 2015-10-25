package textToMP3Frame.buttons;

import java.io.File;

import javax.swing.JButton;

import mediaMainFrame.videoControl.PlayButton;
import doInBackground.FestivalSpeakBackgroundUse;
import doInBackground.WriteSchemeFiles;
import textToMP3Frame.TextToSpeechFrame;

@SuppressWarnings("serial")
public class PlayTextBtn extends JButton {
	TextToSpeechFrame parentFrame;
	FestivalSpeakBackgroundUse backGrd;
	PlayTextBtn playBtn;
	
	final public String cancel = "Cancel Play";
	final public String play = "Play Text";

	public PlayTextBtn(TextToSpeechFrame frame) {
		super();
		parentFrame = frame;
		setText(play);
		setToolTipText("Play Text From Textbox");
		playBtn = this;
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
		backGrd = new FestivalSpeakBackgroundUse(cmd,playBtn);
		backGrd.execute();
	}
	
	public void stopFestival(){
		backGrd.cancel();
	}
}
