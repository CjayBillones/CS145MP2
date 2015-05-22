/*

KEYS:
Enter - Confirm
Left/Right - Choose
M - Toggle sound
S - View stats
A - View actions
E - View incoming attacks

10 characters for stats

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

     final int ATTACK_CHOOSE = 4;
     final int SOLDIERS_CHOOSE = 5;
     final int WON_LOST_WAR = 6;
     final int ENEMY_ATTACKED = 7;
     final int WHITE_WALKER = 8;
     final int PLAYER_DEAD = 9;

	int screen = TITLE;
	int state = NORMAL;

     // PLAYER STATS
	int playerId = 1; // Client input
	int numOfPlayers = 2; // Server input
     int maxHP = 30;
     String house = "";

     // TITLE IMGs
     BufferedImage screenIMG;

     // MENU IMGs
     BufferedImage playerIdIMG, playersNumIMG, menuIMG;
     ArrayList<BufferedImage> playerSelectImage = new ArrayList<BufferedImage>();
     BufferedImage houseSelected = null;
     BufferedImage screenNotifIMG = null;
     ArrayList<BufferedImage> screenNotif = new ArrayList<BufferedImage>();
     BufferedImage miniSigil = null;

     // LOADING IMGs
     BufferedImage sigilIMG = null;

     // GAME IMGs
     BufferedImage statsBarOut, statsBarIn, actionBarOut, actionBarIn, attackBarOut, attackBarIn;
     BufferedImage castleIMG = null;
     BufferedImage statsBarIMG = null;
     BufferedImage actionBarIMG = null;
     BufferedImage attackBarIMG = null;
     BufferedImage[] miniSigilIMGs = new BufferedImage[7];
     ArrayList<BufferedImage> outAttacks = new ArrayList<BufferedImage>(); // max 5
     ArrayList<BufferedImage> inAttacks = new ArrayList<BufferedImage>(); // max 4
     BufferedImage[] brothelIMG = new BufferedImage[4];

     //BufferedImage
     BufferedImage[] actionsIMG = new BufferedImage[5];
     BufferedImage[] actionsSelectIMG = new BufferedImage[5];
     int actionSelected = 0;

     
	BGMIcon bgm = new BGMIcon();
	ArrayList<String> playerSelect = new ArrayList<String>();

	int houseNumSelected = 0;
	int screenNotifNum = 0;

     int miniSigil_xpos = 0;
     int miniSigil_ypos = 0;

     int sigil_xpos = 0;
     int sigil_ypos = 0;

     Stats health = new Stats("health");
     Stats money = new Stats("money");
     Stats defenders = new Stats("defenders");
     Stats warriors = new Stats("warriors");
     Stats brothels = new Stats("brothels");

     boolean statsOut = false;
     boolean actionsOut = false;
     boolean attacksOut = false;

	public GameInterface() {
		screenIMG = MarioWindow.getImage(assetsPath + "misc/title.png");
		menuIMG = MarioWindow.getImage(assetsPath + "1 - menu/menu.png");
		playerIdIMG = MarioWindow.getImage(assetsPath + "1 - menu/p" + playerId + ".png");
		playersNumIMG = MarioWindow.getImage(assetsPath + "1 - menu/" + numOfPlayers + "players.png");

          statsBarOut = MarioWindow.getImage(assetsPath + "3 - game/stats-out.png");
          statsBarIn = MarioWindow.getImage(assetsPath + "3 - game/stats-in.png");
          actionBarOut = MarioWindow.getImage(assetsPath + "3 - game/actions-out.png");
          actionBarIn = MarioWindow.getImage(assetsPath + "3 - game/actions-in.png");
          attackBarOut = MarioWindow.getImage(assetsPath + "3 - game/attacks-out.png");
          attackBarIn = MarioWindow.getImage(assetsPath + "3 - game/attacks-in.png");
          statsBarIMG = statsBarIn;
          actionBarIMG = actionBarIn;
          attackBarIMG = attackBarIn;
          actionsIMG[0] = MarioWindow.getImage(assetsPath + "3 - game/actions-mini-attack.png");
          actionsIMG[1] = MarioWindow.getImage(assetsPath + "3 - game/actions-mini-warrior.png");
          actionsIMG[2] = MarioWindow.getImage(assetsPath + "3 - game/actions-mini-defender.png");
          actionsIMG[3] = MarioWindow.getImage(assetsPath + "3 - game/actions-mini-brothel.png");
          actionsIMG[4] = MarioWindow.getImage(assetsPath + "3 - game/actions-mini-repair.png");
          actionsSelectIMG[0] = MarioWindow.getImage(assetsPath + "3 - game/actions-big-attack.png");
          actionsSelectIMG[1] = MarioWindow.getImage(assetsPath + "3 - game/actions-big-warrior.png");
          actionsSelectIMG[2] = MarioWindow.getImage(assetsPath + "3 - game/actions-big-defender.png");
          actionsSelectIMG[3] = MarioWindow.getImage(assetsPath + "3 - game/actions-big-brothel.png");
          actionsSelectIMG[4] = MarioWindow.getImage(assetsPath + "3 - game/actions-big-repair.png");
          brothelIMG[0] = MarioWindow.getImage(assetsPath + "3 - game/brothel1.png");
          brothelIMG[1] = MarioWindow.getImage(assetsPath + "3 - game/brothel2.png");
          brothelIMG[2] = MarioWindow.getImage(assetsPath + "3 - game/brothel3.png");
          brothelIMG[3] = MarioWindow.getImage(assetsPath + "3 - game/brothel4.png");

		playerSelect.add("arryn");
		playerSelect.add("baratheon");
		playerSelect.add("lannister");
		playerSelect.add("martell");
		playerSelect.add("stark");
		playerSelect.add("targaryen");
		playerSelect.add("tyrell");

		for (int i = 0; i<playerSelect.size(); i++) {
               String houseName = playerSelect.get(i);
			playerSelectImage.add(MarioWindow.getImage(assetsPath + "1 - menu/descrpt-" + houseName + ".png"));
               miniSigilIMGs[i] = MarioWindow.getImage(assetsPath + "sigils/mini-" + houseName + ".png");
		}

		houseSelected = playerSelectImage.get(houseNumSelected);

		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/choose-house-yes.png")); // 0
		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/choose-house-no.png")); // 1
		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/house-chosen.png")); // 2
		screenNotif.add(MarioWindow.getImage(assetsPath + "1 - menu/same-house.png")); // 3

          health.set(30);
          money.set(1000);
          defenders.set(10);
          warriors.set(10);
          brothels.set(1);

          //
          //Testing start
          //

          


          //
          //Testing end
          //

	}

	public void paint(Graphics2D g) {
          if (screenIMG != null) g.drawImage(screenIMG,0,0,null); 

          if (houseSelected != null && screen == MENU) g.drawImage(houseSelected,0,0,null);
          if (playerIdIMG != null && screen == MENU) g.drawImage(playerIdIMG,0,0,null);
          if (playersNumIMG != null && screen == MENU) g.drawImage(playersNumIMG,0,0,null);
          if (miniSigil != null && screen == MENU && state == HOUSE_CHOSEN) g.drawImage(miniSigil,miniSigil_xpos,miniSigil_ypos,null);
          if (screenNotifIMG != null && screen == MENU && state != NORMAL) g.drawImage(screenNotifIMG,0,0,null);

          if (sigilIMG != null && screen == LOADING) g.drawImage(sigilIMG,sigil_xpos,sigil_ypos,null);

          if (screen == GAME) {
               for (int i = 0; i<brothels.stat; i++) {
                    if (brothelIMG[i] != null) g.drawImage(brothelIMG[i],0,0,null);
               }
          }
          if (castleIMG != null && screen == GAME) g.drawImage(castleIMG,0,0,null);
          if (statsBarIMG != null && screen == GAME) g.drawImage(statsBarIMG,0,0,null);
          if (actionBarIMG != null && screen == GAME) g.drawImage(actionBarIMG,0,0,null);
          if (attackBarIMG != null && screen == GAME) g.drawImage(attackBarIMG,0,0,null);
          for (int i = 0; i<5; i++) {
               if (i!=actionSelected) {
                    if (actionsIMG[i] != null && screen == GAME && actionsOut) g.drawImage(actionsIMG[i],0,0,null);
               }
          }
          if (actionsSelectIMG[actionSelected] != null && screen == GAME && actionsOut) g.drawImage(actionsSelectIMG[actionSelected],0,0,null);
          
          while (outAttacks.size() > 5) {
               outAttacks.remove(0);
          }

          for (int i = 0; i<5; i++) {
               if (i<outAttacks.size() && screen == GAME && actionsOut) {
                    int xPos = 555 + (72*i);
                    int yPos = 555;
                    g.drawImage(outAttacks.get(i),xPos,yPos,null);
               }
          }

          while (inAttacks.size() > 4) {
               inAttacks.remove(0);
          }

          for (int i = 0; i<4; i++) {
               if (i<inAttacks.size() && screen == GAME && attacksOut) {
                    int xPos = 980;
                    int yPos = 180 + (58*i);
                    g.drawImage(inAttacks.get(i),xPos,yPos,null);
               }
          }
        
        //if (pressEnter != null) g.drawImage(pressEnter,0,0,null); 
     }
     /*
     public void run() {
     	int k = 0;
     	while (screen != LOADING) {
     		k++;
     	}

     }
     */

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

                         String house = playerSelect.get(houseNumSelected);

                         // do client shit
                         if (house.equals("arryn")) {
                              // +20 health
                         }
                         else if (house.equals("baratheon")) {
                              // +20 defense
                         }
                         else if (house.equals("lannister")) {
                              // +500 gold
                         }
                         else if (house.equals("martell")) {
                              // +20 offense
                         }
                         else if (house.equals("stark")) {
                              // SKILL: white walker
                         }
                         else if (house.equals("targaryen")) {
                              // SKILL: dragon
                         }
                         else {   
                              // SKILL: peek
                         }

                         miniSigil = MarioWindow.getImage(assetsPath + "sigils/mini-" + house + ".png");
     				MarioWindow.delay(3000); //change to protocol

                         screenIMG = null;
     				houseSelected = null;
     				playersNumIMG = null;
     				playerIdIMG = null;
     				screenNotifIMG = null;
                         screen = LOADING;
     				screenIMG = MarioWindow.getImage(assetsPath + "2 - loading/loading-" + house + ".png");
                         sigilIMG = MarioWindow.getImage(assetsPath + "sigils/big-" + house + ".png");

                         /*
                              if (numOfPlayers == 2) {
                                   
                              }
                         */

                         // load other players

                         MarioWindow.delay(5000);

                         bgm.setMusic("CS145MP2/assets/music/wav/GoT_main.wav");
                         screen = GAME;
                         state = NORMAL;
                         screenIMG = MarioWindow.getImage(assetsPath + "3 - game/terrain.png");
                         //house = "arryn";
                         castleIMG = MarioWindow.getImage(assetsPath + "3 - game/castle-" + house + ".png");
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

               else if (screen == GAME && actionsOut) {
                    actionsOut = false;
                    actionBarIMG = actionBarIn;
                    if (actionSelected == 0) { // Attack house

                    }
                    else if (actionSelected == 1) { // Buy warrior
                         if (money.stat >= 50) {
                              warriors.add(1);
                              money.subtract(50);
                         }
                         else {
                              // show dialog box "Not enough gold"
                         }
                    }
                    else if (actionSelected == 2) { // Buy defender
                         if (money.stat >= 50) {
                              defenders.add(1);
                              money.subtract(50);
                         }
                         else {
                              // show dialog box "Not enough gold"
                         }
                    }
                    else if (actionSelected == 3) { // Buy brothel
                         if (money.stat >= 300) {
                              if (brothels.stat < 4) {
                                   brothels.add(1);
                                   money.subtract(300);
                              }
                              else {
                                   // show dialog box "Too many brothels! You should be ashamed!"
                              }
                         }
                         else {
                              // show dialog box "Not enough gold"
                         }
                    }
                    else { // Repair castle
                         if (money.stat >= 300) {
                              if (health.stat < maxHP) {
                                   health.add(5);
                                   if (health.stat > maxHP) health.set(maxHP);
                                   money.subtract(300);
                              }
                              else {
                                   // show dialog box "Your castle is in good condition."
                              }
                         }
                         else {
                              // show dialog box "Not enough gold"
                         }
                    }
               }

               else if (screen == LOSE) {
                    System.exit(0);
               }
     	}

     	else if (key.equals("Right")) {
     		if (screen == MENU && state == NORMAL) {
     			houseNumSelected++;
                    if (houseNumSelected == 7) houseNumSelected = 0;
     			//houseNumSelected%=7;
     			houseSelected = playerSelectImage.get(houseNumSelected);
     		}
     		else if (state == CHOOSE_HOUSE) {
     			screenNotifNum++;
                    if (screenNotifNum == 2) screenNotifNum = 0;
     			//screenNotifNum%=2;
     			screenNotifIMG = screenNotif.get(screenNotifNum);
     		}
               else if (screen == GAME && actionsOut) {
                    actionSelected++;
                    if (actionSelected == 5) actionSelected = 0;
                    //actionSelected%=5;
               }
     	}

     	else if (key.equals("Left")) {
     		if (screen == MENU && state == NORMAL) {
     			houseNumSelected--;
     			//houseNumSelected%=7;
                    if (houseNumSelected < 0) houseNumSelected = 6;
     			houseSelected = playerSelectImage.get(houseNumSelected);
     		}
     		else if (state == CHOOSE_HOUSE) {
     			screenNotifNum--;
     			//screenNotifNum%=2;
                    if (screenNotifNum < 0) screenNotifNum = 1;
     			screenNotifIMG = screenNotif.get(screenNotifNum);
     		}
               else if (screen == GAME && actionsOut) {
                    actionSelected--;
                    if (actionSelected < 0) actionSelected = 4;
                    //actionSelected%=5;
               }
     	}

          else if (key.equals("S") && screen == GAME) { // ok
               if (statsOut) {
                    statsOut = false;
                    statsBarIMG = statsBarIn;
                    health.setVisible(false);
                    money.setVisible(false);
                    defenders.setVisible(false);
                    warriors.setVisible(false);
                    brothels.setVisible(false);
               }
               else {
                    statsOut = true;
                    statsBarIMG = statsBarOut;
                    health.setVisible(true);
                    money.setVisible(true);
                    defenders.setVisible(true);
                    warriors.setVisible(true);
                    brothels.setVisible(true);
               }
          }

          else if (key.equals("A") && screen == GAME) {
               if (actionsOut) {
                    actionsOut = false;
                    actionBarIMG = actionBarIn;
                    // remove other stuff here
               }
               else {
                    actionsOut = true;
                    actionBarIMG = actionBarOut;
                    // add other stuff here
               }
          }

          else if (key.equals("E") && screen == GAME) {
               if (attacksOut) {
                    attacksOut = false;
                    attackBarIMG = attackBarIn;
                    // remove other stuff here
               }
               else {
                    attacksOut = true;
                    attackBarIMG = attackBarOut;
                    // add other stuff here
               }
          }
          
          else if (key.equals("Space")) {
               // Test begin

               int randNum = (int)(Math.random()*500)%7;
               outAttacks.add(miniSigilIMGs[randNum]);
               randNum = (int)(Math.random()*500)%7;
               inAttacks.add(miniSigilIMGs[randNum]);

               if (brothels.stat < 4) brothels.add(1);
               // Test end
          }

          else if (key.equals("W")) {
               screen = WIN;
          }

          else if (key.equals("L")) {
               screen = LOSE;
               health.setVisible(false);
               money.setVisible(false);
               defenders.setVisible(false);
               warriors.setVisible(false);
               brothels.setVisible(false);
               bgm.setMusic("CS145MP2/assets/music/wav/GoT_game_over.wav");
               screenIMG = MarioWindow.getImage(assetsPath + "misc/game-over.png");
          }

     }

	

	// --------------------------------------- [ MAIN FUNCTION ] --------------------------------------- //

	
	public static void main(String args[]) {
		
		MarioWindow window = new MarioWindow();	

		GameInterface test = new GameInterface();
		window.add(test);
		window.add(test.bgm);
          window.add(test.health);
          window.add(test.money);
          window.add(test.defenders);
          window.add(test.warriors);
          window.add(test.brothels);
		window.startGame();

	}
	

}