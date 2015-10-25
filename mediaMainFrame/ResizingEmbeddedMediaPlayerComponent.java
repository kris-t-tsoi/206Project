package mediaMainFrame;

import java.awt.Dimension;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * Allow videoFrame to shrink
 *
 */
@SuppressWarnings("serial")
public class ResizingEmbeddedMediaPlayerComponent extends EmbeddedMediaPlayerComponent {

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(0,0);
	}
	
	@Override
	public void finished(MediaPlayer m) {
		//TODO stuff here
	}
}
