package CS145MP2.src;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.applet.*;

public class BGMIcon extends GameObject {

    final int x = 990;
    final int y = 10;
    boolean on = true;
    boolean playing = true;
    boolean music1 = true;
    BufferedImage bgmOn, bgmOff, img;

    String song = "";

    int n = 0;

    BGM bgm;

    public BGMIcon() {
        song = "assets/music/wav/GoT_menu.wav";
        bgm = new BGM(song);
        bgmOn = MarioWindow.getImage("assets/img/misc/sound-on.png");
        bgmOff = MarioWindow.getImage("assets/img/misc/sound-off.png");
        img = bgmOn;
    }

    public void paint(Graphics2D g) {
        if (img != null) g.drawImage(img,x,y,null); 
    }

    public void keyReleased(String key) {
        if (key.equals("M")) {
            if (on) {            
                on = false;
                img = bgmOff;
                bgm.stop();
            }
            else {
                on = true;
                img = bgmOn;
                /* // JUST TRY THIS ---------------------
                This particular block of code is used for
                changing the background music.
                ----------------------------------------- 
                if (playing) {
                    bgm.setMusic("BGM\\Start Screen\\StartMusic.wav");
                    playing = false;
                }
                else {
                    bgm.setMusic("BGM\\Start Screen\\dkbonus.wav");
                    playing = true;
                }
                /* JUST TRY THIS --------------------- 
                bgm.setMusic("BGM\\Main BGM\\prospects\\" + (n%6) + ".wav"); // <---------- FOR EASIER BGM TESTING
                n++; // */
                bgm.start();
                
            }
        }

    }

    public void mousePressed(int mouse_x, int mouse_y) {
        
    }

    public void setMusic(String fileDir) {
        song = fileDir;
        bgm.setMusic(fileDir);
    }

}