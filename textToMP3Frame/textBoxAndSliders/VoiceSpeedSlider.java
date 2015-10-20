package textToMP3Frame.textBoxAndSliders;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class VoiceSpeedSlider extends JSlider {

	public VoiceSpeedSlider(){
		super(1, 29,10);
		setMajorTickSpacing(10);
		setMinorTickSpacing(1);
		setPaintTicks(true);
		setSnapToTicks(true);
		

		//Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable();
		labelTable.put( new Integer( 1 ), new JLabel("Slow\n(0.1)") );
		labelTable.put( new Integer( 10 ), new JLabel("Normal\n(1)") );
		labelTable.put( new Integer( 29 ), new JLabel("Fast\n(2.9)") );
		setLabelTable( labelTable );
		setPaintLabels(true);
	}
	
}
