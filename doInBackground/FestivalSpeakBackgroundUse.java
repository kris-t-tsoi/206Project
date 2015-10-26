package doInBackground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public FestivalSpeakBackgroundUse(String line, PlayTextBtn playBtn,
			TextToSpeechFrame parent) {
		// get command as input
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
