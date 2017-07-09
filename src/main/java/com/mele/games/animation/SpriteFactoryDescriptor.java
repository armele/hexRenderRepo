package com.mele.games.animation;

import java.util.ArrayList;

/**
 * @author Al
 * 
 */
public class SpriteFactoryDescriptor {
	protected String spriteClass;
	protected ArrayList<AnimationImage> imageList = new ArrayList<AnimationImage>();
	//protected Properties props = new Properties();
	protected int transparency = -65281; // Default to magenta
	protected ERenderPass renderPass = ERenderPass.MIDDLE;
	protected AnimationMethod animationMethod = AnimationMethod.CYCLE; 
	
	public String getSpriteClass() {
		return spriteClass;
	}

	public void setSpriteClass(String spriteClass) {
		this.spriteClass = spriteClass;
	}

	public void addStateImage(AnimationImage animation) {
		imageList.add(animation);
	}
	
	/**
	 * Indicates how many frames (frameCount) a given image (imageName) is displayed, plus
	 * a variable number of frames from 1 to frameVariation (so that not all sprites of
	 * a given type animate in exactly the same way at the same time).
	 * 
	 * @param imageName
	 * @param frameCount
	 * @param frameVariation
	 */
	public void addImageFrames(String imageName, int frameCount, int frameVariation) {
		AnimationImage ai = new AnimationImage();
		ai.initialize(imageName, frameCount, frameVariation);
		imageList.add(ai);
	}
	
	/**
	 * @return
	 */
	public ArrayList<AnimationImage> getImageList() {
		return imageList;
	}
	
	/**
	 * @return the transparency
	 */
	public int getTransparency() {
		return transparency;
	}

	/**
	 * Allows transparencies to be different for
	 * different sprites.  Default is magenta (255, 0, 255)
	 * 
	 * @param transparency
	 *            the transparency to set
	 */
	public void setTransparency(int transparency) {
		this.transparency = transparency;
	}

	/*
	public Properties getProperties() {
		return props;
	}

	public void setProperties(Properties props) {
		this.props = props;
	}
	*/
	
	public ERenderPass getRenderPass() {
		return renderPass;
	}
	
	public void setRenderPass(ERenderPass pass) {
		renderPass = pass;
	}

	/**
	 * @return the animationMethod
	 */
	public AnimationMethod getAnimationMethod() {
		return animationMethod;
	}

	/**
	 * @param animationMethod the animationMethod to set
	 */
	public void setAnimationMethod(AnimationMethod animationMethod) {
		this.animationMethod = animationMethod;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbuff = new StringBuffer();
		
		sbuff.append("SpriteFactoryDescriptor [spriteClass=" + spriteClass);
		sbuff.append(", transparency=" + transparency);
		sbuff.append(", renderPass=" + renderPass);
		sbuff.append(", animationMethod=" + animationMethod);
		sbuff.append(", imageList={");
		
		for (AnimationImage ai : imageList) {
			if (imageList.get(0).equals(ai)) {
				sbuff.append(",");
			}			
			sbuff.append(ai.toString());
		}
		
		sbuff.append("}");
		sbuff.append("]");
		
		return sbuff.toString();
	}

	
}
