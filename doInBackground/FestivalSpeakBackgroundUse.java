package doInBackground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import textToMP3Frame.buttons.PlayTextBtn;
import mediaMainFrame.videoControl.PlayButton;

/**
 * Does terminal commands in background No progress bar only used by PlayTextBtn
 * class
 *
 */
public class FestivalSpeakBackgroundUse extends SwingWorker<Void, Void> {
	private String cmd;
	PlayTextBtn playButton;

	ProcessBuilder probuild;
	Process pb;
	private int festivalID;
	private String[] processID;

	public int getFestivalID() {
		return festivalID;
	}

	public void setFestivalID(int i) {
		this.festivalID = i;
	}

	public FestivalSpeakBackgroundUse(String line, PlayTextBtn playBtn) {
		// get command as input
		cmd = line;
		playButton = playBtn;
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		//playButton.setText(playButton.cancel);

		probuild = new ProcessBuilder("bash", "-c", cmd);
		probuild.redirectErrorStream(true);

		try {
			// start process and wait for one second
			pb = probuild.start();

			// Credit - Nasser Lecture 8
			if (pb.getClass().getName().equals("java.lang.UNIXProcess")) {

				Field f = pb.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				setFestivalID(f.getInt(pb));

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;

	}

	/**
	 * cancels festival speaking
	 */
	public void cancel() {
		// get pid for all festival children processes
		cmd = "pstree -lp " + getFestivalID() + "| grep festival";
		probuild = new ProcessBuilder("bash", "-c", cmd);
		try {
			pb = probuild.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					pb.getInputStream()));
			String inline = in.readLine();

			// get numbers from pstree
			// credit:
			// http://stackoverflow.com/questions/2367381/how-to-extract-numbers-from-a-string-and-get-an-array-of-ints
			Pattern p = Pattern.compile("-?\\d+");
			Matcher m = p.matcher(inline);
			processID = new String[7];
			int i = 0;
			while (m.find()) {
				processID[i] = m.group();
				i++;
			}

			// kill all festival processes for this backgroundTask object
			for (i = (i - 1); i > 0; i--) {
				cmd = "kill -9 " + processID[i];
				probuild = new ProcessBuilder("bash", "-c", cmd);
				probuild.start().waitFor();
			}

		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// destroy process
		pb.destroy();
		done();

	}

	@Override
	protected void done() {
		super.done();
		//playButton.setText(playButton.play);
	}

}
