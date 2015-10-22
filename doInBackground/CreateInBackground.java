package doInBackground;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import doInBackground.progress.ProgressFrame;

public class CreateInBackground extends SwingWorker<Void, Void>  {
	private String cmd;
	private UseTerminalCommands termCmd;
	private ProgressFrame progressFrame;
	Timer time;
	
	
	public CreateInBackground(String line) {
		progressFrame = new ProgressFrame();
		termCmd = new UseTerminalCommands();
		cmd = line;
	}

	@Override
	protected Void doInBackground() throws Exception {

		termCmd.terminalCommandVoid(cmd);
		return null;
	}

	@Override
	protected void process(List<Void> chunks) {
		super.process(chunks);
	}

	@Override
	protected void done() {
		JOptionPane.showMessageDialog(progressFrame, "Done !");
		progressFrame.dispose();
	}

}
