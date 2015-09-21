package guiComponents;

import main.MediaPlayerJFrame;

public class OverlayTextButton extends AbstractMediaButton{

	public OverlayTextButton(MediaPlayerJFrame parentFrame) {
		super(parentFrame);
		setText("Overlay");
		setToolTipText("Overlay the text on the video");
	}

}
