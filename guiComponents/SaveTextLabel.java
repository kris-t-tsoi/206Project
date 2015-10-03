package guiComponents;

/**
 * This class is a JMenuItem that can save text to an mp3 file by extending the AbstractMP3Creator class
 *
 */
public class SaveTextLabel extends AbstractMP3Creator{

	public SaveTextLabel() {
		super();
		setText("Save Text");
		setToolTipText("Save the current text field text to an mp3 file");
	}

}
