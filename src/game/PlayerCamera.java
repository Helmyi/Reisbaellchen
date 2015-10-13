package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PlayerCamera {
	int viewPointX;
	int viewPointY;
	int playerPositionX;
	int playerPositionY;
	int playerTileWidth;
	int playerTileHeight;

	public PlayerCamera(int playerPosX, int playerPosY) {
		this.playerPositionX = playerPosX;
		this.playerPositionY = playerPosY;
		this.playerTileWidth = 32;
		this.playerTileHeight = 64;
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.getGameInstance().getWidth(), Game.getGameInstance().getHeight());

		Graphics2D g2d = (Graphics2D) g;
		Game.getGameInstance().getMap().paint(g2d);

		for (Entity ent : Game.getGameInstance().getEntityList()) {
			ent.paint(g2d);
		}
	}
	
	public void updateViewPointX() {
		if (leftBorderReached()) {
			viewPointX = 0;
		} else {
			adjustViewPointX();
		}
	}

	public void updateViewPointY() {
		if (topBorderReached()) {
			viewPointY = 0;
		} else {
			adjustViewPointY();
		}
	}

	private void adjustViewPointX() {
		if (rightBorderReached()) {
			viewPointX = Game.getGameInstance().getMap().getTileCountX() * Game.getGameInstance().getMap().getTileSize()
					- Game.getGameInstance().getWidth();
		} else {
			viewPointX = (int) this.playerPositionX - Game.getGameInstance().getWidth() / 2 + playerTileWidth / 2;
		}
	}

	private boolean leftBorderReached() {
		return this.playerPositionX < Game.getGameInstance().getWidth() / 2 - playerTileWidth / 2
				|| Game.getGameInstance().getMap().getTileCountX()
						* Game.getGameInstance().getMap().getTileSize() <= Game.getGameInstance().getWidth();
	}

	private boolean rightBorderReached() {
		return this.playerPositionX > Game.getGameInstance().getMap().getTileCountX()
				* Game.getGameInstance().getMap().getTileSize() - Game.getGameInstance().getWidth() / 2
				- playerTileWidth / 2;
	}

	private void adjustViewPointY() {
		if (bottomBorderReached()) {
			viewPointY = Game.getGameInstance().getMap().getTileCountY() * Game.getGameInstance().getMap().getTileSize()
					- Game.getGameInstance().getHeight();
		} else {
			viewPointY = (int) this.playerPositionY - Game.getGameInstance().getHeight() / 2 + playerTileHeight / 2;
		}
	}

	private boolean bottomBorderReached() {
		return this.playerPositionY > Game.getGameInstance().getMap().getTileCountY()
				* Game.getGameInstance().getMap().getTileSize() - Game.getGameInstance().getHeight() / 2
				- playerTileHeight / 2;
	}

	private boolean topBorderReached() {
		return this.playerPositionY < Game.getGameInstance().getHeight() / 2 - playerTileHeight / 2
				|| Game.getGameInstance().getMap().getTileCountY()
						* Game.getGameInstance().getMap().getTileSize() <= Game.getGameInstance().getHeight();
	}

	public int getViewPointX()
	{
		return this.viewPointX;
	}
	
	public int getViewPointY()
	{
		return this.viewPointY;
	}
	
	public void setPlayerPositionX(int x) {
		this.playerPositionX = x;
	}

	public void setPlayerPositionY(int y) {
		this.playerPositionY = y;
	}

	public void setViewPointX(int x) {
		this.viewPointX = x;
	}

	public void setViewPointY(int y) {
		this.viewPointY = y;
	}

	public void setPlayerTileWidth(int width) {
		this.playerTileWidth = width;
	}

	public void setPlayerTileHeight(int height) {
		this.playerTileHeight = height;
	}
}
