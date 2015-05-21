package CS145MP2.src;

import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClient {

	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
																									"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
																									"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
																									"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	/** Connection Variables **/
	Socket s;
	MyConnection conn;
	Thread receiver;
	boolean flag;

	public MyClient(String ip, int port){

		/** Connection Initialization **/
		try{
			this.flag = false;
			this.s = new Socket(ip, port);
			this.conn = new MyConnection(s);
			this.receiver = new Thread(new ClientReceiver());
			this.receiver.start();
		}catch(Exception e){
			System.out.println("Client: An error occurred.");
			e.printStackTrace();
		}
	}
	/*
	public static void main(String args[]){
		try{
			String ip = JOptionPane.showInputDialog("Enter IP Address: ", "127.0.0.1");
			int port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port Number: ", "8888"));
			
			//if(validateIP(ip)){
				//MyClient c = new MyClient(ip, port);
				MyClient c = new MyClient("127.0.0.1", 8888);
				MarioWindow w = new MarioWindow(c);
			//}
			//else{
				//System.out.println("Client: Invalid IP Address");
				//System.exit(1);
			//}
		}catch(NumberFormatException e){
			System.out.println("Client: Invalid Port Number");
			e.printStackTrace();
		}
	}
	*/

	public static boolean validateIP(String ip){
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		return pattern.matcher(ip).matches();
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