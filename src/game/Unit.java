package game;

import java.awt.Graphics;
import java.awt.Image;

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
	
	public Unit(Image image, double x, double y, double speed) {
		super(image, x, y);
		init();
		this.speed = speed;
	}
	
	private boolean unitCollision()
	{
		int tempX[] = new int[2];
		int tempY[] = new int[2];
		if (currentUnitAction == UnitAction.MOVE_DOWN)
		{
			tempX[0] = (int) this.x;
			tempX[1] = (int) this.x + tileWidth;
			tempY[0] = (int) this.y + tileHeight;
			tempY[1] = (int) this.y + tileHeight;
		}
		else if (currentUnitAction == UnitAction.MOVE_UP)
		{
			tempX[0] = (int) this.x;
			tempX[1] = (int) this.x + tileWidth;
			tempY[0] = (int) this.y;// + tileHeight/ 2;
			tempY[1] = (int) this.y;// + tileHeight/ 2;
		}
		else if(currentUnitAction == UnitAction.MOVE_LEFT)
		{
			tempX[0] = (int) this.x;// + tileWidth/ 2;
			tempX[1] = (int) this.x;// + tileWidth/ 2;
			tempY[0] = (int) this.y;
			tempY[1] = (int) this.y + tileHeight;
		}
		else if(currentUnitAction == UnitAction.MOVE_RIGHT)
		{
			tempX[0] = (int) this.x + tileWidth;// / 2;
			tempX[1] = (int) this.x + tileWidth;// / 2;
			tempY[0] = (int) this.y;
			tempY[1] = (int) this.y + tileHeight;
		}
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				if (Game.getGameInstance().getMap().getPositionCollision(tempX[i],tempY[j]))
				{
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void tick() {
		if (this.isMoving() || this.isFighting()) {
			animationStep++;
			animationStep %= animationStepCount;

			switch (currentUnitAction) {
			case MOVE_DOWN:
				this.y += speed;
				if (unitCollision())
				{
					this.y -= speed;
				}
				if (this.y > Game.getGameInstance().getMap().getMapHeight() - tileHeight / 2) {
					this.y = Game.getGameInstance().getMap().getMapHeight() - tileHeight / 2;
				}
				break;
			case MOVE_UP:
				this.y -= speed;
				if (unitCollision())
				{
					this.y += speed;
				}
				if (this.y < 0) {
					this.y = 0;
				}
				break;
			case MOVE_RIGHT:
				this.x += speed;
				if (unitCollision())
				{
					this.x -= speed;
				}
				if (this.x > Game.getGameInstance().getMap().getMapWidth() - tileWidth / 2) {
					this.x = Game.getGameInstance().getMap().getMapWidth() - tileWidth / 2;
				}
				break;
			case MOVE_LEFT:
				this.x -= speed;
				if (unitCollision())
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
		int drawX = (int) x - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointX();
		int drawY = (int) y - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointY();

		g.drawImage(entityImage, drawX, drawY, drawX + tileWidth, drawY + tileHeight, animationStep * tileWidth,
				this.currentUnitAction.toInt() * tileHeight, (animationStep + 1) * tileWidth,
				(this.currentUnitAction.toInt() + 1) * tileHeight, null);
	}

	public void setUnitAction(UnitAction action) {
		this.currentUnitAction = action;
	}
	
	public UnitAction getUnitAction() {
		return this.currentUnitAction;
	}
	
	public double getSpeed()
	{
		return speed;
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
