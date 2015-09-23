package guiComponents;

import main.MediaPlayerJFrame;

/**
 * This class can contains the functionality required to first create an mp3 out of text, 
 * and then add the text to the video. 
 *
 */
public class OverlayTextLabel extends AbstractReplaceAudioLabel{

	public OverlayTextLabel(MediaPlayerJFrame parentFrame) {
		super(parentFrame);
		setText("Text");
		setToolTipText("Replace the current video's audio with the text in the text field");
	}

}
