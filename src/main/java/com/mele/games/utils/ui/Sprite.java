package com.mele.games.utils.ui;

import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.utils.Die;
import com.mele.games.utils.GameException;

/**
 * Used to render an animation sequence described by
 * the descriptor.
 * 
 * @author Ayar
 *
 */
public class Sprite {
	protected static Logger log = LogManager.getLogger(Sprite.class);
	protected SpriteFactoryDescriptor descriptor = null;
	
	/**
	 * An index indicating the current animation image to render.
	 */
	protected int currentImageIndex = 0;
	protected int currentFrame = 0;
	protected int imageFrameCount[] = null;
	
	public Sprite() {
		//	log.debug("Created sprite.");
	}
	
	/**
	 * @return the descriptor
	 */
	public SpriteFactoryDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor the descriptor to set
	 */
	public void setDescriptor(SpriteFactoryDescriptor descriptor) {
		this.descriptor = descriptor;
		generateFrameCounts();
	}
	
	/**
	 * Assigns frame counts to each image in the animation list
	 */
	protected void generateFrameCounts() {
		imageFrameCount = new int[descriptor.getImageList().size()];
		
		int imageindex = 0;
		
		for (AnimationImage ai : descriptor.getImageList()) {
			if (ai.getFrameVariation() > 1) {
				Die d = new Die(ai.getFrameVariation());
				imageFrameCount[imageindex] = ai.getFixedFrames() + d.roll();
			} else {
				imageFrameCount[imageindex] = ai.getFixedFrames();
			}
			imageindex++;
		}
		
		if (descriptor.getAnimationMethod() == AnimationMethod.RANDOM) {
			Die d = new Die(imageFrameCount.length);
			currentImageIndex = d.roll() - 1; 
		}
	}
	
	/**
	 * Animate a sprite by cycling through the images in its image list.
	 * 
	 * @param p
	 * @param g
	 */
	protected void cycleAnimation(Polygon p, Graphics g) {
		ArrayList<AnimationImage> imgList = descriptor.getImageList();
		boolean canRender = false;
		
		if (imgList != null) {			
			if (currentImageIndex >= imgList.size()) {
				currentImageIndex = 0;
			}
			AnimationImage ai = imgList.get(currentImageIndex);
			if (ai != null && ai.getImage() != null) {
				//log.debug("Rendering sprite");
				canRender = g.drawImage(ai.getImage(), (int)p.getBounds().getMinX(),(int) p.getBounds().getMinY(), (int)p.getBounds().getWidth(), (int)p.getBounds().getHeight(), null);
				
				if (canRender) {
					currentFrame++;
					if (currentFrame > imageFrameCount[currentImageIndex]) {
						currentFrame = 0;
						currentImageIndex++;
					}
				}
			}
		}

		if (!canRender) {
			RenderUtils.drawPolygonText(p, "!", g);
		}		
	}
	
	/**
	 * Display a single static image
	 * 
	 * @param p
	 * @param g
	 */
	public void staticAnimation(Polygon p, Graphics g) {
		ArrayList<AnimationImage> imgList = descriptor.getImageList();		
		boolean canRender = false;
		
		AnimationImage ai = imgList.get(currentImageIndex);
		if (ai != null && ai.getImage() != null) {
			//log.debug("Rendering sprite");
			canRender = g.drawImage(ai.getImage(), (int)p.getBounds().getMinX(),(int) p.getBounds().getMinY(), (int)p.getBounds().getWidth(), (int)p.getBounds().getHeight(), null);
		}
		
		if (!canRender) {
			RenderUtils.drawPolygonText(p, "!", g);
		}		
	}
	
	/**
	 * @param p
	 * @param g
	 */
	public void render(Polygon p, Graphics g) {
		if (descriptor != null) {
			if (descriptor.getAnimationMethod() == AnimationMethod.CYCLE) {
				cycleAnimation(p, g);
			} else{
				staticAnimation(p, g);
			}
		} else {
			throw new GameException("Attempt to render sprite with no descriptor.");
		}
	}
	
}
