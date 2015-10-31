package game.net;

import java.nio.ByteBuffer;

import game.Game;
import game.Unit;

public class NetMessageHandler {
	private UDPClient client;
	private byte tickCounter;
	protected byte packageCounter;
	private int ping;
	protected byte lastReceivedPackageNumber;
	
	//ack only for last package
	protected byte[] lastPackage;
	protected boolean ackReceived;
	protected long timeSend;
    
	//message types
	public static final byte MESSAGE_END = -1;
	public static final byte MESSAGE_CONNECT = 0;
	public static final byte MESSAGE_UNIT_UPDATE = 1;
	public static final byte MESSAGE_ACK = 2;
	
	private static final int[] messageLength = {2, 26, 2};
	
	public NetMessageHandler(UDPClient client){
		tickCounter = 0;
		packageCounter = 0;
		ackReceived = true;
		lastReceivedPackageNumber = -1;
		ping = 80; //TODO correct ping
		
		this.client = client;
		if(client != null)client.start();
	}
	
	public UDPClient getClient(){
		return client;
	}
	
	public void processByteMessage(byte[] data){
		int i = 0;
		while(i < data.length){
			switch (data[i]) {
			case MESSAGE_CONNECT:
				//handled by UDPClient for connection establishment
				i += messageLength[MESSAGE_UNIT_UPDATE];
				break;
			case MESSAGE_UNIT_UPDATE:
				processUnitUpdate(data, i);
				i += messageLength[MESSAGE_UNIT_UPDATE];
				break;
			case MESSAGE_ACK:
				if(!processAck(data, i)) return;
				i += messageLength[MESSAGE_ACK];
				break;
			case MESSAGE_END:
				return;
			default:
				System.out.println("NetMessageHandler:processByteMessage error: message length or missing message end");
				return;
			}
		}
	}
	
	public void sendUnitAction(Unit unit, int action, Unit.ViewDirection direction, boolean moving){
		byte data[] = new byte[UDPClient.sendPacketSize];
		int length = 0;
		length += addUnitDataToArray(unit, action, direction, moving, data, 0);
		data[length] = MESSAGE_END;
		
		if(client != null){
			client.sendMessage(data);
			lastPackage = data;
			ackReceived = false;
			timeSend = System.currentTimeMillis();
			
			directPlayerUnitAction(unit, action, direction, moving);
		}else{
			//singleplayer
			processByteMessage(data);
			directPlayerUnitAction(unit, action, direction, moving);
		}

		packageCounter++;
	}
	
	protected void directPlayerUnitAction(Unit unit, int action, Unit.ViewDirection direction, boolean moving){
		unit.setViewDirection(direction);
		unit.setMoving(moving);
		unit.setAction(action);
	}
	
	/**
	 * @return false if package is old or duplicate
	 */
	private boolean processAck(byte[] data, int begin){
		if(data[begin+1] > lastReceivedPackageNumber || (data[begin+1] < -100 && lastReceivedPackageNumber > 100)){
			if(data[begin+1] == lastPackage[1]){
				lastReceivedPackageNumber = data[begin+1];
				ackReceived = true;
			}
			return true;
		}else{
			System.out.println("NetMessageHandler:processAck: throw old or duplicate package away. Nr:" + data[begin+1] + " Time:" + System.currentTimeMillis());
			return false;
		}
	}
	
	private void processUnitUpdate(byte[] data, int begin){
		int unitId;
		int action;
		Unit.ViewDirection direction;
		boolean isMoving;
		double posX;
		double posY;
		
		//get message parameters
		unitId = getIntOutOfmessage(data, 3+begin);
		//igonore own unit
		if(unitId == Game.getGameInstance().getPlayer().getPlayerUnit().getId()) return;
		
		action = (int)data[7+begin];
		int viewDirectionInt = (int)data[8+begin];
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
		isMoving = data[9+begin] == (byte)1 ? true: false;
		posX = getDoubleOutOfmessage(data, 10+begin);
		posY = getDoubleOutOfmessage(data, 18+begin);
		
		//change
		Unit unit = (Unit)Game.getGameInstance().getEnityById(unitId);
		if(unit != null){
			unit.setViewDirection(direction);
			unit.setMoving(isMoving);
			unit.setAction(action);
			unit.setX(posX);
			unit.setY(posY);
		}
	}
	
	/**
	 * checks if package might get lost
	 */
	public void tick(){
		tickCounter += 1;
		if(client instanceof UDPTestClient) ((UDPTestClient) client).tick();
		
		//resend package test
		if(!ackReceived && System.currentTimeMillis() > timeSend + ping){
			System.out.println("resent test: Nr:" + lastPackage[1] + " Time:" + System.currentTimeMillis());
			client.sendMessage(lastPackage);
			timeSend = System.currentTimeMillis();
		}
	}
	
	/**
	 * 
	 * @return length of the information in byte
	 */
	protected int addUnitDataToArray(Unit unit, int action, Unit.ViewDirection direction, boolean moving, byte[] messageArray, int messageArrayStartPosition){
		int masp = messageArrayStartPosition;
		int length = 0;
		messageArray[masp + length++] = MESSAGE_UNIT_UPDATE;
		messageArray[masp + length++] = packageCounter;
		messageArray[masp + length++] = tickCounter;
		length += intIntoMessageArray(unit.getId(), messageArray, masp + length);
		messageArray[masp + length++] = (byte) action;
		messageArray[masp + length++] = (byte) direction.toInt();
		messageArray[masp + length++] = moving ? (byte) 1 : (byte) 0;
		length += doubleIntoMessageArray(unit.getX(), messageArray, masp + length); 
		length += doubleIntoMessageArray(unit.getY(), messageArray, masp + length);
		
		return length;
	}
	
	public static int getMessageEnd(byte[] data){
		int i = 0;
		main:while(i < data.length){
			switch (data[i]) {
			case MESSAGE_UNIT_UPDATE:
				i += messageLength[MESSAGE_UNIT_UPDATE];
				break;
			case MESSAGE_ACK:
				i += messageLength[MESSAGE_ACK];
				break;
			case MESSAGE_END:
				return i;
			default:
				break main;
			}
		}
		
		System.out.println("NetMessageHandler:getMessageEnd: Message length error or Message_End missing");
		return -1;
	}
	
	/**
	 * 
	 * @param value
	 * @param messageArray
	 * @param messageArrayStartPosition
	 * @return return number of bytes added, integer is 4 byte long
	 */
	private static int intIntoMessageArray(int value, byte[] messageArray, int messageArrayStartPosition) {
		byte[] intArray = toByteArray(value);
		for(int i = 0; i < 4; i++){
			messageArray[messageArrayStartPosition + i] = intArray[i];
		}
		return 4;
	}
	
	private static final int getIntOutOfmessage(byte[] data, int arrayStartingPositon){
		byte[] intValues = {data[arrayStartingPositon],data[arrayStartingPositon+1],data[arrayStartingPositon+2],data[arrayStartingPositon+3]};
		return byteToInt(intValues);
	}
	
	/**
	 * 
	 * @param value
	 * @param messageArray
	 * @param messageArrayStartPosition
	 * @return return number of bytes added, double is 8 byte long
	 */
	private static int doubleIntoMessageArray(double value, byte[] messageArray, int MessageArrayStartPosition) {
		byte[] intArray = toByteArray(value);
		for(int i = 0; i < 8; i++){
			messageArray[MessageArrayStartPosition + i] = intArray[i];
		}
		return 8;
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
