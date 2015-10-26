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
	private List<ClientInfo> clientInfos;

	private int port;

	public UDPServer(int port) {
		clientInfos = new ArrayList<ClientInfo>();
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

		while (true) {
			
			//receive something
			byte[] receiveData = new byte[UDPClient.sendPacketSize];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);				
				
				switch(receivePacket.getData()[0]){
				case NetMessageHandler.MESSAGE_CONNECT:
					handleNewClient(receivePacket);
					break;
				case NetMessageHandler.MESSAGE_UNIT_UPDATE:
					handleUnitUpdate(receivePacket,  getClientNumber(receivePacket.getAddress(), receivePacket.getPort()));
					break;
				default:
					sendPackageToAllClients(receivePacket.getData());
				}
			} catch (IOException e) {
				System.out.println("Server receive exception");
				e.printStackTrace();
			}

		}
	}
	
	public void sendPackageToAllClients(byte[] sendData) throws IOException{
		for(int i=0; i < clientInfos.size(); i++){
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientInfos.get(i).getClientIp(), clientInfos.get(i).getClientPort());
			serverSocket.send(sendPacket);
		}
	}
	
	public void sendPackageToAllClientsAndAddAckForSendClient(byte[] sendData, int sendClientNumber, byte ackPackageNumber) throws IOException{
		for(int i=0; i < clientInfos.size(); i++){
			if(i == sendClientNumber) continue;
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientInfos.get(i).getClientIp(), clientInfos.get(i).getClientPort());
			serverSocket.send(sendPacket);
		}
		
		int sendDataEnd = NetMessageHandler.getMessageEnd(sendData);
		if(sendDataEnd != -1){
			//add ack at start
			for(int i = sendDataEnd; i >= 0; i--){
				sendData[i+2] = sendData[i];
			}
			sendData[0] = NetMessageHandler.MESSAGE_ACK;
			sendData[1] = ackPackageNumber;
			
			//remember last package of client
			clientInfos.get(sendClientNumber).setLastPackageData(sendData);

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientInfos.get(sendClientNumber).getClientIp(), clientInfos.get(sendClientNumber).getClientPort());
			serverSocket.send(sendPacket);
		}
	}
	
	public void sendPackageToClient(byte[] sendData, int clientNumber) throws IOException{
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientInfos.get(clientNumber).getClientIp(), clientInfos.get(clientNumber).getClientPort());
		serverSocket.send(sendPacket);
	}
	
	private void handleUnitUpdate(DatagramPacket receivePacket, int clientNumber){
		//check if old or duplicate
		byte recPackageNumber = receivePacket.getData()[1];
		if(recPackageNumber > clientInfos.get(clientNumber).getHighestReceivedPackageNumber()
				|| (recPackageNumber < -100 && clientInfos.get(clientNumber).getHighestReceivedPackageNumber() > 100)){
			clientInfos.get(clientNumber).setHighestReceivedPackage(recPackageNumber, receivePacket.getData());
		}else{
			if(recPackageNumber == clientInfos.get(clientNumber).getHighestReceivedPackageNumber()){
				//resent last package only to client //TODO maybe update unit position in information 
				System.out.println("resent last Package: " + recPackageNumber + " Time:" + System.currentTimeMillis());
				try {
					sendPackageToClient(clientInfos.get(clientNumber).getLastPackageData(), clientNumber);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}else{
				System.out.println("old Package thrown away: Nr: " + recPackageNumber + " Time:" + System.currentTimeMillis());
				return;
			}
		}
		
		//send
		try {
			sendPackageToAllClientsAndAddAckForSendClient(receivePacket.getData(), clientNumber, recPackageNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleNewClient(DatagramPacket receivePacket){
		//add new data
		int clientId = -1;
		if (getClientNumber(receivePacket.getAddress(), receivePacket.getPort()) == -1){
			clientInfos.add(new ClientInfo(receivePacket.getAddress(), receivePacket.getPort()));
		
			receivePacket.getData()[1] = (byte)(clientInfos.size()-1);
			clientId = clientInfos.size()-1;
			System.out.println("new client connected: Nr:" + clientInfos.size());
		}else{
			clientId = getClientNumber(receivePacket.getAddress(), receivePacket.getPort());
			clientInfos.get(clientId).setHighestReceivedPackageNumber((byte)-1);
			receivePacket.getData()[1] = (byte)clientId;
			System.out.println("client connected twice ? Nr:" + clientId);
		}
		
		try {
			sendPackageToClient(receivePacket.getData(), clientId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param newIpAddress
	 * @param port
	 * @return -1 = new Client, number > -1 -> position in lists and clientNumber
	 */
	private int getClientNumber(InetAddress ipAddress, int port) {
		for(int i = 0; i < clientInfos.size(); i++){
			if (clientInfos.get(i).getClientIp().equals(ipAddress) && clientInfos.get(i).getClientPort() == port){
				return i;
			}
		}

		return -1;
	}
}
