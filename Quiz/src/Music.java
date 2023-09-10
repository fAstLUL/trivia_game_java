import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

		Clip clip;
		
		public void setURL(URL soundFileName) {
			
			try {
				//File file = new File(soundFileName);
				AudioInputStream sound = AudioSystem.getAudioInputStream(soundFileName);
				clip = AudioSystem.getClip();
				clip.open(sound);
			}
			catch(Exception e) {
				
			}
		}
		
		public void play() {
			
			clip.setFramePosition(0);
			clip.start();
		}
		
		public void loop() {
			
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		public void stop() {
			
			clip.stop();
			clip.close();
		}
		
	}