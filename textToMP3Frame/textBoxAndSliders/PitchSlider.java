package textToMP3Frame.textBoxAndSliders;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class PitchSlider extends JSlider {
	
	int[] monoPitchRange = {105,105};
	int[] lowPitchRange = {95,85};
	int[] neutPitchRange = {115,100};
	int[] highPitchRange = {145,115};
	

	public PitchSlider() {
		super(0, 3, 2);
		setMajorTickSpacing(1);
		setPaintTicks(true);
		setSnapToTicks(true);
		
		//Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("Monotone") );
		labelTable.put( new Integer( 1 ), new JLabel("Low") );
		labelTable.put( new Integer( 2 ), new JLabel("Neutral") );
		labelTable.put( new Integer( 3 ), new JLabel("High") );
		setLabelTable( labelTable );
		setPaintLabels(true);		
	}

	/**
	 * Finds the pitch range of the selected slider number
	 * @param slideNum - slider number
	 * @return array with pitch range {Start pitch, End pitch}
	 */
	public int[] findRange(int slideNum){
		switch (slideNum) {
		case 0:
			return monoPitchRange; 
		case 1:
			return lowPitchRange; 
		case 2:
			return neutPitchRange; 
		case 3:
			return highPitchRange; 
		}
		return null;
	}
	
}
