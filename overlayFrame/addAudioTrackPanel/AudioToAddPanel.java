package overlayFrame.addAudioTrackPanel;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fileChoosing.UserFileChoose;
import sharedLabels.NameLabel;
import sharedLabels.TimeLabel;
import textToMP3Frame.TextToSpeechFrame;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

public class AudioToAddPanel extends JPanel {
	
	//Panels and Frames
	AudioToAddPanel thisPane;
	final MediaPlayerJFrame mediaPlayerFrame;
	TextToSpeechFrame create;
	
	//file choosing
	final private UserFileChoose fileChoose;
	private String mp3Path;
	
	//Time Labels and textfields
	private TimeLabel durationLbl;
	private NameLabel mp3NameLbl;
	private JLabel startLbl;
	private JLabel endLbl;
	private TimeLabel endTime;
	private JTextField startMin;
	private JTextField startSec;
	private JTextField startMili;
	
	//Buttons
	private SelectMP3Btn selectAudio;
	private CreateMp3ForAudioPanelBtn createAudio;

	//Constant title name for TextToSpeechFrame
	private final String createMP3Title = "Created MP3 To Overlay";

	public String getMp3Path() {
		return mp3Path;
	}

	public void setMp3Path(String mp3Path) {
		this.mp3Path = mp3Path;
	}

	public JTextField getStartMin() {
		return startMin;
	}
	public JTextField getStartSec() {
		return startSec;
	}
	
	
	public JTextField getStartMili() {
		return startMili;
	}

	public AudioToAddPanel(MediaPlayerJFrame mainFrame) {
		thisPane = this;
		mediaPlayerFrame = mainFrame;
		setSize(700, 150);
		fileChoose = new UserFileChoose(mediaPlayerFrame);
		
		JLabel mp3TitleLbl = new JLabel("MP3 :");
		mp3NameLbl = new NameLabel();
		JLabel duraTitleLbl = new JLabel("Duration :");
		durationLbl = new TimeLabel();

		startLbl = new JLabel("Start [MM:SS.ms]:");
		startMin = new JTextField("00");
		JLabel semiCol = new JLabel(":");
		startSec = new JTextField("00");
		JLabel dot = new JLabel(".");
		startMili = new JTextField("00");
		endLbl = new JLabel("End: ");

		// select existing MP3
		selectAudio = new SelectMP3Btn();
		selectAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp3Path = fileChoose.chooseMP3Path(thisPane);
				mp3NameLbl.setText(mp3NameLbl.getFileName(mp3Path));
				getMP3Information(mp3Path);
			}
		});

		// Create a new MP3
		createAudio = new CreateMp3ForAudioPanelBtn();
		createAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				create = new TextToSpeechFrame(createMP3Title, thisPane);

				// /TODO doesnt quite work
				// while (create.isActive()){
				// mp3Path = create.getCreatedMP3Path();
				// }
				// mp3NameLbl.setText(mp3NameLbl.getFileName(mp3Path));
			}
		});

		
		//TODO set restriction on textfields
		
		
		setLayout(new MigLayout(
				"", // Layout Constraint
				"[3px,grow 0,shrink 0][300px,grow,shrink][3px,grow 0,shrink 0][250px,grow,shrink]"
						+ "[3px,grow 0,shrink 0][150px,grow,shrink][3px,grow 0,shrink 0]", // Column
																							// Constraints
				"[5px][40px][40px][40px]")); // Row Constraints
		
		add(new JSeparator(SwingConstants.HORIZONTAL), "cell 0 0 6 0 ,grow");
		add(mp3TitleLbl, "cell 1 1 ,grow");
		add(mp3NameLbl, "cell 1 1 ,grow");
		add(duraTitleLbl, "cell 3 1 ,grow");
		add(durationLbl, "cell 3 1 ,grow");
		add(selectAudio, "cell 5 1 ,grow");
		add(createAudio, "cell 5 2 ,grow");
		add(startLbl, "cell 1 2 ,grow");
		add(startMin, "cell 1 2 ,grow");
		add(semiCol, "cell 1 2 ,grow");
		add(startSec, "cell 1 2 ,grow");
		add(dot, "cell 1 2 ,grow");
		add(startMili, "cell 1 2 ,grow");
		add(endLbl, "cell 3 2 ,grow");
		setVisible(true);

		// TODO Play mp3 file to listen
		
		
		
		
		// TODO work out mp3 end time

	}

	// TODO get duration and name of MP3
	private void getMP3Information(String mp3Path) {
		durationLbl.findDuration(mp3Path);
	}

}
