package game;

import game.actions.*;
import game.ui.HealthBar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Unit extends Entity {
	private boolean isMoving;
	private ViewDirection currentViewDirection;
	private double speed;

	private List<Action> actions;
	private int currentAction;
	private HealthBar healthBar;
	private int maxHealth;
	private int currentHealth;

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

	public Unit(Image image, double x, double y, int currentLevelId) {
		super(image, x, y, currentLevelId);
		init();
	}

	public Unit(Image image, double x, double y, int currentLevelId, double speed) {
		super(image, x, y, currentLevelId);
		init();
		this.speed = speed;
	}

	private boolean unitCollision() {
		if(!Game.getGameInstance().getMap(currentLevelId).getPositionCollision((int)x, (int)y + tileHeight/2)
			&& !Game.getGameInstance().getMap(currentLevelId).getPositionCollision((int)x, (int)y + tileHeight-1)
			&& !Game.getGameInstance().getMap(currentLevelId).getPositionCollision((int)x + tileWidth-1, (int)y + tileHeight/2)
			&& !Game.getGameInstance().getMap(currentLevelId).getPositionCollision((int)x + tileWidth-1, (int)y + tileHeight-1)){
			return false;
		}
		return true;
	}

	@Override
	public void tick(int elapsedTime) {
		actions.get(currentAction).tick();
		if (this.isMoving()) {
			double moveDistance = speed * elapsedTime / 1000.0;
			switch (currentViewDirection) {
			case DOWN:
				y += moveDistance;
				if (unitCollision()) {
					y -= moveDistance;
					//move as close as possible
					y += Game.getGameInstance().getMap(currentLevelId).getTileSize() - (y+63)%Game.getGameInstance().getMap(currentLevelId).getTileSize() - 1;
				}
				break;
			case UP:
				y -= moveDistance;
				if (unitCollision()) {
					y += moveDistance;
					y -= (int)(y+32)%Game.getGameInstance().getMap(currentLevelId).getTileSize();
				}
				break;
			case RIGHT:
				x += moveDistance;
				if (unitCollision()) {
					x -= moveDistance;
					x += Game.getGameInstance().getMap(currentLevelId).getTileSize() - (x+63)%Game.getGameInstance().getMap(currentLevelId).getTileSize() - 1;
				}
				break;
			case LEFT:
				x -= moveDistance;
				if (unitCollision()) {
					x += moveDistance;
					x -= (int)(x+32)%Game.getGameInstance().getMap(currentLevelId).getTileSize();
				}
				break;
			}
		}
		updateHealthBar();
	}

	public void updateHealthBar() {
		double fillWidth;
		if (currentHealth <= 0) {
			fillWidth = 0;
			// implement here what happens on death
		} else {
			fillWidth = (double) currentHealth / maxHealth * healthBar.getBarWidth();
		}
		healthBar.setFillWidth((int) fillWidth);
	}

	@Override
	public void paint(Graphics g) {
		actions.get(currentAction).drawCurrentImage(g);
		healthBar.paint(g, (int) x, (int) y);
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
		if (isMoving && currentAction == 0) {
			currentAction = 1;
		} else if(!isMoving && currentAction == 1){
			currentAction = 0;
		}
	}

	public void setAction(int action) {
		currentAction = action;
	}

	public int getAction() {
		return currentAction;
	}

	public void takeDamage(int damage) {
		currentHealth -= damage;
	}

	private void init() {
		speed = 10;
		isMoving = false;
		tileHeight = 64;
		tileWidth = 32;

		maxHealth = 100;
		currentHealth = 69;
		currentViewDirection = ViewDirection.DOWN;
		healthBar = new HealthBar(tileWidth, 8, Color.GREEN);

		actions = new ArrayList<Action>();
		actions.add(new Action_Stand(this));
		actions.add(new Action_Move(this));
		actions.add(new Action_Punch(this));
		actions.add(new Action_Kick(this));
		currentAction = 0;
	}
}
