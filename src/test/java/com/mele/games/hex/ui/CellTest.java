package com.mele.games.hex.ui;

import org.junit.Test;

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
}
