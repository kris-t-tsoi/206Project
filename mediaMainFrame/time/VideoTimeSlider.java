package mediaMainFrame.time;

import javax.swing.JSlider;

import mediaMainFrame.MediaPlayerJFrame;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@SuppressWarnings("serial")
public class VideoTimeSlider extends JSlider {

	/**
	 * Creates the videoFrame time slider with value from 0-100
	 * 
	 * @param videoFrame
	 */
	public VideoTimeSlider(EmbeddedMediaPlayer video) {
		super(0, 100, 0);
	}

	/**
	 * Gets current position of slider change videoFrame position to specified point
	 * 
	 * @param vidPlayer
	 *            - frame in which videoFrame is being played
	 */
	public void userDrag(MediaPlayerJFrame vidPlayer) {
		vidPlayer.getVideo().setPosition((float) getValue() / 100);
	}

	public void asVidPlay(MediaPlayerJFrame vidPlayer) {
		
		int slidePercent =(int) (vidPlayer.getVideo().getPosition() * 100);

		//if reach the end allow videoFrame to replay again
		if(vidPlayer.getVideo().getPosition()>0.99){
			vidPlayer.stop();
		}else{
		
		// update this slider with current videoFrame position
		setValue(slidePercent);
		}
		
		//if the videoFrame has started
		if (!vidPlayer.getVideoIsStarted()||slidePercent==100||slidePercent==0) {
			vidPlayer.getVidCurrentTime().setText("00:00.00");
		}else{
			// update current time text
			vidPlayer.getVidCurrentTime().currentTime(vidPlayer);
		}
	}

}
