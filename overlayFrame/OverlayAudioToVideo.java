package overlayFrame;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.MediaPlayerJFrame;
import mainFrameGUI.time.VideoTotalTimeLabel;
import net.miginfocom.swing.MigLayout;

public class OverlayAudioToVideo extends JFrame {

	final OverlayAudioToVideo thisFrame = this;
	JPanel contentPane;
	JLabel vidName;
	VideoTotalTimeLabel vidDuration;
	JCheckBox removeVideoAudio;
	JScrollPane scrollPanel;

	public OverlayAudioToVideo(MediaPlayerJFrame video) {
		super("Overlay Video");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 400, 730, 500);
		setVisible(true);

		contentPane = new JPanel();
		setContentPane(contentPane);
		
		//TODO increase font size
		JLabel frameTitleLbl = new JLabel("Overlay Video and Audio");
		JLabel vidTitleLbl = new JLabel("Video :");
		vidName = new JLabel(video.getCurrentVideo());
		JLabel duraTitleLbl = new JLabel("Duration :");
		vidDuration = video.getVidTotalTime();

		
		//Remove audio checkbox
		removeVideoAudio = new JCheckBox("Remove Video's Audio Track", false);
		
		//Add new audiotrack button		
		AddAudioButton addAudioBtn = new AddAudioButton();
		
		
		//overlay video button
		OverlayVidAndAudioButton overlayVidBtn = new OverlayVidAndAudioButton();
		
		//Scroll pane for adding multiple audio
		scrollPanel = new JScrollPane();
		
		
		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[7px,grow 0,shrink 0][234px,grow, shrink][7px,grow 0,shrink 0][234px,grow, shrink]"
						+"[7px,grow 0,shrink 0][234px,grow, shrink][7px,grow 0,shrink 0]", // Column Constraints
						"[50px][40px][40px][50px][220px,grow, shrink]")); // Row
																	// Constraints
		//add(mediaPlayerComponent, "cell 0 0 11 1,grow");
		add(frameTitleLbl, "cell 1 0 5 1,grow");
		add(vidTitleLbl, "cell 1 1 ,grow");
		add(vidName, "cell 1 1 5 2,grow");
		add(duraTitleLbl, "cell 1 2 ,grow");
		add(vidDuration, "cell 1 2 ,grow");
		add(removeVideoAudio, "cell 4 2,grow");
		add(addAudioBtn, "cell 1 3 ,grow");
		add(overlayVidBtn, "cell 4 3 ,grow");
		add(scrollPanel, "cell 1 4 5 1 ,grow");
		setVisible(true);

	}

}
