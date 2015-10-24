package overlayMedia.addAudioTrackPanel;

public class AudioData {
	
	private String path;
	private String name;
	private String startTime;
	private int startMiliTime;
	private String endTime;
	
	
	public AudioData(String mp3Path, String fileName, String start,double mili, String end) {
		path = mp3Path;
		name = fileName;
		startTime = start;
		startMiliTime = (int) mili;
		endTime = end;
	}


	public String getPath() {
		return path;
	}

	public String getStartTime() {
		return startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public int getStartMiliTime() {
		return startMiliTime;
	}


	public String getName() {
		return name;
	}


}
