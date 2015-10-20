package doInBackground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class is used for terminal commands
 * @author kristy
 *
 */
public class UseTerminalCommands {

	/**
	 * Uses input as command for terminal
	 * returns void
	 * @param cmd - command input
	 */
	public void terminalCommandVoid (String cmd) {
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		Process process;
		try {
			process = builder.start();
			process.waitFor();
		} catch (IOException | SecurityException | IllegalArgumentException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Uses input as command for terminal
	 * @param cmd - command input
	 * @return String
	 */
	public String terminalCommandString (String cmd) {
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		Process process;
		try {
			process = builder.start();
			
			   BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
               process.waitFor();
               String line = reader.readLine();
               if (line!= null) {
                   return line;
               }			
						
		} catch (IOException | SecurityException | IllegalArgumentException | InterruptedException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
