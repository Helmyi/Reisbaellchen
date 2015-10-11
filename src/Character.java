import java.awt.Graphics;
import java.awt.Image;

public class Character extends Entity {
	boolean isMoving;
	int animationStep;
	enum MovingDirection {
		Down(0), Up(1), Right(2), Left(3);
		private int code;

		private MovingDirection(int code) {
			this.code = code;
		}

		public int toInt() {
			return code;
		}
	}

	MovingDirection currentDirection;

	public Character(Image image) {
		super(image);
		isMoving = true;
		animationStep = 0;
		this.tileHeight = 64;
		this.tileWidth = 32;
		this.currentDirection = MovingDirection.Right;
	}

	@Override
	public void tick() {
		if (isMoving) {
			animationStep++;
			//TODO weitere animationen für angriffe etc hinzufügen und "4" durch variable ersetzen
			animationStep %= 4;
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(entityImage, x, y, x + tileWidth, y + tileHeight, animationStep * tileWidth, this.currentDirection.toInt() * tileHeight,
				(animationStep + 1) * tileWidth, (this.currentDirection.toInt() + 1) * tileHeight, null);
	}

}
