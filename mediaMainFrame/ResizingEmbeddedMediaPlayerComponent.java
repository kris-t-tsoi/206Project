package mediaMainFrame;

import java.awt.Dimension;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class ResizingEmbeddedMediaPlayerComponent extends EmbeddedMediaPlayerComponent {

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(0,0);
	}
}
