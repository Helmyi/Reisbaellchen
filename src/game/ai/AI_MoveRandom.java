package game.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.Unit;

public class AI_MoveRandom extends UnitAI {
	private Random random;
	private int movementRadius;
	private List<Point> unitSpawnPoints;

	public AI_MoveRandom(Unit controlledUnit) {
		super(controlledUnit);
		random = new Random();
		movementRadius = 32 * 2;
	}

	@Override
	public void tick() {
		int temp;
		int i = 0;
		for (Unit unit : getControledUnits()) {
			temp = random.nextInt() % 4;

			// random move direction
			switch (temp) {
			case 0:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.UnitAction.MOVE_DOWN))
					unit.setUnitAction(Unit.UnitAction.MOVE_DOWN);
				break;
			case 1:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.UnitAction.MOVE_UP))
					unit.setUnitAction(Unit.UnitAction.MOVE_UP);
				break;
			case 2:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.UnitAction.MOVE_LEFT))
					unit.setUnitAction(Unit.UnitAction.MOVE_LEFT);
				break;
			case 3:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.UnitAction.MOVE_RIGHT))
					unit.setUnitAction(Unit.UnitAction.MOVE_RIGHT);
				break;
			default:
				break;
			}
			unit.setMoving(random.nextBoolean());
			i++;
		}
	}

	private boolean nextMovementStepWithinMovementRadius(Unit unit, Point spawnPoint, Unit.UnitAction action) {
		int nextStep;
		if (action == Unit.UnitAction.MOVE_DOWN) {
			nextStep = (int) unit.getY() + (int) unit.getSpeed();
			return nextStep < spawnPoint.getY() + movementRadius;
		} else if (action == Unit.UnitAction.MOVE_UP) {
			nextStep = (int) unit.getY() - (int) unit.getSpeed();
			return nextStep > spawnPoint.getY() - movementRadius;
		} else if (action == Unit.UnitAction.MOVE_LEFT) {
			nextStep = (int) unit.getX() - (int) unit.getSpeed();
			return nextStep > spawnPoint.getX() - movementRadius;
		} else if (action == Unit.UnitAction.MOVE_RIGHT) {
			nextStep = (int) unit.getX() + (int) unit.getSpeed();
			return nextStep < spawnPoint.getX() + movementRadius;
		}
		return false;
	}

	@Override
	public void addUnit(Unit unit) {
		super.addUnit(unit);
		if (unitSpawnPoints == null)
			unitSpawnPoints = new ArrayList<Point>();
		unitSpawnPoints.add(new Point((int) unit.getX(), (int) unit.getY()));
	}

	public void setMovementRadius(int radius) {
		movementRadius = radius;
	}

	public int getMovementRadius() {
		return movementRadius;
	}
}
