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
			Thread.sleep(this.travel_time);
			System.out.println("Resolving Battle. Number of attacking enemies is " + this.num_offense);
			// Battle Resolution
		}catch(Exception e){
			System.out.println("System: An error occurred.");
			e.printStackTrace();
		}
	}


}