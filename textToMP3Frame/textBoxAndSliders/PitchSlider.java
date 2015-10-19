package textToMP3Frame.textBoxAndSliders;

import javax.swing.JSlider;

public class PitchSlider extends JSlider {

	public PitchSlider() {
		super(0, 4, 2);
		setMajorTickSpacing(1);
		setSnapToTicks(true);
	}

}
