package CS145MP2.src;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class MyServer{

	LinkedList<SocketThread> clients;
	int player_houses;
	ServerSocket ssocket;

	public MyServer(){
		
		this.clients = new LinkedList<SocketThread>();

		try{
			System.out.println("Server: Starting server. . .");
			this.ssocket = new ServerSocket(8888);
			System.out.println("Server: Waiting for connections. . .");

			while(true){
				SocketThread sg = new SocketThread(this, ssocket);
				if(this.clients.size() < 4){
					sg.conn.sendMessage("Server: You have been connected.");
					sg.name = "Player" + (this.clients.size()+1);
					broadcastMessage("client", "Server: " + sg.name + " has connected.", sg, false);
					this.clients.add(sg);
					sg.start();
				}
				else{
					sg.conn.sendMessage("/full");
					sg.flag = true;
					sg = null;
				}
			}

		}catch(Exception e){
			System.out.println("Server: An error occurred.");
			e.printStackTrace();
		}		
	}
	
	public void broadcastMessage(String src, String message, SocketThread current, boolean self){
		for(int ac = 0; ac < clients.size(); ac++){
			if(src.equals("client") && clients.get(ac) == current && !self)
				continue;
			clients.get(ac).conn.sendMessage(message);
		}
	}

	public static void main(String args[]){
		new MyServer();
	}
}