package doInBackground;

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
		System.out.println("run");
		video.getVidSlide().asVidPlay(video);
	}

}
