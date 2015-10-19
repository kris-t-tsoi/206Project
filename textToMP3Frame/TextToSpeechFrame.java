package textToMP3Frame;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class TextToSpeechFrame extends JFrame {

	public TextToSpeechFrame() {
		
		super("Text to MP3");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 100, 600, 300);
		setVisible(true);
		
	}
	
}
