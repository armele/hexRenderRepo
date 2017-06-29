package com.mele.games.animation;

import java.awt.Image;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.hex.ui.IHexRenderable;



/**
 *  This class will be used to load and initialize images for sprites.
 *  Sprite classes have descriptors (SpriteFactoryDescriptor) which
 *  describe how they are animated.  They also include a list of
 *  AnimationImages, each one of which represents a specific image in
 *  an animation sequence.
 *  
 * @author Al
 *
 */
public class SpriteFactory {
	protected static Logger log = LogManager.getLogger(SpriteFactory.class);
	
	protected static HashMap<String, SpriteFactoryDescriptor> spriteMap = new HashMap<String, SpriteFactoryDescriptor>();
	
	public static void registerSprite(String spriteTag, SpriteFactoryDescriptor descriptor) {
		spriteMap.put(spriteTag, descriptor);
	}
	
	/**
	 * @param component
	 * @param piece
	 * @return
	 */
	public static Sprite letThereBeSprite(IHexRenderable res) {
		SpriteFactoryDescriptor descriptor = spriteMap.get(res.getSpriteTag());
		Sprite sprite = null;
		
		if (descriptor != null) {
			sprite = new Sprite();
			sprite.setDescriptor(descriptor);
			
			for (AnimationImage ai : descriptor.getImageList()) {
				Image image = RenderUtils.loadImage(ai.getImageName(), descriptor.getTransparency());
				
				if (!ai.isLoaded()) {
					ai.setImage(image);
				}
			}
		}

		return sprite;
	}
	
}
