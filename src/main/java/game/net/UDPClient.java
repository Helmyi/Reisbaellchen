package game.net;

import game.Game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends Thread{
	private int clientNumber;
	private int port;
    private DatagramSocket clientSocket;
    private InetAddress serverIpAddress;
    
    public UDPClient(String serverIp, int port){
    	this.port = port;
    	try {
			clientSocket = new DatagramSocket();
			serverIpAddress = InetAddress.getByName(serverIp);
			sendMessage(new byte[]{NetMessageHandler.MESSAGE_CONNECT});
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    public void run(){
        byte[] receiveData = new byte[256];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		while(true){
			try {
				clientSocket.receive(receivePacket);
				Game.getGameInstance().getNetMessageHandler().processByteMessage(receivePacket.getData());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    

	public void sendMessage(byte[] data) {
		if(data.length > 256) System.out.println("Warning UDPCLient: to much data to send"); 
		DatagramPacket packet = new DatagramPacket(data, data.length, serverIpAddress, port);
		try {
			clientSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
}
