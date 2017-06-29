package com.mele.games.hex.ui;

import java.awt.Polygon;
import java.util.Map;

import org.junit.Test;

import com.mele.games.hex.TestFence;
import com.mele.games.hex.TestFire;
import com.mele.games.hex.TestFrame;
import com.mele.games.hex.TestInvisible;
import com.mele.games.hex.TestType;

import junit.framework.Assert;

public class Renderer_Tests {

	@Test
	public void testVisualMap() {
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
		
		Map<Polygon, CellRenderer> visualMap = tf.hexControl.getView().getVisualMap();

		Assert.assertEquals(30, visualMap.keySet().size());
		Assert.assertEquals(30, visualMap.values().size());
		
		for (Polygon p : visualMap.keySet()) {
			Assert.assertNotNull(visualMap.get(p));
		}

	}	

}
