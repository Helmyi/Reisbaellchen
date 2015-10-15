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
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_UP);
			playerUnit.setMoving(true);
		} else if ((!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveDown)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveDown)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// down
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_DOWN);
			playerUnit.setMoving(true);
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveLeft)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveLeft)
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && key_MoveLeft_Pressed && !key_MoveRight_Pressed)) {
			// left
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_LEFT);
			playerUnit.setMoving(true);
		} else if ((!key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed)
				|| (!key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveRight)
				|| (key_MoveUp_Pressed && !key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed
						&& lastPressedMoveKey == key_MoveRight)
				|| (key_MoveUp_Pressed && key_MoveDown_Pressed && !key_MoveLeft_Pressed && key_MoveRight_Pressed)) {
			// right
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_RIGHT);
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

		if (arg0.getKeyCode() == KeyEvent.VK_J) {
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_LEFT);
			playerUnit.setFighting(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_K) {
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_DOWN);
			playerUnit.setFighting(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_I) {
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_UP);
			playerUnit.setFighting(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_L) {
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_RIGHT);
			playerUnit.setFighting(true);
		}

		calcMoveDirection();
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

		playerUnit.setFighting(false);
		calcMoveDirection();
	}

	public void keyTyped(KeyEvent arg0) {
	}

}
