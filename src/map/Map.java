package map;

import Game.Game;
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
	String tilePath;
	int tileSize;
	int tileCountX;
	int tileCountY;
	List<TileInfo> tileInfoList;

	int layers[][];

	public Map(String path) {
		tileSize = 32;
		tilePath = "resources/Zones/TestMap/";
		loadMap(path);
	}

	public void paint(Graphics g) {
		for (int layerNr = 0; layerNr < layers.length; layerNr++) {
			for (int i = 0; i < layers[layerNr].length; i++) {
				int x = i % tileCountX * tileSize + (int)Game.getGameInstance().getViewBegin().getX();
				int y = i / tileCountY * tileSize + (int)Game.getGameInstance().getViewBegin().getY();
				System.out.println("VIEWBEGIN X: " + Game.getGameInstance().getViewBegin().getX());
				System.out.println("VIEWBEGIN X: " + Game.getGameInstance().getViewBegin().getY());
				// tileID=0 => skip
				if (layers[layerNr][i] == 0)
					continue;
				g.drawImage(tileIdToImage(layers[layerNr][i]), x, y, x + tileSize, y + tileSize,
						tileIdToTileX(layers[layerNr][i]) * tileSize, tileIdToTileY(layers[layerNr][i]) * tileSize,
						(tileIdToTileX(layers[layerNr][i]) + 1) * tileSize,
						(tileIdToTileY(layers[layerNr][i]) + 1) * tileSize, null);
			}
		}
	}

	public int getTileCountX() {
		return tileCountX;
	}

	public int getTileCountY() {
		return tileCountY;
	}

	private int tileIdToTileX(int tileID) {
		for (int i = 0; i < tileInfoList.size(); i++) {
			if (tileID >= tileInfoList.get(i).getTileFirstID()
					&& tileID < tileInfoList.get(i).getTileFirstID() + tileInfoList.get(i).getTileCount()) {
				return (tileID - tileInfoList.get(i).getTileFirstID()) % tileInfoList.get(i).getTileImageCulomns();
			}
		}
		System.out.println("Map.tileIdToTileX error " + tileID);
		return 0;
	}

	private int tileIdToTileY(int tileID) {
		for (int i = 0; i < tileInfoList.size(); i++) {
			if (tileID >= tileInfoList.get(i).getTileFirstID()
					&& tileID < tileInfoList.get(i).getTileFirstID() + tileInfoList.get(i).getTileCount()) {
				return (tileID - tileInfoList.get(i).getTileFirstID()) / tileInfoList.get(i).getTileImageCulomns();
			}
		}
		System.out.println("Map.tileIdToTileY error " + tileID);
		return 0;
	}

	private Image tileIdToImage(int tileID) {
		for (int i = 0; i < tileInfoList.size(); i++) {
			if (tileID >= tileInfoList.get(i).getTileFirstID()
					&& tileID < tileInfoList.get(i).getTileFirstID() + tileInfoList.get(i).getTileCount()) {
				return tileInfoList.get(i).getTileImage();
			}
		}
		System.out.println("Map.tileIdToImage error " + tileID);
		return null;
	}

	private void loadMap(String file) {
		try {
			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			// load mapSize
			tileCountX = Integer
					.parseInt(doc.getDocumentElement().getAttributes().getNamedItem("width").getNodeValue().toString());
			tileCountY = Integer.parseInt(
					doc.getDocumentElement().getAttributes().getNamedItem("height").getNodeValue().toString());

			NodeList nList = doc.getElementsByTagName("tileset");
			tileInfoList = new ArrayList<TileInfo>();

			// load tileset info
			for (int i = 0; i < nList.getLength(); i++) {
				Image tileImage;
				int tileWidth = Integer
						.parseInt(nList.item(i).getAttributes().getNamedItem("tilewidth").getNodeValue().toString());
				int tileHeight = Integer
						.parseInt(nList.item(i).getAttributes().getNamedItem("tileheight").getNodeValue().toString());
				int tileFirstID = Integer
						.parseInt(nList.item(i).getAttributes().getNamedItem("firstgid").getNodeValue().toString());
				int tileCount = Integer
						.parseInt(nList.item(i).getAttributes().getNamedItem("tilecount").getNodeValue().toString());
				int tileImageRows = 0;
				int tileImageCulomns = 0;

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String tempImagePath = eElement.getElementsByTagName("image").item(0).getAttributes()
							.getNamedItem("source").getNodeValue().toString();
					tileImageRows = Integer.parseInt(eElement.getElementsByTagName("image").item(0).getAttributes()
							.getNamedItem("height").getNodeValue().toString()) / tileHeight;
					tileImageCulomns = Integer.parseInt(eElement.getElementsByTagName("image").item(0).getAttributes()
							.getNamedItem("width").getNodeValue().toString()) / tileWidth;

					tempImagePath = tilePath + tempImagePath;
					try {
						tileImage = ImageIO.read(new File(tempImagePath));
						tileInfoList.add(new TileInfo(tileImage, tileWidth, tileHeight, tileFirstID, tileCount,
								tileImageRows, tileImageCulomns));
					} catch (IOException e) {
						System.out.println("Map: Image load failed, path: " + tempImagePath);
						e.printStackTrace();
					}
				}

			}
			// load map data
			nList = doc.getElementsByTagName("layer");
			layers = new int[nList.getLength()][tileCountY * tileCountX];

			for (int i = 0; i < nList.getLength(); i++) {
				// Collision layer // TODO, layer with name Collision used for
				// collision and not for drawing
				if ("Collision".equals(nList.item(i).getAttributes().getNamedItem("name").getNodeValue().toString()))
					continue;

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList mapIdList = eElement.getElementsByTagName("tile");
					for (int j = 0; j < mapIdList.getLength(); j++) {
						layers[i][j] = Integer.parseInt(
								mapIdList.item(j).getAttributes().getNamedItem("gid").getNodeValue().toString());
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Map: load MapFile error: " + file);
			e.printStackTrace();
		}
	}
}
