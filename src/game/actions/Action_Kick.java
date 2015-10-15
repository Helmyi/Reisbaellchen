package game.actions;

import java.awt.Graphics;

import game.Game;
import game.Unit;

public class Action_Kick extends Action{
	long lastAnimationStepTime;
	
	public Action_Kick(Unit owner) {
		super(owner, 2, 2, 4, "Kick");
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
				(getCurrentAnimationStep() + getFirstTileX()) * getOwner().getTileWidth(), 
				(getOwner().getViewDirection().toInt() + getFirstTileY()) * getOwner().getTileHeight(),
				(getCurrentAnimationStep() + getFirstTileX()+1) * getOwner().getTileWidth(),
				(getOwner().getViewDirection().toInt() + 1 + getFirstTileY()) * getOwner().getTileHeight(), null);
	}

}
