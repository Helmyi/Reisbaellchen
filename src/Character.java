import java.awt.Graphics;
import java.awt.Image;

public class Character extends Entity{

	public Character(Image image) {
		super(image);
		this.tileHeight = 64;
		this.tileWidth = 32;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(entityImage, x, y, x + tileWidth, y + tileHeight, 0, 0, tileWidth, tileHeight, null);
	}
	
}
