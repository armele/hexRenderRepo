package com.mele.games.animation;

import java.awt.Image;

/**
 * Represents a specific picture to be used in a sequence of animations
 * for a sprite.
 * 
 * @author Ayar
 *
 */
public class AnimationImage {
	/**
	 * Name of the image resource to be displayed.
	 */
	protected String imageName = null;
	
	/**
	 * The loaded image corresponding to the named resource. 
	 */
	protected Image image = null;
	
	/**
	 * The current frame being rendered. 
	 */
	//protected int currentFrame = 0;
	
	/**
	 * The maximum number of fixed frames this image will be rendered. 
	 */
	protected int fixedframes = 1;
	
	/**
	 * The maximum number of additional frames this image might be rendered. 
	 */
	protected int framevariation = 0;
	
	/**
	 * @param fileName
	 * @param frames
	 */
	public void initialize(String fileName, int fixedframes, int framevariation) {
		imageName = fileName;
		this.fixedframes = fixedframes;
		this.framevariation = framevariation;
	}
	
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean isLoaded() {
		return image == null ? false : true;
	}

	/**
	 * @return the frames
	 */
	public int getFixedFrames() {
		return fixedframes;
	}
	
	public int getFrameVariation() {
		return framevariation;
	}
	
	/**
	 * @param frames
	 */
	public void setFixedFrames(int frames) {
		this.fixedframes = frames;
	}
	
	/**
	 * @param frames
	 */
	public void setFrameVariation(int frames) {
		this.framevariation = frames;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnimationImage [imageName=" + imageName + ", image=" + image + ", fixedframes=" + fixedframes
				+ ", framevariation=" + framevariation + "]";
	}
	
}
