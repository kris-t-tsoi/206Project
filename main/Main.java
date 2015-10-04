package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Main {

	private final MediaPlayerJFrame frame;

	public static void main(String args[]) {
		// Attempt to find users vlc location
		boolean found = new NativeDiscovery().discover();
		if (!found) {
			com.sun.jna.NativeLibrary.addSearchPath(
					RuntimeUtil.getLibVlcLibraryName(), "/usr/bin/vlc");
		}
		// System.out.println(LibVlc.INSTANCE.libvlc_get_version());

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}

	public Main() {
		
		// Changes default look and feel of application
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				//If Nimbus is not found, reverts to Ubuntu look and feel
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
			}
		}

		frame = new MediaPlayerJFrame("Vidivox");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 611);
		frame.addWindowListener(new WindowAdapter() {
			;
			@Override
			public void windowClosing(WindowEvent e) {
				frame.release();// Releases the mediaPlayerComponent
				System.exit(0);
			}
		});
	}

}
