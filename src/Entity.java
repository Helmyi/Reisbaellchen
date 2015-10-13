import java.awt.Graphics;
import java.awt.Image;

public abstract class Entity {
	protected double x;
	protected double y;
	protected Image entityImage;
	protected int tileWidth;
	protected int tileHeight;

	public Entity(Image image, double x, double y) {
		this.entityImage = image;
		this.x = x;
		this.y = y;
	}

	public Entity(Image image) {
		this.entityImage = image;
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

	public abstract void tick();

	public abstract void paint(Graphics g);
}
