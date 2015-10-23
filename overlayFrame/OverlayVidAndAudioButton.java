package overlayFrame;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;

import overlayFrame.addAudioTrackPanel.AudioData;
import overlayFrame.addAudioTrackPanel.AudioToAddPanel;
import sharedLabels.TimeLabel;
import mediaMainFrame.MediaPlayerJFrame;
import doInBackground.CreateInBackground;

@SuppressWarnings("serial")
public class OverlayVidAndAudioButton extends JButton {

	TimeLabel time;

	public OverlayVidAndAudioButton() {
		super();
		setText("Overlay Video with Added Audiotracks");
		setToolTipText("Create New Video By Overlaying Audiotracks and Video");
	}

	public String overlayVideo(ArrayList<AudioData> listAudio,
			MediaPlayerJFrame video, String outName) {

		time = new TimeLabel();

		String ffmpegVideoPath = "ffmpeg -i " + video.getVideoPath() + " ";
		String ffmpegMP3Paths = "";
		String ffmpegDelay = "";
		String ffmpegMediaNumAndChannel = "";

		int count = 1;

		for (AudioData audio : listAudio) {

			int delay = audio.getStartMiliTime();

			// add in paths of the audio files
			ffmpegMP3Paths = ffmpegMP3Paths + "-i " + audio.getPath() + " ";
			ffmpegDelay = ffmpegDelay + "[" + count+ ":a]adelay=" + delay+"[a"+count+"]";
			ffmpegMediaNumAndChannel = ffmpegMediaNumAndChannel +"[a"+count+"]";
			
			//if it is not the last audio add a semicolon
			if (count != listAudio.size()) {
				ffmpegDelay = ffmpegDelay + ";";
			}
			
			count++;
		}

		@SuppressWarnings("static-access")
		String ffmpegAmix = "amix=inputs=" + count + "\" "
				+ video.VIDEO_DIR_RELATIVE_PATH + File.separator + outName
				+ ".mp4";

		String cmd = ffmpegVideoPath + ffmpegMP3Paths + "-filter_complex \""
				+ ffmpegDelay +","+ffmpegMediaNumAndChannel+ ffmpegAmix;

		CreateInBackground back = new CreateInBackground(cmd);
		back.execute();

		// ffmpeg -i Video/big_buck_bunny_1_minute.avi  -i MP3/pleasework.mp3 -i MP3/yery.mp3 -filter_complex "[1:a]adelay=200[a1];[2:a]adelay=10000[a2],[a1][a2]amix=inputs=3" Video/lll.mp4

		//TODO return path file ?
		return "";
	}

	/**
	 * Function to replace an input video's audio with a given mp3 file, and
	 * create a new output file
	 * 
	 * @param localMp3Path
	 * @param localVideoPath
	 * @param outputFile
	 */
	/*
	 * public void replaceAudio(String localMp3Path, String localVideoPath,
	 * String outputFile) { // Replace the video's audio with the synthesized
	 * text BackgroundAudioReplacer replacer = new BackgroundAudioReplacer(
	 * "ffmpeg -y -i \"" + localVideoPath + "\" -i \"" + localMp3Path +
	 * "\" -map 0:v -map 1:a \"" + MediaPlayerJFrame.VIDEO_DIR_RELATIVE_PATH +
	 * File.separator + outputFile + ".mp4\""); replacer.execute(); }
	 */

}
