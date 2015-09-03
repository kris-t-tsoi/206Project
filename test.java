import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class test {
	
	private final MediaPlayerJFrame frame;
	
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	
	public static void main(String args[]) {
		//boolean found = new NativeDiscovery().discover();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new test();
			}
		});
	}
	
	public test() {
		frame = new MediaPlayerJFrame("Vidivox");
		frame.setBounds(100,100,600,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {;
			@Override
			public void windowClosing(WindowEvent e) {
				frame.release();
				System.exit(0);
			}
		});
		
		
		frame.play();
		//frame.addVideo(mediaPlayerComponent);
		
		
	}
	
}
