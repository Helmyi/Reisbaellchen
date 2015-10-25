package game.net;

import java.net.InetAddress;

public class ClientInfo {
	private InetAddress clientIp;
	private int clientPort;
	private byte highestReceivedPackageNumber;
	
	public ClientInfo(InetAddress clientIp, int clientPort, byte highestReceivedPackageNumber){
		setClientIp(clientIp);
		setClientPort(clientPort);
		setHighestReceivedPackageNumber(highestReceivedPackageNumber);
	}
	
	public InetAddress getClientIp() {
		return clientIp;
	}
	
	public void setClientIp(InetAddress clientIp) {
		this.clientIp = clientIp;
	}
	
	public int getClientPort() {
		return clientPort;
	}
	
	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}
	
	public byte getHighestReceivedPackageNumber() {
		return highestReceivedPackageNumber;
	}
	
	public void setHighestReceivedPackageNumber(byte highestReceivedPackageNumber) {
		this.highestReceivedPackageNumber = highestReceivedPackageNumber;
	}
}
