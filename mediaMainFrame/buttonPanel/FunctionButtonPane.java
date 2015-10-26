package mediaMainFrame.buttonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import overlayMedia.OverlayVidAndAudioButton;
import sharedLabels.NameLabel;
import net.miginfocom.swing.MigLayout;
import mediaMainFrame.MediaPlayerJFrame;

@SuppressWarnings("serial")
/**
 * Pane contains add video button, button to open audioTrackTable
 * and overlayButton to overlay currently selected video with 
 * audio tracks in list
 * @author kristy
 *
 */
public class FunctionButtonPane extends JPanel {

	MediaPlayerJFrame mediaPlayerFrame;

	// Dynamic labels for user information
	private static final String CURRENT_VIDEO_TEXT = "Currently Selected Video: ";
	JLabel curVidTitle;
	private NameLabel currentVidName;

	// buttons
	JButton chooseVideo;
	JButton tableBtn;
	OverlayVidAndAudioButton overlayVidBtn;
	
	public NameLabel getCurrentVidName() {
		return currentVidName;
	}

	/**
	 * Panel with buttons for choosing video, opening table frame of audio
	 * tracks added and overlaying added audiotracks to the video
	 * 
	 * @param mainFrame
	 *            - frame with media component
	 */
	public FunctionButtonPane(MediaPlayerJFrame mainFrame) {
		mediaPlayerFrame = mainFrame;
		setSize(100, 175);

		// Labels that displays the currently selected videoFrame
		curVidTitle = new JLabel(CURRENT_VIDEO_TEXT);
		curVidTitle.setFont(mediaPlayerFrame.TITLE_FONT);
		currentVidName = new NameLabel();

		// setup select video,open table of audiotracks and overlaying video button
		setupChooseVideo();
		setupOpenTable();
		setupOverlay();
	

		setLayout(new MigLayout(
				"",
				"[3px,grow 0,shrink 0][300px,grow,shrink][3px,grow 0,shrink 0]",
				"[2px][20px][20px][2px][20px][20px][20px]"));

		add(new JSeparator(SwingConstants.HORIZONTAL), "cell 0 0 6 0 ,grow");

		// current video labels
		add(curVidTitle, "cell 1 1,growx");
		add(currentVidName, "cell 1 2,growx,aligny top");

		// make MP3, overlay and list of audiotrack buttons
		add(chooseVideo, "cell 1 4 ,grow");
		add(tableBtn, "cell 1 5 ,grow");
		add(overlayVidBtn, "cell 1 6 ,grow");

	}

	/**
	 * setup button to overlay video with added audiotracks
	 */
	private void setupOverlay() {		
		overlayVidBtn = new OverlayVidAndAudioButton();
		overlayVidBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// check there is a videoFrame that has been selected
				if (mediaPlayerFrame.getVideoPath() == null) {
					JOptionPane.showMessageDialog(mediaPlayerFrame,
							"No Video has Currently Been Choosen");
					
					//check audiotracks have been added
				} else if (mediaPlayerFrame.getAudioTrackList().size() == 0) { 
					JOptionPane.showMessageDialog(mediaPlayerFrame,
							"No Audiotracks have been Added");
				} else {
					String name = mediaPlayerFrame.fileChoose.saveVideo();
					
					//check if user wants to create a video
					if (!name.equals("")) { 
						overlayVidBtn.overlayVideo(
								mediaPlayerFrame.getAudioTrackList(),
								mediaPlayerFrame, name);
					}
				}

			}
		});
		
	}

	/**
	 * set up button that opens up table of added audiotracks
	 */
	private void setupOpenTable() {
		// Button to open list of audiotracks that have been added
		tableBtn = new JButton();
		tableBtn.setText("List of Audiotracks That Have Been Added");
		tableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerFrame.audTableFrame.setVisible(true);
			}
		});

	}

	/**
	 * sets up video selection button
	 */
	private void setupChooseVideo() {
		chooseVideo = new JButton();
		chooseVideo.setText("Choose Video");
		chooseVideo.setToolTipText("Choose Video to Play and Overlay");
		chooseVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerFrame.selectVideo(mediaPlayerFrame.getBtnPlay());
			}
		});
	}
}
