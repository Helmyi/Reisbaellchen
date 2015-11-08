package game.hero;

import java.awt.Image;

import game.Unit;

public abstract class Hero extends Unit{

	public Hero(Image image, double speed) {
		super(image, 0, 0, 0, speed);
	}
}
