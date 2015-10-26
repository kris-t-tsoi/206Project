package doInBackground;

import javax.swing.SwingWorker;

import mediaMainFrame.addAudioTrackPanel.AudioToAddPanel;

public class DoInBackground extends SwingWorker<Void, Void> {

	private String cmd;
	private UseTerminalCommands term;
	private AudioToAddPanel audioPane;

	public DoInBackground(String command,AudioToAddPanel parent) {
		cmd = command;
		term = new UseTerminalCommands();
		audioPane=parent;
	}

	@Override
	protected Void doInBackground() throws Exception {
		// Execute command
		term.terminalCommandVoid(cmd);
		return null;
	}

	/**
	 * cancel the process
	 */
	public void cancel(){
		term.cancel();
		done();
	}
	
	@Override
	protected void done() {
		audioPane.getPlayAudioBtn().setText(audioPane.playMP3);
		audioPane.setPlaying(false);
	}
	
}
