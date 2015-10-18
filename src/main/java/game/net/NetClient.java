package game.net;

import game.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class NetClient extends Thread{
	private int port;
	private String serverIp;
	private Socket serverSocket;
	private Scanner in;
	private PrintWriter out;
	private Queue<String> receivedMessages;
	
	public NetClient(String serverIp, int port){
		receivedMessages = new LinkedList<String>();
		this.port = port;
		this.serverIp = serverIp;
		
		try {
			serverSocket = new Socket(serverIp, port);
			in = new Scanner(serverSocket.getInputStream());
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			System.out.println("Connected to: " + serverIp + ":" + port);	
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			String message = in.nextLine();
			System.out.println("received Message: " + message);
			receivedMessages.add(message);
			//
			Game.getGameInstance().getNetMessageHandler().processMessage(receivedMessages.poll());
		}
	}
	
	public String pollMessage(){
		return receivedMessages.poll();
	}
	
	public void sendMessage(String message){
		out.println(message);
	}
	
	public int getPort(){
		return port;
	}

	public String getIp(){
		return serverIp;
	}
}
