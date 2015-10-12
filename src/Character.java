import java.awt.Graphics;
import java.awt.Image;

public class Character extends Entity {
	private boolean isMoving;
	private int animationStep;
	private int animationStepCount;
	private MovingDirection currentDirection;

	enum MovingDirection {
		DOWN(0), UP(1), RIGHT(2), LEFT(3);
		private int code;

		private MovingDirection(int code) {
			this.code = code;
		}

		public int toInt() {
			return code;
		}
	}

	public Character(Image image) {
		super(image);
		init();
	}

	@Override
	public void tick() {
		if (this.isMoving()) {
			animationStep++;
			// TODO weitere animationen für angriffe etc hinzufügen und "4"
			// durch variable ersetzen
			animationStep %= animationStepCount;
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(entityImage, x, y, x + tileWidth, y + tileHeight, animationStep * tileWidth,
				this.currentDirection.toInt() * tileHeight, (animationStep + 1) * tileWidth,
				(this.currentDirection.toInt() + 1) * tileHeight, null);
	}

	public void setMovingDirection(MovingDirection direction) {
		this.currentDirection = direction;
	}

	public boolean isMoving() {
		return isMoving;
	}

	private void init() {
		isMoving = false;
		animationStep = 0;
		animationStepCount = 4;
		this.tileHeight = 64;
		this.tileWidth = 32;
		this.currentDirection = MovingDirection.DOWN;
	}
}
