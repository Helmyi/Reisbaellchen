package game.net;

import game.Game;
import game.Unit;

public class NetMessageHandler {
	private NetClient netClient;
	
	public NetMessageHandler(NetClient netClient){
		this.netClient = netClient;
		if(netClient != null)netClient.start();
	}
	
	public NetClient getNetClient(){
		return netClient;
	}
	
	public void sendUnitAction(Unit unit, int action, Unit.ViewDirection direction, boolean moving){
		String message = unit.getId() + ":" + action + ":" + direction.toInt() + ":" + moving;
		if(netClient != null){
			netClient.sendMessage(message);
		}else{
			//singleplayer
			processMessage(message);
		}
	}
	
//	public static String sendUnitAction(Unit unit, int action, Unit.ViewDirection direction, boolean moving){
//		return unit.getId() + ":" + action + ":" + direction.toInt() + ":" + moving;
//	}
//	
	public void processMessage(String message){
		int unitId;
		int action;
		Unit.ViewDirection direction;
		boolean isMoving;
		
		//get message parameters
		String  messageSplit[] = message.split(":");
		unitId = Integer.parseInt(messageSplit[0]);
		action = Integer.parseInt(messageSplit[1]);
		int viewDirectionInt = Integer.parseInt(messageSplit[2]);
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
		isMoving = Boolean.parseBoolean(messageSplit[3]);		
		
		//change
		Unit unit = (Unit)Game.getGameInstance().getEnityById(unitId);
		if(unit != null){
			unit.setViewDirection(direction);
			unit.setMoving(isMoving);
			unit.setAction(action);
		}
	}
}
