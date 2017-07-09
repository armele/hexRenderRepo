package com.mele.games.hex.ui;

import java.awt.Color;

/**
 * TODO: Implement an annotation that allows discovery and self-registration of renderable objects.
 * 
 * Desribes anything which can be painted into a hex.
 * 
 * @author Ayar
 *
 */
public interface IHexRenderable {
	// Property key constants
	public static final String PROPKEY_NEWTYPE = "NEWTYPE";
	
	// Property value constants
	public static final String PROPVAL_TRUE = "TRUE";
	public static final String PROPVAL_FALSE = "FALSE";
	
	public Object getProperty(String propname);
	public void setProperty(String propname, Object propvalue);
	

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor();

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor);

}
