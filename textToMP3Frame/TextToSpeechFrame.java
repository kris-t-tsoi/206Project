package textToMP3Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.VoiceStatus;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.xml.transform.OutputKeys;

import textToMP3Frame.buttons.CreateMP3Btn;
import textToMP3Frame.buttons.PlayTextBtn;
import textToMP3Frame.textBoxAndSliders.PitchSlider;
import textToMP3Frame.textBoxAndSliders.TextToMP3TextBox;
import textToMP3Frame.textBoxAndSliders.VoiceSpeedSlider;
import net.miginfocom.swing.MigLayout;

public class TextToSpeechFrame extends JFrame {
	
	//GUI Components
	final TextToSpeechFrame thisFrame = this;
	JPanel contentPane;
	PlayTextBtn playText;
	CreateMP3Btn createMP3;
	TextToMP3TextBox userText;
	VoiceSpeedSlider speedSlide;
	PitchSlider pitchSlide;
	
	// Constants for the textfield - Max number of words which can be
	// played/saved, and error message
	public static final String ERROR_WORD_LIMIT_MESSAGE = "Sorry, you have exceeded the maximum word count of 50.";
	
	
	//Audio Values
	private float speed;
	private int startPitch;
	private int endPitch;
	private String text;
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getStartPitch() {
		return startPitch;
	}

	public void setStartPitch(int startPitch) {
		this.startPitch = startPitch;
	}

	public int getEndPitch() {
		return endPitch;
	}

	public void setEndPitch(int endPitch) {
		this.endPitch = endPitch;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	/**
	 * Constructor for Text to Speech Frame
	 * Used for - playing text to audio
	 * 			- creating MP3 
	 * @param title
	 */
	public TextToSpeechFrame(String title) {
		
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setBounds(900, 50, 600, 350);
		setVisible(true);
		
		
		contentPane = new JPanel();		
		setContentPane(contentPane);
		
		playText = new PlayTextBtn(thisFrame);
		playText.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//get values from sliders and text box
				getSliderValues();
				
				// If the text is under the allowed limit, speak the text
				if (userText.checkTxtLength()) {
					playText.sayWithFestival(getSpeed(),getStartPitch(),getEndPitch(),getText());
					//TODO get pid so can stop
				} else {
					JOptionPane.showMessageDialog(thisFrame, ERROR_WORD_LIMIT_MESSAGE);
				}
			}
		});
		
		
		createMP3 = new CreateMP3Btn(thisFrame);
		createMP3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				getSliderValues();
				if (userText.checkTxtLength()) {
					String mp3Name = JOptionPane.showInputDialog(thisFrame,
							"Enter a name for the mp3 file");
					
					if ((mp3Name != null) && !mp3Name.startsWith(" ")) {
						createMP3.createAudio(getSpeed(),getStartPitch(),getEndPitch(),getText(), mp3Name);
					}
				} else {
					JOptionPane.showMessageDialog(thisFrame,
							ERROR_WORD_LIMIT_MESSAGE);
				}
			}
		});
		
		userText = new TextToMP3TextBox();
		
		//TODO increase font size
		JLabel titleLbl = new JLabel("Type in Text to Synthesis into MP3 Audio");
		JLabel speedLbl = new JLabel("Voice Speed");
		JLabel pitchLbl = new JLabel("Vocal Pitch");
		
		speedSlide = new VoiceSpeedSlider();
		pitchSlide = new PitchSlider();
		

		contentPane.setLayout(new MigLayout(
				"", // Layout Constraint
				"[4px,grow 0,shrink 0][98px,grow 0, shrink 0][2px,grow 0,shrink 0][346px,grow, shrink]"
						+ "[4px,grow 0,shrink 0][196px,grow 0, shrink 0][4px,grow ,shrink ]", // Column Constraints
				"[70px][70px,grow, shrink][70px][70px]")); // Row Constraints		
		
		
		contentPane.add(titleLbl, "cell 1 0 5 1,grow");
		contentPane.add(userText, "cell 1 1 5 1,grow");
		contentPane.add(speedLbl, "cell 1 2,grow");
		contentPane.add(pitchLbl, "cell 1 3,grow");
		contentPane.add(speedSlide, "cell 3 2 ,grow");
		contentPane.add(pitchSlide, "cell 3 3 ,grow");
		contentPane.add(playText, "cell 5 2,grow");
		contentPane.add(createMP3, "cell 5 3,grow");
		setVisible(true);
		
	}
	
	/**
	 * get the current slider and textbox values
	 */
	private void getSliderValues(){
		setSpeed((float) (speedSlide.getValue())/10);
		int[] pitchRange = pitchSlide.findRange(pitchSlide.getValue());
		setStartPitch(pitchRange[0]);
		setEndPitch(pitchRange[1]);
		setText(userText.getText());
	}
	
}
