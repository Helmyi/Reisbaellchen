package map;

import java.awt.Image;

public class TileInfo {
	private Image tileImage;
	private int tileWidth;
	private int tileHeight;
	private int tileFirstID;
	private int tileCount;
	private int tileImageRows;
	private int tileImageCulomns;
	
	public TileInfo(Image tileImage, int tileWidth, int tileHeight, int tileFirstID, int tileCount, int tileImageRows, int tileImageCulomns){
		this.tileImage = tileImage;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileFirstID = tileFirstID;
		this.tileCount = tileCount;
		this.tileImageRows = tileImageRows;
		this.tileImageCulomns = tileImageCulomns;
	}
	
	public Image getTileImage(){
		return tileImage;
	}
	
	public int getTileFirstID(){
		return tileFirstID;
	}
	public
	int getTileCount(){
		return tileCount;
	}
	
	public int getTileImageRows(){
		return tileImageRows;
	}
	
	public int getTileImageCulomns(){
		return tileImageCulomns;
	}
	
	public int getTileWidth(){
		return tileWidth;
	}
	
	public int getTileHeight(){
		return tileHeight;
	}
}
