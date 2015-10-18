package game.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class NetServer extends Thread{
	private ServerSocket serverSocket;
	private int port;
	private List<NetConnectedClient> clients;
	private boolean waitingForNewClients;
	private Queue<String> receivedMessages;
	
	public NetServer(int port) {
		receivedMessages = new LinkedList<String>();
		this.port = port;
		clients = new ArrayList<NetConnectedClient>();
		waitingForNewClients = true;
		
		try {
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		System.out.println("server started, waiting for Client");

		Socket client = null;
		NetConnectedClient connectedClient = null;
		try {
			while(waitingForNewClients){
				client = serverSocket.accept();
				Scanner in = new Scanner(client.getInputStream());
				PrintWriter out = new PrintWriter(client.getOutputStream(),	true);
				
				connectedClient = new NetConnectedClient(client, in, out, this);
				clients.add(connectedClient);
				connectedClient.start();

			    System.out.println("Client Connected: " + client.getInetAddress());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		NetServer server = new NetServer(27015);
		server.start();
	}
	
	
	public synchronized void addReceivedMessage(String message){
		System.out.println("MessageReceived: " + message);
		receivedMessages.add(message);
		//TODO
		sendMessageToClients(receivedMessages.poll());
	}
	
	private void sendMessageToClients(String message){
		for(NetConnectedClient client: clients){
			client.sendMessageToClient(message);
		}
		
	}
	
	public void setWaitingForNewClients(boolean value){
		this.waitingForNewClients = value;
	}

}
