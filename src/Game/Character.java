package Game;
import java.awt.Graphics;
import java.awt.Image;

public class Character extends Entity {
	private boolean isMoving;
	private boolean isFighting;
	private int animationStep;
	private int animationStepCount;
	private CharacterAction currentCharacterAction;

	public static enum CharacterAction {
		DOWN(0), UP(1), RIGHT(2), LEFT(3), ATTACK_DOWN(4), ATTACK_UP(5), ATTACK_RIGHT(6), ATTACK_LEFT(7);
		private int code;

		private CharacterAction(int code) {
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
		if (this.isMoving() || this.isFighting()) {
			animationStep++;
			animationStep %= animationStepCount;

			switch (currentCharacterAction) {
			case DOWN:
				y++;
				break;
			case UP:
				y--;
				break;
			case RIGHT:
				x++;
				break;
			case LEFT:
				x--;
				break;
			case ATTACK_DOWN:
				break;
			case ATTACK_UP:
				break;
			case ATTACK_RIGHT:
				break;
			case ATTACK_LEFT:
				break;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(entityImage, (int)x, (int)y, (int)x + tileWidth, (int)y + tileHeight, animationStep * tileWidth,
				this.currentCharacterAction.toInt() * tileHeight, (animationStep + 1) * tileWidth,
				(this.currentCharacterAction.toInt() + 1) * tileHeight, null);
	}

	public void setCharacterAction(CharacterAction action) {
		this.currentCharacterAction = action;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isFighting() {
		return isFighting;
	}

	public void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}

	private void init() {
		isMoving = false;
		isFighting = false;
		animationStep = 0;
		animationStepCount = 4;
		this.tileHeight = 64;
		this.tileWidth = 32;
		this.currentCharacterAction = CharacterAction.DOWN;
	}
}
