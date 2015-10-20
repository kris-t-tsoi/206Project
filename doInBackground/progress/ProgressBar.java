package doInBackground.progress;

import javax.swing.JProgressBar;

public class ProgressBar extends JProgressBar {

	public ProgressBar(int min, int max) {
		super(min,max);
		setValue(0);
		setStringPainted(true);
	}
	
}
