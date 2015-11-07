package game;

import game.player.PlayerAction_ActionX_Pressed;
import game.player.PlayerAction_ActionX_Released;
import game.player.PlayerAction_MoveDown_Pressed;
import game.player.PlayerAction_MoveDown_Released;
import game.player.PlayerAction_MoveLeft_Pressed;
import game.player.PlayerAction_MoveLeft_Released;
import game.player.PlayerAction_MoveRight_Pressed;
import game.player.PlayerAction_MoveRight_Released;
import game.player.PlayerAction_MoveUp_Pressed;
import game.player.PlayerAction_MoveUp_Released;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Player{
	private Unit playerUnit;
	private boolean key_MoveUp_Pressed;
	private boolean key_MoveDown_Pressed;
	private boolean key_MoveLeft_Pressed;
	private boolean key_MoveRight_Pressed;
	private String lastPressedMoveKey;
	
	public static String key_MoveUp = "W";
	public static String key_MoveDown = "S";
	public static String key_MoveLeft = "A";
	public static String key_MoveRight = "D";
	
	public static String key_Action1 = "F";
	public static String key_Action2 = "G";
	
	private PlayerCamera camera;

	public Player() {
		setPlayerCamera(new PlayerCamera(this));
		key_MoveUp_Pressed = false;
		key_MoveDown_Pressed = false;
		key_MoveLeft_Pressed = false;
		key_MoveRight_Pressed = false;
		
		configureKeys();
	}
	
	private void configureKeys(){
		//movement keys
		bindAKeyForPressAndReleaseAndModifiers("player move Up", key_MoveUp, new PlayerAction_MoveUp_Pressed(this), new PlayerAction_MoveUp_Released(this));		
		bindAKeyForPressAndReleaseAndModifiers("player move Down", key_MoveDown ,new PlayerAction_MoveDown_Pressed(this), new PlayerAction_MoveDown_Released(this));
		bindAKeyForPressAndReleaseAndModifiers("player move Right", key_MoveRight, new PlayerAction_MoveRight_Pressed(this), new PlayerAction_MoveRight_Released(this));		
		bindAKeyForPressAndReleaseAndModifiers("player move Left", key_MoveLeft ,new PlayerAction_MoveLeft_Pressed(this), new PlayerAction_MoveLeft_Released(this));
		
		//attack keys
		bindAKeyForPressAndReleaseAndModifiers("player Action1", key_Action1, new PlayerAction_ActionX_Pressed(this,2), new PlayerAction_ActionX_Released(this,2));		
		bindAKeyForPressAndReleaseAndModifiers("player Action2", key_Action2 ,new PlayerAction_ActionX_Pressed(this,3), new PlayerAction_ActionX_Released(this,3));
	}
	
	private void bindAKeyForPressAndReleaseAndModifiers(String bindingName, String key, AbstractAction actionPressed, AbstractAction actionReleased){
		String bindingReleased = bindingName + " released";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key), bindingReleased);
		
		String modKey = "control";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + "released " + key), bindingReleased);
		modKey = "shift";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + "released " + key), bindingReleased);
		modKey = "alt";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + "released " + key), bindingReleased);
		modKey = "alt control";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + "released " + key), bindingReleased);
		modKey = "alt shift";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + "released " + key), bindingReleased);
		modKey = "control shift";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + "released " + key), bindingReleased);
		modKey = "control alt shift";
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + key), bindingName);
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(modKey + " " + "released " + key), bindingReleased);
		
		Game.getGameInstance().getActionMap().put(bindingName, actionPressed);
		Game.getGameInstance().getActionMap().put(bindingReleased, actionReleased);
	}
	
	public void setPlayerUnit(Unit playerUnit) {
		this.playerUnit = playerUnit;
		camera.setPlayerTileWidth(getPlayerUnit().getTileWidth());
		camera.setPlayerTileHeight(getPlayerUnit().getTileHeight());
		calcMoveDirection();
	}

	public void setPlayerCamera(PlayerCamera cam) {
		this.camera = cam;
	}

	public PlayerCamera getPlayerCamera() {
		return this.camera;
	}

	public Unit getPlayerUnit() {
		return playerUnit;
	}

	public void calcMoveDirection() {
		if(playerUnit == null) return;
		Unit.ViewDirection tempViewDirection = playerUnit.getViewDirection();
		boolean tempMoving = playerUnit.isMoving();
		int tempAction = playerUnit.getAction();
		
		if ((key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveUp))
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveUp))
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// up
			tempViewDirection = Unit.ViewDirection.UP;
			tempMoving = true;
		} else if ((!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveDown))
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveDown))
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// down
			tempViewDirection = Unit.ViewDirection.DOWN;
			tempMoving = true;
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveLeft))
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveLeft))
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed)) {
			// left
			tempViewDirection = Unit.ViewDirection.LEFT;
			tempMoving = true;
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveRight))
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey.equals(Player.key_MoveRight))
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// right
			tempViewDirection = Unit.ViewDirection.RIGHT;
			tempMoving = true;
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// no movement
			tempMoving = false;
		}
		if(playerUnit.getAction() == 0 && tempMoving) tempAction = 1;
		else if(playerUnit.getAction() == 1 && !tempMoving) tempAction = 0;
		Game.getGameInstance().getNetMessageHandler().sendUnitAction(playerUnit, tempAction, tempViewDirection, tempMoving);
	}
	
	public boolean getKeyMoveUp(){
		return key_MoveUp_Pressed;
	}
	
	public boolean getKeyMoveDown(){
		return key_MoveDown_Pressed;
	}
	
	public boolean getKeyMoveLeft(){
		return key_MoveLeft_Pressed;
	}
	
	public boolean getKeyMoveRight(){
		return key_MoveRight_Pressed;
	}
	
	public void setKeyMoveUp(boolean key_MoveUp_Pressed){
		this.key_MoveUp_Pressed = key_MoveUp_Pressed;
		lastPressedMoveKey = key_MoveUp;
	}

	public void setKeyMoveDown(boolean key_MoveDown_Pressed){
		this.key_MoveDown_Pressed = key_MoveDown_Pressed;
		lastPressedMoveKey = key_MoveDown;
	}

	public void setKeyMoveLeft(boolean key_MoveLeft_Pressed){
		this.key_MoveLeft_Pressed = key_MoveLeft_Pressed;
		lastPressedMoveKey = key_MoveLeft;
	}

	public void setKeyMoveRight(boolean key_MoveRight_Pressed){
		this.key_MoveRight_Pressed = key_MoveRight_Pressed;
		lastPressedMoveKey = key_MoveRight;
	}
}
