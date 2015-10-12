import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

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
	int mapWidth;
	int mapHeight;
	Image tiles[];
	int tileFirstID[];
	
	int layers[][];
	
	
	public Map(){
		tileSize = 32;
		tilePath = "resources/Zones/TestMap/";
		loadMap("resources/Zones/TestMap/TestMap.tmx");
	}
	
	
	public void paint(Graphics g){
		for(int layerNr=0; layerNr< layers.length; layerNr++){
			for(int i=0; i< layers[layerNr].length;i++){
				int x = i%mapWidth * tileSize;
				int y = i/mapHeight * tileSize;
			
				//tileID=0 => skip
				if(layers[layerNr][i] == 0) continue;
				
				g.drawImage(tiles[tileIdToImageId(layers[layerNr][i])],
						x, y, x + tileSize, y + tileSize,
						tileIdToTileX(layers[layerNr][i]) * tileSize,
						tileIdToTileY(layers[layerNr][i]) * tileSize,
						(tileIdToTileX(layers[layerNr][i]) + 1) * tileSize,
						(tileIdToTileY(layers[layerNr][i]) + 1) * tileSize,
						null);
			}
		}
	}
	
	private int tileIdToTileX(int tileID){
		for(int i=0;i<tiles.length;i++){
			if(tileID >= tileFirstID[i] && tileID < tileFirstID[i]+18){
				return (tileID-tileFirstID[i])%3;
			}
		}
		System.out.println("Map.tileIdToTileX error " + tileID);
		return 0;
	}
	
	private int tileIdToTileY(int tileID){
		for(int i=0;i<tiles.length;i++){
			if(tileID >= tileFirstID[i] && tileID < tileFirstID[i]+18){
				return (tileID-tileFirstID[i])/3;
			}
		}
		System.out.println("Map.tileIdToTileY error " + tileID);
		return 0;
	}
	
	private int tileIdToImageId(int tileID){
		for(int i=0;i<tiles.length;i++){
			if(tileID >= tileFirstID[i] && tileID < tileFirstID[i]+18){
				return i;
			}
		}
		System.out.println("Map.tileIdToImageId error " + tileID);
		return 0;
	}
	
	private void loadMap(String file){
	   try {
			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
						
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			//load mapSize
			mapWidth = Integer.parseInt(doc.getDocumentElement().getAttributes().getNamedItem("width").getNodeValue().toString());
			mapHeight = Integer.parseInt(doc.getDocumentElement().getAttributes().getNamedItem("height").getNodeValue().toString());

			NodeList nList = doc.getElementsByTagName("tileset");
			tiles = new Image[nList.getLength()];
			tileFirstID = new int[nList.getLength()];
			
			//load tileset info
			for(int i=0; i<nList.getLength(); i++){
				tileFirstID[i] = Integer.parseInt(nList.item(i).getAttributes().getNamedItem("firstgid").getNodeValue().toString());
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String tempImagePath = eElement.getElementsByTagName("image").item(0).getAttributes().getNamedItem("source").getNodeValue().toString();
					tempImagePath = tilePath+tempImagePath;
					try {
						tiles[i] = ImageIO.read(new File(tempImagePath));
					} catch (IOException e) {
						System.out.println("Map: Image load failed, path: " + tempImagePath);
						e.printStackTrace();
					}
				}
			}
			//load map data
			nList = doc.getElementsByTagName("layer");
			layers = new int[nList.getLength()][mapHeight*mapWidth];
			
			for(int i=0; i<nList.getLength(); i++){
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList mapIdList = eElement.getElementsByTagName("tile");
					for(int j=0; j<mapIdList.getLength(); j++){
						layers[i][j] = Integer.parseInt(mapIdList.item(j).getAttributes().getNamedItem("gid").getNodeValue().toString());
					}
				}
			}
			
	    } catch (Exception e) {
	    	System.out.println("Map: load MapFile error: " + file);
	    	e.printStackTrace();
        }
	}
}
