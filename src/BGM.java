package CS145MP2.src;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class BGM {

	Clip clip;

	public BGM(String fileDir) {
		setMusic(fileDir);
	}

	public void start() {
		clip.start();
	}

	public void stop() {
		clip.stop();
		clip.setMicrosecondPosition(0);
	}

	public void setMusic(String fileDir) {
		try {
			if (clip!=null) clip.stop();
			clip = null;
			File soundFile = new File(fileDir);
			AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
	    		clip = (Clip) AudioSystem.getLine(info);
	    		clip.open(sound);
	    		clip.loop(clip.LOOP_CONTINUOUSLY);
    		} catch (Exception e) {
    			System.out.println("Some exception: " + e);
    		}
	}

}        
