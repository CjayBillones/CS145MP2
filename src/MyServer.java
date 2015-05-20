package CS145MP2.src;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class MyServer{

	LinkedList<SocketThread> clients;
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
	
	public static void main(String args[]){
		new MyServer();
	}
}