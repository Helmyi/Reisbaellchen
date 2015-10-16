package game.actions;

import game.Game;
import game.Unit;

import java.awt.*;

public class Action_Punch extends Action {
    long lastAnimationStepTime;

    public Action_Punch(Unit owner) {
        super(owner, 2, 0, 4, "Punch");
        lastAnimationStepTime = System.currentTimeMillis();
    }

    @Override
    public void tick() {
        if (lastAnimationStepTime + 200 < System.currentTimeMillis()) {
            lastAnimationStepTime = System.currentTimeMillis();
            setCurrentAnimationStep(getCurrentAnimationStep() + 1);
        }

        final java.util.List<Unit> punchedUnits = Game.getGameInstance().getUnitsAt(calculateHitPoint());
        for (Unit unit : punchedUnits){
            unit.takeDamage(2);
        }
    }

    private Point calculateHitPoint() {
        double x = 0;
        double y = 0;
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.LEFT)) {
            x = getOwner().getX();
            y = getOwner().getY() + getOwner().getTileHeight() * 0.5;
        }
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.RIGHT)) {
            x = getOwner().getX() + getOwner().getTileWidth();
            y = getOwner().getY() + getOwner().getTileHeight() * 0.5;
        }
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.UP)) {
            x = getOwner().getX() + getOwner().getTileWidth() * 0.5;
            y = getOwner().getY();
        }
        if (getOwner().getViewDirection().equals(Unit.ViewDirection.DOWN)) {
            x = getOwner().getX() + getOwner().getTileWidth() * 0.5;
            y = getOwner().getY() + getOwner().getTileHeight();
        }
        return new Point((int) x, (int) y);
    }

    @Override
    public void drawCurrentImage(Graphics g) {
        basicImagePaint(g);
    }

}
