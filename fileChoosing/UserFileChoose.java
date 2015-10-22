package fileChoosing;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import overlayFrame.addAudioTrackPanel.AudioToAddPanel;
import mediaMainFrame.MediaPlayerJFrame;
import mediaMainFrame.videoControl.PlayButton;

public class UserFileChoose extends JFileChooser {

	MediaPlayerJFrame vidFrame;
	
	public UserFileChoose(MediaPlayerJFrame parentFrame) {
		vidFrame = parentFrame;
	}
	
	//TODO error handle user type in non existing file
	

	/**
	 * allows user to choose a video file
	 * video files allowed are .avi and .mp4
	 * @param parentFrame
	 * @param playBtn
	 * @return
	 */
	public String chooseVideoPath(JFrame parentFrame,
			PlayButton playBtn) {

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
		setCurrentDirectory(vidFrame.VIDEO_DIR_ABSOLUTE_PATH);
		int returnVal = showOpenDialog(parentFrame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// if user is already playing a video, then remove it
			if (vidFrame.getVideoPath() != null) {
				vidFrame.removeVideo(playBtn);
			}
			return getSelectedFile().getAbsolutePath();

		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(parentFrame,
					vidFrame.getErrorMessage());
		}

		return "";
	}

	/**
	 * Allows user to choose an .mp3 file
	 * @param parentFrame
	 * @return
	 */
	public String chooseMP3Path(JPanel parent) {
		// mp3 media filters
		FileFilter mp3 = new FileTypeFilter(".mp3", "MP3 Files");		
		addChoosableFileFilter(mp3);

		// remove all files filter
		setAcceptAllFileFilterUsed(false);
		
		// Start file search in current directory, and show mp3's
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"MP3 File", "mp3");
		setFileFilter(filter);
		setCurrentDirectory(vidFrame.getMp3DirAbsolutePath());

		int returnVal = showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return getSelectedFile().getAbsolutePath();
		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(parent,
					vidFrame.getErrorMessage());
		}

		return "";
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
