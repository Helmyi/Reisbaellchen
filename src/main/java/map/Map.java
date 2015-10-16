package map;


import java.awt.*;
import java.util.List;

public abstract class Map {
	protected String tilePath;
	protected int tileSize;
	protected int tileCountX;
	protected int tileCountY;
	protected List<TileInfo> tileInfoList;

	public Map(String path) {
		tileSize = 32;
		tilePath = "src/main/resources/Zones/TestMap/";
	}

	
	public abstract void paint(Graphics g);

	public int getTileCountX() {
		return tileCountX;
	}

	public int getTileCountY() {
		return tileCountY;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getMapWidth() {
		return tileCountX * tileSize;
	}

	public int getMapHeight() {
		return tileCountY * tileSize;
	}

	public abstract boolean getTileCollision(int tileX, int tileY);

	public boolean getPositionCollision(int posX, int posY) {
		return getTileCollision((posX / tileSize), posY / tileSize);
	}
}
