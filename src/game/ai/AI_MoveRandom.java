package game.ai;

import java.util.Random;

import game.Game;
import game.Unit;

public class AI_MoveRandom extends UnitAI {
	private Random random;
	private int movementRadius;
	private int spawnX;
	private int spawnY;

	public AI_MoveRandom(Unit controlledUnit) {
		super(controlledUnit);
		random = new Random();
		movementRadius = 32 * 4;
	}

	@Override
	public void tick() {
		int temp;
		for (Unit unit : getControledUnits()) {
			temp = random.nextInt() % 4;

			// random move direction
			switch (temp) {
			case 0:
				if (nextMovementStepWithinMovementRadius(unit, Unit.UnitAction.MOVE_DOWN))
					unit.setUnitAction(Unit.UnitAction.MOVE_DOWN);
				break;
			case 1:
				if (nextMovementStepWithinMovementRadius(unit, Unit.UnitAction.MOVE_UP))
					unit.setUnitAction(Unit.UnitAction.MOVE_UP);
				break;
			case 2:
				if (nextMovementStepWithinMovementRadius(unit, Unit.UnitAction.MOVE_LEFT))
					unit.setUnitAction(Unit.UnitAction.MOVE_LEFT);
				break;
			case 3:
				if (nextMovementStepWithinMovementRadius(unit, Unit.UnitAction.MOVE_RIGHT))
					unit.setUnitAction(Unit.UnitAction.MOVE_RIGHT);
				break;
			default:
				break;
			}
			unit.setMoving(random.nextBoolean());

			// move on off
		}
	}

	private boolean nextMovementStepWithinMovementRadius(Unit unit, Unit.UnitAction action) {
		int nextStep;
		if (action == Unit.UnitAction.MOVE_DOWN) {
			nextStep = (int) unit.getY() + (int) unit.getSpeed();
			return nextStep < spawnY + movementRadius;
		} else if (action == Unit.UnitAction.MOVE_UP) {
			nextStep = (int) unit.getY() - (int) unit.getSpeed();
			return nextStep > spawnY - movementRadius;
		} else if (action == Unit.UnitAction.MOVE_LEFT) {
			nextStep = (int) unit.getX() - (int) unit.getSpeed();
			return nextStep > spawnX - movementRadius;
		} else if (action == Unit.UnitAction.MOVE_RIGHT) {
			nextStep = (int) unit.getX() + (int) unit.getSpeed();
			return nextStep < spawnX + movementRadius;
		}
		return false;
	}

	@Override
	public void addUnit(Unit unit) {
		super.addUnit(unit);
		spawnX = (int) unit.getX();
		spawnY = (int) unit.getY();
	}

	public void setMovementRadius(int radius) {
		movementRadius = radius;
	}

	public int getMovementRadius() {
		return movementRadius;
	}
}
