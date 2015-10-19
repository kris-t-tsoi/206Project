package textToMP3Frame.textBoxAndSliders;

import javax.swing.JSlider;

public class VoiceSpeedSlider extends JSlider {

	VoiceSpeedSlider(){
		super(0, 30,10);
		setMajorTickSpacing(10);
		setMinorTickSpacing(1);
		setSnapToTicks(true);
	}
	
}
