package game.actions;

import java.awt.Graphics;

import game.Unit;

public class Action_Move extends Action{
	long lastAnimationStepTime;
		
	public Action_Move(Unit owner) {
		super(owner, 4, 0, 0, "Move");
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
		basicImagePaint(g);
	}

}
