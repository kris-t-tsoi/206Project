package guiComponents;
import javax.swing.JTextField;

public class InputTextField extends JTextField {
	public static final int MAX_NUMBER_OF_WORDS = 30;
	
	public InputTextField() {
		super("Text to synthesize here - max 30 words");
		setToolTipText("Text to synthesize here - max 30 words");
		//txtInputText.setColumns(10);
	}
	
	/**
	 * Function to check if the number of words a string of text exceeds a
	 * certain value. Used before reading or saving any text.
	 * 
	 * @param text
	 *            - from textField
	 * @return true - number of words is less than 30 false - number of words is
	 *         greater than 30
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
	
	/**
	 * Uses festival to speak the input text by creating a bash process
	 * 
	 * @param text
	 */
	public void sayWithFestival(String text) {
		String cmd = "echo " + text + " | festival --tts&";
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		try {
			builder.start();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
}
