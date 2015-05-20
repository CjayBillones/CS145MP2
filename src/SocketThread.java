package CS145MP2.src;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketThread extends Thread{

	MyServer server;
	ServerSocket ssocket;

	Socket s;
	MyConnection conn;
	Thread receiver;
	boolean flag;

	public SocketThread(MyServer server, ServerSocket ssocket){
		try{
			this.flag = false;
			this.server = server;
			this.ssocket = ssocket;
			this.s = ssocket.accept();
			this.conn = new MyConnection(this.s);
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

				if(message.length() == 0) continue;

				if(message.charAt(0) == '/'){
					if(message.equals("/quit")){
						System.out.println("Server: Client" + server.clients.indexOf(this.sg) + " left.");
						this.sg.flag = true;
						server.clients.remove(this.sg);
					}
				}
			}
		}

	}

}