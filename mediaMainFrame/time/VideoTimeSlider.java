package mediaMainFrame.time;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import mediaMainFrame.MediaPlayerJFrame;
import mediaMainFrame.videoControl.PlayButton;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class VideoTimeSlider extends JSlider{
	
	private mediaMainFrame.MediaPlayerJFrame parentFrame;
	
	/**
	 * Creates the video time slider with value from 0-100
	 * @param video
	 */
	public VideoTimeSlider(EmbeddedMediaPlayer video) {			
		super(0, 100,0);
	}
	
	/**
	 * Gets current position of slider
	 * change video position to specified point
	 * @param vidPlayer - frame in which video is being played
	 */
	public void userDrag(MediaPlayerJFrame vidPlayer){
		vidPlayer.getVideo().setPosition((float)getValue()/100);
	}
	
	
	public void asVidPlay(MediaPlayerJFrame vidPlayer){
		
		//update this slider with current video position
		setValue((int)(vidPlayer.getVideo().getPosition()*100));

		//update current time text
		vidPlayer.getVidCurrentTime().currentTime(vidPlayer);
	}
	
}
