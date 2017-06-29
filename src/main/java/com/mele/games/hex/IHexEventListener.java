package com.mele.games.hex;

import com.mele.games.hex.ui.HexEventDetail;

/**
 * 
 * 
 * @author Al Mele
 *
 */
public interface IHexEventListener {
	

	/**
	 * Provides an indication of some game or interface event which can be handled by a listener
	 * for the purpose of implementing game mechanics.
	 * 
	 * @param eventDetail
	 * @return true if you would like to retain the default behavior for the action (in addition to 
	 * any action you may have implemented in your listener).  Otherwise, false.
	 * 
	 * Default behaviors:
	 * 		On mouse-over, highlight the cell under the pointer.
	 * 		On click, outline the cell under the pointer.
	 */
	public boolean cellEvent(HexEventDetail eventDetail);
	
}
