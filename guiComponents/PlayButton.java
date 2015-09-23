package guiComponents;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import main.MediaPlayerJFrame;

/**
 * This class is the play button of the media player. It alternates between
 * playing and pausing the video with the icon also changing between a pause
 * icon and a play icon. It also acts as a cancel for the skip
 * 
 *
 */
public class PlayButton extends JButton {

	private main.MediaPlayerJFrame parentFrame;
	private BackgroundSkipper bgTask;

	private static final ImageIcon PAUSE_IMAGE = new ImageIcon("images/Pause16.gif");
	public final static ImageIcon PLAY_IMAGE = new ImageIcon("images/Play16.gif");

	private static final int SKIP_AMOUNT = 100;
	private static final int SLEEP_AMOUNT = 10;

	public PlayButton(MediaPlayerJFrame parentFrame) {
		super();
		setToolTipText("Play the video");
		this.parentFrame = parentFrame;
	}

	/**
	 * Class to skip forward/backward continuously without freezing the GUI.
	 * 
	 */
	class BackgroundSkipper extends SwingWorker<Void, Void> {
		// If true, video skips forward. If false, backward.
		private boolean skipForward;

		public BackgroundSkipper(boolean skipFoward) {
			this.skipForward = skipFoward;
		}

		@Override
		protected Void doInBackground() throws Exception {
			// skipForward is a boolean which determines whether to skip
			// forwards or backwards
			int skipValue = skipForward ? SKIP_AMOUNT : -SKIP_AMOUNT;
			while (!isCancelled()/*|| !parentFrame.checkIsEnd()*/) {// check if canceled or video has reached
									// end
//				if (parentFrame.checkIsEnd()) {
//					btnSetPlayIcon();
//					bgTask.cancel(true);
//					bgTask = null;
//					break;
//				} else {
					parentFrame.skip(skipValue);
					Thread.sleep(SLEEP_AMOUNT);// Sleep in between skips to
												// prevent errors
				}
//			}

			return null;
		}

	}

	/**
	 * This method determines what happens when the play button is pressed. 1.
	 * Any skipping is cancelled. 2. If the video has not been chosen, it lets
	 * the user choose a video 3. Start the video if not started
	 */
	public void playPressed() {
		// Cancel any current skipping
		if (bgTask != null) {
			bgTask.cancel(true);
			bgTask = null;
			return;
		} else {
			// If a video is not currently selected
			if (parentFrame.getVideoPath() == null) {
				JOptionPane.showMessageDialog(parentFrame, "Please select a video to play.");
				parentFrame.selectVideo(this);
			}

			// Start the selected video if not started
			else if (!parentFrame.getVideoIsStarted()) {
				btnSetPauseIcon();
				parentFrame.setVideoIsStarted(true);
				parentFrame.setVideoVolume(MediaPlayerJFrame.DEFAULT_VOLUME);
				parentFrame.play(this);

				// Else the video is started
			} else {
				// Pause or play the video

				if (!parentFrame.videoIsPlaying()) {// Pause video if playing
					parentFrame.pauseVideo(false);
					btnSetPauseIcon();

				} else {
					parentFrame.pauseVideo(true);// Play video if paused
					btnSetPlayIcon();
					parentFrame.checkIsEnd();
				}
			}
		}
	}

	/**
	 * Method to allow other classes to change the button to the pause icon
	 */
	private void btnSetPauseIcon() {
		setIcon(PAUSE_IMAGE);
	}

	/**
	 * Method to allow MediaPlayerJFrame to change the button to the play icon
	 * when a new video is selected, as the video does not auto-play but the
	 * icon could be a pause icon if the previous video was playing.
	 */
	public void btnSetPlayIcon() {
		setIcon(PLAY_IMAGE);
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
