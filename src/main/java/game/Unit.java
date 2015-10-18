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
		if(!Game.getGameInstance().getMap().getPositionCollision((int)x, (int)y + tileHeight/2)
			&& !Game.getGameInstance().getMap().getPositionCollision((int)x, (int)y + tileHeight-1)
			&& !Game.getGameInstance().getMap().getPositionCollision((int)x + tileWidth-1, (int)y + tileHeight/2)
			&& !Game.getGameInstance().getMap().getPositionCollision((int)x + tileWidth-1, (int)y + tileHeight-1)){
			return false;
		}
		return true;
	}

	@Override
	public void tick() {
		actions.get(currentAction).tick();
		if (this.isMoving()) {
			switch (currentViewDirection) {
			case DOWN:
				y += speed;
				if (unitCollision()) {
					y -= speed;
					//move as close as possible
					y += Game.getGameInstance().getMap().getTileSize() - (y+63)%Game.getGameInstance().getMap().getTileSize() - 1;
				}
				break;
			case UP:
				y -= speed;
				if (unitCollision()) {
					y += speed;
					y -= (int)(y+32)%Game.getGameInstance().getMap().getTileSize();
				}
				break;
			case RIGHT:
				x += speed;
				if (unitCollision()) {
					x -= speed;
					x += Game.getGameInstance().getMap().getTileSize() - (x+63)%Game.getGameInstance().getMap().getTileSize() - 1;
				}
				break;
			case LEFT:
				x -= speed;
				if (unitCollision()) {
					x += speed;
					x -= (int)(x+32)%Game.getGameInstance().getMap().getTileSize();
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
		if (isMoving) {
			currentAction = 1;
		} else {
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
