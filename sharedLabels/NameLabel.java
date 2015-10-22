package sharedLabels;

import javax.swing.JLabel;

public class NameLabel extends JLabel {

	public NameLabel() {
		super();
		setText("");
	}
	
	/**
	 * Gets the Short name of the file
	 * @param path - of file
	 */
	public String getFileName(String path){
		String[] section = path.split("/");
		String name = section[section.length-1];
		setText(name);
		return name;
	}
	
}
