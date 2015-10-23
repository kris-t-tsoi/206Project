package doInBackground;

import javax.swing.SwingUtilities;

import overlayFrame.audioTable.AudioTableFrame;

public class UpdateRunnable implements Runnable {

	AudioTableFrame frame;
	
	public UpdateRunnable(AudioTableFrame aud) {
		frame = aud;
	}
	
	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//refresh the frame
				frame.repaint();
			}
		});

	}

}
