package doInBackground;

import javax.swing.SwingUtilities;

import mediaMainFrame.MediaPlayerJFrame;

public class UpdateRunnable implements Runnable {
	
	MediaPlayerJFrame video;

	public UpdateRunnable(MediaPlayerJFrame parentFrame) {
		video = parentFrame;
	}
	
	/**
	 * overriding run method
	 * update video's slider and current time label
	 */
	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//use asVidPlay method to update video slider and current time label
				video.getVidSlide().asVidPlay(video);
			}
		});
	}

}
