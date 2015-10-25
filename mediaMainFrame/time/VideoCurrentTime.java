package mediaMainFrame.time;

import mediaMainFrame.MediaPlayerJFrame;
import sharedLabels.TimeLabel;

public class VideoCurrentTime extends TimeLabel {

	public VideoCurrentTime() {
		super();
	}
	
	
	public void currentTime(MediaPlayerJFrame video){
		//current time in millisec
		double curTime = (video.getVideo().getPosition())*(video.getVideoDuration());
		setText(durationDoubleToString(curTime));
	}
	
}
