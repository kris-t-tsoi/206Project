package doInBackground;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import doInBackground.progress.ProgressFrame;

public class BackgroundUse extends SwingWorker<Void, Void> {
	private String cmd;
	private UseTerminalCommands termCmd;

	public BackgroundUse(String line) {

		termCmd = new UseTerminalCommands();
		cmd = line;
	}

	@Override
	protected Void doInBackground() throws Exception {
		termCmd.terminalCommandVoid(cmd);
		return null;
	}

}
