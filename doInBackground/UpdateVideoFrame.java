package doInBackground;

import javax.swing.SwingUtilities;

import mediaMainFrame.MediaPlayerJFrame;

/**
 * Runs in the background of the application
 * calls on a method to update the current position
 * of the video slider and current time lablel
 * @author kristy
 *
 */
public class UpdateVideoFrame implements Runnable {
	
	MediaPlayerJFrame video;

	public UpdateVideoFrame(MediaPlayerJFrame parentFrame) {
		video = parentFrame;
	}
	
	/**
	 * overriding run method
	 * update videoFrame's slider and current time label
	 */
	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//use asVidPlay method to update videoFrame slider and current time label
				video.getVidSlide().asVidPlay(video);
			}
		});
	}

}
