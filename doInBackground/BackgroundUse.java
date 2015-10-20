package doInBackground;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class BackgroundUse extends SwingWorker<Void, Void>{
	private String cmd;
	private UseTerminalCommands termCmd;
	
	public BackgroundUse(String line) {
		termCmd = new UseTerminalCommands();
		cmd = line;		
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		System.out.println(cmd);
		termCmd.terminalCommandVoid(cmd);
		return null;
	}

	@Override
	protected void done() {
		
	}
	
}
