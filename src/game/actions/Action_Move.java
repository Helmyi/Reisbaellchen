package game.actions;

import java.awt.Graphics;

import game.Game;
import game.Unit;

public class Action_Move extends Action{
	long lastAnimationStepTime;
		
	public Action_Move(Unit owner) {
		super(owner, 4, 0, 0, "Move");
		lastAnimationStepTime = System.currentTimeMillis();
	}

	@Override
	public void tick() {
		if(lastAnimationStepTime+200 < System.currentTimeMillis()){
			lastAnimationStepTime = System.currentTimeMillis();
			setCurrentAnimationStep(getCurrentAnimationStep() + 1);
		}
	}
	
	@Override
	public void drawCurrentImage(Graphics g){
		int drawX = (int) getOwner().getX() - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointX();
		int drawY = (int) getOwner().getY() - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointY();

		g.drawImage(getOwner().getImage(), drawX, drawY, drawX + getOwner().getTileWidth(), drawY + getOwner().getTileHeight(),
				getCurrentAnimationStep() * getOwner().getTileWidth(),
				getOwner().getViewDirection().toInt() * getOwner().getTileHeight(),
				(getCurrentAnimationStep() + 1) * getOwner().getTileWidth(),
				(getOwner().getViewDirection().toInt() + 1) * getOwner().getTileHeight(), null);
	}

}
