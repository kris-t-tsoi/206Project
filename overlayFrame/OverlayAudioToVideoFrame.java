package overlayFrame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.plaf.SplitPaneUI;

import fileChoosing.UserFileChoose;
import overlayFrame.addAudioTrackPanel.AudioData;
import overlayFrame.addAudioTrackPanel.AudioToAddPanel;
import overlayFrame.audioTable.AudioTableFrame;
import sharedLabels.NameLabel;
import sharedLabels.TimeLabel;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

public class OverlayAudioToVideoFrame extends JFrame {

	final OverlayAudioToVideoFrame thisFrame = this;
	ArrayList<AudioData> audioTrackList;
	JPanel contentPane;
	JLabel vidName;
	TimeLabel vidDuration;
	Font titleFont = new Font("Tahoma", Font.BOLD, 18);
	UserFileChoose fileChose;
	private boolean isOpen;

	public boolean isOpen() {
		return isOpen;
	}

	public OverlayAudioToVideoFrame(final MediaPlayerJFrame video) {
		super("Overlay Video");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 300, 700, 400);
		setMinimumSize(new Dimension(700, 300));
		setVisible(true);
		isOpen = true;

		contentPane = new JPanel();
		setContentPane(contentPane);

		audioTrackList = video.getAudioTrackList();

		JLabel vidTitleLbl = new JLabel("Video :");
		vidTitleLbl.setFont(titleFont);
		vidName = new NameLabel();
		vidName.setText(video.getCurrentVidName().getText());
		JLabel duraTitleLbl = new JLabel("Duration :");
		duraTitleLbl.setFont(titleFont);
		vidDuration = new TimeLabel();
		vidDuration.setText(video.getVidTotalTime().getText());

		/**
		 * opens a frame with list of audiotracks that have been added
		 */
		JButton audioListBtn = new JButton("Get List of Added Audiotracks");
		audioListBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AudioTableFrame(video);
			}
		});

		AudioToAddPanel addAudioTrack = new AudioToAddPanel(video,
				audioTrackList, thisFrame);

		// overlay video button
		final OverlayVidAndAudioButton overlayVidBtn = new OverlayVidAndAudioButton();
		overlayVidBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check there is a video that has been selected
				if (video.getVideoPath() == null) {
					JOptionPane.showMessageDialog(thisFrame,
							"No Video has Currently Been Choosen");
				} else if (audioTrackList.size() == 0) {	//check audiotracks have been added
					JOptionPane.showMessageDialog(thisFrame,
							"No Audiotracks have been Added");
				} else {
					fileChose = new UserFileChoose(video);
					String name = fileChose.saveVideo();
					if (!name.equals("")) {	//check user wants to create a video
						overlayVidBtn.overlayVideo(audioTrackList, video, name);
					}
				}

			}
		});

		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[7px,grow 0,shrink 0][257px,grow, shrink][7px,grow 0,shrink 0][258px,grow, shrink]"
								+ "[7px,grow 0,shrink 0][257px,grow, shrink][7px,grow 0,shrink 0]", // Column
																									// Constraints
						"[5px][30px][5px][30px][5px][150px,grow, shrink]")); // Row
		// Constraints
		add(vidTitleLbl, "cell 1 1 ,grow");
		add(vidName, "cell 1 1 5 2,grow");
		add(duraTitleLbl, "cell 1 3 ,grow");
		add(vidDuration, "cell 1 3 ,grow");
		add(audioListBtn, "cell 4 3,grow");
		add(overlayVidBtn, "cell 4 1 ,grow");
		add(addAudioTrack, "cell 0 5 6 0 ,grow");
		setVisible(true);

	}

	/**
	 * When frame is closed then variable isOpen becomes false
	 */
	private void closing() {
		thisFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isOpen = false;
			};
		});
	}

	/**
	 * This method replaces the audio of a video with the audio given by mp3Path
	 * It first asks for a valid output file name, and if it is valid it sets
	 * the processing label to say "Processing..." and then finally uses the
	 * label's replaceAudio method to replace the audio.
	 * 
	 * @param label
	 * @param mp3Path
	 */
	/*
	 * private void replaceAudio(AbstractReplaceAudioLabel label, String
	 * mp3Path) { String videoPath = getVideoPath(); if (videoPath != null &&
	 * mp3Path != null) { String outputFile = (String)
	 * JOptionPane.showInputDialog(this,
	 * "Please enter a name for the output file", "Output file name",
	 * JOptionPane.INFORMATION_MESSAGE); if (outputFile != null) {
	 * lblProcessing.setText(PROCESS_TEXT); label.replaceAudio(mp3Path,
	 * videoPath, outputFile); } else { JOptionPane.showMessageDialog(this,
	 * "Error: output file name cannot be blank."); } } else {
	 * JOptionPane.showMessageDialog(this,
	 * "Please select a video and/or and mp3 file."); } }
	 */

}
