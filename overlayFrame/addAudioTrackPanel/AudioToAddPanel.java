package overlayFrame.addAudioTrackPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fileChoosing.UserFileChoose;
import sharedGUIComponets.NameLabel;
import sharedGUIComponets.TimeLabel;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

public class AudioToAddPanel extends JPanel {
	
	AudioToAddPanel thisFrame;
	final MediaPlayerJFrame mediaPlayerFrame;
	private TimeLabel durationLbl;
	private NameLabel mp3NameLbl;
	private SelectMP3Btn selectAudio;
	private CreateMp3ForAudioPanelBtn createAudio;
	final private UserFileChoose fileChoose = new UserFileChoose();
	private  String mp3Path;
	
	public AudioToAddPanel(MediaPlayerJFrame mainFrame) {
		thisFrame = this;
		mediaPlayerFrame = mainFrame;
		setSize(700, 150);
		
		JLabel mp3TitleLbl = new JLabel("MP3 :");
		mp3NameLbl = new NameLabel();
		JLabel duraTitleLbl = new JLabel("Duration :");
		durationLbl = new TimeLabel();
		
		//select existing MP3
		selectAudio = new SelectMP3Btn();
		selectAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp3Path = fileChoose.chooseMP3Path(mediaPlayerFrame);
				mp3NameLbl.setText(mp3NameLbl.getFileName(mp3Path));
			}
		});
		
		//Create a new MP3
		createAudio = new CreateMp3ForAudioPanelBtn();
		createAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO open text to speech frame
				
			}
		});
		
		setLayout(new MigLayout(
				"", // Layout Constraint
				"[3px,grow, shrink][271px,grow 0,shrink 0][3px,grow, shrink][271px,grow 0,shrink 0]"
				+"[3px,grow, shrink][146px,grow 0,shrink 0][3px,grow, shrink]", // Column Constraints
				"[60px][60px][30px]")); // Row Constraints			
		
		//add(titleLbl, "cell 1 0 5 1,grow");
		add(mp3TitleLbl, "cell 1 0 ,grow");
		add(mp3NameLbl, "cell 1 0 ,grow");
		add(duraTitleLbl, "cell 3 0 ,grow");
		add(durationLbl, "cell 3 0 ,grow");
		add(selectAudio, "cell 5 0 ,grow");
		add(createAudio, "cell 5 1 ,grow");
		setVisible(true);
		
		
		//TODO Play mp3 file to listen
		//TODO allow user to set start time
		//TODO work out mp3 end time
		
		
	}
	
	//TODO get duration and name of MP3	
	private void getMP3Information(String mp3Path){
		durationLbl.findDuration(mp3Path);
	}
	
}
