package com.mele.games.hex.ui;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.hex.IHexEventListener;

/**
 * Listens for mouse clicks on the display window and queues them
 * as commands to the game.
 * 
 * @author Ayar
 *
 */
public class HexInputListener implements MouseListener, MouseMotionListener, ComponentListener {
	protected static Logger log = LogManager.getLogger(HexInputListener.class);
	
	protected CellRenderer currentInputCell = null;
	protected HexArrayController hexController = null;
	protected HexInputState inputState = new HexInputState();
	
	public HexInputListener(HexArrayController controller) {
		hexController = controller;
	}
	
	/**
	 * Provides a consistent way to send an event, and echo that event to the default listener (if required).
	 * 
	 * @param eventDetail
	 */
	protected void sendEvent(HexEventDetail eventDetail) {
		IHexEventListener listener = hexController.getHexEventListener();	
		eventDetail.setInputState(inputState);
		boolean useDefault = listener.cellEvent(eventDetail);
		
		if (useDefault) {
			hexController.getHexDefaultListener().cellEvent(eventDetail);
		}		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (MouseEvent.BUTTON1 == arg0.getButton()) {
			inputState.setLeftMousedown(true);
		}
		if (MouseEvent.BUTTON2 == arg0.getButton()) {
			inputState.setRightMousedown(true);
		}		
		
		Point p = arg0.getPoint();
		log.debug(p);
		HexArrayRenderer hr = hexController.getView();
		CellRenderer target = hr.getCellAtDisplayPoint(p);
		
		if (target != null) {
			HexEventDetail eventDetail = new HexEventDetail(target, arg0.getButton());
			sendEvent(eventDetail);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (MouseEvent.BUTTON1 == arg0.getButton()) {
			inputState.setLeftMousedown(false);
		}
		if (MouseEvent.BUTTON2 == arg0.getButton()) {
			inputState.setRightMousedown(false);
		}
		
		HexEventDetail eventDetail = new HexEventDetail(null, arg0.getButton());
		eventDetail.setEventType(EHexEventType.MOUSE_RELEASED);
		sendEvent(eventDetail);		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		currentInputCell = null;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {

	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// Reintialize the hex map if the component is resized.
		hexController.init();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseMoved(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// As the mouse moves, record the cell over which it is positioned and
		// flag that cell as being "hovered" over.
		// Trigger the onCellChange() of the controller.
		boolean cellchange = false;
		Point p = arg0.getPoint();

		HexArrayRenderer hr = hexController.getView();
		CellRenderer newCell = hr.getCellAtDisplayPoint(p);
		CellRenderer oldCell = currentInputCell;
		
		if (newCell != null) {

			if (!newCell.equals(oldCell)) {
				cellchange = true;
			}
			
			currentInputCell = newCell;
			
			if (cellchange) {
				HexEventDetail edOld = new HexEventDetail(oldCell);
				edOld.setEventType(EHexEventType.MOUSE_EXITED);
				sendEvent(edOld);
				
				HexEventDetail edNew = new HexEventDetail(newCell);
				edNew.setEventType(EHexEventType.MOUSE_ENTERED);
				sendEvent(edNew);
			}
		} else {
			if (oldCell != null) {
				HexEventDetail edOld = new HexEventDetail(oldCell);
				edOld.setEventType(EHexEventType.MOUSE_EXITED);
				sendEvent(edOld);
			}
		}
	}

}
