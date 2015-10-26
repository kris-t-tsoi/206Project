package doInBackground.festivalSpeak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class plays text in festival and
 * gets the PID of the festival command
 * @author kristy
 *
 */
public class FestivalTerminal {

	ProcessBuilder probuild;
	Process pb;
	private int festivalID;
	private String[] processID;
	
	
	/**
	 * start the speech using terminal and get pid of speech
	 * @param cmd
	 */
	void start(String cmd){
		probuild = new ProcessBuilder("bash", "-c", cmd);
		probuild.redirectErrorStream(true);

		try {
			// start process and wait for one second
			pb = probuild.start();

			// Credit - Nasser Lecture 8
			if (pb.getClass().getName().equals("java.lang.UNIXProcess")) {

				Field f = pb.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				festivalID=f.getInt(pb);
			}
			
			
		} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * cancels festival speaking
	 */
	 void cancel() {
		// get pid for all festival children processes
		String cmd = "pstree -lp " + festivalID + "| grep festival";
		probuild = new ProcessBuilder("bash", "-c", cmd);
		try {
			pb = probuild.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					pb.getInputStream()));
			String inline = in.readLine();

			// get numbers from pstree
			// credit:
			// http://stackoverflow.com/questions/2367381/how-to-extract-numbers-from-a-string-and-get-an-array-of-ints
			Pattern p = Pattern.compile("-?\\d+");
			Matcher m = p.matcher(inline);
			processID = new String[7];
			int i = 0;
			while (m.find()) {
				processID[i] = m.group();
				i++;
			}

			// kill all festival processes for this backgroundTask object
			for (i = (i - 1); i > 0; i--) {
				cmd = "kill -9 " + processID[i];
				probuild = new ProcessBuilder("bash", "-c", cmd);
				probuild.start().waitFor();
			}

		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}

		// destroy process and set process as done
		pb.destroy();

	}
	
	
}
