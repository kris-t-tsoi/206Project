package overlayMedia.addAudioTrackPanel;

import javax.swing.JButton;

public class SelectMP3Btn extends JButton {

	
	public SelectMP3Btn() {
		super();
		setText("Select MP3");
		setToolTipText("Select MP3 to Add to the Video");
	}
	
	
	
	// This label opens a FileChooser to select an MP3
/*		
			// Start file search in current directory, and show mp3's
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"MP3 File", "mp3");
			mp3FC.setFileFilter(filter);
			mp3FC.setCurrentDirectory(mp3Dir);

			int returnVal = mp3FC.showOpenDialog(thisFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				setMp3Path(mp3FC.getSelectedFile().getAbsolutePath());
			} else if (returnVal == JFileChooser.ERROR_OPTION) {
				JOptionPane.showMessageDialog(thisFrame, ERROR_MESSAGE);
			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				// do nothing
			}
	*/
	
}
