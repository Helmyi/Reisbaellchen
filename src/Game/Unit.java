package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Unit extends Entity {
	private boolean isMoving;
	private boolean isFighting;
	private int animationStep;
	private int animationStepCount;
	private UnitAction currentUnitAction;
	private double speed;

	public static enum UnitAction {
		MOVE_DOWN(0), MOVE_UP(1), MOVE_RIGHT(2), MOVE_LEFT(3), ATTACK_DOWN(4), ATTACK_UP(5), ATTACK_RIGHT(
				6), ATTACK_LEFT(7);
		private int code;

		private UnitAction(int code) {
			this.code = code;
		}

		public int toInt() {
			return code;
		}
	}

	public Unit(Image image, double x, double y) {
		super(image, x, y);
		init();
	}

	@Override
	public void tick() {
		if (this.isMoving() || this.isFighting()) {
			animationStep++;
			animationStep %= animationStepCount;

			switch (currentUnitAction) {
			case MOVE_DOWN:
				this.y += speed;
				if (Game.getGameInstance().getMap().getPositionCollision((int)this.x,(int) this.y))
				{
					this.y -= speed;
				}
				if (this.y > Game.getGameInstance().getMap().getMapHeight() - tileHeight / 2) {
					this.y = Game.getGameInstance().getMap().getMapHeight() - tileHeight / 2;
				}
				break;
			case MOVE_UP:
				this.y -= speed;
				if (Game.getGameInstance().getMap().getPositionCollision((int)this.x,(int) this.y))
				{
					y += speed;
				}
				if (this.y < 0) {
					this.y = 0;
				}
				break;
			case MOVE_RIGHT:
				this.x += speed;
				if (Game.getGameInstance().getMap().getPositionCollision((int)this.x,(int) this.y))
				{
					this.x -= speed;
				}
				if (this.x > Game.getGameInstance().getMap().getMapWidth() - tileWidth / 2) {
					this.x = Game.getGameInstance().getMap().getMapWidth() - tileWidth / 2;
				}
				break;
			case MOVE_LEFT:
				this.x -= speed;
				if (Game.getGameInstance().getMap().getPositionCollision((int)this.x,(int) this.y))
				{
					this.x += speed;
				}
				if (this.x < 0) {
					this.x = 0;
				}
				break;
			case ATTACK_DOWN:
				break;
			case ATTACK_UP:
				break;
			case ATTACK_RIGHT:
				break;
			case ATTACK_LEFT:
				break;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		Point viewBegin = Game.getGameInstance().getViewBegin();
		int drawX = (int) x - viewBegin.x;
		int drawY = (int) y - viewBegin.y;

		g.drawImage(entityImage, drawX, drawY, drawX + tileWidth, drawY + tileHeight, animationStep * tileWidth,
				this.currentUnitAction.toInt() * tileHeight, (animationStep + 1) * tileWidth,
				(this.currentUnitAction.toInt() + 1) * tileHeight, null);
	}

	public void setUnitAction(UnitAction action) {
		this.currentUnitAction = action;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isFighting() {
		return isFighting;
	}

	public void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}

	private void init() {
		speed = 10;
		isMoving = false;
		isFighting = false;
		animationStep = 0;
		animationStepCount = 4;
		this.tileHeight = 64;
		this.tileWidth = 32;
		this.currentUnitAction = UnitAction.MOVE_DOWN;
	}
}
