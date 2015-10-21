package sharedGUIComponets;

import javax.swing.JLabel;

import doInBackground.UseTerminalCommands;

public class TimeLabel extends JLabel {

	UseTerminalCommands termCommand = new UseTerminalCommands();

	public TimeLabel() {
		super();
		setText("00:00:00");
	}

	/**
	 * finds the duration of the video and changes label to the total duration time
	 * @param videoPath
	 */
	public double findDuration(String path) {
		;
		String inputLine = termCommand.terminalCommandString("ffprobe -i \""
				+ path + "\" -show_format 2>&1 | grep Duration");		
		if (inputLine != null) {
			String[] words = inputLine.split("[ ,.]");
			for (int i = 0; i < words.length; i++) {
				if(words[i].equals("Duration:")){					
					setText(words[i+1]);
					return getDurationFloatTime(words[i+1]);
				}
			}
			
		}
		return 0;
	}
	
	/**
	 * 
	 * @param timeString
	 * @return time in seconds
	 */
	private double  getDurationFloatTime (String timeString){
		//00:00:00	hour:min:sec
		String[] timeSplit = timeString.split("[:]");		
		double sec =  Double.parseDouble(timeSplit[2]); 
		double min =  Double.parseDouble(timeSplit[1])*60; 
		double hour =  Double.parseDouble(timeSplit[0])*360; 
		
		return hour+min+sec;
		
	}
}
