package guiComponents;

import main.MediaPlayerJFrame;

public class OverlayExistingMp3Button extends AbstractMediaButton{
	
	public OverlayExistingMp3Button(MediaPlayerJFrame parentFrame) {
		super(parentFrame);
		setText("Add mp3");
		setToolTipText("Overlay the mp3 on the current video");
	}
	
}
