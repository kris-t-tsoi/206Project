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

	

}
