package CS145MP2.src;

public class Player extends GameObject{

	String name;
	String house;
	int castle_hp;
	int offense_soldier;
	int defense_soldier;
	int gold;
	int brothel;

	public Player(){
		this.castle_hp = 30;
		this.offense_soldier = 10;
		this.defense_soldier = 10;
		this.gold = 1000;
		this.brothel = 1;
	}

	public void assignHouse(String house){
		this.house = house;
		if(this.house.equals("Baratheon")) this.defense_soldier += 20;
		else if(this.house.equals("Arryn")) this.castle_hp = 50;
		else if(this.house.equals("Martell")) this.offense_soldier += 20;
	}

}