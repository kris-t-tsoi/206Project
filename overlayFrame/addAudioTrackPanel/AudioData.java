package overlayFrame.addAudioTrackPanel;

public class AudioData {
	
	private String path;
	private String startTime;
	private int startMiliTime;
	private String endTime;
	
	
	public AudioData(String mp3Path, String start,double mili, String end) {
		path = mp3Path;
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


}
