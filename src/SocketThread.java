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
	int player_num;

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
				if(!message.equals("/players_ready"))
					System.out.println(message);				
				String split[] = message.split(" ");

				if(message.length() == 0) continue;

				if(message.charAt(0) == '/'){
					if(message.equals("/quit")){
						System.out.println("Server: " + sg.name + " left.");
						server.broadcastMessage("client", "Server: " + sg.name + " has left.", sg, false);
						sg.p.killBrothelThreads();
						this.sg.flag = true;
						server.clients.remove(this.sg);
					}
					else if(message.equals("/players_ready")){
						if(sg.server.clients.size() > 1 && sg.server.clients.size() == sg.server.player_houses)
							this.sg.conn.sendMessage("/players_ready true");
						else
							this.sg.conn.sendMessage("/players_ready false");
					}
					else if(message.equals("/start_game")){
						System.out.println("Start Game");
						sg.p.initializeBrothels();
						sg.p.initializeWhiteWalkerGenerator();
					}
					else if(split[0].equals("/house")){
						System.out.println("House");
						this.sg.p.assignHouse(split[1]);
						this.sg.server.player_houses++;
					}
					else if(message.equals("/get_num_players")){
						this.sg.conn.sendMessage("/num_players " + this.sg.server.clients.size());
					}
					else if(message.equals("/get_players_houses")){
						String mess = "/player_houses ";
						for(int ac = 0; ac < this.sg.server.clients.size(); ac++){
							mess = mess + this.sg.server.clients.get(ac).p.house + " ";
						}
						this.sg.conn.sendMessage(mess);
					}
					else if(message.equals("/get_stats")){
						this.sg.conn.sendMessage("/health " + this.sg.p.castle_hp);
						this.sg.conn.sendMessage("/gold " + this.sg.p.gold);
						this.sg.conn.sendMessage("/warrior " + this.sg.p.offense_soldier);
						this.sg.conn.sendMessage("/defender " + this.sg.p.defense_soldier);
						this.sg.conn.sendMessage("/brothel " + this.sg.p.brothel);
					}
					else if(message.equals("/buy_warrior")){
						this.sg.p.offense_soldier += 1;
						this.sg.p.gold -= 50;
						this.sg.conn.sendMessage("/warrior " + this.sg.p.offense_soldier);
						this.sg.conn.sendMessage("/gold " + this.sg.p.gold);
					}
					else if(message.equals("/buy_defender")){
						this.sg.p.defense_soldier += 1;
						this.sg.p.gold -= 50;
						this.sg.conn.sendMessage("/defender " + this.sg.p.defense_soldier);
						this.sg.conn.sendMessage("/gold " + this.sg.p.gold);
					}
					else if(message.equals("/buy_brothel")){
						this.sg.p.brothel += 1;
						this.sg.p.gold -= 300;
						this.sg.conn.sendMessage("/brothel " + this.sg.p.brothel);
						this.sg.conn.sendMessage("/gold " + this.sg.p.gold);
						this.sg.p.initializeBrothels();
					}
					else if(message.equals("/repair_castle")){
						this.sg.p.castle_hp += 5;
						if(this.sg.p.castle_hp > 30) this.sg.p.castle_hp = 30;
						this.sg.p.gold -= 300;
						this.sg.conn.sendMessage("/health " + this.sg.p.castle_hp);
						this.sg.conn.sendMessage("/gold " + this.sg.p.gold);
					}
					else if(message.equals("/get_num_of_players")){
						this.sg.conn.sendMessage("/num_of_players " + this.sg.server.clients.size());
					} 
					else if(message.equals("/get_player_num")){
						this.sg.conn.sendMessage("/player_num " + this.sg.player_num);
					}
					else if(split[0].equals("/attack")){
						SocketThread enemy = this.sg.server.getPlayer(split[2]);
						Attack attack = new Attack(this.sg, enemy);
						attack.start();
						/**if(this.sg.p.offense_soldier > enemy.p.defense_soldier){
							this.sg.p.offense_soldier -= enemy.p.defense_soldier;
							enemy.p.defense_soldier = 0;
							enemy.p.castle_hp += (enemy.p.defense_soldier - this.sg.p.offense_soldier);
							this.sg.conn.sendMessage("/warrior " + this.sg.p.offense_soldier);
							enemy.conn.sendMessage("/defender " + enemy.p.defense_soldier);
							enemy.conn.sendMessage("/health " + enemy.p.castle_hp);
						}
						else{
							this.sg.p.offense_soldier = 0;
							enemy.p.defense_soldier -= this.sg.p.offense_soldier;
							this.sg.conn.sendMessage("/warrior " + this.sg.p.offense_soldier);
							enemy.conn.sendMessage("/defender " + enemy.p.defense_soldier);
						}**/
					}
					/*else if (message.startsWith("/get_house_at_") {
						int num = Integer.parseInt(message.charAt(15));
						MyClient player = this.sg.server.clients.get(i).p;
					}*/
				}
			}
		}

	}

}