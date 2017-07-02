package com.mele.games.hex;

import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import org.junit.Test;

import com.mele.games.hex.ui.CellRenderer;
import com.mele.games.hex.ui.HexArray;
import com.mele.games.hex.ui.HexArrayController;
import com.mele.games.hex.ui.HexCell;

import junit.framework.Assert;

public class HexArray_Tests {
	
	@Test
	public void testMapCreation() {
		HexArrayController hac = new HexArrayController(null);
		hac.size(4, 5);
		
		HexArray map = hac.getModel();
		
		Assert.assertEquals(20, map.size());
		
		HexCell a1 = map.getCellAt(1, 1);
		
		Assert.assertEquals(1, a1.getX());
		Assert.assertEquals(1, a1.getY());
		
		HexCell d6 = map.getCellAt(3,4);
		
		Assert.assertEquals(3, d6.getX());
		Assert.assertEquals(4, d6.getY());		
	}
	
	@Test
	public void testMapNavigation() {
		HexArrayController hac = new HexArrayController(null);
		hac.size(6, 6);
		
		HexArray map = hac.getModel();
		
		Assert.assertEquals(36, map.size());
		
		HexCell a1 = map.getCellAt(1,1);
		
		Assert.assertEquals(1, a1.getX());
		Assert.assertEquals(1, a1.getY());
		
		HexCell next = a1.adjacent(EHexVector.SOUTH);
		
		Assert.assertEquals(1, next.getX());
		Assert.assertEquals(2, next.getY());
		
		next = next.adjacent(EHexVector.NORTHEAST);
		
		Assert.assertEquals(2, next.getX());
		Assert.assertEquals(2, next.getY());		
		
		next = next.adjacent(EHexVector.SOUTH);
		
		Assert.assertEquals(2, next.getX());
		Assert.assertEquals(3, next.getY());	
		
		next = next.adjacent(EHexVector.SOUTHEAST);
		
		Assert.assertEquals(3, next.getX());
		Assert.assertEquals(3, next.getY());	
		
		next = next.adjacent(EHexVector.NORTHWEST);
		
		Assert.assertEquals(2, next.getX());
		Assert.assertEquals(3, next.getY());		
		
		next = next.adjacent(EHexVector.NORTHEAST);
		
		Assert.assertEquals(3, next.getX());
		Assert.assertEquals(2, next.getY());		
		
		next = next.adjacent(EHexVector.NORTHWEST);
		
		Assert.assertEquals(2, next.getX());
		Assert.assertEquals(2, next.getY());		
		
		next = next.adjacent(EHexVector.SOUTHWEST);
		
		Assert.assertEquals(1, next.getX());
		Assert.assertEquals(2, next.getY());	
		
		next = next.adjacent(EHexVector.SOUTHWEST);		
		
		Assert.assertEquals(0, next.getX());
		Assert.assertEquals(3, next.getY());	
		
		next = next.adjacent(EHexVector.SOUTHWEST);		
				
		Assert.assertNull (next);			
	}
	
	@Test
	public void testVector() {
		EHexVector reverse = EHexVector.NORTH.reverse();
		Assert.assertEquals(EHexVector.SOUTH, reverse);
		
		reverse = EHexVector.NORTHEAST.reverse();
		Assert.assertEquals(EHexVector.SOUTHWEST, reverse);
		
		reverse = EHexVector.NORTHWEST.reverse();
		Assert.assertEquals(EHexVector.SOUTHEAST, reverse);		
		
		reverse = EHexVector.SOUTH.reverse();
		Assert.assertEquals(EHexVector.NORTH, reverse);	
		
		reverse = EHexVector.SOUTHWEST.reverse();
		Assert.assertEquals(EHexVector.NORTHEAST, reverse);	
		
		reverse = EHexVector.NORTHWEST.reverse();
		Assert.assertEquals(EHexVector.SOUTHEAST, reverse);	
		
		reverse = EHexVector.NORTH.reverse();
		Assert.assertEquals(EHexVector.SOUTH, reverse);			
	}
	
