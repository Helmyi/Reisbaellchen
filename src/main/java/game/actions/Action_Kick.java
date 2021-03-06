package game.actions;

import game.Game;
import game.Unit;

import java.awt.*;

public class Action_Kick extends Action {
    long lastAnimationStepTime;

    public Action_Kick(Unit owner) {
        super(owner, 2, 2, 4, "Kick");
        lastAnimationStepTime = 0;
    }

    @Override
    public void tick() {
        if (lastAnimationStepTime + 200 < Game.getGameInstance().getGameTime()) {
            lastAnimationStepTime = Game.getGameInstance().getGameTime();
            setCurrentAnimationStep(getCurrentAnimationStep() + 1);
        }
        final java.util.List<Unit> kickedUnits = Game.getGameInstance().getUnitsAt(calculateHitPoint(), getOwner().getCurrentLevelId());
        for (Unit unit : kickedUnits){
        	if(getOwner() == unit) continue;
            unit.takeDamage(5);
        }
    }

    private Point calculateHitPoint(){
        double x=0;
        double y=0;
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.LEFT)){
            x = getOwner().getX();
            y = getOwner().getY()+getOwner().getTileHeight()*0.5;
        }
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.RIGHT)){
            x = getOwner().getX()+getOwner().getTileWidth();
            y = getOwner().getY()+getOwner().getTileHeight()*0.5;
        }
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.UP)){
            x = getOwner().getX()+getOwner().getTileWidth()*0.5;
            y = getOwner().getY();
        }
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.DOWN)){
            x = getOwner().getX()+getOwner().getTileWidth()*0.5;
            y = getOwner().getY()+getOwner().getTileHeight();
        }
        return new Point((int)x,(int)y);
    }

    @Override
    public void drawCurrentImage(Graphics g) {
        basicImagePaint(g);
    }

}
