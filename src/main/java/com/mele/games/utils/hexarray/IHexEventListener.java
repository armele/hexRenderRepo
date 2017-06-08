package com.mele.games.utils.hexarray;

/**
 * @author Al Mele
 *
 */
public interface IHexEventListener {
	
	/**
	 * @param eventType
	 * @param eventLocation
	 */
	public void cellEvent(IHexEvent eventDetail, HexCell eventLocation);
}
