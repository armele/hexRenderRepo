package com.mele.games.animation;

import java.awt.Image;
import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.mele.games.hex.ui.IHexRenderable;
import com.mele.games.utils.GameException;



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
	protected static boolean autoregistered = false;
	protected static HashMap<String, SpriteFactoryDescriptor> spriteMap = new HashMap<String, SpriteFactoryDescriptor>();
	
	public static void registerSprite(String spriteTag, SpriteFactoryDescriptor descriptor) {
		spriteMap.put(spriteTag, descriptor);
	}
	
	/**
	 * Responsible for examining the classpath for any objects annotated as being SpriteAnimated, 
	 * setting up their sprite descriptor, and registering that descriptor to a tag.
	 * 
	 */
	protected static void autoregister() {
		log.debug("Autoregistering sprites...");
		Reflections reflections = new Reflections("");    
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(SpriteAnimated.class);
		
		for (Class<?> type : classes) {
			SpriteAnimated renderable = type.getAnnotation(SpriteAnimated.class);
			
			if (IHexRenderable.class.isAssignableFrom(type)) {
				SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor(); 
				
				for (SpriteFrame frame : renderable.frames()) {
					sd.addImageFrames(frame.imageName(), frame.frameCount(), frame.frameVariation());
				}
				sd.setAnimationMethod(renderable.animationMethod());
				sd.setTransparency(renderable.transparency());
				sd.setRenderPass(renderable.renderPass());
				
				log.info("Autoregistering " + renderable.spriteTag() + " to:  " + sd);
				
				registerSprite(renderable.spriteTag(), sd);
			} else {
				throw new GameException("A class which does not implement IHexRenderable has been annotated as SpriteAnimated: " + type.getName());
			}
			
		}		
		
		autoregistered = true;
	}
	
	/**
	 * @param component
	 * @param piece
	 * @return
	 */
	public static Sprite letThereBeSprite(IHexRenderable res) {
		if (!autoregistered) {
			autoregister();
		}
		
		Sprite sprite = null;
		SpriteAnimated renderable = res.getClass().getAnnotation(SpriteAnimated.class);

		if (renderable != null) {
			SpriteFactoryDescriptor descriptor = spriteMap.get(renderable.spriteTag());
			
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
		} else {
			// log.debug("No sprite descriptor found for " + res.getClass().getName());
		}
		return sprite;
	}
	
}
