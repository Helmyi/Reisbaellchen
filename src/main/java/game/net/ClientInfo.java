package game.net;

import java.net.InetAddress;

public class ClientInfo {
	private InetAddress clientIp;
	private int clientPort;
	private byte highestReceivedPackageNumber;
	private byte[] lastPackageData;
	
	public ClientInfo(InetAddress clientIp, int clientPort){
		setClientIp(clientIp);
		setClientPort(clientPort);
		setHighestReceivedPackageNumber((byte)-1);
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

	public void setHighestReceivedPackage(byte highestReceivedPackageNumber, byte[] data) {
		this.highestReceivedPackageNumber = highestReceivedPackageNumber;
		setLastPackageData(data);
	}

	public byte[] getLastPackageData() {
		return lastPackageData;
	}

	public void setLastPackageData(byte[] lastPackageData) {
		this.lastPackageData = lastPackageData;
	}
}
