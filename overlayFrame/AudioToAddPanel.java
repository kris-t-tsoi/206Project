package overlayFrame;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class AudioToAddPanel extends JPanel {
	
	public AudioToAddPanel() {
		
		
		
		
		
		setLayout(new MigLayout(
				"", // Layout Constraint
				"[4px,grow 0,shrink 0][98px,grow 0, shrink 0][2px,grow 0,shrink 0][346px,grow, shrink]"
						+ "[4px,grow 0,shrink 0][196px,grow 0, shrink 0][4px,grow ,shrink ]", // Column Constraints
				"[70px][70px,grow, shrink][70px][70px]")); // Row Constraints		
		
		
		//add(titleLbl, "cell 1 0 5 1,grow");
		setVisible(true);
		
		
	}

}
