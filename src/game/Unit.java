package game;

import game.actions.Action;
import game.actions.Action_Kick;
import game.actions.Action_Move;
import game.actions.Action_Punch;
import game.actions.Action_Stand;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Unit extends Entity{
	private boolean isMoving;
	private ViewDirection currentViewDirection;
	private double speed;
	
	private List<Action> actions;
	private int currentAction;

	public static enum ViewDirection {
		DOWN(0), UP(1), RIGHT(2), LEFT(3);
		private int code;

		private ViewDirection(int code) {
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

	private boolean unitCollision() {
		int tempX[] = new int[2];
		int tempY[] = new int[2];
		if (currentViewDirection == ViewDirection.DOWN) {
			tempX[0] = (int) this.x;
			tempX[1] = (int) this.x + tileWidth;
			tempY[0] = (int) this.y + tileHeight;
			tempY[1] = (int) this.y + tileHeight;
		} else if (currentViewDirection == ViewDirection.UP) {
			tempX[0] = (int) this.x;
			tempX[1] = (int) this.x + tileWidth;
			tempY[0] = (int) this.y;// + tileHeight/ 2;
			tempY[1] = (int) this.y;// + tileHeight/ 2;
		} else if (currentViewDirection == ViewDirection.LEFT) {
			tempX[0] = (int) this.x;// + tileWidth/ 2;
			tempX[1] = (int) this.x;// + tileWidth/ 2;
			tempY[0] = (int) this.y;
			tempY[1] = (int) this.y + tileHeight;
		} else if (currentViewDirection == ViewDirection.RIGHT) {
			tempX[0] = (int) this.x + tileWidth;// / 2;
			tempX[1] = (int) this.x + tileWidth;// / 2;
			tempY[0] = (int) this.y;
			tempY[1] = (int) this.y + tileHeight;
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (Game.getGameInstance().getMap().getPositionCollision(tempX[i], tempY[j])) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void tick() {
		actions.get(currentAction).tick();
		
		if (this.isMoving()) {

			switch (currentViewDirection) {
			case DOWN:
				this.y += speed;
				if (unitCollision()) {
					this.y -= speed;
				}
				if (this.y > Game.getGameInstance().getMap().getMapHeight() - tileHeight / 2) {
					this.y = Game.getGameInstance().getMap().getMapHeight() - tileHeight / 2;
				}
				break;
			case UP:
				this.y -= speed;
				if (unitCollision()) {
					this.y += speed;
				}
				if (this.y < 0) {
					this.y = 0;
				}
				break;
			case RIGHT:
				this.x += speed;
				if (unitCollision()) {
					this.x -= speed;
				}
				if (this.x > Game.getGameInstance().getMap().getMapWidth() - tileWidth / 2) {
					this.x = Game.getGameInstance().getMap().getMapWidth() - tileWidth / 2;
				}
				break;
			case LEFT:
				this.x -= speed;
				if (unitCollision()) {
					this.x += speed;
				}
				if (this.x < 0) {
					this.x = 0;
				}
				break;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		actions.get(currentAction).drawCurrentImage(g);
	}

	public void setViewDirection(ViewDirection action) {
		this.currentViewDirection = action;
	}

	public ViewDirection getViewDirection() {
		return this.currentViewDirection;
	}

	public double getSpeed() {
		return speed;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
		if(isMoving){
			currentAction = 1;
		}else{
			currentAction = 0;
		}
	}

	public void setAction(int action) {
		currentAction = action;
	}

	private void init() {
		speed = 10;
		isMoving = false;
		this.tileHeight = 64;
		this.tileWidth = 32;
		this.currentViewDirection = ViewDirection.DOWN;
		
		//actions
		actions = new ArrayList<Action>();
		actions.add(new Action_Stand(this));
		actions.add(new Action_Move(this));
		actions.add(new Action_Punch(this));
		actions.add(new Action_Kick(this));
		currentAction = 0;
	}

}
