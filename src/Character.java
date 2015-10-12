import java.awt.Graphics;
import java.awt.Image;



public class Character extends Entity {
	private boolean isMoving;
	private int animationStep;
	private int animationStepCount;
	private CharacterAction currentCharacterAction;

	public static enum CharacterAction {
		DOWN(0), UP(1), RIGHT(2), LEFT(3), PUNCH(4), KICK(5);
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
		if (this.isMoving()) {
			animationStep++;
			// TODO weitere animationen für angriffe etc hinzufügen und "4"
			// durch variable ersetzen
			animationStep %= animationStepCount;
			
			//move forward
			switch(currentCharacterAction){
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
			case PUNCH:
				break;
			case KICK:
				break;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(entityImage, x, y, x + tileWidth, y + tileHeight, animationStep * tileWidth,
				this.currentCharacterAction.toInt() * tileHeight, (animationStep + 1) * tileWidth,
				(this.currentCharacterAction.toInt() + 1) * tileHeight, null);
	}

	public void setMovingDirection(CharacterAction action) {
		this.currentCharacterAction = action;
	}

	public boolean isMoving() {
		return isMoving;
	}
	
	public void setMoving(boolean isMoving){
		this.isMoving = isMoving;
	}
	
	private void init() {
		isMoving = false;
		animationStep = 0;
		animationStepCount = 8;
		this.tileHeight = 64;
		this.tileWidth = 32;
		this.currentCharacterAction = CharacterAction.DOWN;
	}
}
