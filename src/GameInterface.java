/*

KEYS:
Enter - Confirm
Left/Right - Choose
M - Toggle sound
S - View stats
A - View actions
E - View incoming attacks

*/

package CS145MP2.src;

import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class GameInterface extends GameObject {

	final String assetsPath = "CS145MP2/assets/img/";

	final int TITLE = 0;
	final int MENU = 1;
	final int LOADING = 2;
	final int GAME = 3;
	final int WIN = 4;
	final int LOSE = 5;

	final int NORMAL = 0;
	final int CHOOSE_HOUSE = 1;
	final int HOUSE_CHOSEN = 2;
	final int SAME_HOUSE = 3;

	int screen = TITLE;
	int state = NORMAL;

	int playerId = 4; // Client input
	int numOfPlayers = 4; // Server input

	BufferedImage screenIMG, playerIdIMG, playersNumIMG;
	BufferedImage menuIMG;
	BGMIcon bgm = new BGMIcon();
	ArrayList<String> playerSelect = new ArrayList<String>();
	ArrayList<BufferedImage> playerSelectImage = new ArrayList<BufferedImage>();
	BufferedImage houseSelected = null;
	BufferedImage screenNotifIMG = null;
     BufferedImage sigilIMG = null;
     BufferedImage miniSigilIMG = null;

	int houseNumSelected = 0;
	int screenNotifNum = 0;

     int miniSigil_xpos = 0;
     int miniSigil_ypos = 0;

     int sigil_xpos = 0;
     int sigil_ypos = 0;

	ArrayList<BufferedImage> screenNotif = new ArrayList<BufferedImage>();

	public GameInterface() {
		screenIMG = MarioWindow.getImage(assetsPath + "misc/title.png");
		menuIMG = MarioWindow.getImage(assetsPath + "1 - menu/menu.png");
		playerIdIMG = MarioWindow.getImage(assetsPath + "1 - menu/p" + playerId + ".png");
		playersNumIMG = MarioWindow.getImage(assetsPath + "1 - menu/" + numOfPlayers + "players.png");

		playerSelect.add("arryn");
		playerSelect.add("baratheon");
		playerSelect.add("lannister");
		playerSelect.add("martell");
		playerSelect.add("stark");
		playerSelect.add("targaryen");
		playerSelect.add("tyrell");

		for (int i = 0; i<playerSelect.size(); i++) {
			playerSelectImage.add(MarioWindow.getImage(assetsPath + "1 - menu/descrpt-" + playerSelect.get(i) + ".png"));
		}

		houseSelected = playerSelectImage.get(houseNumSelected);

		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/choose-house-yes.png")); // 0
		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/choose-house-no.png")); // 1
		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/house-chosen.png")); // 2
		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/same-house.png")); // 3
	}

	public void paint(Graphics2D g) {
        if (screenIMG != null) g.drawImage(screenIMG,0,0,null); 
        if (houseSelected != null && screen == MENU) g.drawImage(houseSelected,0,0,null);
        if (playerIdIMG != null && screen == MENU) g.drawImage(playerIdIMG,0,0,null);
        if (playersNumIMG != null && screen == MENU) g.drawImage(playersNumIMG,0,0,null);
        if (miniSigilIMG != null && screen == MENU && state == HOUSE_CHOSEN) g.drawImage(miniSigilIMG,miniSigil_xpos,miniSigil_ypos,null);
        if (screenNotifIMG != null && screen == MENU && state != NORMAL) g.drawImage(screenNotifIMG,0,0,null);
        if (sigilIMG != null && screen == LOADING) g.drawImage(sigilIMG,sigil_xpos,sigil_ypos,null);
        //if (pressEnter != null) g.drawImage(pressEnter,0,0,null); 
     }

     public void run() {
     	int k = 0;
     	while (screen != LOADING) {
     		k++;
     	}

     }

     public void keyReleased(String key) {
     	if (key.equals("Enter")) {
     		if (screen == TITLE) {
     			screenIMG = menuIMG;
     			screen = MENU;
     		}
     		else if (screen == MENU && state == NORMAL) {
     			state = CHOOSE_HOUSE;
     			screenNotifNum = 0;
     			screenNotifIMG = screenNotif.get(screenNotifNum);
     		}
     		else if (screen == MENU && state == CHOOSE_HOUSE) {
     			if (screenNotifNum == 0) {
     				// Insert checker for House
     				/*
     					// server protocol
     					Client.sendMessage(houseNumSelected);
     					String msg = Client.getMessage();
     					if (msg == "same-house") {
     						screenNotif.get(3);
     						state = SAME_HOUSE;
     					}
                              else -- check ff --
     				*/

     				screenNotifIMG = null;
     				MarioWindow.delay(200);
     				screenNotifIMG = screenNotif.get(2);
     				state = HOUSE_CHOSEN;

                         if (playerId == 1) {
                              sigil_xpos = 160;
                              sigil_ypos = 179;
                              miniSigil_xpos = 180;
                              miniSigil_ypos = 545;
                         }

                         else if (playerId == 2) {
                              sigil_xpos = 349;
                              sigil_ypos = 179;
                              miniSigil_xpos = 416;
                              miniSigil_ypos = 545;
                         }

                         else if (playerId == 3) {
                              sigil_xpos = 537;
                              sigil_ypos = 179;
                              miniSigil_xpos = 655;
                              miniSigil_ypos = 545;
                         }

                         else {
                              sigil_xpos = 725;
                              sigil_ypos = 179;
                              miniSigil_xpos = 898;
                              miniSigil_ypos = 545;
                         }

                         miniSigilIMG = MarioWindow.getImage(assetsPath + "sigils/mini-" + playerSelect.get(houseNumSelected) + ".png");
     				MarioWindow.delay(3000);

                         screenIMG = null;
     				houseSelected = null;
     				playersNumIMG = null;
     				playerIdIMG = null;
     				screenNotifIMG = null;

                         screen = LOADING;
     				screenIMG = MarioWindow.getImage(assetsPath + "2 - loading/loading-" + playerSelect.get(houseNumSelected) + ".png");
                         sigilIMG = MarioWindow.getImage(assetsPath + "sigils/big-" + playerSelect.get(houseNumSelected) + ".png");

                         // load other players

                         MarioWindow.delay(5000);

                         // screen = GAME;
                         // status = NORMAL;
                         // set screenIMG to GameBase

     			}
     			else {
     				screenNotifIMG = null;
     				state = NORMAL;
     			}
     		}
     		else if (screen == MENU && state == SAME_HOUSE) {
     			screenNotifIMG = null;
     			state = NORMAL;
     		}
     	}
     	else if (key.equals("Right")) {
     		if (screen == MENU && state == NORMAL) {
     			houseNumSelected++;
     			houseNumSelected%=7;
     			houseSelected = playerSelectImage.get(houseNumSelected);
     		}
     		if (state == CHOOSE_HOUSE) {
     			screenNotifNum++;
     			screenNotifNum%=2;
     			screenNotifIMG = screenNotif.get(screenNotifNum);
     		}
     	}
     	else if (key.equals("Left")) {
     		if (screen == MENU && state == NORMAL) {
     			houseNumSelected--;
     			houseNumSelected%=7;
     			houseSelected = playerSelectImage.get(houseNumSelected);
     		}
     		if (state == CHOOSE_HOUSE) {
     			screenNotifNum--;
     			screenNotifNum%=2;
     			screenNotifIMG = screenNotif.get(screenNotifNum);
     		}
     	}
     }

	

	// --------------------------------------- [ MAIN FUNCTION ] --------------------------------------- //

	/*
	public static void main(String args[]) {
		
		MarioWindow window = new MarioWindow();	

		GameInterface test = new GameInterface();
		window.add(test);
		window.add(test.bgm);
		window.startGame();

	}*/
	

}