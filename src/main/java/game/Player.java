package game;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import player.PlayerAction_ActionX_Pressed;
import player.PlayerAction_ActionX_Released;
import player.PlayerAction_MoveDown_Pressed;
import player.PlayerAction_MoveDown_Released;
import player.PlayerAction_MoveLeft_Pressed;
import player.PlayerAction_MoveLeft_Released;
import player.PlayerAction_MoveRight_Pressed;
import player.PlayerAction_MoveRight_Released;
import player.PlayerAction_MoveUp_Pressed;
import player.PlayerAction_MoveUp_Released;

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
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key_MoveUp), "player move Up");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key_MoveUp), "player move Up released");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key_MoveDown), "player move Down");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key_MoveDown), "player move Down released");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key_MoveLeft), "player move Left");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key_MoveLeft), "player move Left released");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key_MoveRight), "player move Right");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key_MoveRight), "player move Right released");
		
		Game.getGameInstance().getActionMap().put("player move Up",  new PlayerAction_MoveUp_Pressed(this));
		Game.getGameInstance().getActionMap().put("player move Up released", new PlayerAction_MoveUp_Released(this));
		Game.getGameInstance().getActionMap().put("player move Down",  new PlayerAction_MoveDown_Pressed(this));
		Game.getGameInstance().getActionMap().put("player move Down released", new PlayerAction_MoveDown_Released(this));
		Game.getGameInstance().getActionMap().put("player move Right",  new PlayerAction_MoveRight_Pressed(this));
		Game.getGameInstance().getActionMap().put("player move Right released", new PlayerAction_MoveRight_Released(this));
		Game.getGameInstance().getActionMap().put("player move Left",  new PlayerAction_MoveLeft_Pressed(this));
		Game.getGameInstance().getActionMap().put("player move Left released", new PlayerAction_MoveLeft_Released(this));
		
		//attack keys
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key_Action1), "player Action1");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key_Action1), "player Action1 released");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key_Action2), "player Action2");
		Game.getGameInstance().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key_Action2), "player Action2 released");

		Game.getGameInstance().getActionMap().put("player Action1",  new PlayerAction_ActionX_Pressed(this, 2));
		Game.getGameInstance().getActionMap().put("player Action1 released", new PlayerAction_ActionX_Released(this, 2));
		Game.getGameInstance().getActionMap().put("player Action2",  new PlayerAction_ActionX_Pressed(this, 3));
		Game.getGameInstance().getActionMap().put("player Action2 released", new PlayerAction_ActionX_Released(this, 3));
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
