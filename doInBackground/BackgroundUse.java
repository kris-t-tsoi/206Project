package doInBackground;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class BackgroundUse extends SwingWorker<Void, Void>{
	private String cmd;
	private UseTerminalCommands termCmd;
	
	public BackgroundUse(String line) {
		cmd = line;
		try {
			doInBackground();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		termCmd.terminalCommandVoid (cmd);
		return null;
	}

	@Override
	protected void done() {
		
	}
	
}
