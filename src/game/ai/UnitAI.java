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
	private List<Unit> controledUnits; 
	
	public UnitAI(Unit controlledUnit) {
		controledUnits = new ArrayList<Unit>();
		controledUnits.add(controlledUnit);
	}
	
	public List<Unit> getControledUnits(){
		return controledUnits;
	}
	
	public void addUnit(Unit unit){
		controledUnits.add(unit);
	}
	
	public abstract void tick();
}
