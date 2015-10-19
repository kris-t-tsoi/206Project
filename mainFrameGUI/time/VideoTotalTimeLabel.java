package mainFrameGUI.time;

import javax.swing.JLabel;

import terminal.UseTerminalCommands;

public class VideoTotalTimeLabel extends JLabel {

	UseTerminalCommands termCommand = new UseTerminalCommands();

	public VideoTotalTimeLabel() {
		super();
		setText("00:00:00.00");
	}

	/**
	 * finds the duration of the video and changes label to the total duration time
	 * @param videoPath
	 */
	public void findVideoDuration(String videoPath) {
		;
		String inputLine = termCommand.terminalCommandString("ffprobe -i \""
				+ videoPath + "\" -show_format 2>&1 | grep Duration");
		System.out.println("input " + inputLine);
		if (inputLine != null) {
			String[] words = inputLine.split("[ ,]");
			for (int i = 0; i < words.length; i++) {
				if(words[i].equals("Duration:")){					
					setText(words[i+1]);
				}
			}
			
		}
	}

}
