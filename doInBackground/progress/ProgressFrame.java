package doInBackground.progress;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

public class ProgressFrame extends JFrame {

	JPanel contentPane;
	JLabel percentProgress;
	ProgressBar processBar;

	public ProgressFrame(String title, String backOperation) {
		super(title);
		setSize(250, 300);
		setVisible(true);
		setResizable(false);

		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JLabel nameLbl = new JLabel(backOperation);
		percentProgress = new JLabel("0 % Finished");
		//progressBar = new JProgressBar(0, task.getLengthOfTask());

		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[5px,grow 0,shrink 0][120px,grow 0, shrink 0][5px,grow 0,shrink 0]", // Column
																								// Constraints
						"[30px][100px][40px][100px][30px]")); // Row Constraints

		contentPane.add(nameLbl, "cell 1 1, alignx center");
		contentPane.add(percentProgress, "cell 1 2, alignx center,aligny bottom");
		
		setVisible(true);

	}

}
