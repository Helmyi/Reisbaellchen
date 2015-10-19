package game.actions;

import java.awt.Graphics;

import game.Game;
import game.Unit;

public class Action_Move extends Action{
	long lastAnimationStepTime;
		
	public Action_Move(Unit owner) {
		super(owner, 4, 0, 0, "Move");
		lastAnimationStepTime = 0;
	}

	@Override
	public void tick() {
		if(lastAnimationStepTime+200 < Game.getGameInstance().getGameTime()){
			lastAnimationStepTime = Game.getGameInstance().getGameTime();
			setCurrentAnimationStep(getCurrentAnimationStep() + 1);
		}
	}
	
	@Override
	public void drawCurrentImage(Graphics g){
		basicImagePaint(g);
	}

}
