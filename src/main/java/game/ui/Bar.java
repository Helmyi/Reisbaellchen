package game.ui;

import java.awt.Color;

public abstract class Bar {
	protected int barHeight;
	protected int barWidth;
	protected int fillWidth;
	protected Color barColor;

	public Bar(int width, int height, Color color) {
		barWidth = width;
		barHeight = height;
		barColor = color;
		fillWidth = width;
	}

	public int getBarWidth() {
		return barWidth;
	}

	public void setFillWidth(int fillWidth) {
		this.fillWidth = fillWidth;
	}
}
