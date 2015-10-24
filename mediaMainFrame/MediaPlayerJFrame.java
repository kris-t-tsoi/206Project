package mediaMainFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.FileChooserUI;

import doInBackground.UpdateVideoFrame;
import fileChoosing.UserFileChoose;
import overlayFrame.OverlayAudioToVideoFrame;
import overlayFrame.OverlayVidAndAudioButton;
import overlayFrame.addAudioTrackPanel.AudioData;
import overlayFrame.addAudioTrackPanel.AudioToAddPanel;
import overlayFrame.audioTable.AudioTableFrame;
import mediaMainFrame.time.VideoCurrentTime;
import mediaMainFrame.time.VideoTimeSlider;
import mediaMainFrame.videoControl.PlayButton;
import net.miginfocom.swing.MigLayout;
import sharedLabels.NameLabel;
import sharedLabels.TimeLabel;
import textToMP3Frame.TextToSpeechFrame;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@SuppressWarnings("serial")
public class MediaPlayerJFrame extends JFrame {

	private String videoPath;
	private String currentVideo;

	// video duration in millisec
	private double videoDuration;

	private JPanel contentPane;
	ResizingEmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer video;
	private VideoTimeSlider vidSlide;
	AudioToAddPanel addAudioTrack;
	private ArrayList<AudioData> audioTrackList;

	private TimeLabel vidTotalTime;
	private VideoCurrentTime vidCurrentTime;

	private final JButton btnMute;
	private static final String UNMUTE_TEXT = "Unmute";
	private static final String MUTE_TEXT = "Mute";
	JSlider sliderVolume;

	private boolean videoIsStarted;

	// Default volume of the video
	private static final int DEFAULT_VOLUME = 50;
	private int volume;

	// Error Message
	private static final String ERROR_MESSAGE = "Sorry, an error has occured. please try again.";

	// FileChooser-related fields
	final MediaPlayerJFrame thisFrame = this;
	final private UserFileChoose fileChoose;
	UserFileChoose defaultDirect;
	JMenuBar fileMenuBar;
	JMenu fileMenu;
	JMenuItem menuItem;

	// default directory
	private String defPathDirect;

	/*
	 * // Directory location constants public static final String
	 * VIDEO_DIR_RELATIVE_PATH = "Video"; public static final File
	 * VIDEO_DIR_ABSOLUTE_PATH = new File( System.getProperty("user.dir") +
	 * File.separator + VIDEO_DIR_RELATIVE_PATH);
	 * 
	 * public static final String MP3_DIR_RELATIVE_PATH = "MP3"; private static
	 * final File MP3_DIR_ABSOLUTE_PATH = new File(
	 * System.getProperty("user.dir") + File.separator + MP3_DIR_RELATIVE_PATH);
	 */
	// Dynamic labels for user information

	private static final String CURRENT_VIDEO_TEXT = "Currently Selected Video: ";
	JLabel curVidTitle;
	private NameLabel currentVidName;

	// Images for fast forward and rewind icons
	private static final ImageIcon REWIND_IMAGE = new ImageIcon(
			MediaPlayerJFrame.class.getResource("/Rewind16.gif"));
	private static final ImageIcon FAST_FORWARD_IMAGE = new ImageIcon(
			MediaPlayerJFrame.class.getResource("/FastForward16.gif"));

	public EmbeddedMediaPlayer getVideo() {
		return video;
	}

	public String getVideoPath() {
		return videoPath;
	}

	private void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
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

	public String getCurrentVideo() {
		return currentVideo;
	}

	public void setCurrentVideo(String currentVideo) {
		this.currentVideo = currentVideo;
	}

	public double getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(double videoDuration) {
		this.videoDuration = videoDuration;
	}

	public TimeLabel getVidTotalTime() {
		return vidTotalTime;
	}

	public void setVidTotalTime(TimeLabel vidTotalTime) {
		this.vidTotalTime = vidTotalTime;
	}

