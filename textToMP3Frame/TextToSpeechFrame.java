package textToMP3Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class TextToSpeechFrame extends JFrame {

	JPanel contentPane;
	
	public TextToSpeechFrame() {
		
		super("Text to MP3");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 100, 600, 300);
		setVisible(true);
		
		
		contentPane = new JPanel();		
		setContentPane(contentPane);
		
		contentPane.setLayout(new MigLayout(
				"", // Layout Constraint
				"[4px,grow 0,shrink 0][196px,grow 0,shrink 0][4px,grow 0,shrink 0][196px,grow 0,shrink 0]"
						+ "[4px,grow 0,shrink 0][4196x,grow 0,shrink 0][4px,grow 0,shrink 0]", // Column Constraints
				"[75px][75px,grow, shrink][75px][75px]")); // Row Constraints		
		
		
		
		setVisible(true);
		
	}
	
}
