package mediaMainFrame;


/**
 * This class can only replace audio with an mp3 file - createMP3 is not needed as part of the functionality.
 *
 */
public class ReplaceWithExistingMP3Label extends AbstractReplaceAudioLabel {
	
	public ReplaceWithExistingMP3Label(MediaPlayerJFrame parentFrame) {
		super(parentFrame);
		setText("Currently Selected MP3");
	}
	
	//Workaround for not being able to extend 2 classes.
	//Ideally the OverlayTextLabel would extend a class containing createMP3 and another class containing replaceAudio, 
	//but this will have to suffice, as this class should not need this method.
	@Override
	public void createMP3(String s, String o) {
		throw new UnsupportedOperationException("This class cannot create Mp3s.");
	}
}
