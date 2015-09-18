import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MediaPlayerJFrame extends JFrame {

	private String videoPath;
	private String mp3Path;
	private JPanel contentPane;
	private JTextField txtInputText;
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer video;
	protected boolean videoIsStarted;
	private final int buttonWidth = 125; // Standard width for all buttons
	private BackgroundTask bgTask; // Used to skip forwards and backwards without gui freezing
	private static final int DEFAULT_VOLUME = 50;// Default volume of the video

	final JFileChooser vfc = new JFileChooser();
	final JFileChooser mp3fc = new JFileChooser();
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	File currentPath = new File(System.getProperty("user.dir"));
	
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	
	public String getMp3Path() {
		return mp3Path;
	}

	public void setMp3Path(String mp3Path) {
		this.mp3Path = mp3Path;
	}


	
	

	private static final int MAX_NUMBER_OF_WORDS = 30; // Max number of words which can be played/saved
	private static final String ERROR_MESSAGE = "Sorry, you have exceeded the maximum word count of 30.";

	/**
	 * Create the frame.
	 */
	public MediaPlayerJFrame(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));// Give the actual video a border
		setContentPane(contentPane);
		final JFrame thisFrame = this;

		JPanel mediaPanel = new JPanel(new BorderLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		video = mediaPlayerComponent.getMediaPlayer();
		mediaPanel.add(mediaPlayerComponent, BorderLayout.CENTER);

		/**
		 * MenuBar placed at top of frame
		 * 	Item : Files
		 */
		menuBar = new JMenuBar();
		menuBar.setBounds(55, 28, 129, 21);
		contentPane.add(menuBar);

		/**
		 * JMenu Files -- Select Video and MP3
		 */
		menu = new JMenu("Files");
		
		menuItem = new JMenuItem("Choose Video File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//start file search in current file 
				//May want to add in video folder is do then just add in (+"folder name")
				vfc.setCurrentDirectory(currentPath);
				int returnVal = vfc.showOpenDialog(menuItem);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setVideoPath( vfc.getSelectedFile().getAbsolutePath());	
					//play choosen video
					play();
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					JFrame popup = new JFrame();
					JOptionPane.showMessageDialog(popup,"Sorry an ERROR occured, Please try again");
				}
			}
		}));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Choose MP3 File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//start file search in current file 
				//May want to add in mp3 folder is do then just add in (+"folder name")
				FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File","mp3");
				mp3fc.setFileFilter(filter);
				mp3fc.setCurrentDirectory(currentPath);
				int returnVal = mp3fc.showOpenDialog(menuItem);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setMp3Path(mp3fc.getSelectedFile().getAbsolutePath());	
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					JFrame popup = new JFrame();
					JOptionPane.showMessageDialog(popup, "Sorry an ERROR occured, Please try again");
				}
			}
		}));
		menu.add(menuItem);

		menuBar.add(menu);
	
		
		
		
		/*
		 * Button to play the video It also acts as a pause/unpause button, and
		 * is used to stop skipping backward or forward
		 */
		final JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Cancel any current skipping
				if (bgTask != null) {
					bgTask.cancel(true);
					bgTask = null;
					return;
				} else
					// Start the video if not started
					if (!videoIsStarted) {
					play();
					btnPlay.setText("Pause");
					videoIsStarted = true;
					video.setVolume(DEFAULT_VOLUME);
					return;
				}

				// Pause or play the video
				if (!video.isPlaying()) {// Pause video if playing
					video.setPause(false);
					btnPlay.setText("Pause");

				} else {
					video.setPause(true);// Play video if paused
					btnPlay.setText("Play");
				}
			}

		});
		btnPlay.setToolTipText("Play the video");

		// Button to skip backwards
		JButton btnBackward = new JButton("Back");
		btnBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (videoIsStarted)
					skipVideoForwards(false);
			}
		});

		// Button to skip forward
		JButton btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (videoIsStarted)
					skipVideoForwards(true);
			}
		});

		// JTextField that allows for user input so that
		txtInputText = new JTextField();
		txtInputText.setToolTipText("Text to synthesize here - max 30 words");
		txtInputText.setText("Text to synthesize here - max 30 words");
		txtInputText.setColumns(10);

		// Button to speak the text in the JTextField using festival
		JButton btnPlayText = new JButton("Play text");
		btnPlayText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check if number of word is within limit
				if (checkTxtLength(txtInputText.getText())) {
					sayWithFestival(txtInputText.getText());
				} else {
					JOptionPane.showMessageDialog(thisFrame, ERROR_MESSAGE);
				}
			}
		});
		btnPlayText.setToolTipText("Listen to the text");

		// Button to save text as mp3
		JButton btnSaveText = new JButton("Save text");
		btnSaveText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check if number of word is within limit
				if (checkTxtLength(txtInputText.getText())) {
					String s = (String) JOptionPane.showInputDialog(thisFrame, "Enter a name for the mp3 file");
					if (!s.startsWith(" ")) {
						createMP3(s);
					}

				} else {
					JOptionPane.showMessageDialog(thisFrame, ERROR_MESSAGE);
				}
			}
		});
		btnSaveText.setToolTipText("Save the text to a mp3 file");

		// Button to select an mp3
		JButton btnSelectMp3 = new JButton("Add text");
		btnSelectMp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		btnSelectMp3.setToolTipText("Add the text to the current video");

		JButton btnAdd = new JButton("Add mp3");
		btnAdd.setToolTipText("Select an mp3 to add to the start of the video\r\n");

		// Button to mute audio
		final JButton btnMute = new JButton("Mute");
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!video.isMute()) {
					btnMute.setText("Unmute");
				} else {
					btnMute.setText("Mute");
				}

				video.mute();

			}
		});
		btnMute.setToolTipText("Mute the audio");

		// Create a JSlider with 0 and 100 as the volume limits. 50 is the default (it is set to 50 in the constructor.
		JSlider sliderVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, DEFAULT_VOLUME);
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				video.setVolume(((JSlider) arg0.getSource()).getValue());
			}
		});
		sliderVolume.setMinorTickSpacing(1);
		sliderVolume.setToolTipText("Change the volume of the video");

		// Windowbuilder generated code below, enter at your own risk
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnMute, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(txtInputText, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
						.addContainerGap()).addGroup(
								gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(btnPlayText, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSaveText, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSelectMp3, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, buttonWidth,
												GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(
								gl_contentPane
										.createSequentialGroup().addContainerGap().addComponent(sliderVolume,
												GroupLayout.DEFAULT_SIZE, 525, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMute, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtInputText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPlayText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSaveText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelectMp3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		// Set the frame as visible
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
		video.playMedia(getVideoPath());
	}

	/**
	 * Uses festival to speak the input text by creating a bash process
	 * 
	 * @param text
	 */
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
	 * Class to skip forward/backward continuously without freezing the GUI.
	 * 
	 */
	class BackgroundTask extends SwingWorker<Void, Void> {
		private boolean skipForward;

		public BackgroundTask(boolean skipFoward) {
			this.skipForward = skipFoward;
		}

		@Override
		protected Void doInBackground() throws Exception {
			int skipValue = skipForward ? 1000 : -1000;
			while (!isCancelled()) {
				video.skip(skipValue);
				Thread.sleep(200);//Sleep in between skips to prevent errors
			}
			return null;
		}
	}

	/**
	 * Function to continuously skip forwards or backwards depending on the
	 * input boolean.
	 * 
	 * @param forwards
	 */
	private void skipVideoForwards(boolean forwards) {
		if (bgTask != null)
			bgTask.cancel(true);
		bgTask = new BackgroundTask(forwards);
		bgTask.execute();
	}

	/**
	 * Executes a given terminal command as-is, where we don't do anything with
	 * different return values. This function waits for the process to finish,
	 * so can freeze the GUI if a swingworker is not used.
	 * 
	 * @param cmd
	 */
	private void useTerminalCommand(String cmd) {
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		Process process;
		try {
			process = builder.start();
			process.waitFor();
		} catch (IOException | SecurityException | IllegalArgumentException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Function to create an mp3 from a string of text by:
	 * 		1. creating a wav file using text2wave
	 * 		2. creating an mp3 from the wav file using ffmpeg
	 * 		3. removing the wav file
	 * @param s is the input string
	 */
	private void createMP3(String s) {
		useTerminalCommand("echo " + txtInputText.getText() + "|text2wave -o " + s + ".wav");
		useTerminalCommand("ffmpeg -i " + s + ".wav -f mp3 " + s + ".mp3");
		useTerminalCommand("rm " + s + ".wav");
	}

	/**
	 * Function to check if the number of words a string of text exceeds a certain value.
	 * Used before reading or saving any text.
	 * @param text
	 *            - from textField
	 * @return true - number of words is less than 30 false - number of words is
	 *         greater than 30
	 * 
	 */
	public boolean checkTxtLength(String text) {
		// Removes all spaces and punctuation apart from ' for conjunctions
		String[] punct = text.split("[^a-zA-Z0-9']");
		int words = 0;
		for (int i = 0; i < punct.length; i++) {
			if (!punct[i].equals("")) {
				words++;
			}
		}
		if (words <= MAX_NUMBER_OF_WORDS) {
			return true;
		}
		return false;
	}


}
