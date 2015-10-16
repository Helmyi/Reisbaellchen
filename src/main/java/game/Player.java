package game;

import java.awt.event.KeyEvent;

public class Player {
	private Unit playerUnit;
	private boolean key_MoveUp_Pressed;
	private boolean key_MoveDown_Pressed;
	private boolean key_MoveLeft_Pressed;
	private boolean key_MoveRight_Pressed;
	private int lastPressedMoveKey;
	private int key_MoveUp = KeyEvent.VK_W;
	private int key_MoveDown = KeyEvent.VK_S;
	private int key_MoveLeft = KeyEvent.VK_A;
	private int key_MoveRight = KeyEvent.VK_D;
	private int key_Action1 = KeyEvent.VK_F;
	private int key_Action2 = KeyEvent.VK_G;
	private PlayerCamera camera;

	public Player() {
		key_MoveUp_Pressed = false;
		key_MoveDown_Pressed = false;
		key_MoveLeft_Pressed = false;
		key_MoveRight_Pressed = false;
	}

	public void setPlayerUnit(Unit playerUnit) {
		this.playerUnit = playerUnit;
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

	public void updatePlayerPosition() {
		this.camera.setPlayerPositionX((int) this.getPlayerUnit().getX());
		this.camera.setPlayerPositionY((int) this.getPlayerUnit().getY());
	}

	private void calcMoveDirection() {
		if ((key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveUp)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveUp)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// up
			playerUnit.setViewDirection(Unit.ViewDirection.UP);
			playerUnit.setMoving(true);
		} else if ((!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveDown)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveDown)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// down
			playerUnit.setViewDirection(Unit.ViewDirection.DOWN);
			playerUnit.setMoving(true);
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveLeft)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveLeft)
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed)) {
			// left
			playerUnit.setViewDirection(Unit.ViewDirection.LEFT);
			playerUnit.setMoving(true);
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveRight)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveRight)
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// right
			playerUnit.setViewDirection(Unit.ViewDirection.RIGHT);
			playerUnit.setMoving(true);
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// no movement
			playerUnit.setMoving(false);
		}
	}

	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == key_MoveUp) {
			key_MoveUp_Pressed = true;
			lastPressedMoveKey = key_MoveUp;
		}
		if (arg0.getKeyCode() == key_MoveDown) {
			key_MoveDown_Pressed = true;
			lastPressedMoveKey = key_MoveDown;
		}
		if (arg0.getKeyCode() == key_MoveRight) {
			key_MoveRight_Pressed = true;
			lastPressedMoveKey = key_MoveRight;
		}
		if (arg0.getKeyCode() == key_MoveLeft) {
			key_MoveLeft_Pressed = true;
			lastPressedMoveKey = key_MoveLeft;
		}
		calcMoveDirection();
		
		if (arg0.getKeyCode() == key_Action1) {

			playerUnit.setAction(2);
		}
		if (arg0.getKeyCode() == key_Action2) {
			playerUnit.setAction(3);
		}
		
	}

	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == key_MoveUp) {
			key_MoveUp_Pressed = false;
		}
		if (arg0.getKeyCode() == key_MoveDown) {
			key_MoveDown_Pressed = false;
		}
		if (arg0.getKeyCode() == key_MoveRight) {
			key_MoveRight_Pressed = false;
		}
		if (arg0.getKeyCode() == key_MoveLeft) {
			key_MoveLeft_Pressed = false;
		}

		calcMoveDirection();
		if (arg0.getKeyCode() == key_Action1 || arg0.getKeyCode() == key_Action2) {
			playerUnit.setAction(0);
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

}
