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
		long seed = 1;
		random = new Random(seed);
		movementRadius = 32 * 4; // 4 tiles in every direction
	}

	@Override
	public void tick() {
		int temp;
		int i = 0;
		for (Unit unit : getControledUnits()) {

			temp = random.nextInt() % 4;

			// make sure unit doesn't move until its clear that movementRadius
			// wont be exceeded
			unit.setMoving(false);

			// prevent units from moving too often
			// TODO: find better solution for this
			if (System.currentTimeMillis() % 13 != 0) {
				continue;
			}
			// random move in random direction
			switch (temp) {
			case 0:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.ViewDirection.DOWN)
						&& (unit.getViewDirection() == Unit.ViewDirection.LEFT
								|| unit.getViewDirection() == Unit.ViewDirection.RIGHT)) {
					unit.setViewDirection(Unit.ViewDirection.DOWN);
					unit.setMoving(random.nextBoolean());
				} else
					unit.setMoving(false);
				break;
			case 1:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.ViewDirection.UP)
						&& (unit.getViewDirection() == Unit.ViewDirection.LEFT
								|| unit.getViewDirection() == Unit.ViewDirection.RIGHT)) {
					unit.setViewDirection(Unit.ViewDirection.UP);
					unit.setMoving(random.nextBoolean());
				} else
					unit.setMoving(false);
				break;
			case 2:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.ViewDirection.LEFT)
						&& (unit.getViewDirection() == Unit.ViewDirection.UP
								|| unit.getViewDirection() == Unit.ViewDirection.DOWN)) {
					unit.setViewDirection(Unit.ViewDirection.LEFT);
					unit.setMoving(random.nextBoolean());
				} else
					unit.setMoving(false);
				break;
			case 3:
				if (nextMovementStepWithinMovementRadius(unit, unitSpawnPoints.get(i), Unit.ViewDirection.RIGHT)
						&& (unit.getViewDirection() == Unit.ViewDirection.UP
								|| unit.getViewDirection() == Unit.ViewDirection.DOWN)) {
					unit.setViewDirection(Unit.ViewDirection.RIGHT);
					unit.setMoving(random.nextBoolean());
				} else
					unit.setMoving(false);
				break;
			default:
				break;
			}
			i++;
		}
	}

	private boolean nextMovementStepWithinMovementRadius(Unit unit, Point spawnPoint, Unit.ViewDirection action) {
		int nextStep;
		if (action == Unit.ViewDirection.DOWN) {
			nextStep = (int) unit.getY() + (int) unit.getSpeed();
			return nextStep < spawnPoint.getY() + movementRadius;
		} else if (action == Unit.ViewDirection.UP) {
			nextStep = (int) unit.getY() - (int) unit.getSpeed();
			return nextStep > spawnPoint.getY() - movementRadius;
		} else if (action == Unit.ViewDirection.LEFT) {
			nextStep = (int) unit.getX() - (int) unit.getSpeed();
			return nextStep > spawnPoint.getX() - movementRadius;
		} else if (action == Unit.ViewDirection.RIGHT) {
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
