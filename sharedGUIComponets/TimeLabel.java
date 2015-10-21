package sharedGUIComponets;

import javax.swing.JLabel;

import doInBackground.UseTerminalCommands;

public class TimeLabel extends JLabel {

	UseTerminalCommands termCommand = new UseTerminalCommands();

	public TimeLabel() {
		super();
		setText("00:00.00");
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
			String[] words = inputLine.split("[ ,]");
			for (int i = 0; i < words.length; i++) {
				if(words[i].equals("Duration:")){	
					String[] times = words[i+1].split("[:.]");
					String minSecMilli = times[1]+":"+times[2]+"."+times[3];
					setText(minSecMilli);
					return getDurationFloatTime(minSecMilli);
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
		//00:00.00	min:sec.mil
		String[] timeSplit = timeString.split("[:.]");	
		double millli  = (Double.parseDouble(timeSplit[2])/10);
		double sec =  Double.parseDouble(timeSplit[1]); 
		double min =  Double.parseDouble(timeSplit[0])*60; 
		
		return min+sec;
		
	}
}
