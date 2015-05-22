package CS145MP2.src;

public class Attack extends Thread{

	SocketThread src_player;
	SocketThread dest_player;
	int num_offense;
	int travel_time;

	public Attack(SocketThread src_player, SocketThread dest_player, int num_offense){
		this.src_player = src_player;
		this.dest_player = dest_player;
		this.num_offense = num_offense;
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
				if(((src_player != null && !src_player.flag) || !dest_player.flag)){
					System.out.println("Resolving Battle. Number of attacking enemies is " + this.num_offense);
					// Battle Resolution Here			
					break;
				}
				// Trigger update client UI
			}
		}catch(Exception e){
			System.out.println("System: An error occurred.");
			e.printStackTrace();
		}
	}


}