package mediaMainFrame.buttonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import fileChoosing.UserFileChoose;
import overlayMedia.OverlayVidAndAudioButton;
import sharedLabels.NameLabel;
import net.miginfocom.swing.MigLayout;
import mediaMainFrame.MediaPlayerJFrame;

public class FunctionButtonPane extends JPanel {

	MediaPlayerJFrame mediaPlayerFrame;

	// Dynamic labels for user information
	private static final String CURRENT_VIDEO_TEXT = "Currently Selected Video: ";
	JLabel curVidTitle;
	private NameLabel currentVidName;
	UserFileChoose fileChoose;

	public NameLabel getCurrentVidName() {
		return currentVidName;
	}

	public FunctionButtonPane(final MediaPlayerJFrame mainFrame) {
		mediaPlayerFrame = mainFrame;
		setSize(100, 175);

		// Labels that displays the currently selected videoFrame
		curVidTitle = new JLabel(CURRENT_VIDEO_TEXT);
		curVidTitle.setFont(mediaPlayerFrame.TITLE_FONT);
		currentVidName = new NameLabel();

		// Button to create MP3 from text
		JButton makeMP3Btn = new JButton();//TODO change to choose video file
		makeMP3Btn.setText("Text to Speech");
		makeMP3Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerFrame.ttsFrame.setVisible(true);
			}
		});

		// Button to open list of audiotracks that have been added
		JButton tableBtn = new JButton();
		tableBtn.setText("List of Audiotracks That Have Been Added");
		tableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerFrame.audTableFrame.setVisible(true);
			}
		});

		fileChoose = new UserFileChoose(mediaPlayerFrame);
		// overlay videoFrame button
		final OverlayVidAndAudioButton overlayVidBtn = new OverlayVidAndAudioButton();
		overlayVidBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check there is a videoFrame that has been selected
				if (mediaPlayerFrame.getVideoPath() == null) {
					JOptionPane.showMessageDialog(mediaPlayerFrame,
							"No Video has Currently Been Choosen");
				} else if (mediaPlayerFrame.getAudioTrackList().size() == 0) { // check
																				// audiotracks
					// have been added
					JOptionPane.showMessageDialog(mediaPlayerFrame,
							"No Audiotracks have been Added");
				} else {
					String name = fileChoose.saveVideo();
					if (!name.equals("")) { // check user wants to create a
											// videoFrame
						overlayVidBtn.overlayVideo(
								mediaPlayerFrame.getAudioTrackList(),
								mediaPlayerFrame, name);
					}
				}

			}
		});

		setLayout(new MigLayout(
				"",
				"[3px,grow 0,shrink 0][300px,grow,shrink][3px,grow 0,shrink 0]",
				"[2px][20px][20px][2px][20px][20px][20px]"));

		add(new JSeparator(SwingConstants.HORIZONTAL), "cell 0 0 6 0 ,grow");

		// current videoFrame labels
		add(curVidTitle, "cell 1 1,growx");
		add(currentVidName, "cell 1 2,growx,aligny top");

		// make MP3, overlay and list of audiotrack buttons
		add(makeMP3Btn, "cell 1 4 ,grow");
		add(tableBtn, "cell 1 5 ,grow");
		add(overlayVidBtn, "cell 1 6 ,grow");

	}
}
