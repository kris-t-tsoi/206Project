package overlayFrame.audioTable;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import overlayFrame.addAudioTrackPanel.AudioData;
import mediaMainFrame.MediaPlayerJFrame;

@SuppressWarnings("serial")
public class AudioTableFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	JScrollPane scrollPane;
	AudioTableFrame thisFrame;
	MediaPlayerJFrame video;

	// buttons
	JButton deleteAudioBtn;
	JButton refreshAudioBtn;

	/**
	 * Creates a JFrame with list of all audio tracks to be overlaid with video
	 * 
	 * @param video
	 *            main Frame - where audiotrack list is stored
	 */
	public AudioTableFrame(MediaPlayerJFrame vid) {
		thisFrame = this;
		video = vid;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(1600, 100, 300, 650);
		setMinimumSize(new Dimension(300, 100));

		// setup panes
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		scrollPane = new JScrollPane();

		//title label
		JLabel frameName = new JLabel("Audio to be Overlaid");
		frameName.setFont(new Font("Audio To Be Overlaid", Font.BOLD, 20));

		// set up table
		setupTable(video.getAudioTrackList());

		// setup delete and refresh button
		setUpDeleteBtn();
		setUpRefreshBtn();

		//set layout of contentPane
		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[5px,grow 0,shrink 0][90px,grow, shrink][5px,grow 0,shrink 0]", // Column
																							// Constraints
						"[5px][50px][5px][200px,grow,shrink][3px][50px][5px]")); // Row
																					// Constraints
		add(frameName, "cell 1 1, grow");
		add(scrollPane, "cell 1 3, grow");
		add(deleteAudioBtn, "cell 1 5, grow");
		add(refreshAudioBtn, "cell 1 6, grow");

		setVisible(true);
	}

	// sets up the delete button
	private void setUpDeleteBtn() {
		deleteAudioBtn = new JButton("Delete Audio");
		deleteAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// check if a row is selected or not
				if (table.getSelectedRow() != -1) {

					// get data from selected row
					int index = table.getSelectedRow();
					video.getAudioTrackList().remove(index);

					setupTable(video.getAudioTrackList());
					contentPane.repaint();
				}
			}
		});
		deleteAudioBtn.setFont(new Font("Dialog", Font.BOLD, 20));
	}

	// set up refresh button
	private void setUpRefreshBtn() {
		refreshAudioBtn = new JButton("Refresh Added Audio");
		refreshAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setupTable(video.getAudioTrackList());
				contentPane.repaint();
			}
		});

		refreshAudioBtn.setFont(new Font("Dialog", Font.ITALIC, 20));
	}

	/**
	 * Gets the list of audio tracks and adds them to the table
	 * 
	 * @param arrayList
	 *            - list of Audio tracks
	 */
	@SuppressWarnings("serial")
	private void setupTable(ArrayList<AudioData> arrayList) {
		// set JTable with items in audioTrackList
		table = new JTable(new DefaultTableModel(new Object[] { "Name",
				"Start Time", "End Time" }, 0) {
			@Override
			public Class getColumnClass(int columnIndex) {
				return Integer.class;
			}

		});
		table.setFont(new Font("Dialog", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		final DefaultTableModel model = (DefaultTableModel) table.getModel();

		// get all added mp3s
		for (AudioData audData : arrayList) {
			model.addRow(new Object[] { audData.getName(),
					audData.getStartTime(), audData.getEndTime() });
		}
		// only allow selection of one row
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

}
