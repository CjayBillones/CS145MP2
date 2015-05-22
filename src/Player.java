package CS145MP2.src;

import java.util.*;

public class Player extends GameObject{

	String name;
	String house;
	int castle_hp;
	int offense_soldier;
	int defense_soldier;
	int gold;
	int brothel;

	LinkedList<BrothelThread> brothels;

	public Player(){
		this.castle_hp = 30;
		this.offense_soldier = 10;
		this.defense_soldier = 10;
		this.gold = 1000;
		this.brothel = 1;
		brothels = new LinkedList<BrothelThread>();
		initializeBrothels();
	}

	public void assignHouse(String house){
		this.house = house;
		if(this.house.equals("Baratheon")) this.defense_soldier += 20;
		else if(this.house.equals("Arryn")) this.castle_hp = 50;
		else if(this.house.equals("Martell")) this.offense_soldier += 20;
		else if(this.house.equals("Lannister")) this.gold += 500;
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

	// For generation of gold
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