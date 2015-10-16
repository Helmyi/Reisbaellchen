package game.actions;

import java.awt.Graphics;

import game.Game;
import game.Unit;

public abstract class Action {
	private String name;
	private int numberAnimationSteps;
	private int currentStep;
	private int firstTileX;
	private int firstTileY;
	private Unit owner;

	public Action(Unit owner, int numberStates, int firstTileX, int firstTileY, String name) {
		this.setOwner(owner);
		this.setNumberStates(numberStates);
		this.setFirstTileX(firstTileX);
		this.setFirstTileY(firstTileY);
		this.setName(name);
		currentStep = 0;
	}

	public abstract void tick();

	public abstract void drawCurrentImage(Graphics g);

	protected void basicImagePaint(Graphics g) {
		int drawX = (int) getOwner().getX() - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointX();
		int drawY = (int) getOwner().getY() - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointY();

		g.drawImage(getOwner().getImage(), drawX, drawY, drawX + getOwner().getTileWidth(),
				drawY + getOwner().getTileHeight(),
				(getCurrentAnimationStep() + getFirstTileX()) * getOwner().getTileWidth(),
				(getOwner().getViewDirection().toInt() + getFirstTileY()) * getOwner().getTileHeight(),
				(getCurrentAnimationStep() + getFirstTileX() + 1) * getOwner().getTileWidth(),
				(getOwner().getViewDirection().toInt() + 1 + getFirstTileY()) * getOwner().getTileHeight(), null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberStates() {
		return numberAnimationSteps;
	}

	public void setNumberStates(int numberStates) {
		this.numberAnimationSteps = numberStates;
	}

	public int getFirstTileY() {
		return firstTileY;
	}

	public void setFirstTileY(int firstTileY) {
		this.firstTileY = firstTileY;
	}

	public int getFirstTileX() {
		return firstTileX;
	}

	public void setFirstTileX(int firstTileX) {
		this.firstTileX = firstTileX;
	}

	public Unit getOwner() {
		return owner;
	}

	public void setOwner(Unit owner) {
		this.owner = owner;
	}

	public int getCurrentAnimationStep() {
		return currentStep;
	}

	protected void setCurrentAnimationStep(int currentStep) {
		this.currentStep = currentStep % numberAnimationSteps;
	}
}
