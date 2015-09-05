import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MediaPlayerJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtInputText;
	private final int buttonWidth = 125;
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer video;
	protected boolean videoIsStarted;
	
	/**
	 * Create the frame.
	 */
	public MediaPlayerJFrame(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 431, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));//Give the video a border
		setContentPane(contentPane);
		
		JPanel mediaPanel = new JPanel(new BorderLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		video = mediaPlayerComponent.getMediaPlayer();
		mediaPanel.add(mediaPlayerComponent, BorderLayout.CENTER);
		
		//Button to skip backwards
		JButton btnBackward = new JButton("Back");
		btnBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO
			}
		});
		
		/*Button to play the video
		 * It also acts as a pause/unpause button, and is used to stop skipping backward or forward
		 */
		final JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!videoIsStarted) {
					play();
					videoIsStarted = true;
					btnPlay.setText("Pause");
				} else {
					if (!video.isPlaying()) {
						video.setPause(false);
						btnPlay.setText("Pause");
					} else {
						video.setPause(true);
						btnPlay.setText("Play");
					}
				}
				
			}
		});
		btnPlay.setToolTipText("Play the video");
		
		//Button to skip forward
		JButton btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		//JTextField that allows for user input so that 
		txtInputText = new JTextField();
		txtInputText.setToolTipText("Text to synthesize here - max 30 words");
		txtInputText.setText("Text to synthesize here - max 30 words");
		txtInputText.setColumns(10);
		txtInputText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				//Do nothing i.e. no change to the text inside when focus is lost
			}
			@Override
			public void focusGained(FocusEvent e) {
				txtInputText.setText("");
			}
		});
		
		//Button to speak the text in the JTextField using festival 
		JButton btnPlayText = new JButton("Play text");
		btnPlayText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sayWithFestival(txtInputText.getText());
			}
		});
		btnPlayText.setToolTipText("Listen to the text");
		
		//Button to save text as mp3
		JButton btnSaveText = new JButton("Save text");
		btnSaveText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		btnSaveText.setToolTipText("Save the text to a mp3 file");
		
		//Button to select an mp3
		JButton btnSelectMp3 = new JButton("Select mp3");
		btnSelectMp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		btnSelectMp3.setToolTipText("Select an mp3 to add to the start of the video\r\n");
		
		//Windowbuilder generated code below, enter at your own risk
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, buttonWidth, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, buttonWidth, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, buttonWidth, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(145, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtInputText, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnPlayText, GroupLayout.PREFERRED_SIZE, buttonWidth, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSaveText, GroupLayout.PREFERRED_SIZE, buttonWidth, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSelectMp3, GroupLayout.PREFERRED_SIZE, buttonWidth, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(145, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtInputText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPlayText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSaveText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelectMp3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		setVisible(true);
		
	}
	/**
	 * Function to allow release of the mediaPlayerComponent from the main class
	 */
	public void release() {
		mediaPlayerComponent.release();
	}
	
	/**
	 * Function to play a given media
	 */
	public void play() {
		video.playMedia("big_buck_bunny_1_minute.avi");
	}
	
	/**
	 * Uses festival to speak the input text by creating a bash process
	 * @param text
	 */
	public void sayWithFestival(String text) {
		String cmd = "echo "+text+ " | festival --tts&";
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			try {
				builder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
