//package guiComponents;
//
//import javax.swing.JButton;
//import javax.swing.JMenuItem;
//
//public class PlayTextButton extends JMenuItem{
//	
//	public PlayTextButton() {
//		super("Play Text");
//	}
//	
//	/**
//	 * Uses festival to speak the input text by creating a bash process
//	 * 
//	 * @param text
//	 */
//	public void sayWithFestival(String text) {
//		String cmd = "echo " + text + " | festival --tts&";
//		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
//		try {
//			builder.start();
//		} catch (java.io.IOException e) {
//			e.printStackTrace();
//		}
//	}
//}
