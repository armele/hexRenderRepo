package com.mele.games.utils.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.net.URL;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.utils.GameException;

/**
 * Inner class to assist with transparency load.
 * 
 * @author Al
 *
 */
class TransparentFilter extends RGBImageFilter {
	protected int transparentColor;
	
	TransparentFilter(int transparency) {
		transparentColor = transparency;
		canFilterIndexColorModel = true;
	}

    public int filterRGB(int x, int y, int rgb) {
		 // the RGB code of the color to turn into "transparent"
		
       if (rgb==transparentColor) {
           return 0x000000ff;
       } else {
           return rgb;
       }
    }
}

public class RenderUtils {
	public static final int DEFAULT_TRANSPARENCY = -65281;
	protected static HashMap<String, Image> imageByResourceName = new HashMap<String, Image>();
	protected static Logger log = LogManager.getLogger(RenderUtils.class);
	
	/**
	 * @param component
	 * @param imageResource
	 * @return
	 */
	public static Image loadImage(String imageResource) {
		return loadImage(imageResource, DEFAULT_TRANSPARENCY);
	}
	
	/**
	 * @param component
	 * @param imageResource
	 * @param transparency
	 * @return
	 */
	public static Image loadImage(String imageResource, int transparency) {
		Image image = imageByResourceName.get(imageResource);
		
		// Skip the image loading if we've already cached it by resource name;
		if (image != null) {
			return image;
		}
		
		if (imageResource != null && imageResource.length() > 0) {
			URL url = RenderUtils.class.getResource(imageResource); 
			
			if (url != null) {
				log.info("Loading image from: " + url.getFile());
				image = transparentLoad(url, transparency);
			} else {
				throw new GameException("No image found for: " + imageResource);
			}
		}
		
		// Cache this image for later use.
		imageByResourceName.put(imageResource, image);
		
		return image;
	}
	
	/**
	 * Creates a transparent image out of the image file 
	 * provided.
	 * 
	 * @param imageFile
	 * @return
	 */
	protected static Image transparentLoad(URL imageFile, int transparentColor) {
		// the original image
		Image original = Toolkit.getDefaultToolkit().getImage(imageFile);

		// the resulting image
		Image transparent
		   = Toolkit.getDefaultToolkit().createImage(
		        new FilteredImageSource(original.getSource(),  new TransparentFilter(transparentColor))
		     );
		
		return transparent;
	}
	
	/**
	 * @param p
	 * @param text
	 * @param g
	 */
	public static void drawPolygonText(Polygon p, String text, Graphics g) {
		Color backup = g.getColor();
		g.setColor(Color.black);
		
		int x = (int)p.getBounds().getMinX() + ((int)p.getBounds().getWidth() / 2) - (g.getFontMetrics().stringWidth(text) / 2);
		int y = (int)p.getBounds().getMinY() + ((int)p.getBounds().getHeight() / 2) + (g.getFontMetrics().getHeight() / 2);

		g.drawString(text, x, y);
		
		g.setColor(backup);
	}
	
	/**
	 * Given a reference location, creates a polygon object with "sides" number of sides, and side length "sideLength"
	 * @param referencePointX
	 * @param referencePointY
	 * @param sides
	 * @param sideLength
	 * @return
	 */
	public static Polygon drawPolygon(int referencePointX, int referencePointY, int sides, int sideLength, double rotationDegree) {
	    Polygon p = new Polygon();
	    
	    /*
	    log.info("rotationDegree: " + rotationDegree 
	    		+ "Cos Rotation: " + cosRotation 
	    		+ ", Cos: " + Math.toRadians(cosRotation) 
	    		+ ", Sin: " + Math.toRadians(rotationDegree));
	    */
	    
		for (int i = 0; i < sides; i++) {
		      p.addPoint(
		    	(int) (referencePointX + 
		    			(sideLength * (
		    					Math.cos(((i * 2 * Math.PI) / sides) + (rotationDegree/180.0 * Math.PI))	
		    					)
		    			)),
		        (int) (referencePointY + 
		        		(sideLength * (
		        				Math.sin(((i * 2 * Math.PI) / sides) + (rotationDegree/180.0 * Math.PI)) 
		        				)
		        		))
		      );
	    }
		
		return p;
	}
}
