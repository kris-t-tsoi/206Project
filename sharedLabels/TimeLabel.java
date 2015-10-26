package sharedLabels;

import javax.swing.JLabel;

import doInBackground.UseTerminalCommands;

public class TimeLabel extends JLabel {

	UseTerminalCommands termCommand = new UseTerminalCommands();

	public TimeLabel() {
		super();
		setText("00:00.00");
	}

	/**
	 * finds the duration of the videoFrame and changes label to the total duration
	 * time
	 * 
	 * @param videoPath
	 */
	public double findDuration(String path) {
		;
		String inputLine = termCommand.terminalCommandString("ffprobe -i \""
				+ path + "\" -show_format 2>&1 | grep Duration");
		if (inputLine != null) {
			String[] words = inputLine.split("[ ,]");
			for (int i = 0; i < words.length; i++) {
				if (words[i].equals("Duration:")) {
					String[] times = words[i + 1].split("[:.]");
					String minSecMilli = times[1] + ":" + times[2] + "."
							+ times[3];
					setText(minSecMilli);
					return durationStringToInt(minSecMilli);
				}
			}

		}
		return 0;
	}
	
	public String addTimes(){
		
		return "";
	}
	
	
	/**
	 * 
	 * @param timeString
	 * @return time in milliSeconds
	 */
	public int  durationStringToInt (String timeString){
		//00:00.00	min:sec.mil
		String[] timeSplit = timeString.split("[:.]");	
		int milli  = (Integer.parseInt(timeSplit[2])*100);
		int sec =  Integer.parseInt(timeSplit[1])*1000; 
		int min =  Integer.parseInt(timeSplit[0])*60000; 		
		return (min+sec+milli);
		
	}
	
	/**
	 * Convert duration in millisec into string
	 * @param duration - of item in millisec
	 * @return string - in MM:SS.ms format
	 */
	public String durationDoubleToString(double duration){
		//convert into seconds
		duration = duration/1000;
		
		//et min sec and millisec
		int min = (int)(duration/360);
		int sec = (int)(duration%360);
		int milli = (int)((duration-min-sec)*100);
		
		//make into string
		String minString = min +"";
		String secString = sec +"";
		String milliString = milli +"";
		
		// if either min, sec or mill are <10 or >=0 then add 0 to beginning
		if(min<10 & min>=0){
			minString = "0"+minString;
		}
		
		if(sec<10 & sec>=0){
			secString = "0"+secString;
		}
		
		
		if(milli<10 & milli>=0){
			milliString = "0"+milliString;
		}
		
		return minString+":"+secString+"."+milliString;
		
	}
	
	
}
