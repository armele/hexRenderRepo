package com.mele.games.utils.hexarray;

import java.awt.Color;

import com.mele.games.utils.ui.hex.IHexRenderable;

/**
 * The interface for describing a Cell type
 * 
 * @author Ayar
 *
 */
public interface ICellType {
	/**
	 * @param backgroundImage the backgroundImage to set
	 */
	public void setBackgroundImage(IHexRenderable backgroundImage);

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor();

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor);
	
	/**
	 * @return the backgroundImage
	 */
	public IHexRenderable getBackgroundImage();
	
}
