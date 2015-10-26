package sharedLabels;

import javax.swing.JLabel;

@SuppressWarnings("serial")
/**
 * Label that gets the name of a file 
 * from the path
 * @author kristy
 *
 */
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
		//split using file seperator
		String[] section = path.split("/");
		
		//gets the last value in array
		String name = section[section.length-1];
		setText(name);
		return name;
	}
	
}
