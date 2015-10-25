package game.net;

import game.Game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPClient extends Thread{
	private int clientNumber;
	private int port;
    private DatagramSocket clientSocket;
    private InetAddress serverIpAddress;
    public static final int sendPacketSize = 128;
    public static final int receivePacketSize = 256;
    
    public UDPClient(String serverIp, int port, int maxConnectTrys) throws ConnectionFailedException{
    	this.port = port;
    	
    	clientNumber = -1; // -1 = connection failed
    	try {
			clientSocket = new DatagramSocket();
			serverIpAddress = InetAddress.getByName(serverIp);
			
			//connection handshake
	        byte[] receiveData = new byte[receivePacketSize];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			for(int i=0; i < maxConnectTrys; i++){
				try {
					//say hello, wait, get response clientNumber for connection established
					sendMessage(new byte[]{NetMessageHandler.MESSAGE_CONNECT});
					clientSocket.setSoTimeout(1000);
					clientSocket.receive(receivePacket);
					clientNumber = (int)receivePacket.getData()[1];
					clientSocket.setSoTimeout(0);
					break;
				} catch (SocketTimeoutException e){
					System.out.println("Client connect Try " + (i+1) +" failed");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	
    	if(clientNumber == -1) throw new ConnectionFailedException("Connection to IP: " + serverIp + ":" + port + " failed");
    }
    
    public void run(){
        byte[] receiveData = new byte[receivePacketSize];
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
		if(data.length > sendPacketSize) System.out.println("Warning UDPCLient: to much data to send"); 
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
