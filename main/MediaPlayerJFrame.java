package main;

import java.awt.BorderLayout;
import java.awt.Dimension;

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
import guiComponents.AbstractMP3Creator;
import guiComponents.AbstractMediaLabel;
import guiComponents.InputTextField;
import guiComponents.OverlayExistingMp3Label;
import guiComponents.OverlayTextLabel;
import guiComponents.PlayButton;
import guiComponents.SaveTextLabel;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MediaPlayerJFrame extends JFrame {

	private String videoPath;
	private String mp3Path;
	private JPanel contentPane;
	private InputTextField txtInputText;
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer video;
	private boolean videoIsStarted;
	private final int buttonWidth = 125; // Standard width for all buttons

	// Default volume of the video
	public static final int DEFAULT_VOLUME = 50;

	// Constants for the textfield - Max number of words which can be
	// played/saved, and error message
	public static final String ERROR_WORD_LIMIT_MESSAGE = "Sorry, you have exceeded the maximum word count of 30.";
	private static final String ERROR_MESSAGE = "Sorry, an error has occured. please try again.";

	// FileChooser-related fields
	final MediaPlayerJFrame thisFrame = this;
	final JFileChooser vfc = new JFileChooser();
	final JFileChooser mp3fc = new JFileChooser();
	JMenuBar fileMenuBar;
	JMenu fileMenu;
	JMenuItem menuItem;

	// Directory location constants

	public static final String VIDEO_DIR_RELATIVE_PATH = "Video";
	public static final File VIDEO_DIR_ABSOLUTE_PATH = new File(
			System.getProperty("user.dir") + File.separator + VIDEO_DIR_RELATIVE_PATH);
	public static final String MP3_DIR_RELATIVE_PATH = "MP3";

	private static final String MP3_DIR_ABSOLUTE_PATH = System.getProperty("user.dir") + File.separator
			+ MP3_DIR_RELATIVE_PATH;

	// Dynamic labels for user information
	private static final String CURRENT_MP3_TEXT = "Currently selected mp3: ";
	private static final String CURRENT_VIDEO_TEXT = "Currently selected video: ";
	private static final String PROCESS_TEXT = "Processing...";
	private static final String COMPLETE_TEXT = "Complete!";
	JLabel lblCurrentMP3;
	JLabel lblCurrentVideo;
	JLabel lblProcessing = new JLabel(" ");
	
	private static final ImageIcon REWIND_IMAGE = new ImageIcon("images/Rewind16.gif");
	private static final ImageIcon FAST_FORWARD_IMAGE = new ImageIcon("images/Pause16.gif");
	private static final ImageIcon MUTE_IMAGE = new ImageIcon("images/Pause16.gif");//TODO

	// Getters and setters for FileChoosers
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
		setDisplayedMedia(lblCurrentVideo, CURRENT_VIDEO_TEXT, videoPath);
	}

	public String getMp3Path() {
		return mp3Path;
	}

	public void setMp3Path(String mp3Path) {
		this.mp3Path = mp3Path;
		setDisplayedMedia(lblCurrentMP3, CURRENT_MP3_TEXT, mp3Path);
	}

	public boolean getVideoIsStarted() {
		return videoIsStarted;
	}

	public void setVideoIsStarted(boolean videoIsStarted) {
		this.videoIsStarted = videoIsStarted;
	}

	public void setVideoVolume(int value) {
		video.setVolume(value);
	}

	public boolean videoIsPlaying() {
		return video.isPlaying();
	}

	public void pauseVideo(boolean pause) {
		video.setPause(pause);
	}

	/**
	 * Create the frame.
	 */
	public MediaPlayerJFrame(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 600, 700);

		// Create the folders needed if they don't exist
		final File videoDir = VIDEO_DIR_ABSOLUTE_PATH;
		final File mp3Dir = new File(MP3_DIR_RELATIVE_PATH);

		videoDir.mkdir();
		mp3Dir.mkdir();

		contentPane = new JPanel();
		// Give the video a border
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel mediaPanel = new JPanel(new BorderLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		video = mediaPlayerComponent.getMediaPlayer();
		mediaPanel.add(mediaPlayerComponent, BorderLayout.CENTER);

		
		/*
		 * Button to play the video It also acts as a pause/unpause button, and
		 * is used to stop skipping backward or forward
		 */
		
		final PlayButton btnPlay = new PlayButton(this);
		btnPlay.setIcon(PlayButton.PLAY_IMAGE);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPlay.playPressed();
			}
		});

		// Button to skip backwards
		JButton btnBackward = new JButton();
		btnBackward.setIcon(REWIND_IMAGE);
		btnBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getVideoIsStarted())
					btnPlay.skipVideoForwards(false);
			}
		});

		// Button to skip forward
		JButton btnForward = new JButton();
		btnForward.setIcon(FAST_FORWARD_IMAGE);
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getVideoIsStarted())
					btnPlay.skipVideoForwards(true);
			}
		});

		// JTextField that allows for user input so that
		txtInputText = new InputTextField();

		// Button to mute audio
		final JButton btnMute = new JButton("Mute");
		btnMute.setToolTipText("Mute the audio");
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/*Set the text depending on the current state of the video.
				 * Doesnt work if put after video.mute(), possibly because it doesnt update the
				 * videos mute status quickly enough
				 */
				if (!video.isMute()) {
					btnMute.setText("Unmute");
				} else {
					btnMute.setText("Mute");
				}
				
				//video.mute() unmutes if muted and vice versa
				video.mute();
			}
		});

		// Create a JSlider with 0 and 100 as the volume limits. 50 is the
		// default (it is set to 50 in the constructor).
		JSlider sliderVolume = new JSlider(SwingConstants.HORIZONTAL, 0, 100, DEFAULT_VOLUME);
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				video.setVolume(((JSlider) arg0.getSource()).getValue());
			}
		});
		sliderVolume.setMinorTickSpacing(1);
		sliderVolume.setToolTipText("Change the volume of the video");

		
		//Label which 
		JLabel lblMediaToOverlay = new JLabel("Media to overlay:");
		
		// Labels that displays the currently selected mp3 and video
		lblCurrentMP3 = new JLabel(CURRENT_MP3_TEXT);
		
		lblCurrentVideo = new JLabel(CURRENT_VIDEO_TEXT);
		
		/**
		 * MenuBar placed at top of frame Item : Files
		 */
		fileMenuBar = new JMenuBar();
		//fileMenuBar.setBounds(55, 28, 129, 21);

		/**
		 * JMenu Files -- Select Video and MP3
		 */
		fileMenu = new JMenu("File");

		menuItem = new JMenuItem("Choose Video File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Let the user select a video with a fileChooser
				selectVideo(btnPlay);
			}
		}));
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Choose MP3 File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start file search in current directory, and show mp3's
				FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File", "mp3");
				mp3fc.setFileFilter(filter);
				mp3fc.setCurrentDirectory(mp3Dir);
				
				int returnVal = mp3fc.showOpenDialog(thisFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setMp3Path(mp3fc.getSelectedFile().getAbsolutePath());
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					JOptionPane.showMessageDialog(thisFrame, ERROR_MESSAGE);
				}
			}
		}));
		fileMenu.add(menuItem);
		fileMenuBar.add(fileMenu);
		
		fileMenu = new JMenu("Text");
		
		//Label which allows the user to play the text in the textField with festival
		menuItem = new JMenuItem("Play Text");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//If the text is under the allowed limit, speak the text
				if (txtInputText.checkTxtLength()) {
					txtInputText.sayWithFestival(txtInputText.getText());
				} else {
					JOptionPane.showMessageDialog(thisFrame, ERROR_WORD_LIMIT_MESSAGE);
				}
			}
		}));
		fileMenu.add(menuItem);
		
		//Label which allows the user to save the text in the textField to an mp3
		final SaveTextLabel saveTextMenuItem = new SaveTextLabel();
		saveTextMenuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createValidMP3(saveTextMenuItem);
			}
		}));
		fileMenu.add(saveTextMenuItem);
		fileMenuBar.add(fileMenu);
		
		fileMenu = new JMenu("Video");
		
		JMenu subMenu = new JMenu("Overlay Current Video With");
		fileMenu.add(subMenu);
		
		//Label to replace a video's audio with the text in the textField
		final OverlayTextLabel overlayTextItem = new OverlayTextLabel(this);
		overlayTextItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// First create the mp3
				String mp3 = createValidMP3(overlayTextItem);
				
				// Then replace the audio
				String localMp3Path = MP3_DIR_ABSOLUTE_PATH + File.separator + mp3;
				replaceAudio(overlayTextItem, localMp3Path);
			}
		}));
		subMenu.add(overlayTextItem);
		
		//Sub-label which allows the user to replace a video's audio with a pre-selected mp3
		final OverlayExistingMp3Label overlayMp3Item = new OverlayExistingMp3Label(this);
		overlayMp3Item.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get the mp3, then replace the audio
				String localMp3Path = getMp3Path();
				replaceAudio(overlayMp3Item, localMp3Path);
			}
		}));
		subMenu.add(overlayMp3Item);
		
		fileMenuBar.add(fileMenu);
		// setJMenuBar(fileMenuBar);
		mediaPanel.add(fileMenuBar, BorderLayout.NORTH);

		//Image which is on the left of the JSlider
		JLabel lblImageIcon = new JLabel(new ImageIcon("images/Volume16.gif"));

		// Windowbuilder generated code below, enter at your own risk
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(mediaPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblMediaToOverlay)
									.addPreferredGap(ComponentPlacement.RELATED, 328, Short.MAX_VALUE)
									.addComponent(lblProcessing, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnMute, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblImageIcon, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
									.addGap(4)
									.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtInputText, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(7))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCurrentVideo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
						.addComponent(lblCurrentMP3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(mediaPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnBackward, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnForward, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnMute, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblImageIcon, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtInputText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProcessing)
						.addComponent(lblMediaToOverlay))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCurrentVideo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCurrentMP3)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setSize(603, 476);// Custom size so UI behaves nicely
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
	 * 
	 * @return true - user had just selected initial video 
	 * @return	false -  
	 */
	public void play(PlayButton btnPlay) {
		if (getVideoPath() == null) {
			JOptionPane.showMessageDialog(this, "Please select a video to play.");
			selectVideo(btnPlay);
		} else {
			video.playMedia(getVideoPath());
		}
	}

	/**
	 * Function to extract the media files basename i.e. everything after the
	 * last slash, and sets it as the label's text so the user knows what they
	 * have selected
	 * 
	 * @param path
	 *            - the path to the video or mp3
	 */
	private void setDisplayedMedia(JLabel label, String constantText, String path) {
		String[] splitPath = path.split("/");
		label.setText(constantText + splitPath[splitPath.length - 1]);
	}

	/**
	 * Function to skip the video (for use by other objects)
	 * 
	 * @param value
	 */
	public void skip(int value) {
		video.skip(value);
	}

	/**
	 * Function that creates an mp3 from the textField only if the number of
	 * words is under the limit. It also returns the name of the created mp3
	 * 
	 * @param parentFrame
	 *            - the current frame, used in JOptionPane
	 * @return mp3Name - name of the created mp3
	 */
	private String createValidMP3(AbstractMP3Creator menuItem) {
		// check if number of word is within limit
		if (txtInputText.checkTxtLength()) {
			String mp3Name = JOptionPane.showInputDialog(this, "Enter a name for the mp3 file");
			if ((mp3Name != null) && !mp3Name.startsWith(" ")) {
				menuItem.createMP3(txtInputText.getText(), mp3Name);
				// Return the name of the mp3 that was created
				return mp3Name + ".mp3";
			}
		} else {
			JOptionPane.showMessageDialog(this, MediaPlayerJFrame.ERROR_WORD_LIMIT_MESSAGE);
		}
		return null;
	}

	/**
	 * Method to set the processing label to say complete, for use by an AbstractMediaLabel subclass.
	 * This method is called in the done() of AbstactMediaLabel's swingworker
	 */
	public void setLabelComplete() {
		lblProcessing.setText(COMPLETE_TEXT);
	}


	/**
	 * This method replaces the audio of a video with the audio given by mp3Path
	 * It first asks for a valid output file name, and if it is valid it sets the processing label to say
	 * "Processing..." and then finally uses the label's replaceAudio method to replace the audio.
	 * @param label
	 * @param mp3Path
	 */
	private void replaceAudio(AbstractMediaLabel label, String mp3Path) {
		String videoPath = getVideoPath();
		if (videoPath != null && mp3Path != null) {
			String outputFile = (String) JOptionPane.showInputDialog(this,
					"Please enter a name for the output file", "Output file name", JOptionPane.INFORMATION_MESSAGE);
			if (outputFile != null) {
				lblProcessing.setText(PROCESS_TEXT);
				label.replaceAudio(this, mp3Path, videoPath, outputFile);
			} else {
				JOptionPane.showMessageDialog(this, "Error: output file name cannot be blank.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select a video and/or and mp3 file.");
		}
	}

	/**
	 * Open FileChooser and let the user choose a video to play
	 */
	public void selectVideo(PlayButton btnPlay) {
		// start file search in current file
		vfc.setCurrentDirectory(VIDEO_DIR_ABSOLUTE_PATH);
		int returnVal = vfc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		
		
			//check if user already playing another video
			if(getVideoPath() != null){	
				mediaPlayerComponent.getMediaPlayer().stop();
				setVideoIsStarted(false);
				btnPlay.btnSetPlayIcon();
			}
			
			setVideoPath(vfc.getSelectedFile().getAbsolutePath());
			JOptionPane.showMessageDialog(this, vfc.getSelectedFile().getName() + " has been selected.");

		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(this, ERROR_MESSAGE);
		}
	}

}
