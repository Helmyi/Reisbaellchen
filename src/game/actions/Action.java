package game.actions;

import java.awt.Graphics;
import game.Unit;

public abstract class Action {
	private String name;
	private int numberAnimationSteps;
	private int currentStep;
	private int firstTileX;
	private int firstTileY;
	private Unit owner;
	
	public Action(Unit owner, int numberStates, int firstTileX, int firstTileY, String name){
		this.setOwner(owner);
		this.setNumberStates(numberStates);
		this.setFirstTileX(firstTileX);
		this.setFirstTileY(firstTileY);
		this.setName(name);
		currentStep = 0;
	}
	
	public abstract void tick();
	
	public abstract void drawCurrentImage(Graphics g);

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
	
	public int getCurrentAnimationStep(){
		return currentStep;
	}
	
	protected void setCurrentAnimationStep(int currentStep){
		this.currentStep = currentStep%numberAnimationSteps;
	}
}
