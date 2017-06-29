package com.mele.games.hex.ui;

/**
 * @author Al Mele
 *
 */
public enum EHexEventType {
	MOUSE_LEFT (EHexEventCategory.MOUSECLICK),
	MOUSE_RIGHT (EHexEventCategory.MOUSECLICK),
	MOUSE_CENTER (EHexEventCategory.MOUSECLICK),
	MOUSE_RELEASED (EHexEventCategory.MOUSECLICK),	
	MOUSE_ENTERED (EHexEventCategory.MOUSEMOVE),
	MOUSE_EXITED (EHexEventCategory.MOUSEMOVE);
	
	protected EHexEventCategory eventCategory = EHexEventCategory.UNDEFINED;
	
	private EHexEventType (EHexEventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}
	
	public EHexEventCategory getEventCategory() {
		return this.eventCategory;
	}
}
