package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Class that administrates the viewable content for the player.
 * 
 * @author Dimi
 */

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

	/**
	 * viewPointX and viewPointY are used to calculate the part of the map that
	 * should be painted. Call this function every time before map is painted.
	 */
	public void updateViewPointX() {
		if (leftBorderReached()) {
			viewPointX = 0;
		} else {
			adjustViewPointX();
		}
	}

	/**
	 * viewPointX and viewPointY are used to calculate the part of the map that
	 * should be painted. Call this function every time before map is painted.
	 */
	public void updateViewPointY() {
		if (topBorderReached()) {
			viewPointY = 0;
		} else {
			adjustViewPointY();
		}
	}

	private void adjustViewPointX() {
		if (rightBorderReached()) {
			adjustViewPointXToRightBorder();
		} else {
			adjustViewPointXToPlayerPosition();
		}
	}

	private void adjustViewPointY() {
		if (bottomBorderReached()) {
			adjustViewPointYToBottomBorder();
		} else {
			adjustViewPointYToPlayerPosition();
		}
	}

	private boolean rightBorderReached() {
		return this.playerPositionX > Game.getGameInstance().getMap().getTileCountX()
				* Game.getGameInstance().getMap().getTileSize() - Game.getGameInstance().getWidth() / 2
				- playerTileWidth / 2;
	}

	private void adjustViewPointXToRightBorder() {
		viewPointX = Game.getGameInstance().getMap().getTileCountX() * Game.getGameInstance().getMap().getTileSize()
				- Game.getGameInstance().getWidth();
	}

	private void adjustViewPointXToPlayerPosition() {
		viewPointX = (int) this.playerPositionX - Game.getGameInstance().getWidth() / 2 + playerTileWidth / 2;
	}

	private boolean leftBorderReached() {
		return this.playerPositionX < Game.getGameInstance().getWidth() / 2 - playerTileWidth / 2
				|| Game.getGameInstance().getMap().getTileCountX()
						* Game.getGameInstance().getMap().getTileSize() <= Game.getGameInstance().getWidth();
	}

	private void adjustViewPointYToBottomBorder() {
		viewPointY = Game.getGameInstance().getMap().getTileCountY() * Game.getGameInstance().getMap().getTileSize()
				- Game.getGameInstance().getHeight();
	}

	private void adjustViewPointYToPlayerPosition() {
		viewPointY = (int) this.playerPositionY - Game.getGameInstance().getHeight() / 2 + playerTileHeight / 2;
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

	public int getViewPointX() {
		return this.viewPointX;
	}

	public int getViewPointY() {
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
