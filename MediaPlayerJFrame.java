import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
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
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel mediaPanel = new JPanel(new BorderLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		video = mediaPlayerComponent.getMediaPlayer();
		mediaPanel.add(mediaPlayerComponent, BorderLayout.CENTER);

		JButton btnBackward = new JButton("Back");
		btnBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});

		final JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!videoIsStarted) {
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

		JButton btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		txtInputText = new JTextField();
		txtInputText.setToolTipText("Text to synthesize here - max 30 words");
		txtInputText.setText("Text to synthesize here - max 30 words");
		txtInputText.setColumns(10);
		txtInputText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// Do nothing
			}

			@Override
			public void focusGained(FocusEvent e) {
				txtInputText.setText("");
			}
		});

		JButton btnPlayText = new JButton("Play text");
		btnPlayText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check if number of word is within limit
				if(checkTxtLength(txtInputText.getText())){
					sayWithFestival(txtInputText.getText());					
				}else{
					popup("Sorry you have exceeded the maximum word count of 30");
				}
				
			}
		});
		btnPlayText.setToolTipText("Listen to the text");

		JButton btnSaveText = new JButton("Save text");
		btnSaveText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				//check if number of word is within limit
				if(checkTxtLength(txtInputText.getText())){
					
				}else{
					popup("Sorry you have exceeded the maximum word count of 30");
				}
			}
		});
		btnSaveText.setToolTipText("Save the text to a mp3 file");

		JButton btnSelectMp3 = new JButton("Select mp3");
		btnSelectMp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		btnSelectMp3.setToolTipText("Select an mp3 to add to the start of the video\r\n");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, buttonWidth,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, buttonWidth,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, buttonWidth,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(145, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(txtInputText, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE).addContainerGap())
				.addGroup(
						gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(btnPlayText, GroupLayout.PREFERRED_SIZE, buttonWidth,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSaveText, GroupLayout.PREFERRED_SIZE, buttonWidth,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSelectMp3,
										GroupLayout.PREFERRED_SIZE, buttonWidth, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(145, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE).addGap(10)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtInputText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPlayText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSaveText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelectMp3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		setVisible(true);

	}

	public void release() {
		mediaPlayerComponent.release();
	}

	public void play() {
		video.playMedia("big_buck_bunny_1_minute.avi");
	}

	public void sayWithFestival(String text) {
		String cmd = "echo " + text + " | festival --tts&";
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		try {
			builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param text - from textField
	 * @return true - number of words is less than 30
	 * 			false - number of words is greater than 30
	 * 
	 */
	public boolean checkTxtLength(String text) {
		// removes all spaces and punctuation apart from ' for conjunctions
		String[] punct = text.split("[^a-zA-Z0-9']");
		int words = 0;
		for (int i = 0; i < punct.length; i++) {
			if (!punct[i].equals("")) {
				words++;
			}
		}
		if(words<=30){
			return true;
		}
		return false;
	}
	
	/**
	 * create popup frame with chosen message
	 * 
	 * @param message
	 *            gets text to be printed on frame
	 */
	void popup(String message) {
		JFrame popup = new JFrame();
		JOptionPane.showMessageDialog(popup, message);

	}

}
