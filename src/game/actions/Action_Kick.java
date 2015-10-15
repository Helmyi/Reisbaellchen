package game.actions;

import java.awt.Graphics;
import game.Unit;

public class Action_Kick extends Action{
	long lastAnimationStepTime;
	
	public Action_Kick(Unit owner) {
		super(owner, 2, 2, 4, "Kick");
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
