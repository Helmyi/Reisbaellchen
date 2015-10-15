package game.actions;

import java.awt.Graphics;

import game.Game;
import game.Unit;

public class Action_Stand extends Action{

	public Action_Stand(Unit owner) {
		super(owner, 1, 0, 0, "Stand");
	}

	@Override
	public void tick() {
	}
	
	@Override
	public void drawCurrentImage(Graphics g){
		int drawX = (int) getOwner().getX() - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointX();
		int drawY = (int) getOwner().getY() - Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointY();

		g.drawImage(getOwner().getImage(), drawX, drawY, drawX + getOwner().getTileWidth(), drawY + getOwner().getTileHeight(),
				0, getOwner().getViewDirection().toInt() * getOwner().getTileHeight(),
				getOwner().getTileWidth(), (getOwner().getViewDirection().toInt() + 1) * getOwner().getTileHeight(), null);
	}

}
