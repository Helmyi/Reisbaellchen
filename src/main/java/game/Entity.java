package game;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Entity implements Comparable<Entity> {
	private static int idCounter = 0;
	
	protected final int id;
	protected double x;
	protected double y;
	protected Image entityImage;
	protected int tileWidth;
	protected int tileHeight;
	protected int currentLevelId;
	
	public Entity(Image image, double x, double y, int currentLevelId) {
		id = idCounter++;
		this.entityImage = image;
		this.tileHeight = image.getHeight(null);
		this.tileWidth = image.getWidth(null);
		this.x = x;
		this.y = y;
		this.currentLevelId = currentLevelId;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getCurrentLevelId() {
		return currentLevelId;
	}

	public void setCurrentLevelId(int currentLevelId) {
		this.currentLevelId = currentLevelId;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public Image getImage() {
		return entityImage;
	}

	public abstract void tick(int elapsedTime);

	public abstract void paint(Graphics g);

	@Override
	public int compareTo(Entity otherEnt) {
		return otherEnt.getY() <= getY() ? 1 : -1;
	}
	
	public int getId(){
		return id;
	}
}
