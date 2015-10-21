package fileChoosing;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import mediaMainFrame.MediaPlayerJFrame;
import mediaMainFrame.videoControl.PlayButton;

public class UserFileChoose extends JFileChooser {

	public UserFileChoose() {
		
	}

	public String chooseVideoPath(MediaPlayerJFrame parentFrame,PlayButton playBtn){
		// start file search in current file
				setCurrentDirectory(parentFrame.VIDEO_DIR_ABSOLUTE_PATH);
				int returnVal = showOpenDialog(parentFrame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//if user is already playing a video, then remove it
					if (parentFrame.getVideoPath() != null) {
						parentFrame.removeVideo(playBtn);
					}
					return getSelectedFile().getAbsolutePath();

				} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					JOptionPane.showMessageDialog(this, parentFrame.getErrorMessage());
				}
		
		return "";
	}

	public String chooseMP3Path() {

		return "";
	}

}
