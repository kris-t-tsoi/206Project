package guiComponents;

import main.MediaPlayerJFrame;

public class OverlayExistingMp3Label extends AbstractMediaLabel {
	
	public OverlayExistingMp3Label(MediaPlayerJFrame parentFrame) {
		super(parentFrame);
		setText("Existing MP3");
	}
	
	//Workaround for not being able to extend 2 classes. Thanks java.
	@Override
	public void createMP3(String s, String o) {
		throw new UnsupportedOperationException("This class cannot create Mp3s.");
	}
	
}
