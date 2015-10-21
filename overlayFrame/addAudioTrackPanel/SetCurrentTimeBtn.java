package overlayFrame.addAudioTrackPanel;

import javax.swing.JButton;

import mediaMainFrame.MediaPlayerJFrame;

public class SetCurrentTimeBtn extends JButton {
	
	MediaPlayerJFrame currentVid;

	public SetCurrentTimeBtn(MediaPlayerJFrame video) {
		super();
		currentVid = video;
		setText("Set Start to Current Time");
		setToolTipText("Sets start time of audiotrack to the current video time");
	}
	
	String currentVideoTime(){
		return "";
		
	}
	
}
