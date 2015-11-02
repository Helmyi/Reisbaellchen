package game;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.common.io.Resources;

/**
 * handles images loading that each image only is loaded once
 * @author Helmi
 *
 */
public class ImageLoader {
	public static List<Image> images = new ArrayList<Image>();
	public static List<String> imagePaths = new ArrayList<String>();
	
	public static Image getImage(String path){
		Image tempImage = null;
		//search if already loaded
		for(int i=0; i<images.size();i++){
			if(imagePaths.get(i).equals(path)){
				return images.get(i);
			}
		}
		
		//load
		try {
			tempImage = ImageIO.read(Resources.getResource(path));
			images.add(tempImage);
			imagePaths.add(path);
			return tempImage;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//not found
		System.out.println("ImageLoader: Image not found: " + path);
		return null;
	}
	
	/**
	 * remove image from List only if it will no longer be used by new units 
	 * 
	 * @param image
	 */
	public static void removeImage(Image image){
		for(int i=0; i<images.size();i++){
			if(images.get(i) == image){
				images.remove(i);
				imagePaths.remove(i);
				return;
			}
		}
	}

}
