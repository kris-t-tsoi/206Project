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
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MediaPlayerJFrame extends JFrame {

	private String videoPath;
	private String mp3Path;
	private JPanel contentPane;
	private JTextField txtInputText;
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer video;
	protected boolean videoIsStarted;
	private final int buttonWidth = 125; // Standard width for all buttons
	private BackgroundSkipper bgTask; // Used to skip forwards and backwards
										// without gui freezing

	// Default volume of the video
	private static final int DEFAULT_VOLUME = 50;

	// Constants for the textfield - Max number of words which can be
	// played/saved, and error message
	private static final int MAX_NUMBER_OF_WORDS = 30;
	private static final String ERROR_MESSAGE = "Sorry, you have exceeded the maximum word count of 30.";

	// FileChooser-related fields
	final JFileChooser vfc = new JFileChooser();
	final JFileChooser mp3fc = new JFileChooser();
	JMenuBar fileMenuBar;
	JMenu fileMenu;
	JMenuItem menuItem;

	// Directory location constants

	private static final String VIDEO_DIR_RELATIVE_PATH = "Video";
	private static final File VIDEO_DIR_ABSOLUTE_PATH = new File(
			System.getProperty("user.dir") + File.separator + VIDEO_DIR_RELATIVE_PATH);
	// private static final File MP3_DIR_PATH = new
	// File(System.getProperty("user.dir") + File.separator + "MP3");
	private static final String MP3_DIR_RELATIVE_PATH = "MP3";

	private static final String MP3_DIR_ABSOLUTE_PATH = System.getProperty("user.dir") + File.separator
			+ MP3_DIR_RELATIVE_PATH;

	// Dynamic labels for user information
	private static final String CURRENTLY_SELECTED = "Currently selected: ";
	JLabel lblCurrentSelection;
	JLabel lblProcessing;

	// Getters and setters for FileChoosers
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
		setDisplayedMp3(mp3Path);
	}

	/**
	 * Create the frame.
	 */
	public MediaPlayerJFrame(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 468);

		// Create the folders needed if they don't exist
		final File videoDir = VIDEO_DIR_ABSOLUTE_PATH;
		// File mp3Dir = MP3_DIR_PATH;
		final File mp3Dir = new File(MP3_DIR_RELATIVE_PATH);

		videoDir.mkdir();
		mp3Dir.mkdir();

		contentPane = new JPanel();
		// Give the video a border
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final JFrame thisFrame = this;

		JPanel mediaPanel = new JPanel(new BorderLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		video = mediaPlayerComponent.getMediaPlayer();
		mediaPanel.add(mediaPlayerComponent, BorderLayout.CENTER);

		/**
		 * MenuBar placed at top of frame Item : Files
		 */
		fileMenuBar = new JMenuBar();
		fileMenuBar.setBounds(55, 28, 129, 21);
		//setJMenuBar(fileMenuBar);
		mediaPanel.add(fileMenuBar, BorderLayout.NORTH);
		
		
		/**
		 * JMenu Files -- Select Video and MP3
		 */
		fileMenu = new JMenu("Files");

		menuItem = new JMenuItem("Choose Video File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// start file search in current file
				vfc.setCurrentDirectory(VIDEO_DIR_ABSOLUTE_PATH);
				int returnVal = vfc.showOpenDialog(menuItem);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setVideoPath(vfc.getSelectedFile().getAbsolutePath());

					JOptionPane.showMessageDialog(thisFrame, vfc.getSelectedFile().getName() + " has been selected");

				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					JOptionPane.showMessageDialog(thisFrame, "Sorry an ERROR occured, Please try again");
				}
			}
		}));
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Choose MP3 File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start file search in current directory
				FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File", "mp3");
				mp3fc.setFileFilter(filter);
				mp3fc.setCurrentDirectory(mp3Dir);
				int returnVal = mp3fc.showOpenDialog(menuItem);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setMp3Path(mp3fc.getSelectedFile().getAbsolutePath());
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					JOptionPane.showMessageDialog(thisFrame, "Sorry an ERROR occured, Please try again");
				}
			}
		}));
		fileMenu.add(menuItem);
		fileMenuBar.add(fileMenu);
	//	setJMenuBar(fileMenuBar);
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
					play(thisFrame);
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
				createValidMP3(thisFrame);
			}
		});
		btnSaveText.setToolTipText("Save the text to a mp3 file");

		// Button to add the text to the video
		JButton btnSelectMp3 = new JButton("Add text");
		btnSelectMp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mp3 = createValidMP3(thisFrame);
				String localMp3Path = MP3_DIR_ABSOLUTE_PATH + File.separator + mp3;
				String localVideoPath = getVideoPath();
				replaceAudio(thisFrame, localMp3Path, localVideoPath);
			}
		});
		btnSelectMp3.setToolTipText("Add the text to the current video");

		// Button to add a selected mp3 to the file
		JButton btnAdd = new JButton("Add mp3");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String mp3Path = getMp3Path();
				String videoPath = getVideoPath();
				if (mp3Path != null && videoPath != null) {
					String output = (String) JOptionPane.showInputDialog(thisFrame,
							"Please enter a name for the output file", "Output file name",
							JOptionPane.INFORMATION_MESSAGE);

					if (output != null) {
						// Replace the video's audio with the synthesized text
						BackgroundAudioReplacer replacer = new BackgroundAudioReplacer(
								"ffmpeg -i " + videoPath + " -i " + mp3Path + " -map 0:v -map 1:a "
										+ VIDEO_DIR_RELATIVE_PATH + File.separator + output + ".mp4");
						lblProcessing.setText("Processing...");
						replacer.execute();
					}
				} else {
					JOptionPane.showMessageDialog(thisFrame, "Please select a video and/or and mp3 file.");
				}

				String localMp3Path = getMp3Path();
				String localVideoPath = getVideoPath();
				replaceAudio(thisFrame, localMp3Path, localVideoPath);

			}
		});
		btnAdd.setToolTipText("Add selected mp3 to the start of the video");

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

		// Create a JSlider with 0 and 100 as the volume limits. 50 is the
		// default (it is set to 50 in the constructor).
		JSlider sliderVolume = new JSlider(SwingConstants.VERTICAL, 0, 100, DEFAULT_VOLUME);
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				video.setVolume(((JSlider) arg0.getSource()).getValue());
			}
		});
		sliderVolume.setMinorTickSpacing(1);
		sliderVolume.setToolTipText("Change the volume of the video");

		// Label that displays the currently selected mp3
		lblCurrentSelection = new JLabel("Currently selected:");

		lblProcessing = new JLabel(" ");

		// Windowbuilder generated code below, enter at your own risk
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(mediaPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblCurrentSelection, GroupLayout.DEFAULT_SIZE, 246,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblProcessing,
														GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnMute,
														GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
										.addComponent(txtInputText, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btnPlayText, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnSaveText, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSelectMp3,
														GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(sliderVolume,
										GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
						.addGap(7)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_contentPane
						.createSequentialGroup()
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
								.addComponent(btnSelectMp3, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)))
						.addComponent(sliderVolume, 0, 0, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCurrentSelection).addComponent(lblProcessing)).addContainerGap()));
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
	public void play(JFrame thisFrame) {

		if (getVideoPath() == null) {
			JOptionPane.showMessageDialog(thisFrame, "Please select video to play");
		} else {
			video.playMedia(getVideoPath());
		}
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
	class BackgroundSkipper extends SwingWorker<Void, Void> {
		private boolean skipForward;

		public BackgroundSkipper(boolean skipFoward) {
			this.skipForward = skipFoward;
		}

		@Override
		protected Void doInBackground() throws Exception {
			// skipForward is a boolean which determines whether to skip
			// forwards or backwards
			int skipValue = skipForward ? 1000 : -1000;
			while (!isCancelled()) {
				video.skip(skipValue);
				Thread.sleep(200);// Sleep in between skips to prevent errors
			}
			return null;
		}
	}

	/**
	 * Class to do the audio processing in the background so that when it is
	 * complete we can set the label Created with the command in the constructor
	 * 
	 * @author stefan
	 *
	 */
	class BackgroundAudioReplacer extends SwingWorker<Void, Void> {
		private String cmd;

		public BackgroundAudioReplacer(String cmd) {
			this.cmd = cmd;
		}

		@Override
		protected Void doInBackground() throws Exception {
			useTerminalCommand(cmd);
			return null;
		}

		@Override
		protected void done() {
			lblProcessing.setText("Complete");
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
		bgTask = new BackgroundSkipper(forwards);
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
	 * Function to create an mp3 from a string of text by: 1. creating a wav
	 * file using text2wave 2. creating an mp3 from the wav file using ffmpeg 3.
	 * removing the wav file NB: this overwrites an existing mp3 with the same
	 * name
	 * 
	 * @param s
	 *            - the input string
	 * 
	 */
	private void createMP3(String s) {
		useTerminalCommand("echo " + txtInputText.getText() + "|text2wave -o " + s + ".wav;" + "ffmpeg -y -i " + s
				+ ".wav -f mp3 " + MP3_DIR_RELATIVE_PATH + File.separator + s + ".mp3;" + "rm " + s + ".wav");
	}

	/**
	 * Function to check if the number of words a string of text exceeds a
	 * certain value. Used before reading or saving any text.
	 * 
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

	/**
	 * Function to extract the media files basename i.e. everything after the
	 * last slash, and sets it as the label's text so the user knows what they
	 * have selected
	 * 
	 * @param path
	 *            - the path to the video
	 */
	private void setDisplayedMp3(String path) {
		String[] splitPath = path.split("/");
		lblCurrentSelection.setText(CURRENTLY_SELECTED + splitPath[splitPath.length - 1]);
	}

	/**
	 * Function that creates an mp3 from the textField only if the number of
	 * words is under the limit. It also returns the name of the created mp3
	 * 
	 * @param parentFrame
	 *            - the current frame, used in JOptionPane
	 * @return mp3Name - name of the created mp3
	 */
	private String createValidMP3(JFrame parentFrame) {
		// check if number of word is within limit
		if (checkTxtLength(txtInputText.getText())) {
			String mp3Name = JOptionPane.showInputDialog(parentFrame, "Enter a name for the mp3 file");
			if ((mp3Name != null) && !mp3Name.startsWith(" ")) {
				createMP3(mp3Name);
				return mp3Name + ".mp3";
			}
		} else {
			JOptionPane.showMessageDialog(parentFrame, ERROR_MESSAGE);
		}
		return null;
	}

	private void replaceAudio(JFrame thisFrame, String localMp3Path, String localVideoPath) {
		if (localMp3Path != null && localVideoPath != null) {
			String output = (String) JOptionPane.showInputDialog(thisFrame, "Please enter a name for the output file",
					"Output file name", JOptionPane.INFORMATION_MESSAGE);

			if (output != null) {
				// Replace the video's audio with the synthesized text
				BackgroundAudioReplacer replacer = new BackgroundAudioReplacer(
						"ffmpeg -y -i \"" + localVideoPath + "\" -i \"" + localMp3Path + "\" -map 0:v -map 1:a \""
								+ VIDEO_DIR_RELATIVE_PATH + File.separator + output + ".mp4\"");
				lblProcessing.setText("Processing...");
				replacer.execute();
			}
		} else {
			JOptionPane.showMessageDialog(thisFrame, "Please select a video and/or and mp3 file.");
		}
	}
}