	@Test
	public void showRenderer() {
		int passcount = 0;
		TestFrame tf = new TestFrame();
		tf.hexControl.size(5, 6);
		HexArray hexmap = tf.hexControl.getModel();
		hexmap.getCellAt(1, 1).addResident(new TestFence());
		hexmap.getCellAt(1, 1).addResident(new TestFire());
		hexmap.getCellAt(2, 1).setType(new TestType());
		hexmap.getCellAt(3, 3).addResident(new TestFence());
		hexmap.getCellAt(2, 2).addResident(new TestInvisible());
		hexmap.getCellAt(2, 3).setType(new TestType());
		
		Assert.assertEquals(5, hexmap.getColumns());
		Assert.assertEquals(6, hexmap.getRows());
		
		tf.display();
		
		while (!tf.closed) {
			try {
				Thread.sleep(86);
				tf.display();
				passcount++;
				
				if (passcount > 100) {
					tf.dispatchEvent(new WindowEvent(tf, WindowEvent.WINDOW_CLOSING));
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}
	
	@Test
	public void testAutoscaling() {
		int passcount = 0;
		TestFrame tf = new TestFrame();
		tf.hexControl.size(5, 6);
		tf.hexControl.setAutoscale(true);
		
		HexArray hexmap = tf.hexControl.getModel();
		hexmap.getCellAt(1, 1).addResident(new TestFence());
		hexmap.getCellAt(1, 1).addResident(new TestFire());
		hexmap.getCellAt(2, 1).setType(new TestType());
		hexmap.getCellAt(3, 3).addResident(new TestFence());
		hexmap.getCellAt(2, 2).addResident(new TestInvisible());
		hexmap.getCellAt(2, 3).setType(new TestType());
		
		Assert.assertEquals(5, hexmap.getColumns());
		Assert.assertEquals(6, hexmap.getRows());
		
		tf.display();
		
		while (!tf.closed) {
			try {
				Thread.sleep(86);
				tf.display();
				passcount++;
				
				if (passcount == 10) {
					Rectangle rect = tf.getBounds();
					rect.setSize((int)rect.getWidth() * 2, (int)rect.getHeight());
					tf.setBounds(rect);
				}
				if (passcount == 15) {
					Assert.assertEquals(25, tf.hexControl.getView().getCellSize());
				}
				if (passcount == 20) {
					Rectangle rect = tf.getBounds();
					rect.setSize((int)rect.getWidth(), (int)rect.getHeight() * 2);
					tf.setBounds(rect);
				}
				if (passcount == 25) {
					Assert.assertEquals(52, tf.hexControl.getView().getCellSize());
				}				
				if (passcount > 100) {
					tf.dispatchEvent(new WindowEvent(tf, WindowEvent.WINDOW_CLOSING));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}	
	
	@Test
	public void testSelectedCells() {
		TestFrame tf = new TestFrame();
		tf.hexControl.size(5, 6);
		tf.hexControl.setAutoscale(true);
		
		CellRenderer crend = tf.hexControl.getCellAt(1, 1);
		crend.setSelected(true);
		
		crend = tf.hexControl.getCellAt(3, 2);
		crend.setSelected(true);		
		
		ArrayList<CellRenderer> sList = (ArrayList<CellRenderer>) tf.hexControl.selectedCells();
		
		Assert.assertEquals(2, sList.size());
		
		boolean found1 = false;
		boolean found2 = false;
		
		for (CellRenderer cr : sList) {
			if (cr.getCell().getX() == 1 && cr.getCell().getY() == 1) {
				found1 = true;
			}
			if (cr.getCell().getX() == 3 && cr.getCell().getY() == 2) {
				found2 = true;
			}			
		}
		
		Assert.assertEquals(true, found1 && found2);
	}		
}
