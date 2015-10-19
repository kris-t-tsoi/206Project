package overlayFrame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import net.miginfocom.swing.MigLayout;

public class OverlayAudioToVideo extends JFrame {

	final OverlayAudioToVideo thisFrame = this;
	JPanel contentPane;

	public OverlayAudioToVideo() {
		super("Overlay Video");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 400, 600, 300);
		setVisible(true);

		contentPane = new JPanel();
		setContentPane(contentPane);

		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[4px,grow 0,shrink 0][196px,grow, shrink][4px,grow 0,shrink 0][196px,grow, shrink]"
								+ "[4px,grow 0,shrink 0][196px,grow, shrink][4px,grow 0,shrink 0]", // Column
																									// Constraints
						"[70px][70px,grow, shrink][70px][70px]")); // Row
																	// Constraints

		setVisible(true);

	}

}
