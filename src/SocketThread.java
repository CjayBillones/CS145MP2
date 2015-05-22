package CS145MP2.src;

import java.io.*;
import java.net.*;
import java.util.*;

/** Server Protocols (To Be Sent To Client) **/
/**
* 
*	/name <player_name>
* /house <house_name>
*	/castle_hp <remaining_hp>
* /gold <amount_of_gold>
*	/offense <number_of_offense_soldiers>
* /defense <number_of_defense_soldiers>
* /brothel <number_of_brothels>
*
**/

public class SocketThread extends Thread{

	MyServer server;
	ServerSocket ssocket;

	Socket s;
	MyConnection conn;
	Thread receiver;
	boolean flag;
	String name;

	Player p;

	public SocketThread(MyServer server, ServerSocket ssocket){
		try{
			this.flag = false;
			this.server = server;
			this.ssocket = ssocket;
			this.s = ssocket.accept();
			this.conn = new MyConnection(this.s);
			this.p = new Player(this);
			this.receiver = new Thread(new ServerReceiver(this));
			this.receiver.start();
		}catch(Exception e){
			System.out.println("Server: An error occurred.");
			e.printStackTrace();
		}
	}

	public void run(){
		try{
			while(!this.flag){
				// Do Nothing. Just let the thread die.
			}
			System.out.println("Killed");
		}catch(Exception e){
			System.out.println("Server: An error occurred.");
			e.printStackTrace();
		}
	}

	private class ServerReceiver implements Runnable{

		SocketThread sg;

		public ServerReceiver(SocketThread sg){
			this.sg = sg;
		}

		public void run(){
			while(!sg.flag){
				String message = conn.getMessage();
				String split[] = message.split(" ");
				System.out.println(message);

				if(message.length() == 0) continue;

				if(message.charAt(0) == '/'){
					if(message.equals("/quit")){
						System.out.println("Server: " + sg.name + " left.");
						server.broadcastMessage("client", "Server: " + sg.name + " has left.", sg, false);
						sg.p.killBrothelThreads();
						this.sg.flag = true;
						server.clients.remove(this.sg);
					}
					else if(message.equals("/start_game")){
						System.out.println("Start Game");
						sg.p.initializeBrothels();
						sg.p.initializeWhiteWalkerGenerator();
					}
					else if(split[0].equals("/house")){
						System.out.println("House");
						this.sg.p.assignHouse(split[1]);
					}
				}
			}
		}

	}

}