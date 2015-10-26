package doInBackground.festivalSpeak;

import javax.swing.SwingWorker;

import textToMP3Frame.TextToSpeechFrame;
import textToMP3Frame.buttons.PlayTextBtn;

/**
 * Does terminal commands in background No progress bar only used by PlayTextBtn
 * class
 *
 */
public class FestivalSpeakBackgroundUse extends SwingWorker<Void, Void> {
	private String cmd;
	PlayTextBtn playButton;
	TextToSpeechFrame textFrame;
	FestivalTerminal festTerm;

	ProcessBuilder probuild;
	Process pb;

	/*
	 * constructor initialise all variables
	 */
	public FestivalSpeakBackgroundUse(String line, PlayTextBtn playBtn,
			TextToSpeechFrame parent) {
		cmd = line;
		playButton = playBtn;
		textFrame = parent;
		festTerm = new FestivalTerminal();
	}

	@Override
	protected Void doInBackground() throws Exception {

		festTerm.start(cmd);
		return null;

	}

	
}
