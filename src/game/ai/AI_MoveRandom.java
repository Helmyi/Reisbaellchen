package game.ai;

import java.util.Random;

import game.Unit;

public class AI_MoveRandom extends UnitAI{
	Random random;

	public AI_MoveRandom(Unit controlledUnit) {
		super(controlledUnit);
		random = new Random();
	}

	@Override
	public void tick() {
		int temp;
		for(Unit unit: getControledUnits()){
			temp = random.nextInt()%4;

			//random move direction
			switch(temp){
			case 0:
				unit.setUnitAction(Unit.UnitAction.MOVE_DOWN);
				break;
			case 1:
				unit.setUnitAction(Unit.UnitAction.MOVE_UP);
				break;
			case 2:
				unit.setUnitAction(Unit.UnitAction.MOVE_LEFT);
				break;
			case 3:
				unit.setUnitAction(Unit.UnitAction.MOVE_RIGHT);
				break;
			default:
				break;
			}
			unit.setMoving(random.nextBoolean());
				
			//move on off
		}
	}

}
