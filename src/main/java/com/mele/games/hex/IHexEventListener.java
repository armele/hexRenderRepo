package com.mele.games.hex;

import com.mele.games.hex.ui.HexEventDetail;

/**
 * @author Al Mele
 *
 */
public interface IHexEventListener {
	
	/**
	 * @param eventType
	 * @param eventLocation
	 */
	public void cellEvent(HexEventDetail eventDetail);
	
	/**
	 * Indicates whether or not you want to retain the default hexRender handling of events or not.
	 * 
	 * Default behaviors:
	 * 		On mouse-over, highlight the cell under the pointer.
	 * 		On click, outline the cell under the pointer.
	 * 
	 * @return
	 */
	public boolean keepDefaultBehavior();
}
