package game.ui;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Bar {
	protected int maxValue;
	protected int currentValue;
	protected int barHeight;
	protected int barWidth;
	
	Color color;

	public Bar(int maxValue) {
		this.maxValue = maxValue;
		this.currentValue = maxValue;
	}

	public void decreaseValue(int value) {
		this.maxValue -= value;
	}

	public void increaseValue(int value) {
		this.maxValue += value;
	}

	public abstract void paint(Graphics g);

	public abstract void updateBar();
	
	public abstract void tick();
}
