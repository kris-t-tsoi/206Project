package guiComponents;

import javax.swing.JLabel;

import terminal.UseTerminalCommands;

public class VideoTotalTimeLabel extends JLabel {

	UseTerminalCommands termCommand = new UseTerminalCommands();

	public VideoTotalTimeLabel() {
		super();
		setText("00:00:00.00");
	}

	// TODO get video time

	// prints Duration: 00:01:00.04, start: 0.000000, bitrate: 438 kb/s
	// .split("[^0-9:.]")
	// ffprobe -i big_buck_bunny_1_minute.avi -show_format 2>&1 | grep Duration

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
