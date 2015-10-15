package game;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Entity implements Comparable<Entity>{
	protected double x;
	protected double y;
	protected Image entityImage;
	protected int tileWidth;
	protected int tileHeight;

	public Entity(Image image, double x, double y) {
		this.entityImage = image;
		this.tileHeight = image.getHeight(null);
		this.tileWidth = image.getWidth(null);
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public abstract void tick();

	public abstract void paint(Graphics g);

	@Override
	public int compareTo(Entity otherEnt) {
		return otherEnt.getY() <= getY() ? 1 : -1;
	}
}
