package com.mele.games.utils.ui.hex;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listens for mouse clicks on the display window and queues them
 * as commands to the game.
 * 
 * @author Ayar
 *
 */
public class HexInputListener implements MouseListener, MouseMotionListener, ComponentListener {
	protected static Logger log = LogManager.getLogger(HexInputListener.class);
	
	protected HexArrayController hexController = null;
	
	public HexInputListener(HexArrayController controller) {
		hexController = controller;
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO: Differentiate between right-clicks and left clicks
		Point p = arg0.getPoint();
		log.debug(p);
		HexArrayRenderer hr = hexController.getView();
		CellRenderer target = hr.getCellAtDisplayPoint(p);
		
		if (target != null) {
			if (hexController.getHexEventListener() != null) {
				hexController.getHexEventListener().cellEvent(eventDetail, target.getCell());
			} else {
				target.setSelected(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		hexController.clearSelection();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Clear all selections and hovering when the mouse leaves the window.
		hexController.clearSelection();
		if (hexController.getCurrentCell() != null) {
			hexController.getCurrentCell().setHovered(false);
		}		
		hexController.setCurrentCell(null);
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

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// As the mouse moves, record the cell over which it is positioned and
		// flag that cell as being "hovered" over.
		// Trigger the onCellChange() of the controller.
		boolean cellchange = false;
		Point p = arg0.getPoint();

		HexArrayRenderer hr = hexController.getView();
		CellRenderer target = hr.getCellAtDisplayPoint(p);
		
		if (target != null) {
			target.setHovered(true);
			if (!target.equals(hexController.getCurrentCell())) {
				if (hexController.getCurrentCell() != null) {
					hexController.getCurrentCell().setHovered(false);
				}
				cellchange = true;
			}
			hexController.setCurrentCell(target);
			if (cellchange) {
				hexController.onCellChange();
			}
		}
	}

}
