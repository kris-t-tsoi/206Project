package doInBackground;

import javax.swing.SwingWorker;

/**
 * Does terminal commands in background
 * No progress bar
 * @author kristy
 *
 */
public class BackgroundUse extends SwingWorker<Void, Void> {
	private String cmd;
	private UseTerminalCommands termCmd;

	public BackgroundUse(String line) {
		//get command as input
		termCmd = new UseTerminalCommands();
		cmd = line;
	}

	@Override
	protected Void doInBackground() throws Exception {
		//run command in terminal
		termCmd.terminalCommandVoid(cmd);
		return null;
	}

}
