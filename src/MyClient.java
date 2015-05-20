package CS145MP2.src;

import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyClient extends JFrame{

	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	/** Connection Variables **/
	Socket s;
	MyConnection conn;
	Thread receiver;
	boolean flag;

	public MyClient(){

		/** Connection Initialization **/
		try{
			this.flag = false;
			this.s = new Socket("127.0.0.1", 8888);
			this.conn = new MyConnection(s);
			this.receiver = new Thread(new ClientReceiver());
			this.receiver.start();
		}catch(Exception e){
			System.out.println("Client: An error occurred.");
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		MyClient c = new MyClient();
		MarioWindow w = new MarioWindow(c);
	}

	private class ClientReceiver implements Runnable{

		public void run(){
			while(!flag){
				String message = conn.getMessage();
				System.out.println(message);

				if(message.charAt(0) == '/'){
					if(message.equals("/full")){
						System.out.println("Server: Server is full.");
						flag = true;
						System.exit(1);
					}
				}
			}
		}
	}

}