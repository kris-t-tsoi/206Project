package doInBackground;

import javax.swing.SwingWorker;

public class DoInBackground extends SwingWorker<Void, Void> {

	private String cmd;
	private UseTerminalCommands term;

	public DoInBackground(String command) {
		cmd = command;
		term = new UseTerminalCommands();
	}

	@Override
	protected Void doInBackground() throws Exception {
		// Execute command
		term.terminalCommandVoid(cmd);
		return null;
	}

}
