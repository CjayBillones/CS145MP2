package CS145MP2.src;

import java.util.*;

public class Player extends GameObject{

	SocketThread sg;
	WhiteWalkerGenerator wwgt;

	String name;
	String house;
	int castle_hp;
	int offense_soldier;
	int defense_soldier;
	int gold;
	int brothel;

	LinkedList<BrothelThread> brothels;
	LinkedList<Attack> incoming;
	LinkedList<Attack> outgoing;

	public Player(SocketThread sg){
		this.sg = sg;
		this.castle_hp = 30;
		this.offense_soldier = 10;
		this.defense_soldier = 10;
		this.gold = 1000;
		this.brothel = 1;
		brothels = new LinkedList<BrothelThread>();
		incoming = new LinkedList<Attack>();
		outgoing = new LinkedList<Attack>();
		initializeBrothels();
		initializeWhiteWalkerGenerator();
	}

	public void assignHouse(String house){
		this.house = house;
		if(this.house.equals("Baratheon")) this.defense_soldier += 20;
		else if(this.house.equals("Arryn")) this.castle_hp = 50;
		else if(this.house.equals("Martell")) this.offense_soldier += 20;
		else if(this.house.equals("Lannister")) this.gold += 500;
	}

	public void initializeWhiteWalkerGenerator(){
		wwgt = new WhiteWalkerGenerator(this.sg);
		wwgt.start();
	}

	public void initializeBrothels(){
		for(int ac = brothels.size(); ac < this.brothel; ac++){
			BrothelThread bt = new BrothelThread(this);
			brothels.add(bt);
			bt.start();
		}
	}

	public void killBrothelThreads(){
		for(int ac = 0; ac < brothels.size(); ac++){
			this.brothels.get(ac).stop = true;
		}	
	}

	// Generation of Random White Walker Attacks
	private class WhiteWalkerGenerator extends Thread{
		
		SocketThread sg;

		public WhiteWalkerGenerator(SocketThread sg){
			this.sg = sg;
		}

		public void run(){
			while(!this.sg.flag){
				try{
					int next_wave = Utilities.randInt(3000, 6000);
					int num_offense = Utilities.randInt(8, 15);
					Attack attack = new Attack(null, this.sg, num_offense);
					sg.p.incoming.add(attack);
					attack.start();
					Thread.sleep(next_wave);
				}catch(Exception e){
					System.out.println("System: An error occurred.");
					e.printStackTrace();
				}
			}
		}
	}	

	// Generation of Gold
	private class BrothelThread extends Thread{

		Player p;
		boolean stop;

		public BrothelThread(Player p){
			this.p = p;
			this.stop = false;
		}

		public void run(){
			while(!stop){
				try{
					int delay = Utilities.randInt(30000, 60000);
					p.gold += 70;
					System.out.println(delay + " - " + p.gold);
					Thread.sleep(delay);
				}catch(Exception e){
					System.out.println("System: An error occured.");
					e.printStackTrace();
				}
			}
		}

	}
}