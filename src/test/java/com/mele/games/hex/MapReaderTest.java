package com.mele.games.hex;

import org.junit.Test;

import com.mele.games.hex.HexArray;
import com.mele.games.hex.MapReader;

import junit.framework.Assert;

public class MapReaderTest {

	@Test
	public void testRead() {
		MapReader mr = new MapReader();
		mr.registerTypeSymbol("-", TestType2.class);
		mr.registerTypeSymbol("D", TestType.class);
		mr.registerResidentSymbol(".", null);
		mr.registerResidentSymbol(":", TestFire.class);
		
		
		HexArray har = mr.loadMapTerrain("testMap.map");
		
		Assert.assertEquals(7, har.getColumns());
		Assert.assertEquals(3, har.getRows());
		
		Assert.assertEquals(0, har.getCellAt(0, 0).getResidentList().size());
		Assert.assertEquals(0, har.getCellAt(1, 0).getResidentList().size());
		Assert.assertEquals(0, har.getCellAt(2, 0).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(3, 0).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(4, 0).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(5, 0).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(6, 0).getResidentList().size());
		Assert.assertEquals(null, har.getCellAt(7, 0));
		Assert.assertEquals(0, har.getCellAt(0, 1).getResidentList().size());
		Assert.assertEquals(0, har.getCellAt(1, 1).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(2, 1).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(3, 1).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(4, 1).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(5, 1).getResidentList().size());
		Assert.assertEquals(0, har.getCellAt(6, 1).getResidentList().size());
		Assert.assertEquals(null, har.getCellAt(7, 1));				
		Assert.assertEquals(0, har.getCellAt(0, 2).getResidentList().size());
		Assert.assertEquals(0, har.getCellAt(1, 2).getResidentList().size());
		Assert.assertEquals(0, har.getCellAt(2, 2).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(3, 2).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(4, 2).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(5, 2).getResidentList().size());
		Assert.assertEquals(1, har.getCellAt(6, 2).getResidentList().size());
		Assert.assertEquals(null, har.getCellAt(7, 2));		
		
		Assert.assertEquals(TestType.class, har.getCellAt(2, 1).getType().getClass());	
		Assert.assertEquals(TestFire.class, har.getCellAt(2, 1).getResidentList().get(0).getClass());	
	}

}
