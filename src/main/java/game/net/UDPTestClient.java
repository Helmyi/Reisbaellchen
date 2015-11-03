package game.net;

import game.Game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.List;

/**
 *  used to simulate delay for test on single computer
 * 
 * @author Helmi
 */
public class UDPTestClient extends UDPClient{
	private List<PacketInfo> recPacketInfos;
	private List<PacketInfo> sendPacketInfos;
	private int customDelay;
	private int customJitter;
	
	public class PacketInfo{
		public PacketInfo(byte[] data, long receivedTime){
			this.data = data;
			this.receivedTime = receivedTime;
		}
		
		byte[] data;
		long receivedTime;
	}
    
    public UDPTestClient(String serverIp, int port, int maxConnectTrys, int customDelay, int customJitter) throws ConnectionFailedException{
    	super(serverIp, port, maxConnectTrys);
    	this.customDelay = customDelay;
		recPacketInfos = new LinkedList<PacketInfo>();
		sendPacketInfos = new LinkedList<PacketInfo>();
		this.customJitter = customJitter;
	}
    
    @Override
    public void run(){
        byte[] receiveData = new byte[receivePacketSize];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		while(true){
			try {
				clientSocket.receive(receivePacket);
				if(recPacketInfos==null){
					Game.getGameInstance().getNetMessageHandler().processByteMessage(receivePacket.getData().clone());
					continue;
				}
				recPacketInfos.add(0, new PacketInfo(receivePacket.getData().clone(), calcPacketTime()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    @Override
	public void sendMessage(byte[] data) {
    	if(sendPacketInfos==null){
    		super.sendMessage(data);
    		return;
    	}
		sendPacketInfos.add(0, new PacketInfo(data, calcPacketTime()));
	}
    
    /**
     * @return should simulate the travel time of a packet
     */
    private long calcPacketTime(){
    	return System.currentTimeMillis() + (int)(customJitter * Math.random()) + customDelay;
    }
    
	public void tick(){
		for(int i=sendPacketInfos.size()-1; i>=0; i--){
			if(sendPacketInfos.get(i).receivedTime < System.currentTimeMillis()){
				super.sendMessage(sendPacketInfos.get(i).data);
				sendPacketInfos.remove(i);
			}
		}
		
		for(int i=recPacketInfos.size()-1; i>=0; i--){
			if(recPacketInfos.get(i).receivedTime < System.currentTimeMillis()){
				Game.getGameInstance().getNetMessageHandler().processByteMessage(recPacketInfos.get(i).data);
				recPacketInfos.remove(i);
			}
		}
	}
}
