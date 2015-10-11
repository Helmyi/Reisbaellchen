import java.awt.Graphics;
import java.awt.Image;

public abstract class Entity {
	int x;
	int y;
	Image entityImage;
	
	public Entity(Image image)
	{
		this.entityImage = image;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	//TODO: sinnvollen namen/funktion finden
	public abstract void tick();
	public abstract void paint(Graphics g);
}
