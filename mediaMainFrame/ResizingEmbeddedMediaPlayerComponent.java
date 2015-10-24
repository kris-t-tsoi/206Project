package mediaMainFrame;

import java.awt.Dimension;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * Allow video to shrink
 *
 */
@SuppressWarnings("serial")
public class ResizingEmbeddedMediaPlayerComponent extends EmbeddedMediaPlayerComponent {

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(0,0);
	}
}
