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
	public boolean cellEvent(HexEventDetail eventDetail) {
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
	
		// This class implements the default behavior; there is no further default behavior to be triggered.
		return false;
	}

}
