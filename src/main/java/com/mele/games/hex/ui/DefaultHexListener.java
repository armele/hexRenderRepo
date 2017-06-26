package com.mele.games.hex.ui;

import com.mele.games.hex.IHexEventListener;

/**
 * @author Al Mele
 *
 */
public class DefaultHexListener implements IHexEventListener {
	protected HexArrayController hexController = null;
	
	public DefaultHexListener(HexArrayController controller) {
		hexController = controller;
	}
	
	@Override
	public void cellEvent(HexEventDetail eventDetail) {
		CellRenderer location = eventDetail.getEventLocation();
		
		if (location != null) {
		
			switch (eventDetail.getEventType()) {
			case MOUSE_ENTERED:
				location.setHovered(true);
				
				// Supports "drag selection" (if the left mouse is down when moving into a cell, select this new cell
				if (eventDetail.getInputState().isLeftMousedown()) {
					location.setSelected(true);
				}
				
				break;
				
			case MOUSE_EXITED:
				location.setHovered(false);			
				break;
				
			case MOUSE_LEFT:
				// Toggle selection on left click.
				location.setSelected(!location.isSelected());
				break;
				
			default:
				break;
			}

		}
	}
	
	/* (non-Javadoc)
	 * @see com.mele.games.hex.IHexEventListener#keepDefaultBehavior()
	 * 
	 * Perhaps it seems unintuitive to have the setting "keepDefaultBehavior" false in a class called DefaultHexListener -
	 * but it makse sense! (Really!)  This class is providing the default behavior, and if this setting were "true" there
	 * would be a double-echo if no user listener is set.
	 * 
	 */
	@Override
	public boolean keepDefaultBehavior() {
		return false;
	}

}
