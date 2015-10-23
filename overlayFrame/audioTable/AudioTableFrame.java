package overlayFrame.audioTable;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AudioTableFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	public AudioTableFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1200, 100, 300, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//TODO set min size


		//TODO Title
		
		JLabel frameName = new JLabel();
		frameName.setFont(new Font("Audio To Be Overlaid", Font.BOLD, 50));
		frameName.setBounds(5, 5, 200, 100);;
		contentPane.add(frameName);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		// set JTable
		table = new JTable(new DefaultTableModel(new Object[] { "Name", "Start Time", "End Time" }, 0) {
			@Override
			public Class getColumnClass(int columnIndex) {
				return Integer.class;
			}

		});
		table.setFont(new Font("Dialog", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
	}
	
	
}
