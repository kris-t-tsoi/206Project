package overlayMedia.addAudioTrackPanel;

public class AudioData {
	
	private String path;
	private String name;
	private String startTime;
	private int startMiliTime;
	
	
	public AudioData(String mp3Path, String fileName, String start,double mili) {
		path = mp3Path;
		name = fileName;
		startTime = start;
		startMiliTime = (int) mili;
	}


	public String getPath() {
		return path;
	}

	public String getStartTime() {
		return startTime;
	}

	public int getStartMiliTime() {
		return startMiliTime;
	}


	public String getName() {
		return name;
	}


}
