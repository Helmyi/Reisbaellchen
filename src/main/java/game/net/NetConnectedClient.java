package game.net;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NetConnectedClient extends Thread{
	private Socket client;
	private Scanner in;
	private PrintWriter out;
	private NetServer server;
	
	public NetConnectedClient(Socket client, Scanner in, PrintWriter out, NetServer server){
		this.server = server;
		this.client = client;
		this.in = in;
		this.out = out;
	}
	
	public void run(){
		while(true){
			String message = in.nextLine();
			System.out.println("message:" + message);
			server.addReceivedMessage(message);
		}
	}
	
	public void sendMessageToClient(String message){
		out.println(message);
	}
	
	public Socket getClientSocket(){
		return client;
	}

}
