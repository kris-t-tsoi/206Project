package mediaMainFrame.addAudioTrackPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import doInBackground.DoInBackground;
import doInBackground.UseTerminalCommands;
import fileChoosing.UserFileChoose;
import sharedLabels.NameLabel;
import sharedLabels.TimeLabel;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AudioToAddPanel extends JPanel {

	// Panels and Frames
	final MediaPlayerJFrame mediaPlayerFrame;

	// file choosing
	final private UserFileChoose fileChoose;
	private String mp3Path = "";

	// Time Labels and textfields
	private TimeLabel durationLbl;
	private NameLabel mp3NameLbl;
	private JLabel startLbl;
	private JTextField startMin;
	private JTextField startSec;
	private JTextField startMili;

	// Buttons
	private SelectMP3Btn selectAudio;
	private JButton createAudio;
	private JButton playAudioBtn;
	private JButton addAudioBtn;
	
	//playing mp3
	boolean isPlaying;
	DoInBackground back;
	
	// Constant strings
	private final String defaultText = "00";
	private final String formatErrorMessage = "Please have format [MM:SS.mm] using Numbers";
	private final String playMP3 = "Play Selected MP3";
	private final String cancelMP3 = "Stop";

	public String getMp3Path() {
		return mp3Path;
	}

	public void setMp3Path(String mp3Path) {
		this.mp3Path = mp3Path;
	}

	public JTextField getStartMin() {
		return startMin;
	}

	public JTextField getStartSec() {
		return startSec;
	}

	public JTextField getStartMili() {
		return startMili;
	}

	/**
	 * Constructs add audio panel
	 * 
	 * @param mainFrame
	 * @param audioTrackList
	 * @param parentFrame
	 */
	public AudioToAddPanel(final MediaPlayerJFrame mainFrame) {
		mediaPlayerFrame = mainFrame;
		setSize(700, 175);
		fileChoose = new UserFileChoose(mediaPlayerFrame);

		// Name and Path of MP3 setup
		JLabel mp3TitleLbl = new JLabel("MP3 :");
		mp3TitleLbl.setFont(mediaPlayerFrame.TITLE_FONT);
		mp3NameLbl = new NameLabel();
		mp3NameLbl.setText("");
		setMp3Path("");

		// duration of MP3 setup
		JLabel duraTitleLbl = new JLabel("Duration :");
		duraTitleLbl.setFont(mediaPlayerFrame.TITLE_FONT);
		durationLbl = new TimeLabel();

		durationLbl.setText("");

		// Time Labels initialize
		startLbl = new JLabel("Start [MM:SS.mm]:");
		startLbl.setFont(mediaPlayerFrame.TITLE_FONT);
		startMin = new JTextField();
		startSec = new JTextField();
		startMili = new JTextField();
		JLabel semiCol = new JLabel(":");
		JLabel dot = new JLabel(".");

		// reset time textfields to initial value 00
		resetText();

		// add focus listenter to textfields
		textFocusListen(startMin);
		textFocusListen(startSec);
		textFocusListen(startMili);

		// select existing MP3
		selectAudio = new SelectMP3Btn();
		selectAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp3Path = fileChoose.chooseMP3Path(mediaPlayerFrame);
				if (!mp3Path.equals("")) { // if user choose an mp3
					mp3NameLbl.setText(mp3NameLbl.getFileName(mp3Path));
					getMP3Information(mp3Path);
				}
			}
		});

		// Create a new MP3
		createAudio = new JButton("Create Audio File From Text");
		createAudio.setToolTipText("Create MP3 Audio From Text");
		createAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerFrame.ttsFrame.setVisible(true);

			}
		});

		//there is no current audio playing
		isPlaying = false;
				
		// Plays selected audio
		playAudioBtn = new JButton(playMP3);
		playAudioBtn.setToolTipText("Play MP3 Audio File That Has Been Selected");
		playAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if there is no mp3 choosen
				if (getMp3Path().equals("")) {
					JOptionPane.showMessageDialog(mediaPlayerFrame,
							"Please Chose a MP3 File to Listen To");
				} else {
					if(isPlaying==true){ //if playing stop
						back.cancel();
						isPlaying=false;
						playAudioBtn.setText(playMP3);
					}else{ //if not playing then start 
						back = new DoInBackground("ffplay -nodisp -autoexit -af volume=1" + " \"" +getMp3Path()+"\"");
						back.execute();
						isPlaying = true;
						playAudioBtn.setText(cancelMP3);
						} 
					}
				}
		});

		// Add new audiotrack button
		addAudioBtn = new JButton("Add Audio Track");
		setUpAddAudioBtn(mainFrame.getAudioTrackList(),
				mainFrame.audTableFrame.table);

		setLayout(new MigLayout(
				"", // Layout Constraint
				"[3px,grow 0,shrink 0][300px,grow,shrink][3px,grow 0,shrink 0][250px,grow,shrink]"
						+ "[3px,grow 0,shrink 0][150px,grow,shrink][3px,grow 0,shrink 0]", // Column
																							// Constraints
				"[5px][40px][40px][5px][40px]")); // Row Constraints

		add(new JSeparator(SwingConstants.HORIZONTAL), "cell 0 0 6 0 ,grow");
		add(mp3TitleLbl, "cell 1 1 ,grow");
		add(mp3NameLbl, "cell 1 1 ,grow");
		add(duraTitleLbl, "cell 3 1 ,grow");
		add(durationLbl, "cell 3 1 ,grow");
		add(selectAudio, "cell 5 1 ,grow");
		add(createAudio, "cell 3 2 ,grow");
		add(playAudioBtn, "cell 5 2 ,grow");
		add(startLbl, "cell 1 2 ,grow");
		add(startMin, "cell 1 2 ,grow");
		add(semiCol, "cell 1 2 ,grow");
		add(startSec, "cell 1 2 ,grow");
		add(dot, "cell 1 2 ,grow");
		add(startMili, "cell 1 2 ,grow");
		add(addAudioBtn, "cell 1 4 5 1 ,grow");
		setVisible(true);

	}

	private void setUpAddAudioBtn(final ArrayList<AudioData> audioTrackList,
			JTable audTable) {
		addAudioBtn.setToolTipText("Add Another Audio Track to the Video");
		addAudioBtn.setBackground(Color.lightGray);
		addAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check mp3 path is not empty
				if (getMp3Path() != null && (!getMp3Path().equals(""))) {

					// check if text box is in correct format
					if (checkTwoNumbers(startMin.getText().trim())
							&& checkTwoNumbers(startSec.getText().trim())
							&& checkTwoNumbers(startMili.getText().trim())) {

						String start = startTime();

						AudioData audioData = new AudioData(getMp3Path(),
								mp3NameLbl.getText(), start, new TimeLabel()
										.durationStringToInt(start));

						// add to audio track list
						audioTrackList.add(audioData);

						// add to table frame
						mediaPlayerFrame.audTableFrame.addToTable(
								mp3NameLbl.getText(), start);

						// reset textfields
						resetText();

					}
				} else {
					JOptionPane.showMessageDialog(mediaPlayerFrame,
							"Please Select a MP3 File to Add");
				}

			}
		});

	}

	/**
	 * clears time textfields
	 */
	private void resetText() {
		startMin.setText(defaultText);
		startSec.setText(defaultText);
		startMili.setText(defaultText);
	}

	/**
	 * get string format of start time textbox
	 * 
	 * @return MM:SS.mm format of start time
	 */
	private String startTime() {
		return (addZero(startMin.getText().trim()) + ":"
				+ addZero(startSec.getText().trim()) + "." + addZero(startMili
				.getText().trim()));
	}

	/**
	 * if only a single digit is in text box a zero is added infront
	 * 
	 * @param text
	 *            - from textbox
	 * @return 2 char string
	 */
	private String addZero(String text) {
		if (text.length() == 1) {
			return "0" + text;
		}
		return text;
	}

	/**
	 * set min size of text fields make focus listener for input textfield
	 * 
	 * @param text
	 *            - JTextField
	 */
	private void textFocusListen(final JTextField text) {
		// set min size
		text.setMinimumSize(new Dimension(30, 40));

		// add focus listener
		text.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// check if it is 2 characters long and is numbers
				if (!checkTwoNumbers(text.getText().trim())) {
					// set text back to default
					text.setText(defaultText);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	/**
	 * Checks - if the text in text box is either a length of 1 or 2 - if the
	 * characters are numbers
	 * 
	 * @param text
	 * @return
	 */
	private boolean checkTwoNumbers(String text) {
		// check text is length 1 or 2
		if (text.length() > 2 || text.length() < 0) {
			JOptionPane.showMessageDialog(mediaPlayerFrame, formatErrorMessage);
			return false;
		} else {
			try {
				// Check if all characters are numbers
				int number = Integer.parseInt(text);
				if (number < 0) {// negative time can not be given
					JOptionPane.showMessageDialog(mediaPlayerFrame,
							"Can Not Be Negative");
					return false;
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(mediaPlayerFrame,
						formatErrorMessage);
				return false;
			}
		}
		return true;
	}

	/**
	 * Finds the duration of a MP3 file
	 * 
	 * @param mp3Path
	 */
	private void getMP3Information(String mp3Path) {
		durationLbl.findDuration(mp3Path);
	}

}
