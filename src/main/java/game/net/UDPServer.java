package game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UDPServer extends Thread {

	private DatagramSocket serverSocket;
	private List<InetAddress> clientIps;
	private List<Integer> clientPorts;
	private int port;

	public UDPServer(int port) {
		clientIps = new ArrayList<InetAddress>();
		clientPorts = new ArrayList<Integer>();
		this.port = port;
		
		try {
			serverSocket = new DatagramSocket(this.port);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		UDPServer server = new UDPServer(27015);
		server.start();
	}
	
	public void run() {
		System.out.println("UDP server started, waiting for Client");
		byte[] receiveData = new byte[256];
		byte[] sendData = new byte[256];

		while (true) {
			
			//receive something
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				System.out.println("Server receive exception");
				e.printStackTrace();
			}

			InetAddress ipAddress = receivePacket.getAddress();
			
			if(receivePacket.getData()[0] == NetMessageHandler.MESSAGE_CONNECT){
				//add new data
				if (getClientNumber(ipAddress, receivePacket.getPort()) == -1){
					clientIps.add(ipAddress);
					clientPorts.add(receivePacket.getPort());
				
					receivePacket.getData()[1] = (byte)(clientIps.size()-1);
					System.out.println("new client connected: Nr:" + clientIps.size());
				}else{
					receivePacket.getData()[1] = (byte)getClientNumber(ipAddress, receivePacket.getPort());
					System.out.println("client connected twice ? Nr:" + getClientNumber(ipAddress, receivePacket.getPort()));
				}
			}

			//just send immediately back
			sendData = receivePacket.getData();
			try {
				sendPackageToAllClients(sendData);
			} catch (IOException e) {
				System.out.println("Server send exception");
				e.printStackTrace();
			}
		}
	}
	
	public void sendPackageToAllClients(byte[] sendData) throws IOException{
		for(int i=0; i < clientIps.size(); i++){
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIps.get(i), clientPorts.get(i));
			serverSocket.send(sendPacket);
		}
	}

	/**
	 * 
	 * @param newIpAddress
	 * @param port
	 * @return -1 = new Client, number > -1 -> position in lists and clientNumber
	 */
	private int getClientNumber(InetAddress ipAddress, int port) {
		for(int i = 0; i < clientIps.size(); i++){
			if (clientIps.get(i).equals(ipAddress) && clientPorts.get(i) == port){
				return i;
			}
		}

		return -1;
	}
}
