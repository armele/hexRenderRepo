package com.mele.games.util.hexarray;

import java.awt.Color;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.mele.games.utils.hexarray.HexArray;

import junit.framework.Assert;

public class Manual_Test {
	protected static Logger log = LogManager.getLogger(Manual_Test.class);
	
	@Test
	public void manualTest() {
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
		tf.hexControl.getView().setSelectionColor(Color.green);
		
		Assert.assertEquals(5, hexmap.getColumns());
		Assert.assertEquals(6, hexmap.getRows());
		
		tf.display();
		
		while (!tf.closed) {
			try {
				Thread.sleep(86);
				tf.display();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}
	
	
}
