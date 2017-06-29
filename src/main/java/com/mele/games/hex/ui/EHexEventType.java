package com.mele.games.hex.ui;

/**
 * @author Al Mele
 *
 */
public enum EHexEventType {
	MOUSE_LEFTCLICK (EHexEventCategory.MOUSECLICK),
	MOUSE_RIGHTCLICK (EHexEventCategory.MOUSECLICK),
	MOUSE_CENTERCLICK (EHexEventCategory.MOUSECLICK),
	MOUSE_RELEASED (EHexEventCategory.MOUSECLICK),	
	MOUSE_ENTERED (EHexEventCategory.MOUSEMOVE),
	MOUSE_EXITED (EHexEventCategory.MOUSEMOVE),
	MOUSE_WINEXIT (EHexEventCategory.MOUSEMOVE);
	
	protected EHexEventCategory eventCategory = EHexEventCategory.UNDEFINED;
	
	private EHexEventType (EHexEventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}
	
	public EHexEventCategory getEventCategory() {
		return this.eventCategory;
	}
}
