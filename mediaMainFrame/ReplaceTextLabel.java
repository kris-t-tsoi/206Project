package mediaMainFrame;


/**
 * This class can contains the functionality required to first create an mp3 out of text, 
 * and then add the text to the video. 
 *
 */
public class ReplaceTextLabel extends AbstractReplaceAudioLabel{

	public ReplaceTextLabel(MediaPlayerJFrame parentFrame) {
		super(parentFrame);
		setText("Text");
		setToolTipText("Replace the current video's audio with the text in the text field");
	}

}
