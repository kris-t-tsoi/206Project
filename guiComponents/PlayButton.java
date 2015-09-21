package guiComponents;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import main.MediaPlayerJFrame;

public class PlayButton extends JButton {

	private main.MediaPlayerJFrame parentFrame;
	private BackgroundSkipper bgTask;

	public PlayButton(MediaPlayerJFrame parentFrame) {
		super("Play");
		setToolTipText("Play the video");
		this.parentFrame = parentFrame;
	}

	/**
	 * Class to skip forward/backward continuously without freezing the GUI.
	 * 
	 */
	class BackgroundSkipper extends SwingWorker<Void, Void> {
		private boolean skipForward;

		public BackgroundSkipper(boolean skipFoward) {
			this.skipForward = skipFoward;
		}

		@Override
		protected Void doInBackground() throws Exception {
			// skipForward is a boolean which determines whether to skip
			// forwards or backwards
			int skipValue = skipForward ? 1000 : -1000;
			while (!isCancelled()) {
				parentFrame.skip(skipValue);
				Thread.sleep(200);// Sleep in between skips to prevent errors
			}
			return null;
		}

	}

	public void playPressed() {
		// Cancel any current skipping
		if (bgTask != null) {
			bgTask.cancel(true);
			bgTask = null;
			return;
		} else {
			// Start the video if not started
			if (!parentFrame.getVideoIsStarted()) {
				//check if video has just been selected or not
				if(!parentFrame.play(parentFrame)){
					setText("Pause");
					parentFrame.setVideoIsStarted(true);
					parentFrame.setVideoVolume(MediaPlayerJFrame.DEFAULT_VOLUME);
					parentFrame.play(parentFrame);
				}
				

				return;
			} else {

				// Pause or play the video
				if (!parentFrame.videoIsPlaying())

				{// Pause video if playing
					parentFrame.pauseVideo(false);
					setText("Pause");

				} else

				{
					parentFrame.pauseVideo(true);// Play video if paused
					setText("Play");
				}
			}
		}
	}

	/**
	 * Function to continuously skip forwards or backwards depending on the
	 * input boolean.
	 * 
	 * @param forwards
	 */
	public void skipVideoForwards(boolean forwards) {
		if (bgTask != null)
			bgTask.cancel(true);
		bgTask = new BackgroundSkipper(forwards);
		bgTask.execute();
	}
}
