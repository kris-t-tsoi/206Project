package mainFrameGUI;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import main.MediaPlayerJFrame;

/**
 * This abstract class allows extending classes to replace the audio of a video with an mp3 as well as
 * create the required mp3.
 *
 * Extending classes: 	OverlayTextLabel
 * 						OverlayExistingMp3Label - this has createMP3 overridden as it should not have 
 * 													that functionality
 */
public abstract class AbstractReplaceAudioLabel extends AbstractMP3Creator{
	private MediaPlayerJFrame parentFrame;
	public AbstractReplaceAudioLabel(MediaPlayerJFrame parentFrame) {
		super();
		this.parentFrame = parentFrame;
	}
	
	/**
	 * Class to do the audio processing in the background so that when it is
	 * complete we can set the label Created with the command in the constructor
	 */
	class BackgroundAudioReplacer extends SwingWorker<Void, Void> {
		private String cmd;

		public BackgroundAudioReplacer(String cmd) {
			this.cmd = cmd;
		}

		@Override
		protected Void doInBackground() throws Exception {
			useTerminalCommand(cmd);
			return null;
		}

		@Override
		protected void done() {
			parentFrame.setLabelComplete();
		}
	}
	
	/**
	 * Function to replace an input video's audio with a given mp3 file, and create a new output file
	 * @param localMp3Path
	 * @param localVideoPath
	 * @param outputFile
	 */
	public void replaceAudio(String localMp3Path, String localVideoPath, String outputFile) {
				// Replace the video's audio with the synthesized text
				BackgroundAudioReplacer replacer = new BackgroundAudioReplacer(
						"ffmpeg -y -i \"" + localVideoPath + "\" -i \"" + localMp3Path + "\" -map 0:v -map 1:a \""
								+ MediaPlayerJFrame.VIDEO_DIR_RELATIVE_PATH + File.separator + outputFile + ".mp4\"");
				replacer.execute();
	}
}
