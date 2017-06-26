package com.mele.games.hex;

import junit.framework.Assert;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.Map;

import org.junit.Test;

import com.mele.games.hex.EHexVector;
import com.mele.games.hex.HexArray;
import com.mele.games.hex.HexCell;
import com.mele.games.hex.ui.CellRenderer;

public class HexArray_Tests {
	
	@Test
	public void testMapCreation() {
		HexArray map = new HexArray(4, 5);
		
		Assert.assertEquals(20, map.size());
		
		HexCell a1 = map.getCellAt(1, 1);
		
		Assert.assertEquals(1, a1.getX());
		Assert.assertEquals(1, a1.getY());
		
		HexCell d6 = map.getCellAt(3,4);
		
		Assert.assertEquals(3, d6.getX());
		Assert.assertEquals(4, d6.getY());		
	}
	
	@Test
	public void testHexDistance() {
		HexCell origin = new HexCell(4, 4, null);
		
		HexCell[] circle = new HexCell[12];
		circle[0] = new HexCell(4, 2, null);
		circle[1] = new HexCell(5, 2, null);
		circle[2] = new HexCell(6, 3, null);
		circle[3] = new HexCell(6, 4, null);
		circle[4] = new HexCell(6, 5, null);
		circle[5] = new HexCell(5, 5, null);
		circle[6] = new HexCell(4, 6, null);
		circle[7] = new HexCell(3, 5, null);
		circle[8] = new HexCell(2, 5, null);
		circle[9] = new HexCell(2, 4, null);
		circle[10] = new HexCell(2, 3, null);
		circle[11] = new HexCell(3, 2, null);
		
		Assert.assertEquals(0, origin.distance(origin).intValue());
		Assert.assertEquals(2, origin.distance(circle[0]).intValue());
		Assert.assertEquals(2, origin.distance(circle[1]).intValue());
		Assert.assertEquals(2, origin.distance(circle[2]).intValue());
		Assert.assertEquals(2, origin.distance(circle[3]).intValue());
		Assert.assertEquals(2, origin.distance(circle[4]).intValue());
		Assert.assertEquals(2, origin.distance(circle[5]).intValue());
		Assert.assertEquals(2, origin.distance(circle[6]).intValue());
		Assert.assertEquals(2, origin.distance(circle[7]).intValue());
		Assert.assertEquals(2, origin.distance(circle[8]).intValue());
		Assert.assertEquals(2, origin.distance(circle[9]).intValue());
		Assert.assertEquals(2, origin.distance(circle[10]).intValue());
		Assert.assertEquals(2, origin.distance(circle[11]).intValue());
	}
	
	@Test
	public void testOnVector() {
		HexCell origin = new HexCell(4, 4, null);
		
		HexCell[] circle = new HexCell[12];
		circle[0] = new HexCell(4, 2, null);
		circle[1] = new HexCell(5, 2, null);
		circle[2] = new HexCell(6, 3, null);
		circle[3] = new HexCell(6, 4, null);
		circle[4] = new HexCell(6, 5, null);
		circle[5] = new HexCell(5, 5, null);
		circle[6] = new HexCell(4, 6, null);
		circle[7] = new HexCell(3, 5, null);
		circle[8] = new HexCell(2, 5, null);
		circle[9] = new HexCell(2, 4, null);
		circle[10] = new HexCell(2, 3, null);
		circle[11] = new HexCell(3, 2, null);
		
		Assert.assertEquals(null, origin.onVector(origin));
		Assert.assertEquals(EHexVector.NORTH, origin.onVector(circle[0]));
		Assert.assertEquals(null, origin.onVector(circle[1]));
		Assert.assertEquals(EHexVector.NORTHEAST, origin.onVector(circle[2]));
		Assert.assertEquals(null, origin.onVector(circle[3]));
		Assert.assertEquals(EHexVector.SOUTHEAST, origin.onVector(circle[4]));
		Assert.assertEquals(null, origin.onVector(circle[5]));
		Assert.assertEquals(EHexVector.SOUTH, origin.onVector(circle[6]));
		Assert.assertEquals(null, origin.onVector(circle[7]));
		Assert.assertEquals(EHexVector.SOUTHWEST, origin.onVector(circle[8]));
		Assert.assertEquals(null, origin.onVector(circle[9]));
		Assert.assertEquals(EHexVector.NORTHWEST, origin.onVector(circle[10]));
		Assert.assertEquals(null, origin.onVector(circle[11]));
	}	
	
	@Test
	public void testDiagonal() {
		HexArray map = new HexArray(6, 6);
		HexCell test = map.getCellAt(0, 0);
		
		Assert.assertEquals(0, test.getDiagonal());
		
		test = map.getCellAt(0, 1);
		
		Assert.assertEquals(1, test.getDiagonal());
		
		test = map.getCellAt(0, 2);
		
		Assert.assertEquals(2, test.getDiagonal());		
		
		test = map.getCellAt(1, 0);
		
		Assert.assertEquals(1, test.getDiagonal());	
		
		test = map.getCellAt(1, 1);
		
		Assert.assertEquals(2, test.getDiagonal());	
		
		test = map.getCellAt(2, 1);
		
		Assert.assertEquals(2, test.getDiagonal());				
	}
	
	@Test
	public void testMapNavigation() {
		HexArray map = new HexArray(6, 6);
		
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
		tf.hexControl.resize(5, 6);
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
		tf.hexControl.resize(5, 6);
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
					Assert.assertEquals(28, tf.hexControl.getView().getCellSize());
				}
				if (passcount == 20) {
					Rectangle rect = tf.getBounds();
					rect.setSize((int)rect.getWidth(), (int)rect.getHeight() * 2);
					tf.setBounds(rect);
				}
				if (passcount == 25) {
					Assert.assertEquals(58, tf.hexControl.getView().getCellSize());
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
	public void testVisualMap() {
		TestFrame tf = new TestFrame();
		tf.hexControl.resize(5, 6);
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
		
		Map<Polygon, CellRenderer> visualMap = tf.hexControl.getView().getVisualMap();

		Assert.assertEquals(30, visualMap.keySet().size());
		Assert.assertEquals(30, visualMap.values().size());
		
		for (Polygon p : visualMap.keySet()) {
			Assert.assertNotNull(visualMap.get(p));
		}

	}		
}
