package game;

import game.ai.AI_MoveRandom;
import game.ai.UnitAI;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import map.Map;
import map.Map3;

/**
 * contains all Information relevant for a single level
 * 
 * @author Helmi
 *
 */
public class Level {
	private static int idCounter = 0;
	private List<UnitAI> unitAIs;
	private List<Entity> entityList;
	private Map map;
	private String levelname;
	private final int levelId;
	
	public Level(String levelname, String mapPath){
		levelId = idCounter;
		idCounter++;

		unitAIs = new ArrayList<UnitAI>();
		entityList = new ArrayList<Entity>();
		this.levelname = levelname;
		map = new Map3(mapPath);
	}
	
	public void tick(int elapsedTime) {
		for (UnitAI ai : unitAIs) {
			ai.tick();
		}

		Collections.sort(entityList);
		for (Entity ent : entityList) {
			ent.tick(elapsedTime);
		}
	}
	
	public List<UnitAI> getUnitAIs(){
		return unitAIs;
	}
	
	public List<Entity> getEntityList(){
		return entityList;
	}
	
	public Map getMap(){
		return map;
	}
	
	public String getLevelname(){
		return levelname;
	}
	
	public int getLevelId(){
		return levelId;
	}

	public static Level createTestLevel() {
		Level level = new Level("Test Level", "src/main/resources/Zones/TestMap/Wüste6.tmx");
		List<Entity> entityList = level.getEntityList();
		List<UnitAI> unitAIs = level.getUnitAIs();
		List<Image> unitImages = Game.getGameInstance().getImageList();
		int levelId = level.getLevelId();
		
		entityList.add(new Unit(unitImages.get(1), 10 * 32, 5 * 32, levelId, 250));
		entityList.add(new Unit(unitImages.get(1), 11 * 32, 5 * 32, levelId, 250));
		entityList.add(new Unit(unitImages.get(1), 13 * 32, 7 * 32, levelId, 250));
		entityList.add(new Unit(unitImages.get(1), 15 * 32, 7 * 32, levelId, 250));
		entityList.add(new Unit(unitImages.get(1), 15 * 32, 5 * 32, levelId, 250));
		entityList.add(new Unit(unitImages.get(1), 13 * 32, 5 * 32, levelId, 250));

		unitAIs.add(new AI_MoveRandom((Unit) entityList.get(4)));
		unitAIs.get(0).addUnit((Unit) entityList.get(5));


		return level;
	}
}
