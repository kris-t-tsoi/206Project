package overlayFrame.audioTable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import doInBackground.UpdateRunnable;
import net.miginfocom.swing.MigLayout;
import overlayFrame.addAudioTrackPanel.AudioData;
import mediaMainFrame.MediaPlayerJFrame;

public class AudioTableFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	AudioTableFrame thisFrame;

	/**
	 * Creates a JFrame with list of all audio tracks to be overlaid with video
	 * 
	 * @param video
	 *            main Frame - where audiotrack list is stored
	 */
	public AudioTableFrame(final MediaPlayerJFrame video) {
		thisFrame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(1600, 100, 300, 650);
		setMinimumSize(new Dimension(300, 100));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel frameName = new JLabel("Audio to be Overlaid");
		frameName.setFont(new Font("Audio To Be Overlaid", Font.BOLD, 20));
		frameName.setBounds(5, 5, 200, 100);

		JScrollPane scrollPane = new JScrollPane();
		
		
		//TODO refresh table

		// use to update video slider and current time label every 0.5 sec
		// https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/basic/PlayerControlsPanel.java
		ScheduledExecutorService executorService = Executors
				.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new UpdateRunnable(thisFrame), 0L,
				500L, TimeUnit.MILLISECONDS);

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
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		// get all added mp3s
		for (AudioData audData : video.getAudioTrackList()) {
			model.addRow(new Object[] { audData.getName(),
					audData.getStartTime(), audData.getEndTime() });
		}

		/*
		 * // sort column //Credit:
		 * http://www.codejava.net/java-se/swing/6-techniques
		 * -for-sorting-jtable-you-should-know TableRowSorter<TableModel> sort =
		 * new TableRowSorter<>(table.getModel()); table.setRowSorter(sort);
		 * ArrayList<RowSorter.SortKey> sortValue = new ArrayList<>(); //sort
		 * the start times int colIndex = 1; sortValue.add(new
		 * RowSorter.SortKey(colIndex, SortOrder.DESCENDING));
		 * sort.setSortKeys(sortValue); sort.sort();
		 * 
		 * // only allow selection of one row
		 * table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * 
		 * // set table model table.setModel(model); table.setBorder(new
		 * LineBorder(new Color(0, 0, 0)));
		 */

		/**
		 * button deletes selected mp3 from audio track list
		 */
		JButton deleteAudioBtn = new JButton("Delete Audio");
		deleteAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// get data from selected row
				int index = table.getSelectedRow();
				video.getAudioTrackList().remove(index);

			}
		});
		deleteAudioBtn.setFont(new Font("Dialog", Font.BOLD, 20));
		deleteAudioBtn.setBounds(650, 508, 200, 150);

		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[5px,grow 0,shrink 0][90px,grow, shrink][5px,grow 0,shrink 0]", // Column
																							// Constraints
						"[5px][50px][5px][200px,grow,shrink][5px][75px][5px]")); // Row
																					// Constraints
		add(frameName, "cell 1 1, grow");
		add(scrollPane, "cell 1 3, grow");
		add(deleteAudioBtn, "cell 1 5, grow");

		setVisible(true);
	}

}
