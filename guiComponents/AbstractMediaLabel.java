package guiComponents;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import main.MediaPlayerJFrame;

public abstract class AbstractMediaLabel extends AbstractMP3Creator{
	private MediaPlayerJFrame parentFrame;
	public AbstractMediaLabel(MediaPlayerJFrame parentFrame) {
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
	
	public void replaceAudio(JFrame parentFrame, String localMp3Path, String localVideoPath, String outputFile) {
				// Replace the video's audio with the synthesized text
				BackgroundAudioReplacer replacer = new BackgroundAudioReplacer(
						"ffmpeg -y -i \"" + localVideoPath + "\" -i \"" + localMp3Path + "\" -map 0:v -map 1:a \""
								+ MediaPlayerJFrame.VIDEO_DIR_RELATIVE_PATH + File.separator + outputFile + ".mp4\"");
				System.out.println("ffmpeg -y -i \"" + localVideoPath + "\" -i \"" + localMp3Path + "\" -map 0:v -map 1:a \""
								+ MediaPlayerJFrame.VIDEO_DIR_RELATIVE_PATH + File.separator + outputFile + ".mp4\"");
				replacer.execute();
	}
}
