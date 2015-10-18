package guiComponents;

import guiComponents.PlayButton.BackgroundSkipper;

import javax.swing.JSlider;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class VideoTimeSlider extends JSlider{
	
	private main.MediaPlayerJFrame parentFrame;
	private BackgroundSkipper bgTask = null;
	
	public VideoTimeSlider(EmbeddedMediaPlayer video) {	
		//video.getLength() is long, in miliseconds		
		super(0, 100,0);
	}

	
	// prints Duration: 00:01:00.04, start: 0.000000, bitrate: 438 kb/s
	//.split("[^0-9:.]")
	//ffprobe -i big_buck_bunny_1_minute.avi -show_format 2>&1 | grep Duration

}
