package game.net;

import java.util.LinkedList;
import java.util.List;

import game.Unit;

/**
 * used to simulate delay for test on single computer
 *  
 * @author Helmi
 */
public class TestNetMessageHandler extends NetMessageHandler{
	private List<PacketInfo> recPacketInfos;
	private List<PacketInfo> sendPacketInfos;
	private int customDelay;
	
	public class PacketInfo{
		public PacketInfo(byte[] data, long receivedTime){
			this.data = data;
			this.receivedTime = receivedTime;
		}
		
		byte[] data;
		long receivedTime;
	}

	public TestNetMessageHandler(UDPClient client, int customDelay){
		super(client);
		this.customDelay = customDelay;
		recPacketInfos = new LinkedList<PacketInfo>();
		sendPacketInfos = new LinkedList<PacketInfo>();
	}
	
	@Override
	public void sendUnitAction(Unit unit, int action, Unit.ViewDirection direction, boolean moving){
		if(getClient() == null){
			super.sendUnitAction(unit, action, direction, moving);
			return;
		}
		
		byte data[] = new byte[UDPClient.sendPacketSize];
		int length = 0;
		length += addUnitDataToArray(unit, action, direction, moving, data, 0);
		data[length] = MESSAGE_END;
		
		directPlayerUnitAction(unit, action, direction, moving);
		sendPacketInfos.add(new PacketInfo(data, System.currentTimeMillis()));
	}
	
	@Override
	public void processByteMessage(byte[] data){
		recPacketInfos.add(new PacketInfo(data.clone(), System.currentTimeMillis()));
	}
	
	@Override
	public void tick(){
		for(int i=sendPacketInfos.size()-1; i>=0; i--){
			if(sendPacketInfos.get(i).receivedTime + customDelay < System.currentTimeMillis()){
				getClient().sendMessage(sendPacketInfos.get(i).data);
				lastPackage = sendPacketInfos.get(i).data;
				ackReceived = false;
				timeSend = System.currentTimeMillis();
				packageCounter++;
				sendPacketInfos.remove(i);
			}
		}
		
		for(int i=recPacketInfos.size()-1; i>=0; i--){
			if(recPacketInfos.get(i).receivedTime + customDelay < System.currentTimeMillis()){
				super.processByteMessage(recPacketInfos.get(i).data);
				recPacketInfos.remove(i);
			}
		}
		
		super.tick();
	}
	
}
