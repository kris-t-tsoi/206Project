package fileChoosing;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import mediaMainFrame.MediaPlayerJFrame;
import mediaMainFrame.videoControl.PlayButton;

@SuppressWarnings("serial")
public class UserFileChoose extends JFileChooser {

	MediaPlayerJFrame vidFrame;
	private final String fileNotExist = "File Does Not Exist, Please Pick Another";

	/**
	 * open or save media files
	 * @param parentFrame - media mainframe
	 */
	public UserFileChoose(MediaPlayerJFrame parentFrame) {
		vidFrame = parentFrame;
	}

	/**
	 * allows user to choose a video file video files allowed are .avi and .mp4
	 * @param parent - panel which called this method
	 * @param playBtn - play button of main media player frame
	 * @return - path of selected mp4
	 * 			- "" (nothing) is returned if user does not wish to select
	 */
	public String chooseVideoPath(JFrame parentFrame, PlayButton playBtn) {

		// video media filters
		FileFilter avi = new FileTypeFilter(".avi", "AVI Files");
		FileFilter mp4 = new FileTypeFilter(".mp4", "MP4 Files");
		FileNameExtensionFilter all = new FileNameExtensionFilter(
				"Video Files [.avi .mp4]", "avi", "mp4");
		addChoosableFileFilter(all);
		addChoosableFileFilter(avi);
		addChoosableFileFilter(mp4);
		setFileFilter(all);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		// start file search in current file
		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		int returnVal = showOpenDialog(parentFrame);

		boolean validFile = false;

		while (validFile == false) {

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// check file exists
				if (getSelectedFile().exists()) {
					// if user is already playing a video, then remove it
					if (vidFrame.getVideoPath() != null) {
						vidFrame.removeVideo(playBtn);
					}
					return getSelectedFile().getAbsolutePath();

				}
			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			} else if (returnVal == JFileChooser.ERROR_OPTION) {
				JOptionPane.showMessageDialog(parentFrame,
						vidFrame.getErrorMessage());
			}
		}

		return "";
	}

	/**
	 * Allows user to choose an .mp3 file
	 * 
	 * @param parent - panel which called this method
	 * @return - path of selected mp3
	 * 			- "" (nothing) is returned if user does not wish to select
	 */
	public String chooseMP3Path(JFrame parentFrame) {
		// mp3 media filters
		FileFilter mp3 = new FileTypeFilter(".mp3", "MP3 Files");
		addChoosableFileFilter(mp3);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		// Start file search in current directory, and show mp3's
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"MP3 File", "mp3");
		setFileFilter(filter);
		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));

		boolean validFile = false;

		while (validFile == false) {
			int returnVal = showOpenDialog(parentFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// check file exists
				if (getSelectedFile().exists()) {
					return getSelectedFile().getAbsolutePath();
				} else {
					JOptionPane.showMessageDialog(vidFrame, fileNotExist);
				}

			} else if (returnVal == JFileChooser.ERROR_OPTION) {
				JOptionPane.showMessageDialog(parentFrame,
						vidFrame.getErrorMessage());
				break;
			} else {
				break;
			}

		}
		return "";
	}

	/**
	 * Choose location to save created mp4
	 * @return -path of created mp4
	 * 			- "" (nothing) is returned if user does not wish to create
	 */
	public String saveVideo() {
		// video media filters
		FileFilter mp4 = new FileTypeFilter(".mp4", "MP4 Files");
		addChoosableFileFilter(mp4);
		setFileFilter(mp4);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		int returnVal = showSaveDialog(vidFrame);

		boolean validName = false;
		while (validName == false) {

			if (returnVal == APPROVE_OPTION) {
				if (!getSelectedFile().getName().trim().equals("")) {
					return getSelectedFile().getAbsolutePath() + ".mp4";
				}
			} else if (returnVal == JFileChooser.ERROR_OPTION) {
				JOptionPane.showMessageDialog(vidFrame, 
						vidFrame.getErrorMessage());
				break;
			} else {
				break;
			}
		}

		return "";
	}

	/**
	 *Choose location to save created mp3
	 * @return -path of created mp3
	 * 			- "" (nothing) is returned if user does not wish to create
	 */
	public String saveMP3() {
		// mp3 media filters
		FileFilter mp3 = new FileTypeFilter(".mp3", "MP3 Files");
		addChoosableFileFilter(mp3);
		setFileFilter(mp3);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);
		
		
		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		int returnVal = showSaveDialog(vidFrame);

		boolean validName = false;
		while (validName == false) {

			if (returnVal == APPROVE_OPTION) {
				if (!getSelectedFile().getName().trim().equals("")) {
					return getSelectedFile().getAbsolutePath();
				}
			} else if (returnVal == JFileChooser.ERROR_OPTION) {
				JOptionPane.showMessageDialog(vidFrame, 
						vidFrame.getErrorMessage());
				break;
			} else {
				break;
			}
		}

		return "";
	}

	/**
	 * Allow user to choose the default working directory
	 * 
	 * @param startUp
	 */
	public void setDefaultDirectoy(boolean startUp) {
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// If user has previously selected and wants to change current working
		// directory
		if (startUp == false) {
			setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		} else {
			JOptionPane.showMessageDialog(vidFrame,
					"Please Set Default Work Directory");
		}

		int returnDirect = showOpenDialog(vidFrame);
		if (returnDirect == JFileChooser.APPROVE_OPTION) {
			vidFrame.setDefPathDirect(getSelectedFile().getAbsolutePath());
		} else {// If starting up and user does not choose then set user
				// directory as default
			if (startUp == true) {
				vidFrame.setDefPathDirect(System.getProperty("user.dir"));
				JOptionPane.showMessageDialog(
						vidFrame,
						"No Workspace was Choosen, "
								+ vidFrame.getDefPathDirect()
								+ " has been set as the default directory");

			}
		}
	}

	/**
	 * Class for file filtering Code from:
	 * www.codejava.net/java-se/swing/add-file-filter-for-jfilechooser-dialog
	 *
	 */
	class FileTypeFilter extends FileFilter {
		private String extension;
		private String description;

		public FileTypeFilter(String extension, String description) {
			this.extension = extension;
			this.description = description;
		}

		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			return file.getName().endsWith(extension);
		}

		public String getDescription() {
			return description + String.format(" (*%s)", extension);
		}

	}

}
