package map;

import game.Game;
import game.PlayerCamera;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class Map3 extends Map{

	private boolean collision[];
	private List<int[]> layers;

	public Map3(String path) {
		super(path);
		loadMap(path);
	}

	@Override
	public void paint(Graphics g) {
		PlayerCamera cam = Game.getGameInstance().getPlayer().getPlayerCamera();
		for (int layerNr = 0; layerNr < layers.size(); layerNr++) {
			int startX = (int) cam.getViewPointX()
					/ tileSize - 2;
			int endX = (int) cam.getViewPointX()
					/ tileSize + Game.getGameInstance().getWidth() / tileSize
					+ 2;
			int startY = (int) cam.getViewPointY()
					/ tileSize - 2;
			int endY = (int) cam.getViewPointY()
					/ tileSize + Game.getGameInstance().getHeight() / tileSize
					+ 2;

			if (startX < 0)
				startX = 0;
			if (endX > tileCountX)
				endX = tileCountX;
			if (startY < 0)
				startY = 0;
			if (endY > tileCountX)
				endY = tileCountY;

			for (int xTile = startX; xTile < endX; xTile++) {
				int x = xTile * tileSize
						- (int) cam.getViewPointX();
				for (int yTile = startY; yTile < endY; yTile++) {
					int y = yTile
							* tileSize
							- (int) cam.getViewPointY();

					// tileID=0 => skip
					if (layers.get(layerNr)[xTile + yTile * tileCountX] == 0)
						continue;
					g.drawImage(tileIdToImage(layers.get(layerNr)[xTile + yTile
							* tileCountX]), x, y, x + tileSize, y + tileSize,
							tileIdToTileX(layers.get(layerNr)[xTile + yTile
									* tileCountX])
									* tileSize,
							tileIdToTileY(layers.get(layerNr)[xTile + yTile
									* tileCountX])
									* tileSize,
							(tileIdToTileX(layers.get(layerNr)[xTile + yTile
									* tileCountX]) + 1)
									* tileSize,
							(tileIdToTileY(layers.get(layerNr)[xTile + yTile
									* tileCountX]) + 1)
									* tileSize, null);
				}
			}
		}
	}

	@Override
	public boolean getTileCollision(int tileX, int tileY) {
		if (tileX + tileCountY * tileY >= collision.length
				|| tileX + tileCountY * tileY < 0)
			return true;
		return collision[tileX + tileCountY * tileY];
	}

	private int tileIdToTileX(int tileID) {
		for (int i = 0; i < tileInfoList.size(); i++) {
			if (tileID >= tileInfoList.get(i).getTileFirstID()
					&& tileID < tileInfoList.get(i).getTileFirstID()
							+ tileInfoList.get(i).getTileCount()) {
				return (tileID - tileInfoList.get(i).getTileFirstID())
						% tileInfoList.get(i).getTileImageCulomns();
			}
		}
		System.out.println("Map.tileIdToTileX error " + tileID);
		return 0;
	}

	private int tileIdToTileY(int tileID) {
		for (int i = 0; i < tileInfoList.size(); i++) {
			if (tileID >= tileInfoList.get(i).getTileFirstID()
					&& tileID < tileInfoList.get(i).getTileFirstID()
							+ tileInfoList.get(i).getTileCount()) {
				return (tileID - tileInfoList.get(i).getTileFirstID())
						/ tileInfoList.get(i).getTileImageCulomns();
			}
		}
		System.out.println("Map.tileIdToTileY error " + tileID);
		return 0;
	}

	private Image tileIdToImage(int tileID) {
		for (int i = 0; i < tileInfoList.size(); i++) {
			if (tileID >= tileInfoList.get(i).getTileFirstID()
					&& tileID < tileInfoList.get(i).getTileFirstID()
							+ tileInfoList.get(i).getTileCount()) {
				return tileInfoList.get(i).getTileImage();
			}
		}
		System.out.println("Map.tileIdToImage error " + tileID);
		return null;
	}

	private void loadMap(String file) {
		try {
			System.out.println("Beginn loading Map.");
			long loadingMapStartTime = System.currentTimeMillis();
			tileInfoList = new ArrayList<TileInfo>();
			layers = new ArrayList<int[]>();
			
			File fXmlFile = new File(file);
			Charset ENCODING = StandardCharsets.UTF_8;
			try (BufferedReader reader = Files.newBufferedReader(fXmlFile.toPath(), ENCODING)) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					if(line.contains("<map")){
						tileCountX = Integer.parseInt(getValueOfLineAttribute(line, "width"));
						tileCountY = Integer.parseInt(getValueOfLineAttribute(line, "height"));
						collision = new boolean[tileCountX*tileCountY];
						continue;
					}

					//load tileset info
					if(line.contains("<tileset")){
						Image tileImage;
						int tileWidth = Integer.parseInt(getValueOfLineAttribute(line, "tilewidth"));
						int tileHeight = Integer.parseInt(getValueOfLineAttribute(line, "tileheight"));
						int tileFirstID = Integer.parseInt(getValueOfLineAttribute(line, "firstgid"));
						int tileCount = Integer.parseInt(getValueOfLineAttribute(line, "tilecount"));
						int tileImageRows = 0;
						int tileImageCulomns = 0;
						
						if(!line.contains("<image"))line = reader.readLine();
						String tempImagePath = getValueOfLineAttribute(line, "source");
						tileImageRows = Integer.parseInt(getValueOfLineAttribute(line, "height"))/tileHeight;
						tileImageCulomns = Integer.parseInt(getValueOfLineAttribute(line, "width"))/tileWidth;
						tempImagePath = tilePath + tempImagePath;
								
						try {
							tileImage = ImageIO.read(new File(tempImagePath));
							tileInfoList.add(new TileInfo(tileImage, tileWidth,
									tileHeight, tileFirstID, tileCount,
									tileImageRows, tileImageCulomns));
						} catch (IOException e) {
							System.out.println("Map: Image load failed, path: "
								+ tempImagePath);
							e.printStackTrace();
						}
						continue;
					}
					//load map info
					if(line.contains("<data>")){
						int data[] = new int[tileCountX*tileCountY];
						int i=0;
						while ((line = reader.readLine()) != null && line.contains("<tile")) {
							if(i >= data.length){
								System.out.println("Map3 ma load error, more data than specified ?");
								break;
							}
							data[i] = Integer.parseInt(getValueOfLineAttribute(line, "gid"));
							i++;
						}
						
						layers.add(data);
						continue;
					}
				}
				reader.close();
				System.gc();
			}

			System.out.println("Finished loading Map, time: "
					+ (System.currentTimeMillis() - loadingMapStartTime)
					/ 1000.0 + "sec");
		} catch (Exception e) {
			System.out.println("Map: load MapFile error: " + file);
			e.printStackTrace();
		}
	}
	
	private String getValueOfLineAttribute(String line, String attributeName){
		if(line.contains(attributeName + "=\"")){
			String temp = line.substring(line.indexOf(attributeName + "=\"") + attributeName.length() + 2);
			temp = temp.substring(0,temp.indexOf("\""));
			return temp;
		}else{
			System.out.println("Map3 loading error: lineAttribute not found, att: " + attributeName + ", line:" + line);
		}
		return "";
	}
}
