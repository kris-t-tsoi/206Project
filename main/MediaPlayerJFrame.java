package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import mainFrameGUI.AbstractReplaceAudioLabel;
import mainFrameGUI.ReplaceTextLabel;
import mainFrameGUI.ReplaceWithExistingMP3Label;
import mainFrameGUI.ResizingEmbeddedMediaPlayerComponent;
import mainFrameGUI.SaveTextLabel;
import mainFrameGUI.time.VideoCurrentTime;
import mainFrameGUI.time.VideoTimeSlider;
import mainFrameGUI.time.VideoTotalTimeLabel;
import mainFrameGUI.videoControl.PlayButton;
import net.miginfocom.swing.MigLayout;
import textToMP3Frame.TextToSpeechFrame;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MediaPlayerJFrame extends JFrame {

	private String videoPath;
	private String mp3Path;

	private JPanel contentPane;
	ResizingEmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer video;
	VideoTimeSlider vidSlide;
	VideoTotalTimeLabel vidTotalTime;
	VideoCurrentTime vidCurrentTime;

	private final JButton btnMute;
	private static final String UNMUTE_TEXT = "Unmute";
	private static final String MUTE_TEXT = "Mute";

	private boolean videoIsStarted;

	// Default volume of the video
	public static final int DEFAULT_VOLUME = 50;

	// Constants for the textfield - Max number of words which can be
	// played/saved, and error message
	public static final String ERROR_WORD_LIMIT_MESSAGE = "Sorry, you have exceeded the maximum word count of 30.";
	private static final String ERROR_MESSAGE = "Sorry, an error has occured. please try again.";

	// FileChooser-related fields
	final MediaPlayerJFrame thisFrame = this;
	final JFileChooser videoFC = new JFileChooser();
	final JFileChooser mp3FC = new JFileChooser();
	JMenuBar fileMenuBar;
	JMenu fileMenu;
	JMenuItem menuItem;

	// Directory location constants
	public static final String VIDEO_DIR_RELATIVE_PATH = "Video";
	private static final File VIDEO_DIR_ABSOLUTE_PATH = new File(
			System.getProperty("user.dir") + File.separator
					+ VIDEO_DIR_RELATIVE_PATH);

	public static final String MP3_DIR_RELATIVE_PATH = "MP3";
	private static final String MP3_DIR_ABSOLUTE_PATH = System
			.getProperty("user.dir") + File.separator + MP3_DIR_RELATIVE_PATH;

	// Dynamic labels for user information
	private static final String CURRENT_MP3_TEXT = "Currently Selected MP3: ";
	private static final String CURRENT_VIDEO_TEXT = "Currently Selected Video: ";
	private static final String PROCESS_TEXT = "Processing...";
	private static final String COMPLETE_TEXT = "Complete!";
	JLabel lblCurrentMP3;
	JLabel lblCurrentVideo;
	JLabel lblProcessing = new JLabel(" ");

	// Images for fast forward and rewind icons
	private static final ImageIcon REWIND_IMAGE = new ImageIcon(
			MediaPlayerJFrame.class.getResource("/Rewind16.gif"));
	private static final ImageIcon FAST_FORWARD_IMAGE = new ImageIcon(
			MediaPlayerJFrame.class.getResource("/FastForward16.gif"));

	// Getters and setters for FileChoosers
	public String getVideoPath() {
		return videoPath;
	}

	private void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
		setDisplayedMedia(lblCurrentVideo, CURRENT_VIDEO_TEXT, videoPath);
	}

	private String getMp3Path() {
		return mp3Path;
	}

	private void setMp3Path(String mp3Path) {
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
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		//TODO give user warning that files are being made and where they are located
		
		// Create the folders needed if they don't exist
		final File videoDir = VIDEO_DIR_ABSOLUTE_PATH;
		final File mp3Dir = new File(MP3_DIR_RELATIVE_PATH);
		videoDir.mkdir();
		mp3Dir.mkdir();

		contentPane = new JPanel();
		// Give the video a border
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Create a second JPanel which will contain the video
		JPanel mediaPanel = new JPanel(new BorderLayout());
		mediaPlayerComponent = new ResizingEmbeddedMediaPlayerComponent();
		video = mediaPlayerComponent.getMediaPlayer();
		mediaPanel.add(mediaPlayerComponent, BorderLayout.CENTER);

		
		/*
		 * Button to play the video. It also acts as a pause/unpause button, and
		 * is used to stop skipping.
		 */
		final PlayButton btnPlay = new PlayButton(this);
		btnPlay.setIcon(PlayButton.PLAY_IMAGE);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPlay.playPressed();
			}
		});

		// Button to skip backwards - video is muted while skipping
		JButton btnBackward = new JButton();
		btnBackward.setIcon(REWIND_IMAGE);
		btnBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getVideoIsStarted())
					// Skip backwards and mute the video
					btnPlay.skipVideo(false);
				video.mute(true);
			}
		});

		// Button to skip forwards - video is muted while skipping
		JButton btnForward = new JButton();
		btnForward.setIcon(FAST_FORWARD_IMAGE);
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getVideoIsStarted())
					// Skip forwards and mute the video
					btnPlay.skipVideo(true);
				video.mute(true);
			}
		});

		
		// Button to mute audio
		btnMute = new JButton("Mute");
		btnMute.setToolTipText("Mute the audio");
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Set the new button text depending on the current state of the
				// video.
				if (!video.isMute()) {
					btnMute.setText(UNMUTE_TEXT);
				} else {
					btnMute.setText(MUTE_TEXT);
				}
				// video.mute mutes if unmuted and vice versa
				video.mute();
			}
		});

		// Create a JSlider with 0 and 100 as the volume limits. 50 is the
		// default (it is set to 50 in the constructor).
		JSlider sliderVolume = new JSlider(SwingConstants.HORIZONTAL, 0, 100,
				DEFAULT_VOLUME);
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				video.setVolume(((JSlider) arg0.getSource()).getValue());
			}
		});
		sliderVolume.setMinorTickSpacing(1);
		sliderVolume.setToolTipText("Change the volume of the video");

		// Label which simply explains the other labels in the GUI
		JLabel lblMediaToOverlay = new JLabel("Media to Combine:");

		// Labels that displays the currently selected mp3 and video
		lblCurrentMP3 = new JLabel(CURRENT_MP3_TEXT);
		lblCurrentVideo = new JLabel(CURRENT_VIDEO_TEXT);

		/*
		 * JMenuBar containing most of the functionality The tabs are: File,
		 * Text, and Video
		 */
		fileMenuBar = new JMenuBar();

		// File tab: Choose Video File, Choose MP3 File
		fileMenu = new JMenu("File");

		// This label opens a FileChooser to select a video
		menuItem = new JMenuItem("Choose Video File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Let the user select a video with a fileChooser
				selectVideo(btnPlay);
			}
		}));
		fileMenu.add(menuItem);

		// This label opens a FileChooser to select an MP3
		menuItem = new JMenuItem("Choose MP3 File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start file search in current directory, and show mp3's
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"MP3 File", "mp3");
				mp3FC.setFileFilter(filter);
				mp3FC.setCurrentDirectory(mp3Dir);

				int returnVal = mp3FC.showOpenDialog(thisFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setMp3Path(mp3FC.getSelectedFile().getAbsolutePath());
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					JOptionPane.showMessageDialog(thisFrame, ERROR_MESSAGE);
				}
			}
		}));
		fileMenu.add(menuItem);

		fileMenuBar.add(fileMenu);

		fileMenu = new JMenu("Text");

		
		
		//TODO text to mp3 frame
		menuItem = new JMenuItem("Text Frame");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TextToSpeechFrame f = new TextToSpeechFrame();
			}
		}));
		fileMenu.add(menuItem);
		
		
		fileMenuBar.add(fileMenu);
		
		

		fileMenu = new JMenu("Video");

		// Create a subMenu which contains two more tabs: Text and Existing MP3
		JMenu subMenu = new JMenu("Replace Audio With");
		fileMenu.add(subMenu);



		// Sub-label which allows the user to replace a video's audio with a
		// pre-selected mp3
		final ReplaceWithExistingMP3Label overlayMP3Item = new ReplaceWithExistingMP3Label(
				this);
		overlayMP3Item.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get the mp3, then replace the audio
				String mp3Path = getMp3Path();
				replaceAudio(overlayMP3Item, mp3Path);
			}
		}));
		subMenu.add(overlayMP3Item);

		fileMenuBar.add(fileMenu);
		setJMenuBar(fileMenuBar);

		// Image which is on the left of the JSlider
		JLabel lblImageIcon = new JLabel(new ImageIcon(
				MediaPlayerJFrame.class.getResource("/Volume16.gif")));

		vidSlide = new VideoTimeSlider(video);
		vidSlide.addMouseListener(new MouseListener(){
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});
		
		
		vidTotalTime = new VideoTotalTimeLabel();
		vidCurrentTime = new VideoCurrentTime();
		JLabel dash = new JLabel(" / ");

		// Windowbuilder generated code below

		/*
		 * MigLayout is essentially a gridlayout but with custom sized grids:
		 * the grids do not have to be square but can be rectangular. First the
		 * column sizes are created and then the rows. Each column also can have
		 * a grow and shrink priority - in this case, only the last column and
		 * first row can expand.
		 */
		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[60px,grow 0,shrink 0][4px,grow 0,shrink 0][60px,grow 0,shrink 0][4px,grow 0,shrink 0]"
								+ "[60px,grow 0,shrink 0][4px,grow 0,shrink 0][100px,grow 0,shrink 0][4px,grow 0,shrink 0]"
								+ "[10px,grow 0,shrink 0][2px,grow 0,shrink 0][421px,grow,shrink]", // Column
																									// Constraints
						"[406px,grow, shrink][20px][20px][14px][14px][14px]")); // Row
																				// Constraints

		/*
		 * Then every component is added to a specific grid location, with two
		 * extra optional numbers: the width and height that the component
		 * should cover (in terms of rows and columns). Finally, a component can
		 * grow in and align to a specific direction (e.g. growy, aligny top) or
		 * grow in both directions (grow)
		 */
		contentPane.add(mediaPlayerComponent, "cell 0 0 11 1,grow");
		contentPane.add(lblMediaToOverlay,
				"cell 0 3 3 1,alignx left,aligny top");
		contentPane.add(lblProcessing, "cell 10 3,alignx right,aligny top");
		contentPane.add(btnBackward, "cell 0 2,alignx center,grow");
		contentPane.add(btnPlay, "cell 2 2,grow");
		contentPane.add(btnForward, "cell 4 2,alignx center,grow");
		contentPane.add(btnMute, "cell 6 2,grow");
		contentPane.add(lblImageIcon, "cell 8 2,grow");
		contentPane.add(sliderVolume, "cell 10 2,grow");
		contentPane.add(vidCurrentTime, "cell 0 1,grow");		
		contentPane.add(dash, "cell 1 1 11 2,growx,aligny top");
		contentPane.add(vidTotalTime, "cell 2 1,grow");
		contentPane.add(vidSlide, "cell 3 1 11 2,growx,aligny top");
		contentPane.add(lblCurrentVideo, "cell 2 4 9 1,growx,aligny top");
		contentPane.add(lblCurrentMP3, "cell 2 5 9 1,growx,aligny top");

		setVisible(true);
	}

	/**
	 * Method to allow release of the mediaPlayerComponent from the main class
	 */
	protected void release() {
		mediaPlayerComponent.release();
	}

	/**
	 * Method to play a given media.
	 */
	public void play(PlayButton btnPlay) {
		video.playMedia(getVideoPath());

	}
	//TODO make vidCurrentTime label change
	//TODO make Jslider change with video
	

	/**
	 * Method to extract the media files basename i.e. everything after the last
	 * slash, and sets it as the label's text so the user knows what they have
	 * selected
	 * 
	 * @param path
	 *            -the path to the video or mp3
	 * 
	 */
	private void setDisplayedMedia(JLabel label, String constantText,
			String path) {
		String[] splitPath = path.split(File.separator);
		label.setText(constantText + splitPath[splitPath.length - 1]);
	}

	/**
	 * Method to skip the video (for use by other objects)
	 * 
	 * @param value
	 */
	public void skip(int value) {
		video.skip(value);
	}

	/**
	 * This method is used to restore the videos mute status after the play
	 * button is pressed to stop skipping. During skipping, the video is muted
	 * so this method restores it.
	 */
	public void restoreMutedStatus() {
		if (btnMute.getText().equals(MUTE_TEXT)) {
			video.mute(false);
		} else {
			video.mute(true);
		}
	}



	/**
	 * Method to set the processing label to say complete, for use by an
	 * AbstractMediaLabel subclass. This method is called in the done() of
	 * AbstactMediaLabel's swingworker
	 */
	public void setLabelComplete() {
		lblProcessing.setText(COMPLETE_TEXT);
	}

	/**
	 * This method replaces the audio of a video with the audio given by mp3Path
	 * It first asks for a valid output file name, and if it is valid it sets
	 * the processing label to say "Processing..." and then finally uses the
	 * label's replaceAudio method to replace the audio.
	 * 
	 * @param label
	 * @param mp3Path
	 */
	private void replaceAudio(AbstractReplaceAudioLabel label, String mp3Path) {
		String videoPath = getVideoPath();
		if (videoPath != null && mp3Path != null) {
			String outputFile = (String) JOptionPane.showInputDialog(this,
					"Please enter a name for the output file",
					"Output file name", JOptionPane.INFORMATION_MESSAGE);
			if (outputFile != null) {
				lblProcessing.setText(PROCESS_TEXT);
				label.replaceAudio(mp3Path, videoPath, outputFile);
			} else {
				JOptionPane.showMessageDialog(this,
						"Error: output file name cannot be blank.");
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Please select a video and/or and mp3 file.");
		}
	}

	/**
	 * Open FileChooser and let the user choose a video to play. If the user is
	 * already playing a video, stop the old one, set videoIsStarted to false,
	 * and update the play button's icon to the play icon
	 * 
	 * @param btnPlay
	 *            - the play button which has its icon set to the play icon.
	 */
	public void selectVideo(PlayButton btnPlay) {
		// start file search in current file
		videoFC.setCurrentDirectory(VIDEO_DIR_ABSOLUTE_PATH);
		int returnVal = videoFC.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			/*
			 * Check if user already playing another video. If they are, stop
			 * the current one and set isVideoStarted to false and the play
			 * button to display the play icon
			 */
			if (getVideoPath() != null) {
				mediaPlayerComponent.getMediaPlayer().stop();
				setVideoIsStarted(false);
				btnPlay.btnSetPlayIcon();
			}

			setVideoPath(videoFC.getSelectedFile().getAbsolutePath());
			JOptionPane.showMessageDialog(this, videoFC.getSelectedFile()
					.getName() + " has been selected.");

			
			// set total time of video
			vidTotalTime.findVideoDuration(getVideoPath());
			

		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(this, ERROR_MESSAGE);
		}
	}

}
