package doInBackground.progress;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
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
		setBounds(700, 350, 500, 400);
		setResizable(false);	
		
		processBar = new JProgressBar(JProgressBar.HORIZONTAL);
		processBar.setIndeterminate(true);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JLabel nameLbl = new JLabel("Processing ... ");
		nameLbl.setFont(new Font("Tahoma", Font.BOLD,30));
		nameLbl.setForeground(Color.BLUE);

		contentPane
				.setLayout(new MigLayout(
						"", 
						"[5px,grow 0,shrink 0][120px,grow, shrink][5px,grow 0,shrink 0]",
						"[5px][100px][10px][100px][5px]")); // Row Constraints

		contentPane.add(nameLbl, "cell 1 1, alignx center,grow");
		contentPane.add(processBar, "cell 1 3, alignx center,grow");
		
		setVisible(true);
	}

}
