package doInBackground.progress;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
/**
 * Creates frame with an indeterminate progress bar
 * @author kristy
 *
 */
public class ProgressFrame extends JFrame {

	JPanel contentPane;
	JLabel percentProgress;
	JProgressBar processBar;

	/**
	 * Creates a popup frame with process bar
	 */
	public ProgressFrame() {
		super("Working In Background");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 350, 500, 200);
		setResizable(false);	
		
		processBar = new JProgressBar(JProgressBar.HORIZONTAL);
		processBar.setIndeterminate(true);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JLabel nameLbl = new JLabel("Processing ... ");
		nameLbl.setFont(new Font("Tahoma", Font.BOLD,30));
		nameLbl.setForeground(Color.RED);

		contentPane
				.setLayout(new MigLayout(
						"", 
						"[5px,grow 0,shrink 0][120px,grow, shrink][5px,grow 0,shrink 0]",
						"[5px][100px][3px][100px][3px]")); 

		contentPane.add(nameLbl, "cell 1 1, alignx center,grow");
		contentPane.add(processBar, "cell 1 3, alignx center,grow");
		
		setVisible(true);
	}

}
