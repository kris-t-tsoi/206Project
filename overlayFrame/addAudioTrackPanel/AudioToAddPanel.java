package overlayFrame.addAudioTrackPanel;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import overlayFrame.AddAudioButton;
import overlayFrame.OverlayAudioToVideoFrame;
import fileChoosing.UserFileChoose;
import sharedLabels.NameLabel;
import sharedLabels.TimeLabel;
import textToMP3Frame.TextToSpeechFrame;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

public class AudioToAddPanel extends JPanel {

	// Panels and Frames
	AudioToAddPanel thisPane;
	final MediaPlayerJFrame mediaPlayerFrame;
	TextToSpeechFrame create;
	String formatErrorMessage = "Please have format [MM:SS.mm] using Numbers";

	// file choosing
	final private UserFileChoose fileChoose;
	private String mp3Path = "";

	// Time Labels and textfields
	private TimeLabel durationLbl;
	private NameLabel mp3NameLbl;
	private JLabel startLbl;
	private JLabel endLbl;
	private TimeLabel endTime;
	private JTextField startMin;
	private JTextField startSec;
	private JTextField startMili;

	// Buttons
	private SelectMP3Btn selectAudio;
	private CreateMp3ForAudioPanelBtn createAudio;

	// Constant strings
	private final String createMP3Title = "Created MP3 To Overlay";
	private final String defaultText = "00";

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
	public AudioToAddPanel(final MediaPlayerJFrame mainFrame,
			final ArrayList<AudioData> audioTrackList,
			final OverlayAudioToVideoFrame parentFrame) {
		thisPane = this;
		mediaPlayerFrame = mainFrame;
		setSize(700, 175);
		fileChoose = new UserFileChoose(mediaPlayerFrame);

		// MP3 Labels initialize
		JLabel mp3TitleLbl = new JLabel("MP3 :");
		mp3NameLbl = new NameLabel();
		JLabel duraTitleLbl = new JLabel("Duration :");
		durationLbl = new TimeLabel();

		// Time Labels initialize
		startLbl = new JLabel("Start [MM:SS.mm]:");
		startMin = new JTextField();
		startSec = new JTextField();
		startMili = new JTextField();
		JLabel semiCol = new JLabel(":");
		JLabel dot = new JLabel(".");
		endLbl = new JLabel("End: ");
		endTime = new TimeLabel();

		// reset variables to initial values
		resetVariables();

		textFocusListen(startMin);
		textFocusListen(startSec);
		textFocusListen(startMili);

		// select existing MP3
		selectAudio = new SelectMP3Btn();
		selectAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp3Path = fileChoose.chooseMP3Path(thisPane);
				mp3NameLbl.setText(mp3NameLbl.getFileName(mp3Path));
				getMP3Information(mp3Path);
			}
		});

		// Create a new MP3
		createAudio = new CreateMp3ForAudioPanelBtn();
		createAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				create = new TextToSpeechFrame(mainFrame);

			}
		});

		// Add new audiotrack button
		AddAudioButton addAudioBtn = new AddAudioButton();
		addAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check mp3 path is not empty
				if (getMp3Path() != null || (!getMp3Path().equals(""))) {

					// check if text box is in correct format
					if (checkTwoNumbers(startMin.getText().trim())
							&& checkTwoNumbers(startSec.getText().trim())
							&& checkTwoNumbers(startMili.getText().trim())) {

						String start = addZero(startMin.getText().trim()) + ":"
								+ addZero(startSec.getText().trim()) + "."
								+ addZero(startMili.getText().trim());

						AudioData audioData = new AudioData(getMp3Path(),
								mp3NameLbl.getText(), start, new TimeLabel()
										.durationStringToInt(start), endTime
										.getText());
						audioTrackList.add(audioData);

						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						// clear variables
						resetVariables();

					}
				} else {
					JOptionPane.showMessageDialog(parentFrame,
							"Please Select a MP3 File to Add");
				}

			}
		});

		// TODO Play mp3 file to listen

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
		add(createAudio, "cell 5 2 ,grow");
		add(startLbl, "cell 1 2 ,grow");
		add(startMin, "cell 1 2 ,grow");
		add(semiCol, "cell 1 2 ,grow");
		add(startSec, "cell 1 2 ,grow");
		add(dot, "cell 1 2 ,grow");
		add(startMili, "cell 1 2 ,grow");
		add(endLbl, "cell 3 2 ,grow");
		add(addAudioBtn, "cell 1 4 5 1 ,grow");
		setVisible(true);

	}

	/**
	 * clears mp3 name label, time textfields and time labels
	 */
	private void resetVariables() {
		mp3NameLbl.setText("");
		setMp3Path("");
		startMin.setText(defaultText);
		startSec.setText(defaultText);
		startMili.setText(defaultText);
		durationLbl.setText("");
		endTime.setText("");
	}

	// TODO work out mp3 end time
	private void addForEndtime() {

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
	 * make focus listener for input textfield
	 * 
	 * @param text
	 *            - JTextField
	 */
	private void textFocusListen(final JTextField text) {
		text.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// check if it is 2 characters long and is numbers
				if (checkTwoNumbers(text.getText().trim())) {
					// if true then update end time label
					// TODO update end time
				} else { // set text back to default
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
			JOptionPane.showMessageDialog(thisPane, formatErrorMessage);
			return false;
		} else {
			try {
				// Check if all characters are numbers
				Integer.parseInt(text);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(thisPane, formatErrorMessage);
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
