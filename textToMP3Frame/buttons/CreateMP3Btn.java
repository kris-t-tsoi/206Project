package textToMP3Frame.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.MediaPlayerJFrame;
import mainFrameGUI.AbstractMP3Creator;
import mainFrameGUI.ReplaceTextLabel;

@SuppressWarnings("serial")
public class CreateMP3Btn extends JButton {

	public CreateMP3Btn() {
		super();
		setText("Create MP3");
		setToolTipText("Create MP3 File From Text");
	}

	
	
	/**
	 * Method that creates an mp3 from the textField only if the number of words
	 * is under the limit. It also returns the name of the created mp3
	 * 
	 * @param parentFrame
	 *            - the current frame, used in JOptionPane
	 * @return mp3Name - name of the created mp3
	 */
/*	private String createValidMP3(AbstractMP3Creator menuItem) {
		// check if number of word is within limit
		if (txtInputText.checkTxtLength()) {
			String mp3Name = JOptionPane.showInputDialog(this,
					"Enter a name for the mp3 file");
			if ((mp3Name != null) && !mp3Name.startsWith(" ")) {
				menuItem.createMP3(txtInputText.getText(), mp3Name);
				// Return the name of the mp3 that was created
				return mp3Name + ".mp3";
			}
		} else {
			JOptionPane.showMessageDialog(this,
					MediaPlayerJFrame.ERROR_WORD_LIMIT_MESSAGE);
		}
		return null;
	}
	*/
	
	
	
	
	
	/*
	
	// Label to replace a video's audio with the text in the textField
	final ReplaceTextLabel overlayTextItem = new ReplaceTextLabel(this);
	overlayTextItem.addActionListener((new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// First create the mp3, and get its name
			String mp3 = createValidMP3(overlayTextItem);

			// Then replace the audio
			String mp3Path = MP3_DIR_ABSOLUTE_PATH + File.separator + mp3;
			replaceAudio(overlayTextItem, mp3Path);
		}
	}));
	subMenu.add(overlayTextItem);
	
	*/
}
