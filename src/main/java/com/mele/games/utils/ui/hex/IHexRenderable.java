package com.mele.games.utils.ui.hex;

/**
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
	
	public String getSpriteTag();
	public Object getProperty(String propname);
	public void setProperty(String propname, Object propvalue);
}
