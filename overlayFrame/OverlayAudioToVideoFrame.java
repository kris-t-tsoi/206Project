package overlayFrame;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import overlayFrame.addAudioTrackPanel.AudioToAddPanel;
import sharedGUIComponets.TimeLabel;
import mediaMainFrame.AbstractReplaceAudioLabel;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

public class OverlayAudioToVideoFrame extends JFrame {

	final OverlayAudioToVideoFrame thisFrame = this;
	ArrayList<AudioToAddPanel> audioTrackList;
	JPanel contentPane;
	JLabel vidName;
	TimeLabel vidDuration;
	JCheckBox removeVideoAudio;
	JScrollPane scrollPanel;

	public OverlayAudioToVideoFrame(final MediaPlayerJFrame video) {
		super("Overlay Video");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 400, 800, 400);
		setVisible(true);

		contentPane = new JPanel();
		setContentPane(contentPane);
		
		//scrollPanel.add(addAudioTrack); //TODO get pane to fit in properly
		//scrollPanel.setVisible(true);
		audioTrackList = new ArrayList<AudioToAddPanel>();
		
		//TODO increase font size
		JLabel vidTitleLbl = new JLabel("Video :");
		vidName = new JLabel(video.getCurrentVideo());
		JLabel duraTitleLbl = new JLabel("Duration :");
		vidDuration = video.getVidTotalTime();

		
		//Remove audio checkbox
		removeVideoAudio = new JCheckBox("Remove Video's Audio Track", false);
		
		AudioToAddPanel addAudioTrack = new AudioToAddPanel(video);
		audioTrackList.add(addAudioTrack);		
		
		//Add new audiotrack button		
/*		AddAudioButton addAudioBtn = new AddAudioButton();
		addAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AudioToAddPanel addAudioTrack = new AudioToAddPanel(video);
				audioTrackList.add(addAudioTrack);
				//scrollPanel.add(addAudioTrack); //TODO get pane to fit in properly
				//scrollPanel.setVisible(true);
				//contentPane.add(addAudioTrack);
				//contentPane.setVisible(true);
				setVisible(true);
				
			}
		});

		*/
		
		//overlay video button
		OverlayVidAndAudioButton overlayVidBtn = new OverlayVidAndAudioButton();
		overlayVidBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Overlay video
				
			}
		});
		
		//Scroll pane for adding multiple audio
		scrollPanel = new JScrollPane();
		
		
		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[7px,grow 0,shrink 0][257px,grow, shrink][7px,grow 0,shrink 0][258px,grow, shrink]"
						+"[7px,grow 0,shrink 0][257px,grow, shrink][7px,grow 0,shrink 0]", // Column Constraints
						"[5px][30px][30px][30px][5px][200px,grow, shrink]")); // Row
																	// Constraints
		add(vidTitleLbl, "cell 1 1 ,grow");
		add(vidName, "cell 1 1 5 2,grow");
		add(duraTitleLbl, "cell 1 2 ,grow");
		add(vidDuration, "cell 1 2 ,grow");
		add(removeVideoAudio, "cell 4 2,grow");
//		add(addAudioBtn, "cell 1 3 ,grow");
		add(overlayVidBtn, "cell 4 3 ,grow");
		add(addAudioTrack, "cell 0 5 6 0 ,grow");
		//add(scrollPanel, "cell 0 4 6 0 ,grow");
		setVisible(true);
		
		
		//ffmpeg -i Video/big_buck_bunny_1_minute.avi -i MP3/haehah.mp3 -filter_complex [media number:channel]adelay=delayinMilisec4,amix=inputs=2 out.mp4

		
		
		//adds audio to beginning
		//ffmpeg -i Video/big_buck_bunny_1_minute.avi -i MP3/haehah.mp3 -filter_complex adelay=50000,amix=inputs=2 out.mp4

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
/*	private void replaceAudio(AbstractReplaceAudioLabel label, String mp3Path) {
		String videoPath = getVideoPath();
		if (videoPath != null && mp3Path != null) {
			String outputFile = (String) JOptionPane.showInputDialog(this,
					"Please enter a name for the output file",
					"Output file name", JOptionPane.INFORMATION_MESSAGE);
			if (outputFile != null) {
				lblProcessing.setText(PROCESS_TEXT);
				label.replaceAudio(mp3Path, videoPath, outputFile);
			} else {
				JOptionPane.showMessageDialog(this,
						"Error: output file name cannot be blank.");
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Please select a video and/or and mp3 file.");
		}
	}
*/	
}
