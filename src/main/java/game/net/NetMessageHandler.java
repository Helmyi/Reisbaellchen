package game.net;

import java.nio.ByteBuffer;

import game.Game;
import game.Unit;

public class NetMessageHandler {
	private UDPClient client;
	private byte tickCounter;
	private byte packageCounter;

	//message types
	public static final byte MESSAGE_CONNECT = 0;
	public static final byte MESSAGE_UNIT_UPDATE = 1;
	
	
	public NetMessageHandler(UDPClient client){
		tickCounter = 0;
		packageCounter = 0;
		
		this.client = client;
		if(client != null)client.start();
	}
	
	public UDPClient getClient(){
		return client;
	}
	
	public void processByteMessage(byte[] data){
		switch (data[0]) {
		case MESSAGE_CONNECT:
			helloMessage(data);
			break;
		case MESSAGE_UNIT_UPDATE:
			processUnitUpdate(data);
			break;
		default:
			break;
		}
	}
	
	public void sendUnitAction(Unit unit, int action, Unit.ViewDirection direction, boolean moving){
		System.out.println("Send: " + unit.getId() + ":" + action + ":" + direction.toInt() + ":" + moving + ":" + unit.getX() + ":" + unit.getY());
		byte data[] = new byte[256];
		data[0] = MESSAGE_UNIT_UPDATE;
		data[1] = packageCounter;
		data[2] = tickCounter;
		intIntoMessageArray(unit.getId(), data, 3);
		data[7] = (byte) action;
		data[8] = (byte) direction.toInt();
		data[9] = moving ? (byte) 1 : (byte) 0;
		doubleIntoMessageArray(unit.getX(), data, 10); 
		doubleIntoMessageArray(unit.getY(), data, 18); 
			
		
		if(client != null){
			client.sendMessage(data);
		}else{
			//singleplayer
			processByteMessage(data);
		}
		packageCounter++;
	}
	
	private void processUnitUpdate(byte[] data){
		int unitId;
		int action;
		Unit.ViewDirection direction;
		boolean isMoving;
		double posX;
		double posY;
		
		//get message parameters
		unitId = getIntOutOfmessage(data, 3);
		action = (int)data[7];
		int viewDirectionInt = (int)data[8];
		if(viewDirectionInt == Unit.ViewDirection.DOWN.toInt()){
			direction = Unit.ViewDirection.DOWN;
		}else if(viewDirectionInt == Unit.ViewDirection.UP.toInt()){
			direction = Unit.ViewDirection.UP;
		}else if(viewDirectionInt == Unit.ViewDirection.LEFT.toInt()){
			direction = Unit.ViewDirection.LEFT;
		}else if(viewDirectionInt == Unit.ViewDirection.RIGHT.toInt()){
			direction = Unit.ViewDirection.RIGHT;
		}else{
			System.out.println("netMessageHandler: unitViewDirection int error: " + viewDirectionInt);
			direction = Unit.ViewDirection.DOWN;
		}
		isMoving = data[9] == (byte)1 ? true: false;
		posX = getDoubleOutOfmessage(data, 10);
		posY = getDoubleOutOfmessage(data, 18);
		
		System.out.println("receive:" + unitId + ":" + action + ":" + direction.toInt() + ":" + isMoving + ":" + posX + ":" + posY);
		
		//change
		Unit unit = (Unit)Game.getGameInstance().getEnityById(unitId);
		if(unit != null){
			unit.setViewDirection(direction);
			unit.setMoving(isMoving);
			unit.setAction(action);
		}
	}
	
	public void tick(){
		tickCounter += 1;
	}
	
	private void helloMessage(byte[] data){
		int clientNumber = (int)data[1];
		client.setClientNumber(clientNumber);
	}

	private static final void intIntoMessageArray(int value, byte[] messageArray, int MessageArrayStartPosition) {
		byte[] intArray = toByteArray(value);
		messageArray[MessageArrayStartPosition] = intArray[0];
		messageArray[MessageArrayStartPosition+1] = intArray[1];
		messageArray[MessageArrayStartPosition+2] = intArray[2];
		messageArray[MessageArrayStartPosition+3] = intArray[3];
		
	}
	
	private static final int getIntOutOfmessage(byte[] data, int arrayStartinPositon){
		byte[] intValues = {data[arrayStartinPositon],data[arrayStartinPositon+1],data[arrayStartinPositon+2],data[arrayStartinPositon+3]};
		return byteToInt(intValues);
	}
	
	private static final void doubleIntoMessageArray(double value, byte[] messageArray, int MessageArrayStartPosition) {
		byte[] intArray = toByteArray(value);
		messageArray[MessageArrayStartPosition] = intArray[0];
		messageArray[MessageArrayStartPosition+1] = intArray[1];
		messageArray[MessageArrayStartPosition+2] = intArray[2];
		messageArray[MessageArrayStartPosition+3] = intArray[3];
		messageArray[MessageArrayStartPosition+4] = intArray[4];
		messageArray[MessageArrayStartPosition+5] = intArray[5];
		messageArray[MessageArrayStartPosition+6] = intArray[6];
		messageArray[MessageArrayStartPosition+7] = intArray[7];
		
	}
	
	private static final double getDoubleOutOfmessage(byte[] data, int arrayStartinPositon){
		byte[] intValues = {data[arrayStartinPositon],data[arrayStartinPositon+1],data[arrayStartinPositon+2],data[arrayStartinPositon+3],
				data[arrayStartinPositon+4],data[arrayStartinPositon+5],data[arrayStartinPositon+6],data[arrayStartinPositon+7]};
		return byteToDouble(intValues);
	}
	
	private static byte[] toByteArray(double value) {
	    byte[] bytes = new byte[8];
	    ByteBuffer.wrap(bytes).putDouble(value);
	    return bytes;
	}

	private static byte[] toByteArray(int value) {
	    byte[] bytes = new byte[4];
	    ByteBuffer.wrap(bytes).putInt(value);
	    return bytes;
	}

	private static double byteToDouble(byte[] bytes) {
	    return ByteBuffer.wrap(bytes).getDouble();
	}

	private static int byteToInt(byte[] bytes) {
	    return ByteBuffer.wrap(bytes).getInt();
	}
}
