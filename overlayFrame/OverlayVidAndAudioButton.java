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
		setText("Ovelay Video with Added Audiotracks");
		setToolTipText("Create New Video By Overlaying Audiotracks and Video");
	}

	public String overlayVideo(ArrayList<AudioData> listAudio,
			MediaPlayerJFrame video, String outName) {

		time = new TimeLabel();

		String ffmpegVideoPath = "ffmpeg -i " + video.getVideoPath() + " ";
		String ffmpegMP3Paths = "";
		String ffmpegMediaNumAndChannel = "";

		int count = 1;

		for (AudioData audio : listAudio) {

			int delay = audio.getStartMiliTime();

			System.out.println("delay " + delay);

			// add in paths of the audio files
			ffmpegMP3Paths = ffmpegMP3Paths + "-i " + audio.getPath() + " ";
			ffmpegMediaNumAndChannel = ffmpegMediaNumAndChannel + "[" + count+ ":a]adelay=" + delay;
			
			//if it is not the last audio add a semicolon
			if (count != listAudio.size()) {
				ffmpegMediaNumAndChannel = ffmpegMediaNumAndChannel + ";";
			}
			
			count++;
		}

		@SuppressWarnings("static-access")
		String ffmpegAmix = ",amix=inputs=" + count + "\" "
				+ video.VIDEO_DIR_RELATIVE_PATH + File.separator + outName
				+ ".mp4";

		System.out.println("ffmpeg " + ffmpegVideoPath);
		System.out.println("ffmpeg " + ffmpegMP3Paths);
		System.out.println("ffmpeg " + ffmpegMediaNumAndChannel);
		System.out.println("ffmpeg " + ffmpegAmix);

		String cmd = ffmpegVideoPath + ffmpegMP3Paths + "-filter_complex \""
				+ ffmpegMediaNumAndChannel + ffmpegAmix;
		System.out.println(cmd);

		CreateInBackground back = new CreateInBackground(cmd);
		back.execute();

		// ffmpeg -i Video/big_buck_bunny_1_minute.avi -i MP3/haehah.mp3
		// -filter_complex [media
		// number:channel]adelay=delayinMilisec4,amix=inputs=2 out.mp4

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
