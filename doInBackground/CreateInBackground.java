package doInBackground;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import doInBackground.progress.ProgressFrame;

/**
 * Class creates MP3 and MP4 files in the background
 * @author kristy
 *
 */
public class CreateInBackground extends SwingWorker<Void, Void>  {
	private String cmd;
	private UseTerminalCommands termCmd;
	private ProgressFrame progressFrame;
	Timer time;
	
	
	public CreateInBackground(String line) {
		termCmd = new UseTerminalCommands();
		cmd = line;
		
		//start up progress bar
		progressFrame = new ProgressFrame();
	}

	@Override
	protected Void doInBackground() throws Exception {
		//Execute command
		termCmd.terminalCommandVoid(cmd);
		return null;
	}

	
	@Override
	protected void done() {
		//When the creation is done, notify user and dispose of progress bar
		JOptionPane.showMessageDialog(progressFrame, "Done !");
		progressFrame.dispose();
	}

}
