package mediaMainFrame.time;

import mediaMainFrame.MediaPlayerJFrame;
import sharedLabels.TimeLabel;

@SuppressWarnings("serial")
/**
 * label shows the current time of the playing video
 * @author kristy
 *
 */
public class VideoCurrentTime extends TimeLabel {

	public VideoCurrentTime() {
		super();
	}
	
	/**
	 * gets the current time of the video
	 * then sets text to current time
	 * @param video
	 */
	public void currentTime(MediaPlayerJFrame video){
		//current time in millisec
		double curTime = (video.getVideo().getPosition())*(video.getVideoDuration());
		setText(durationDoubleToString(curTime));
	}
	
}
