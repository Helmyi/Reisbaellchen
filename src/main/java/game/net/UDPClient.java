package game.net;

import game.Game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends Client{
	int port;
    DatagramSocket clientSocket;
    InetAddress serverIpAddress;
    
    public UDPClient(String serverIp, int port){
    	this.port = port;
    	try {
			clientSocket = new DatagramSocket();
			serverIpAddress = InetAddress.getByName(serverIp);
			sendMessage("Hello");
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    public void run(){
        byte[] receiveData = new byte[256];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		String message;
		
		while(true){
			try {
				clientSocket.receive(receivePacket);
				message = new String(receivePacket.getData());
				Game.getGameInstance().getNetMessageHandler().processMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    @Override
	public void sendMessage(String message) {
		byte[] data = new byte[256];
		data = message.getBytes();
		System.out.println("UDPClient Message: " + message);
		DatagramPacket packet = new DatagramPacket(data, data.length, serverIpAddress, port);
		try {
			clientSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
