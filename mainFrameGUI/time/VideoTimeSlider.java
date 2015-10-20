package mainFrameGUI.time;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import main.MediaPlayerJFrame;
import mainFrameGUI.videoControl.PlayButton;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class VideoTimeSlider extends JSlider{
	
	private main.MediaPlayerJFrame parentFrame;
	
	/**
	 * Creates the video time slider with value from 0-100
	 * @param video
	 */
	public VideoTimeSlider(EmbeddedMediaPlayer video) {			
		super(0, 100,0);
	}
	
	
	public void userDrag(MediaPlayerJFrame videoPlayer){
		
	}
	
}
