package textToMP3Frame.textBoxAndSliders;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class PitchSlider extends JSlider {

	public PitchSlider() {
		super(0, 4, 2);
		setMajorTickSpacing(1);
		setPaintTicks(true);
		setSnapToTicks(true);
		
		//Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("Low") );
		labelTable.put( new Integer( 4 ), new JLabel("High") );
		setLabelTable( labelTable );
		setPaintLabels(true);
		
	}

}
