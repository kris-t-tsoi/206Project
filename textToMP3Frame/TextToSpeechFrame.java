package textToMP3Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import textToMP3Frame.buttons.CreateMP3Btn;
import textToMP3Frame.buttons.PlayTextBtn;
import textToMP3Frame.textBoxAndSliders.TextToMP3TextBox;
import net.miginfocom.swing.MigLayout;

public class TextToSpeechFrame extends JFrame {
	
	final TextToSpeechFrame thisFrame = this;
	JPanel contentPane;
	PlayTextBtn playText;
	CreateMP3Btn createMP3;
	TextToMP3TextBox userText;
	
	
	public TextToSpeechFrame() {
		
		super("Text to MP3");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 100, 600, 300);
		setVisible(true);
		
		
		contentPane = new JPanel();		
		setContentPane(contentPane);
		
		playText = new PlayTextBtn();
		playText.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// If the text is under the allowed limit, speak the text
				if (userText.checkTxtLength()) {
					userText.sayWithFestival(userText.getText());
				} else {
					JOptionPane.showMessageDialog(thisFrame, main.MediaPlayerJFrame.ERROR_WORD_LIMIT_MESSAGE);
				}
				
			}
		});
		createMP3 = new CreateMP3Btn();
		userText = new TextToMP3TextBox();
		
		
		JLabel titleLbl = new JLabel("Type in Text to Synthesis into MP3 Audio");
		JLabel speedLbl = new JLabel("Voice Speed");
		JLabel pitchLbl = new JLabel("Vocal Pitch");
		

		contentPane.setLayout(new MigLayout(
				"", // Layout Constraint
				"[4px,grow 0,shrink 0][196px,grow, shrink][4px,grow 0,shrink 0][196px,grow, shrink]"
						+ "[4px,grow 0,shrink 0][196px,grow, shrink][4px,grow 0,shrink 0]", // Column Constraints
				"[70px][70px,grow, shrink][70px][70px]")); // Row Constraints		
		
		
		contentPane.add(titleLbl, "cell 1 0 ,grow");
		contentPane.add(userText, "cell 1 1 5 1,grow");
		contentPane.add(speedLbl, "cell 1 2,grow");
		contentPane.add(pitchLbl, "cell 1 3,grow");
		//TODO Add in Sliders
		
		contentPane.add(playText, "cell 5 2,grow");
		contentPane.add(createMP3, "cell 5 3,grow");
		setVisible(true);
		
	}
	
}
