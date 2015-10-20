package overlayFrame.addAudioTrackPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainFrameGUI.time.TotalTimeLabel;
import net.miginfocom.swing.MigLayout;

public class AudioToAddPanel extends JPanel {
	
	TotalTimeLabel durationLbl;
	JLabel mp3NameLbl;
	
	public AudioToAddPanel() {
		
		setSize(700, 150);
		
		JLabel mp3TitleLbl = new JLabel("MP3 :");
		mp3NameLbl = new JLabel("");
		JLabel duraTitleLbl = new JLabel("Duration :");
		durationLbl = new TotalTimeLabel();
		
		//select existing MP3
		SelectMP3Btn selectAudio = new SelectMP3Btn();
		
		//Create a new MP3
		CreateMp3ForAudioPanelBtn createAudio = new CreateMp3ForAudioPanelBtn();
		
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
		
		
	}

	
	//TODO get duration and name of MP3	
	private void getMP3Information(){
		
	}
	
}
