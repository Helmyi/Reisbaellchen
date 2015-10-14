package game.ai;

import java.util.ArrayList;
import java.util.List;

import game.Unit;

/**
 * Abstract class for the AI control of one ore more Units
 * 
 * @author Helmi
 */
public abstract class UnitAI{
	private List<Unit> controlledUnits; 
	
	public UnitAI(Unit controlledUnit) {
		controlledUnits = new ArrayList<Unit>();
		controlledUnits.add(controlledUnit);
	}
	
	public List<Unit> getControledUnits(){
		return controlledUnits;
	}
	
	public void addUnit(Unit unit){
		controlledUnits.add(unit);
	}
	
	public abstract void tick();
}
