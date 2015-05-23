package CS145MP2.src;

import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClient {

   private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                                                           "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                                                           "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                                                           "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
   
   public static int WIDTH = 1024;
   public static int HEIGHT = 600;

   /** Connection Variables **/
   Socket s;
   MyConnection conn;
   Thread receiver;
   boolean flag;
   boolean ready;

   GameInterface test;

   public MyClient(String ip, int port){

      /** Connection Initialization **/
      try{
         this.flag = false;
         this.ready = false;
         this.s = new Socket(ip, port);
         this.conn = new MyConnection(s);
         this.receiver = new Thread(new ClientReceiver(this));
         //this.receiver.start();
      }catch(Exception e){
         System.out.println("Client: An error occurred.");
         e.printStackTrace();
      }
   }
   
   public static void main(String args[]){
      try{
         //String ip = JOptionPane.showInputDialog("Enter IP Address: ", "127.0.0.1");
         //int port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port Number: ", "8888"));
         
         //if(validateIP(ip)){
            //MyClient c = new MyClient(ip, port);
            MyClient c = new MyClient("127.0.0.1", 8888);
            MarioWindow window = new MarioWindow(c);
            GameInterface test = new GameInterface(c);
            c.test = test;
            c.receiver.start();
            window.add(test);
            window.add(test.bgm);
            window.add(test.health);
            window.add(test.money);
            window.add(test.defenders);
            window.add(test.warriors);
            window.add(test.brothels);
            window.startGame();
            
         //}
         //else{
            //System.out.println("Client: Invalid IP Address");
            //System.exit(1);
         //}
      }catch(NumberFormatException e){
         System.out.println("Client: Invalid Port Number");
         e.printStackTrace();
      }
   }
   

   public static boolean validateIP(String ip){
      Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
      return pattern.matcher(ip).matches();
   }

   private class ClientReceiver implements Runnable{

      MyClient c;

      public ClientReceiver(MyClient c){
         this.c = c;
      }

      public void run(){
         while(!flag){
            try{
               String message = conn.getMessage();
               String split[] = message.split(" ");
               System.out.println(message);

               if(message.charAt(0) == '/'){
                  if(message.equals("/full")){
                     System.out.println("Server: Server is full.");
                     flag = true;
                     System.exit(1);
                  }
                  else if(split[0].equals("/players_ready")){
                     if(split[1].equals("true")) {
                        c.ready = true;
                     }
                     else
                        c.ready = false;
                  }
                  else if(split[0].equals("/num_players")){
                     c.test.numOfPlayers = Integer.parseInt(split[1]);
                     c.test.playerHouses = new String[c.test.numOfPlayers];
                     c.conn.sendMessage("/get_players_houses");
                  }
                  else if(split[0].equals("/player_houses")){
                     for(int ac = 1; ac < split.length; ac++){
                        c.test.playerHouses[ac-1] = split[ac];
                     }
                  }
                  else if(split[0].equals("/health")){
                     System.out.println("Health");
                     c.test.health.set(Integer.parseInt(split[1]));
                     if(c.test.health.stat <= 0){
                        c.test.screen = c.test.LOSE;
                        c.test.health.setVisible(false);
                        c.test.money.setVisible(false);
                        c.test.defenders.setVisible(false);
                        c.test.warriors.setVisible(false);
                        c.test.brothels.setVisible(false);
                        c.test.bgm.setMusic("CS145MP2/assets/music/wav/GoT_game_over.wav");
                        c.test.screenIMG = MarioWindow.getImage(c.test.assetsPath + "misc/game-over.png");                        
                     }
                  }
                  else if(split[0].equals("/gold")){
                     System.out.println("Gold");
                     c.test.money.set(Integer.parseInt(split[1]));
                  }
                  else if(split[0].equals("/defender")){
                     System.out.println("Defender");
                     c.test.defenders.set(Integer.parseInt(split[1]));
                  }
                  else if(split[0].equals("/warrior")){
                     System.out.println("Warrior");
                     c.test.warriors.set(Integer.parseInt(split[1]));
                  }
                  else if(split[0].equals("/brothel")){
                     System.out.println("Brothel");
                     c.test.brothels.set(Integer.parseInt(split[1]));
                  }
                  else if (split[0].equals("/player_num")) {
                     c.test.playerId = Integer.parseInt(split[1]);
                  }
               }              
            }catch(Exception e){
               e.printStackTrace();
            }

         }
      }
   }

}