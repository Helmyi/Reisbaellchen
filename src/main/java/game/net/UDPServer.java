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

			String message = new String(receivePacket.getData());
			System.out.println("Server received: \"" + message + "\"");

			InetAddress ipAddress = receivePacket.getAddress();
			if (isNewIP(ipAddress)){
				clientIps.add(ipAddress);
				clientPorts.add(receivePacket.getPort());
				
				if(message.startsWith("Hello")){
					message = "Hello:" + (clientIps.size()-1) + ":End";
					System.out.println("new client connected: Nr:" + clientIps.size());
				}
			}

			//just send immediately back
			sendData = message.getBytes();
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

	private boolean isNewIP(InetAddress newIpAddress) {
		for (InetAddress ipAddress : clientIps) {
			if (ipAddress.equals(newIpAddress))
				return false;
		}
		return true;
	}
}
