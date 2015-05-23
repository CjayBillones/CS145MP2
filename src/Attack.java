package CS145MP2.src;

public class Attack extends Thread{

	SocketThread src_player;
	SocketThread dest_player;
	int num_offense;
	int travel_time;

	public Attack(SocketThread src_player, SocketThread dest_player){
		this.src_player = src_player;
		this.dest_player = dest_player;
		this.num_offense = Utilities.randInt(8,15);
		this.travel_time = Utilities.randInt(5000, 10000);
	}

	public void run(){
		try{
			System.out.println(travel_time);
			while((src_player != null && !src_player.flag) || !dest_player.flag){
				for(int ac = 0; ac < (travel_time/1000); ac++){
					if(((src_player != null && !src_player.flag) || !dest_player.flag)) break;
					Thread.sleep(1000);
				}
				if(((src_player != null && !src_player.flag) && !dest_player.flag)){
					System.out.println("Resolving Battle. Number of attacking enemies is " + this.num_offense);
					// Battle Resolution Here
						if(src_player.p.offense_soldier > dest_player.p.defense_soldier){
							src_player.p.offense_soldier -= dest_player.p.defense_soldier;
							dest_player.p.defense_soldier = 0;
							dest_player.p.castle_hp += (dest_player.p.defense_soldier - src_player.p.offense_soldier);
							src_player.conn.sendMessage("/warrior " + src_player.p.offense_soldier);
							Thread.sleep(500);
							dest_player.conn.sendMessage("/defender " + dest_player.p.defense_soldier);
							Thread.sleep(500);
							dest_player.conn.sendMessage("/health " + dest_player.p.castle_hp);
							Thread.sleep(500);
						}
						else{
							src_player.p.offense_soldier = 0;
							//dest_player.p.defense_soldier -= src_player.p.offense_soldier;
							src_player.conn.sendMessage("/warrior " + src_player.p.offense_soldier);
							Thread.sleep(500);
							dest_player.conn.sendMessage("/defender " + dest_player.p.defense_soldier);
							Thread.sleep(500);
						}							
					break;
				}
				else if(src_player == null && !dest_player.flag){
						if(this.num_offense > dest_player.p.defense_soldier){
							dest_player.p.defense_soldier = 0;
							//dest_player.p.castle_hp += (dest_player.p.defense_soldier - this.num_offense);
							dest_player.conn.sendMessage("/defender " + dest_player.p.defense_soldier);
							Thread.sleep(500);
							dest_player.conn.sendMessage("/health " + dest_player.p.castle_hp);
							Thread.sleep(500);
						}
						else{
							dest_player.p.defense_soldier -= this.num_offense;
							dest_player.conn.sendMessage("/defender " + dest_player.p.defense_soldier);
							Thread.sleep(500);
						}							
				}
				// Trigger update client UI
			}
		}catch(Exception e){
			System.out.println("System: An error occurred.");
			e.printStackTrace();
		}
	}


}