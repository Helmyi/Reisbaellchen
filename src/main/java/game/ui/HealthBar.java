package game.ui;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar extends Bar {
	private double percentage;
	private double fillWidth;
	public HealthBar(int maxValue) {
		super(maxValue);
		this.color = Color.GREEN;
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.barWidth, this.barHeight);
		g.setColor(color);
		g.fillRect(1, 1, (int)this.fillWidth - 1, this.barHeight - 1);
	}

	public void updateBar() {
		this.percentage = this.currentValue / this.maxValue;
		this.fillWidth = this.percentage * this.barWidth;
	}

	public void tick() {
		updateBar();
	}
}
