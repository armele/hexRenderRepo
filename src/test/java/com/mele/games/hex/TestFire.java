package com.mele.games.hex;

import java.awt.Color;

import com.mele.games.animation.SpriteFactory;
import com.mele.games.animation.SpriteFactoryDescriptor;
import com.mele.games.hex.ui.IHexRenderable;
import com.mele.games.hex.ui.ResidentMetadata;

@ResidentMetadata(symbol = ":")
public class TestFire implements IHexRenderable, IHexResident {

	public TestFire() {
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/com/mele/hexrender/Fire_0.png", 2, 0);
		sd.addImageFrames("/com/mele/hexrender/Fire_1.png", 2, 0);
		sd.addImageFrames("/com/mele/hexrender/Fire_2.png", 2, 0);
		sd.addImageFrames("/com/mele/hexrender/Fire_1.png", 2, 0);
		
		SpriteFactory.registerSprite(getSpriteTagX(), sd);
	}
	
	public String getSpriteTagX() {
		return "FIRE";
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
