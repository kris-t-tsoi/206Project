package mediaMainFrame.time;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.xml.datatype.Duration;

import mediaMainFrame.MediaPlayerJFrame;
import sharedGUIComponets.TimeLabel;

public class VideoCurrentTime extends TimeLabel {

	public VideoCurrentTime() {
		super();
	}
	
	
	public void currentTime(MediaPlayerJFrame video){
		//current time in millisec
		double curTime = (video.getVideo().getPosition())*(video.getVideoDuration());
		setText(durationDoubleToString(curTime));
		System.out.println(curTime+"");
	}
	
}
