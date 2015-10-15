package game.actions;

import java.awt.Graphics;

import game.Unit;

public class Action_Punch extends Action{
	long lastAnimationStepTime;
	
	public Action_Punch(Unit owner) {
		super(owner, 2, 0, 4, "Punch");
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
