package fileChoosing;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import mediaMainFrame.MediaPlayerJFrame;
import mediaMainFrame.videoControl.PlayButton;

@SuppressWarnings("serial")
public class UserFileChoose extends JFileChooser {

	MediaPlayerJFrame vidFrame;

	// Constant Message
	private final String errorMessage = "Sorry, an error has occured. please try again.";
	private final String fileNotExist = "File Does Not Exist, No File was Selected";
	private final String fileNotCreate = "File Was Not Created";
	private final String fileOverWrite = "There Exists a file with the same name, Would you like to overwrite it ?";

	// File filters
	FileFilter avi;
	FileFilter mp4;
	FileFilter mp3;
	FileNameExtensionFilter allVid;
	FileNameExtensionFilter dir;

	/**
	 * open or save media files
	 * 
	 * @param parentFrame
	 *            - media mainframe
	 */
	public UserFileChoose(MediaPlayerJFrame parentFrame) {
		vidFrame = parentFrame;
		setupFileFilters();
	}

	private void setupFileFilters() {
		// video files
		avi = new FileTypeFilter(".avi", "AVI Files");
		mp4 = new FileTypeFilter(".mp4", "MP4 Files");
		allVid = new FileNameExtensionFilter("Video Files [.avi .mp4]", "avi",
				"mp4");

		// audio filter
		mp3 = new FileTypeFilter(".mp3", "MP3 Files");

		// directory filter
		dir = new FileNameExtensionFilter("Directories", "dir");

	}

	/**
	 * allows user to choose a videoFrame file videoFrame files allowed are .avi
	 * and .mp4
	 * 
	 * @param parent
	 *            - panel which called this method
	 * @param playBtn
	 *            - play button of main media player frame
	 * @return - path of selected mp4 - "" (nothing) is returned if user does
	 *         not wish to select
	 */
	public String chooseVideoPath(JFrame parentFrame, PlayButton playBtn) {

		// add in videoFrame media filters
		addChoosableFileFilter(allVid);
		addChoosableFileFilter(avi);
		addChoosableFileFilter(mp4);
		setFileFilter(allVid);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		// start file search in current file
		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		int returnVal = showOpenDialog(parentFrame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// check file exists
			if (getSelectedFile().exists()) {
				// if user is already playing a videoFrame, then remove it
				if (vidFrame.getVideoPath() != null) {
					vidFrame.removeVideo(playBtn);
				}
				return getSelectedFile().getAbsolutePath();

			} else {
				JOptionPane.showMessageDialog(parentFrame, fileNotExist);
			}
		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(parentFrame, errorMessage);
		}

		return "";
	}

	/**
	 * Allows user to choose an .mp3 file
	 * 
	 * @param parent
	 *            - panel which called this method
	 * @return - path of selected mp3 - "" (nothing) is returned if user does
	 *         not wish to select
	 */
	public String chooseMP3Path(JFrame parentFrame) {
		// mp3 media filters
		addChoosableFileFilter(mp3);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		setFileFilter(mp3);
		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));

		int returnVal = showOpenDialog(parentFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// check file exists
			if (getSelectedFile().exists()) {
				return getSelectedFile().getAbsolutePath();
			} else {
				JOptionPane.showMessageDialog(vidFrame, fileNotExist);
			}

		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(parentFrame, errorMessage);
		}

		return "";
	}

	/**
	 * Choose location to save created mp4
	 * 
	 * @return -path of created mp4 - "" (nothing) is returned if user does not
	 *         wish to create
	 */
	public String saveVideo() {
		// videoFrame media filters
		addChoosableFileFilter(mp4);
		removeChoosableFileFilter(avi);
		removeChoosableFileFilter(allVid);
		setFileFilter(mp4);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		setSelectedFile(new File(""));
		int returnVal = showSaveDialog(vidFrame);
		if (returnVal == APPROVE_OPTION) {
			if (new File(getSelectedFile() + ".mp4").exists()) {
				if (overwriteFile() == true) {
					return getSelectedFile().getAbsolutePath() + ".mp4";
				}
			} else if (!getSelectedFile().getName().trim().equals("")) {
				return getSelectedFile().getAbsolutePath() + ".mp4";
			}
		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(vidFrame, errorMessage);
		}

		return "";
	}

	/**
	 * Choose location to save created mp3
	 * 
	 * @return -path of created mp3 - "" (nothing) is returned if user does not
	 *         wish to create
	 */
	public String saveMP3() {
		// mp3 media filters
		FileFilter mp3 = new FileTypeFilter(".mp3", "MP3 Files");
		addChoosableFileFilter(mp3);
		setFileFilter(mp3);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		setSelectedFile(new File(""));
		int returnVal = showSaveDialog(vidFrame);

		if (returnVal == APPROVE_OPTION) {
			if (new File(getSelectedFile() + ".mp3").exists()) {
				if (overwriteFile() == true) {
					return getSelectedFile().getAbsolutePath();
				} else {
					setSelectedFile(new File(""));
				}
			} else if (!getSelectedFile().getName().trim().equals("")) {
				return getSelectedFile().getAbsolutePath();
			}
		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(vidFrame, errorMessage);
		}

		return "";
	}

	/**
	 * Allow user to choose the default working directory
	 * 
	 * @param startUp
	 *            - true at application start up - false if default working
	 *            directory has been previously selected
	 */
	public void setDefaultDirectoy(boolean startUp) {
		// only allow user to choose directories
		addChoosableFileFilter(dir);
		setFileFilter(dir);
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);

		// If user has previously selected and wants to change current working
		// directory
		if (startUp == false) {
			setCurrentDirectory(new File(vidFrame.getDefPathDirect()));
		} else {
			JOptionPane.showMessageDialog(vidFrame,
					"Please Set the Default Working Directory");
		}

		// set dialog title
		setDialogTitle("Choose Default Working Directory");

		int returnDirect = showOpenDialog(vidFrame);
		if (returnDirect == JFileChooser.APPROVE_OPTION) {
			vidFrame.setDefPathDirect(getSelectedFile().getAbsolutePath());
		} else {// If starting up and user does not choose then set user
				// directory as default
			if (startUp == true) {
				vidFrame.setDefPathDirect(System.getProperty("user.dir"));
				JOptionPane.showMessageDialog(
						vidFrame,
						"No Workspace was Chosen, "
								+ vidFrame.getDefPathDirect()
								+ " has been set as the default directory");

			}
		}
	}

	/**
	 * check if user would like to overwrite an existing file with the same name
	 * as their input
	 * 
	 * @return
	 */
	private boolean overwriteFile() {
		int overwrite = JOptionPane.showConfirmDialog(vidFrame, fileOverWrite,
				"Overwrite File", JOptionPane.YES_NO_OPTION);
		if (overwrite == JOptionPane.YES_OPTION) {
			return true;
		}else{
			JOptionPane.showMessageDialog(vidFrame, fileNotCreate);
		}
		return false;
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
