package overlayFrame.addAudioTrackPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fileChoosing.UserFileChoose;
import sharedGUIComponets.NameLabel;
import sharedGUIComponets.TimeLabel;
import textToMP3Frame.TextToSpeechFrame;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

public class AudioToAddPanel extends JPanel {

	AudioToAddPanel thisPane;
	final MediaPlayerJFrame mediaPlayerFrame;
	TextToSpeechFrame create;
	private TimeLabel durationLbl;
	private NameLabel mp3NameLbl;
	JLabel startLbl;
	JLabel endLbl;
	private SelectMP3Btn selectAudio;
	private CreateMp3ForAudioPanelBtn createAudio;
	final private UserFileChoose fileChoose = new UserFileChoose();
	private String mp3Path;
	private final String createMP3Title = "Created MP3 To Overlay";

	public String getMp3Path() {
		return mp3Path;
	}

	public void setMp3Path(String mp3Path) {
		this.mp3Path = mp3Path;
	}

	public AudioToAddPanel(MediaPlayerJFrame mainFrame) {
		thisPane = this;
		mediaPlayerFrame = mainFrame;
		setSize(700, 150);

		JLabel mp3TitleLbl = new JLabel("MP3 :");
		mp3NameLbl = new NameLabel();
		JLabel duraTitleLbl = new JLabel("Duration :");
		durationLbl = new TimeLabel();

		startLbl = new JLabel("Start [hh:mm:ss]:");

		endLbl = new JLabel("End: ");

		// select existing MP3
		selectAudio = new SelectMP3Btn();
		selectAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp3Path = fileChoose.chooseMP3Path(mediaPlayerFrame);
				mp3NameLbl.setText(mp3NameLbl.getFileName(mp3Path));
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

		setLayout(new MigLayout(
				"", // Layout Constraint
				"[3px,grow 0,shrink 0][300px,grow,shrink][3px,grow 0,shrink 0][250px,grow,shrink]"
						+ "[3px,grow 0,shrink 0][150px,grow,shrink][3px,grow 0,shrink 0]", // Column
																							// Constraints
				"[60px][60px][30px]")); // Row Constraints

		add(mp3TitleLbl, "cell 1 0 ,grow");
		add(mp3NameLbl, "cell 1 0 ,grow");
		add(duraTitleLbl, "cell 3 0 ,grow");
		add(durationLbl, "cell 3 0 ,grow");
		add(selectAudio, "cell 5 0 ,grow");
		add(createAudio, "cell 5 1 ,grow");
		add(startLbl, "cell 1 1 ,grow");
		add(endLbl, "cell 3 1 ,grow");
		setVisible(true);

		// TODO Play mp3 file to listen
		// TODO allow user to set start time
		// TODO work out mp3 end time

	}

	// TODO get duration and name of MP3
	private void getMP3Information(String mp3Path) {
		durationLbl.findDuration(mp3Path);
	}

}
