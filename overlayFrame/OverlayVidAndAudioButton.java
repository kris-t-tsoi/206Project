package overlayFrame;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;

import overlayFrame.addAudioTrackPanel.AudioToAddPanel;
import sharedLabels.TimeLabel;
import mediaMainFrame.MediaPlayerJFrame;
import doInBackground.BackgroundUse;
import doInBackground.CreateInBackground;
import doInBackground.WriteSchemeFiles;

public class OverlayVidAndAudioButton extends JButton {
	
	TimeLabel time;

	public OverlayVidAndAudioButton() {
		super();
		setText("Overlay and Create Video");
		setToolTipText("Create New Video By Overlaying Audiotracks and Video");
	}
	
	public String overlayVideo(ArrayList<AudioToAddPanel> listAudio, MediaPlayerJFrame video,String outName){
		
		time = new TimeLabel();
		
		String ffmpegVideoPath = "ffmpeg -i "+video.getVideoPath()+" ";
		String ffmpegMP3Paths = "";
		String ffmpegMediaNumAndChannel = "";
		//String ffmpegAdelay;
		
		int count = 1;
		
		for(AudioToAddPanel audio : listAudio){
			
			String delay = audio.getStartMin().getText()+":"+audio.getStartMin().getText()+"."+audio.getStartMili().getText();
			
			//add in paths of the audio files
			ffmpegMP3Paths = ffmpegMP3Paths+ "-i "+audio.getMp3Path()+" ";
			ffmpegMediaNumAndChannel = ffmpegMediaNumAndChannel+"["+count+":a]adelay="+time.durationStringToDouble(delay);
					count++;
		}
		
		String ffmpegAmix = ",amix=inputs="+count+" "+video.VIDEO_DIR_RELATIVE_PATH+outName+".mp4";
		
		String cmd = ffmpegVideoPath+ffmpegMP3Paths+"-filter_complex "+ffmpegMediaNumAndChannel+ffmpegAmix;
		
		CreateInBackground back = new CreateInBackground(cmd);
		back.execute();
		
		//ffmpeg -i Video/big_buck_bunny_1_minute.avi -i MP3/haehah.mp3 -filter_complex [media number:channel]adelay=delayinMilisec4,amix=inputs=2 out.mp4
		
		
		/*
		
		String mp3Path = MediaPlayerJFrame.MP3_DIR_RELATIVE_PATH+ File.separator +"";

		// MP3 created in the background
		BackgroundUse backGrd = new BackgroundUse("festival -b "+ playScm.getAbsolutePath().toString() + ";"
				+ "ffmpeg -y -i \"" + mp3Path + audioName + ".wav\" -f mp3 \""+ mp3Path+ audioName + ".mp3\";" 
				+ "rm \"" + mp3Path+audioName + ".wav\"");
		backGrd.execute();
		
		//TODO Progress bar
	
		return mp3Path+ audioName + ".mp3";
		
		*/
		
		return "";
	}
	
	
	
	
	
	
	/**
	 * Function to replace an input video's audio with a given mp3 file, and create a new output file
	 * @param localMp3Path
	 * @param localVideoPath
	 * @param outputFile
	 */
/*	public void replaceAudio(String localMp3Path, String localVideoPath, String outputFile) {
				// Replace the video's audio with the synthesized text
				BackgroundAudioReplacer replacer = new BackgroundAudioReplacer(
						"ffmpeg -y -i \"" + localVideoPath + "\" -i \"" + localMp3Path + "\" -map 0:v -map 1:a \""
								+ MediaPlayerJFrame.VIDEO_DIR_RELATIVE_PATH + File.separator + outputFile + ".mp4\"");
				replacer.execute();
	}
	
	
	*/

}