	public String getErrorMessage() {
		return ERROR_MESSAGE;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public VideoCurrentTime getVidCurrentTime() {
		return vidCurrentTime;
	}

	public VideoTimeSlider getVidSlide() {
		return vidSlide;
	}

	public ArrayList<AudioData> getAudioTrackList() {
		return audioTrackList;
	}

	public NameLabel getCurrentVidName() {
		return currentVidName;
	}

	public String getDefPathDirect() {
		return defPathDirect;
	}

	public void setDefPathDirect(String defPathDirect) {
		this.defPathDirect = defPathDirect;
	}

	public boolean videoIsPlaying() {
		return video.isPlaying();
	}

	public void pauseVideo(boolean pause) {
		video.setPause(pause);
	}

	/**
	 * Create the main media frame.
	 */
	public MediaPlayerJFrame(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setMinimumSize(new Dimension(800, 600));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// create list to store audio tracks to be overlaid
		audioTrackList = new ArrayList<AudioData>();

		// get default working directory
		defaultDirect = new UserFileChoose(thisFrame);
		defaultDirect.setDefaultDirectoy(true);
		
		//initalise user file chooser
		fileChoose = new UserFileChoose(thisFrame);

		// use to update video slider and current time label every 0.5 sec
		// https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/basic/PlayerControlsPanel.java
		ScheduledExecutorService executorService = Executors
				.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new UpdateVideoFrame(thisFrame),
				0L, 200L, TimeUnit.MILLISECONDS);

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
				setVideoVolume(sliderVolume.getValue());
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

		// Button to create MP3 from text
		JButton makeMP3Btn = new JButton();
		makeMP3Btn.setText("Text to Speech");
		makeMP3Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TextToSpeechFrame(thisFrame);
			}
		});

		// Button to open list of audiotracks that have been added
		JButton tableBtn = new JButton();
		tableBtn.setText("List of Audiotracks That Have Been Added");
		tableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AudioTableFrame(thisFrame);
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
		
		
		
		// overlay video button
				final OverlayVidAndAudioButton overlayVidBtn = new OverlayVidAndAudioButton();
				overlayVidBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// check there is a video that has been selected
						if (getVideoPath() == null) {
							JOptionPane.showMessageDialog(thisFrame,
									"No Video has Currently Been Choosen");
						} else if (audioTrackList.size() == 0) { // check audiotracks
																	// have been added
							JOptionPane.showMessageDialog(thisFrame,
									"No Audiotracks have been Added");
						} else {
							String name = fileChoose.saveVideo();
							if (!name.equals("")) { // check user wants to create a
													// video
								overlayVidBtn.overlayVideo(audioTrackList, thisFrame, name);
							}
						}

					}
				});
		

		// Create a JSlider with 0 and 100 as the volume limits. 50 is the
		// default (it is set to 50 in the constructor).
		sliderVolume = new JSlider(SwingConstants.HORIZONTAL, 0, 100,
				DEFAULT_VOLUME);
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// get volume in case moved before video is played
				setVolume(((JSlider) arg0.getSource()).getValue());
				// if video is playing then the volume changes as it plays
				video.setVolume(getVolume());

			}
		});
		sliderVolume.setMinorTickSpacing(1);
		sliderVolume.setToolTipText("Change the volume of the video");

		// Labels that displays the currently selected video
		curVidTitle = new JLabel(CURRENT_VIDEO_TEXT);
		currentVidName = new NameLabel();

		/*
		 * JMenuBar containing most of the functionality The tabs are: File,
		 * Text, and Video
		 */
		fileMenuBar = new JMenuBar();
		fileBarInital(fileMenuBar, btnPlay);
		setJMenuBar(fileMenuBar);

		// Image which is on the left of the JSlider
		JLabel volumeIconLbl = new JLabel(new ImageIcon(
				MediaPlayerJFrame.class.getResource("/Volume16.gif")));

		vidSlide = new VideoTimeSlider(video);
		vidSlide.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// change video to slider position
				vidSlide.userDrag(thisFrame);
			}
		});
		vidSlide.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// change video to slider position
				vidSlide.userDrag(thisFrame);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		//Time Labels
		setVidTotalTime(new TimeLabel());
		vidCurrentTime = new VideoCurrentTime();
		JLabel dash = new JLabel("/");
		
		//audioPanel
		addAudioTrack = new AudioToAddPanel(thisFrame,
				audioTrackList);

		// set up layout
		contentPane
				.setLayout(new MigLayout(//(Layout constraints,column constraints,row constraints)
						"",
						"[60px,grow 0,shrink 0][4px,grow 0,shrink 0][60px,grow 0,shrink 0][4px,grow 0,shrink 0]"
								+ "[60px,grow 0,shrink 0][4px,grow 0,shrink 0][100px,grow 0,shrink 0][4px,grow 0,shrink 0]"
								+ "[10px,grow 0,shrink 0][2px,grow 0,shrink 0][421px,grow,shrink]",
						"[500px,grow, shrink][20px][20px][17px][17px][17px][240px,grow, shrink][8px]"));

		// Media player
		contentPane.add(mediaPlayerComponent, "cell 0 0 11 1,grow");

		// control buttons
		contentPane.add(btnBackward, "cell 0 2,alignx center,grow");
		contentPane.add(btnPlay, "cell 2 2,grow");
		contentPane.add(btnForward, "cell 4 2,alignx center,grow");

		// volume
		contentPane.add(btnMute, "cell 6 2,grow");
		contentPane.add(volumeIconLbl, "cell 8 2,grow");
		contentPane.add(sliderVolume, "cell 10 2,grow");

		// time labels and slider
		contentPane.add(vidCurrentTime, "cell 0 1,grow");
		contentPane.add(dash, "cell 1 1,grow");
		contentPane.add(getVidTotalTime(), "cell 2 1,grow");
		contentPane.add(vidSlide, "cell 3 1 11 3,growx,aligny top");

		// make MP3, overlay and list of audiotrack buttons
		contentPane.add(makeMP3Btn, "cell 10 3 ,grow");
		contentPane.add(tableBtn, "cell 10 4 ,grow");
		contentPane.add(overlayVidBtn, "cell 10 5 ,grow");

		// current video labels
		contentPane.add(curVidTitle, "cell 0 3 9 0,growx");
		contentPane.add(currentVidName, "cell 0 4 9 0,growx,aligny top");
		
		//Add audio
		add(addAudioTrack, "cell 0 6 11 0 ,grow");
		

		setVisible(true);
	}
	

	/**
	 * Method to allow release of the mediaPlayerComponent from the main class
	 */
	public void release() {
		mediaPlayerComponent.release();
	}

	/**
	 * Method to play a given media.
	 */
	public void play(PlayButton btnPlay) {
		video.playMedia(getVideoPath());

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
	 * Open FileChooser and let the user choose a video to play. If the user is
	 * already playing a video, stop the old one, set videoIsStarted to false,
	 * and update the play button's icon to the play icon
	 * 
	 * @param btnPlay
	 *            - the play button which has its icon set to the play icon.
	 */
	public void selectVideo(PlayButton btnPlay) {

		String path = fileChoose.chooseVideoPath(thisFrame, btnPlay);

		if (!path.equals("")) {

			setVideoPath(path);
			setCurrentVideo(currentVidName.getFileName(path));

			JOptionPane.showMessageDialog(this, getCurrentVideo()
					+ " has been selected.");

			// set total time of video
			setVideoDuration(getVidTotalTime().findDuration(getVideoPath()));
		}
	}

	/**
	 * Stops the current video set the play button to display play icon
	 * 
	 * @param btnPlay
	 */
	public void removeVideo(PlayButton btnPlay) {
		mediaPlayerComponent.getMediaPlayer().stop();
		setVideoIsStarted(false);
		btnPlay.btnSetPlayIcon();
	}

	/**
	 * Set up file bar components
	 * 
	 * @param menuBar
	 * @param btnPlay
	 */
	private void fileBarInital(JMenuBar menuBar, final PlayButton btnPlay) {
		// File tab: Choose Video File, Choose default working directory
		fileMenu = new JMenu("File");

		// This label opens a FileChooser to select a video
		menuItem = new JMenuItem("Choose Video File");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectVideo(btnPlay);
			}
		}));
		fileMenu.add(menuItem);

		// This label allows user to change the default working directory
		menuItem = new JMenuItem("Change Default Working Directory");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				defaultDirect.setDefaultDirectoy(false);
			}
		}));
		fileMenu.add(menuItem);
		fileMenuBar.add(fileMenu);

		// Audio tab: text to speech
		fileMenu = new JMenu("Text & Audio");

		// create mp3 from text
		menuItem = new JMenuItem("Listen and Create MP3");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TextToSpeechFrame(thisFrame);
			}
		}));
		fileMenu.add(menuItem);

		// List of audio that has already been added
		menuItem = new JMenuItem("List of Audiotracks Added");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AudioTableFrame(thisFrame);
			}
		}));
		fileMenu.add(menuItem);
		fileMenuBar.add(fileMenu);

		// Help tab: help tab
		fileMenu = new JMenu("Help");// TODO

		// create mp3 from text
		menuItem = new JMenuItem("Main Page");
		menuItem.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		}));

		fileMenu.add(menuItem);
		fileMenuBar.add(fileMenu);

	}

}
