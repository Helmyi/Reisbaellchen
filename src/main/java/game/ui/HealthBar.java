package game.ui;

import java.awt.Color;
import java.awt.Graphics;

import game.Game;

public class HealthBar extends Bar {

	public HealthBar(int width, int height, Color color) {
		super(width, height, color);

	}

	public void paint(Graphics g, int x, int y) {
		int viewPointX, viewPointY;

		viewPointX = Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointX();
		viewPointY = Game.getGameInstance().getPlayer().getPlayerCamera().getViewPointY();
		g.setColor(Color.BLACK);
		g.drawRect(x - viewPointX, y - viewPointY, barWidth, barHeight);
		g.setColor(barColor);
		g.fillRect(x - viewPointX + 1, y - viewPointY + 1, (int) fillWidth - 2, barHeight - 2);
	}
}
