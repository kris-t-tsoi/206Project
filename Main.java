import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
<<<<<<< HEAD
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

=======

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.log.NativeLog;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
>>>>>>> d576ddb6da1cc72e3deea0bb6f49aef8c9ccf35e

public class Main {
	
	private final MediaPlayerJFrame frame;
	
	public static void main(String args[]) {
<<<<<<< HEAD
		//boolean found = new NativeDiscovery().discover();
		
=======
		boolean found = new NativeDiscovery().discover();
       	if(!found) {
       		com.sun.jna.NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"/usr/bin/vlc");
       	}
       //	System.out.println(LibVlc.INSTANCE.libvlc_get_version());
        
>>>>>>> d576ddb6da1cc72e3deea0bb6f49aef8c9ccf35e
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}
	
	public Main() {
		frame = new MediaPlayerJFrame("Vidivox");
		frame.setBounds(100,100,600,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {;
			@Override
			public void windowClosing(WindowEvent e) {
<<<<<<< HEAD
				frame.release();
=======
				frame.release();//Releases the mediaPlayerComponent
>>>>>>> d576ddb6da1cc72e3deea0bb6f49aef8c9ccf35e
				System.exit(0);
			}
		});
		
		
		
		
		
	}
	
}
