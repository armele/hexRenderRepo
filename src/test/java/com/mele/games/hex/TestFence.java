package com.mele.games.hex;

import java.awt.Color;

import com.mele.games.animation.SpriteFactory;
import com.mele.games.animation.SpriteFactoryDescriptor;
import com.mele.games.hex.IHexResident;
import com.mele.games.hex.ui.IHexRenderable;

public class TestFence implements IHexRenderable, IHexResident {

	public TestFence() {
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/com/mele/hexrender/Fence.png", 1, 0);
		SpriteFactory.registerSprite(getSpriteTagX(), sd);
	}
	
	
	public String getSpriteTagX() {
		return "FENCE";
	}

	public Object getProperty(String propname) {
		return null;
	}

	public void setProperty(String propname, Object propvalue) {
	}


	@Override
	public Color getBackgroundColor() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setBackgroundColor(Color backgroundColor) {
		// TODO Auto-generated method stub
		
	}

}
