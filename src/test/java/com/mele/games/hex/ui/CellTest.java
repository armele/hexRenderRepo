package com.mele.games.hex.ui;

import org.junit.Test;

import com.mele.games.hex.EHexVector;

import junit.framework.Assert;

/**
 * @author Al Mele
 *
 */
public class CellTest {
	
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
}
