package overlayFrame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

import overlayFrame.addAudioTrackPanel.AudioData;
import overlayFrame.addAudioTrackPanel.AudioToAddPanel;
import sharedLabels.TimeLabel;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

public class OverlayAudioToVideoFrame extends JFrame {

	final OverlayAudioToVideoFrame thisFrame = this;
	ArrayList<AudioData> audioTrackList;
	JPanel contentPane;
	JLabel vidName;
	TimeLabel vidDuration;
	JCheckBox removeVideoAudio;
	Font titleFont = new Font("Tahoma", Font.BOLD, 20);

	// TODO button to show table of all added audio
	// TODO Add audio track added to table
	// TODO play audio

	// TODO Table frame show all added audio with their name start time and end
	// time

	public OverlayAudioToVideoFrame(final MediaPlayerJFrame video) {
		super("Overlay Video");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 400, 800, 400);
		setMinimumSize(new Dimension(700, 300));
		setVisible(true);		
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		audioTrackList = video.getAudioTrackList();

		JLabel vidTitleLbl = new JLabel("Video :");
		vidTitleLbl.setFont(titleFont);
		vidName = new JLabel(video.getCurrentVideo());
		JLabel duraTitleLbl = new JLabel("Duration :");
		vidDuration = video.getVidTotalTime();

		// Remove audio checkbox
		removeVideoAudio = new JCheckBox("Remove Video's Audio Track", false);

		AudioToAddPanel addAudioTrack = new AudioToAddPanel(video,audioTrackList);

		//TODO move this out to table frame
		// overlay video button
		final OverlayVidAndAudioButton overlayVidBtn = new OverlayVidAndAudioButton();
		overlayVidBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO allow user to choose where to say file

				String mp4Name = JOptionPane.showInputDialog(thisFrame,
						"Enter a name for the mp4 file");

				if (((mp4Name != null) && !mp4Name.startsWith(" "))||audioTrackList.size()==0||video.getVideoPath()==null) {
					String path = overlayVidBtn.overlayVideo(audioTrackList,
							video, mp4Name);
				}else{
					//TODO message saying missing video or audio or invlaid mp4 name
				}
			}
		});


		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[7px,grow 0,shrink 0][257px,grow, shrink][7px,grow 0,shrink 0][258px,grow, shrink]"
								+ "[7px,grow 0,shrink 0][257px,grow, shrink][7px,grow 0,shrink 0]", // Column
																									// Constraints
						"[5px][30px][30px][5px][200px,grow, shrink]")); // Row
		// Constraints
		add(vidTitleLbl, "cell 1 1 ,grow");
		add(vidName, "cell 1 1 5 2,grow");
		add(duraTitleLbl, "cell 1 2 ,grow");
		add(vidDuration, "cell 1 2 ,grow");
		add(removeVideoAudio, "cell 4 2,grow");
		add(overlayVidBtn, "cell 4 1 ,grow");
		add(addAudioTrack, "cell 0 4 6 0 ,grow");
		setVisible(true);
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
