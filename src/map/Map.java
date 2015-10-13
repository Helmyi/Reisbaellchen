package map;

import game.Game;
import game.Player;
import game.PlayerCamera;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Map {
	private String tilePath;
	private int tileSize;
	private int tileCountX;
	private int tileCountY;
	private List<TileInfo> tileInfoList;

	private boolean collision[];
	private int layers[][];

	public Map(String path) {
		tileSize = 32;
		tilePath = "resources/Zones/TestMap/";
		loadMap(path);
	}

	public void paint(Graphics g) {
		Player player = Game.getGameInstance().getPlayer();
		PlayerCamera cam = Game.getGameInstance().getPlayer().getPlayerCamera();
		player.updatePlayerPosition();
		cam.updateViewPointX();
		cam.updateViewPointY();
		
		for (int layerNr = 0; layerNr < layers.length; layerNr++) {
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
				endX = tileCountX - 1;
			if (startY < 0)
				startY = 0;
			if (endY > tileCountX)
				endY = tileCountY - 1;

			for (int xTile = startX; xTile < endX; xTile++) {
				int x = xTile * tileSize
						- (int) cam.getViewPointX();
				for (int yTile = startY; yTile < endY; yTile++) {
					int y = yTile
							* tileSize
							- (int) Game.getGameInstance().getViewBegin()
									.getY();

					// tileID=0 => skip
					if (layers[layerNr][xTile + yTile * tileCountX] == 0)
						continue;
					g.drawImage(tileIdToImage(layers[layerNr][xTile + yTile
							* tileCountX]), x, y, x + tileSize, y + tileSize,
							tileIdToTileX(layers[layerNr][xTile + yTile
									* tileCountX])
									* tileSize,
							tileIdToTileY(layers[layerNr][xTile + yTile
									* tileCountX])
									* tileSize,
							(tileIdToTileX(layers[layerNr][xTile + yTile
									* tileCountX]) + 1)
									* tileSize,
							(tileIdToTileY(layers[layerNr][xTile + yTile
									* tileCountX]) + 1)
									* tileSize, null);
				}
			}
		}
	}

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

	public boolean[] getCollisionArray() {
		return collision;
	}

	public boolean getTileCollision(int tileX, int tileY) {
		if (tileX + tileCountY * tileY >= collision.length
				|| tileX + tileCountY * tileY < 0)
			return true;
		return collision[tileX + tileCountY * tileY];
	}

	public boolean getPositionCollision(int posX, int posY) {
		return getTileCollision((posX / tileSize), posY / tileSize);
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
			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			// load mapSize
			tileCountX = Integer.parseInt(doc.getDocumentElement()
					.getAttributes().getNamedItem("width").getNodeValue()
					.toString());
			tileCountY = Integer.parseInt(doc.getDocumentElement()
					.getAttributes().getNamedItem("height").getNodeValue()
					.toString());

			NodeList nList = doc.getElementsByTagName("tileset");
			tileInfoList = new ArrayList<TileInfo>();

			// load tileset info
			for (int i = 0; i < nList.getLength(); i++) {
				Image tileImage;
				int tileWidth = Integer.parseInt(nList.item(i).getAttributes()
						.getNamedItem("tilewidth").getNodeValue().toString());
				int tileHeight = Integer.parseInt(nList.item(i).getAttributes()
						.getNamedItem("tileheight").getNodeValue().toString());
				int tileFirstID = Integer.parseInt(nList.item(i)
						.getAttributes().getNamedItem("firstgid")
						.getNodeValue().toString());
				int tileCount = Integer.parseInt(nList.item(i).getAttributes()
						.getNamedItem("tilecount").getNodeValue().toString());
				int tileImageRows = 0;
				int tileImageCulomns = 0;

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String tempImagePath = eElement
							.getElementsByTagName("image").item(0)
							.getAttributes().getNamedItem("source")
							.getNodeValue().toString();
					tileImageRows = Integer.parseInt(eElement
							.getElementsByTagName("image").item(0)
							.getAttributes().getNamedItem("height")
							.getNodeValue().toString())
							/ tileHeight;
					tileImageCulomns = Integer.parseInt(eElement
							.getElementsByTagName("image").item(0)
							.getAttributes().getNamedItem("width")
							.getNodeValue().toString())
							/ tileWidth;

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
				}

			}
			// load map data
			nList = doc.getElementsByTagName("layer");
			layers = new int[nList.getLength()][tileCountY * tileCountX];
			collision = new boolean[tileCountY * tileCountX];

			for (int i = 0; i < nList.getLength(); i++) {
				// Collision layer // TODO, layer with name Collision used for
				// collision and not for drawing
				if ("Collision".equals(nList.item(i).getAttributes()
						.getNamedItem("name").getNodeValue().toString())) {
					Node nNode = nList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						NodeList mapIdList = eElement
								.getElementsByTagName("tile");
						for (int j = 0; j < mapIdList.getLength(); j++) {
							if (Integer.parseInt(mapIdList.item(j)
									.getAttributes().getNamedItem("gid")
									.getNodeValue().toString()) == 0) {
								collision[j] = false;
							} else {
								collision[j] = true;
							}
						}
					}
					continue;
				}

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList mapIdList = eElement.getElementsByTagName("tile");
					for (int j = 0; j < mapIdList.getLength(); j++) {
						layers[i][j] = Integer.parseInt(mapIdList.item(j)
								.getAttributes().getNamedItem("gid")
								.getNodeValue().toString());
					}
				}
			}

			System.out.println("Finished loading Map, time: "
					+ (System.currentTimeMillis() - loadingMapStartTime)
					/ 1000.0 + "sec");
		} catch (Exception e) {
			System.out.println("Map: load MapFile error: " + file);
			e.printStackTrace();
		}
	}
}
