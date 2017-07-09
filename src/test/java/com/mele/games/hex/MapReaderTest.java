package com.mele.games.hex;

import org.junit.Test;

import com.mele.games.hex.ui.HexArray;
import com.mele.games.hex.ui.MapReader;

import junit.framework.Assert;

public class MapReaderTest {

	@Test
	public void testRead() {
		/*
		MapReader.registerTypeSymbol("-", TestType2.class);
		MapReader.registerTypeSymbol("D", TestType.class);
		MapReader.registerResidentSymbol(".", null);
		MapReader.registerResidentSymbol(":", TestFire.class);
		*/
		
		HexArray har = MapReader.loadMapTerrain("testMap.map");
		
		Assert.assertEquals(7, har.getColumns());
		Assert.assertEquals(3, har.getRows());
		
		Assert.assertEquals(0, har.getCellAt(0, 0).getResidents().size());
		Assert.assertEquals(0, har.getCellAt(1, 0).getResidents().size());
		Assert.assertEquals(0, har.getCellAt(2, 0).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(3, 0).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(4, 0).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(5, 0).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(6, 0).getResidents().size());
		Assert.assertEquals(null, har.getCellAt(7, 0));
		Assert.assertEquals(0, har.getCellAt(0, 1).getResidents().size());
		Assert.assertEquals(0, har.getCellAt(1, 1).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(2, 1).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(3, 1).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(4, 1).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(5, 1).getResidents().size());
		Assert.assertEquals(0, har.getCellAt(6, 1).getResidents().size());
		Assert.assertEquals(null, har.getCellAt(7, 1));				
		Assert.assertEquals(0, har.getCellAt(0, 2).getResidents().size());
		Assert.assertEquals(0, har.getCellAt(1, 2).getResidents().size());
		Assert.assertEquals(0, har.getCellAt(2, 2).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(3, 2).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(4, 2).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(5, 2).getResidents().size());
		Assert.assertEquals(1, har.getCellAt(6, 2).getResidents().size());
		Assert.assertEquals(null, har.getCellAt(7, 2));		
		
		Assert.assertEquals(TestType.class, har.getCellAt(2, 1).getType().getClass());	
		Assert.assertEquals(TestFire.class, har.getCellAt(2, 1).getResidents().toArray()[0].getClass());	
	}

}
