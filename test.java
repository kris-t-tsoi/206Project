import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class test {
	
	private final JFrame frame;
	
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	
	
	
	public static void main(String args[]) {
		boolean found = new NativeDiscovery().discover();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new test();
			}
		});
	}
	
	public test() {
		frame = new JFrame("A media player");
		frame.setBounds(100,100,600,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {;
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		frame.setContentPane(mediaPlayerComponent);
		frame.setVisible(true);
		mediaPlayerComponent.getMediaPlayer().playMedia(System.getProperty("user.dir") + "/sample.mp4");
		
	}
	
}
