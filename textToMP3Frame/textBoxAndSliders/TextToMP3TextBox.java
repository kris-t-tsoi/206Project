package textToMP3Frame.textBoxAndSliders;

import java.awt.Dimension;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
/**
 * Text Area where text gets synthesized into audio
 * @author kristy
 *
 */
public class TextToMP3TextBox extends JTextArea {
	public static final int MAX_NUMBER_OF_WORDS = 50;

	public TextToMP3TextBox() {
		super("Text to synthesize here - max 50 words");
		setToolTipText("Type in Text you Wish to Hear as Audio");

		// if sentence is too long the whole word will go onto the next line
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	/**
	 * Function to check if the number of words a string of text exceeds a
	 * certain value. Used before reading or saving any text.
	 * 
	 * @param text
	 *            - from textField
	 * @return true if number of words is less than 30, false if number of words
	 *         is greater than 30
	 * 
	 */
	public boolean checkTxtLength() {
			// Removes all spaces and punctuation apart from ' for conjunctions
			String[] punct = getText().split("[^a-zA-Z0-9']");
			int words = 0;
			for (int i = 0; i < punct.length; i++) {
				if (!punct[i].equals("")) {
					words++;
				}
			}
			if (words <= MAX_NUMBER_OF_WORDS) {
				return true;
			}
		return false;
	}

	@Override //text area can not be smaller that this
	public Dimension getMinimumSize() {
		return new Dimension(100, 50);
	}
}
