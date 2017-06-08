package com.mele.games.utils.ui.hex;

import org.junit.Test;

import com.mele.games.util.hexarray.TestFrame;

import junit.framework.Assert;

public class Renderer_Test {

	@Test
	public void testAutoscaleCalc() {
		TestFrame tf = new TestFrame();
		tf.hexControl.resize(5, 6);
		int cellsize = tf.hexControl.getView().calcMaxCellSize(0, 0, 224, 315, 5, 6);
		
		Assert.assertEquals(28, cellsize);
	}

}
