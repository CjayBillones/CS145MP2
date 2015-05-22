package CS145MP2.src;

import java.io.*;
import java.net.*;

public class MyConnection{

	Socket s;
	PrintWriter out;
	BufferedReader in;
	BufferedOutputStream bos;
	BufferedInputStream bis;

	public MyConnection(Socket s){
		try{
			this.s = s;
			this.out = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()), true);
			this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
			this.bos = new BufferedOutputStream(this.s.getOutputStream());
			this.bis = new BufferedInputStream(this.s.getInputStream());
		}catch(Exception e){
			System.out.println("System: An error occurred.");
			e.printStackTrace();
		}
	}

	public boolean sendBytes(byte msg[], int size){
		try {
			bos.write(msg, 0, size);
			bos.flush();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	public byte[] getBytes(){
		try{
			byte message[] = new byte[s.getReceiveBufferSize()];
			bis.read(message, 0, message.length);
			return message;
		}catch(Exception e){
			System.out.println("System: An error occurred.");
			e.printStackTrace();
			return null;			
		}
	}

	public boolean sendMessage(String msg){
		try{
			this.out.println(msg);
			return true;
		}catch(Exception e){
			System.out.println("System: An error occurred.");
			e.printStackTrace();
			return false;
		}
	}

	public String getMessage(){
		try{
			return in.readLine();
		}catch(Exception e){
			e.printStackTrace();
			return "System: An error occurred.";
		}
	}

}