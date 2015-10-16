package game.actions;

import java.awt.Graphics;

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
		basicImagePaint(g);
	}
}
