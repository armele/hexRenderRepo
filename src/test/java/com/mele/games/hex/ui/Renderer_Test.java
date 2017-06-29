package com.mele.games.hex.ui;

import org.junit.Test;

import com.mele.games.hex.TestFrame;

import junit.framework.Assert;

public class Renderer_Test {

	@Test
	public void testAutoscaleCalc() {
		TestFrame tf = new TestFrame();
		tf.hexControl.size(5, 6);
		int cellsize = tf.hexControl.getView().calcMaxCellSize(0, 0, 224, 315, 5, 6);
		
		Assert.assertEquals(27, cellsize);
	}

}
