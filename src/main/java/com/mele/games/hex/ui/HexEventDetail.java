package com.mele.games.hex.ui;

import java.awt.event.MouseEvent;

/**
 * @author Al Mele
 *
 */
public class HexEventDetail {
	protected CellRenderer eventLocation;
	protected EHexEventType eventType;
	protected HexInputState inputState = null;
	
	/**
	 * 
	 */
	public HexEventDetail(CellRenderer eventLocation) {
		this.eventLocation = eventLocation;
	}
	
	/**
	 * @param eventLocation
	 * @param mouseButton
	 */
	public HexEventDetail(CellRenderer eventLocation, int mouseButton) {
		this.eventLocation = eventLocation;
		
		switch (mouseButton) {
		case MouseEvent.BUTTON1:
			eventType = EHexEventType.MOUSE_LEFTCLICK;
			break;
			
		case MouseEvent.BUTTON2:
			eventType = EHexEventType.MOUSE_RIGHTCLICK;
			break;
			
		case MouseEvent.BUTTON3:
			eventType = EHexEventType.MOUSE_CENTERCLICK;
			break;	
		}
		
	}
	
	/**
	 * @return the eventLocation
	 */
	public CellRenderer getEventLocation() {
		return eventLocation;
	}
	/**
	 * @param eventLocation the eventLocation to set
	 */
	protected void setEventLocation(CellRenderer eventLocation) {
		this.eventLocation = eventLocation;
	}
	/**
	 * @return the eventType
	 */
	public EHexEventType getEventType() {
		return eventType;
	}
	/**
	 * @param eventType the eventType to set
	 */
	protected void setEventType(EHexEventType eventType) {
		this.eventType = eventType;
	}
	
	/**
	 * @return the inputState
	 */
	public HexInputState getInputState() {
		return inputState;
	}

	/**
	 * @param inputState the inputState to set
	 */
	public void setInputState(HexInputState inputState) {
		this.inputState = inputState;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(eventLocation);
		sb.append(eventType);
		
		return sb.toString();
	}
	
	
}
