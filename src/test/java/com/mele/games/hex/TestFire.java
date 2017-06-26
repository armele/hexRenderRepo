package com.mele.games.hex;

import com.mele.games.animation.SpriteFactory;
import com.mele.games.animation.SpriteFactoryDescriptor;
import com.mele.games.hex.IHexResident;
import com.mele.games.hex.ui.IHexRenderable;

public class TestFire implements IHexRenderable, IHexResident {

	public TestFire() {
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/com/mele/hexrender/Fire_0.png", 2, 0);
		sd.addImageFrames("/com/mele/hexrender/Fire_1.png", 2, 0);
		sd.addImageFrames("/com/mele/hexrender/Fire_2.png", 2, 0);
		sd.addImageFrames("/com/mele/hexrender/Fire_1.png", 2, 0);
		
		SpriteFactory.registerSprite(getSpriteTag(), sd);
	}
	
	public String getSpriteTag() {
		return "FIRE";
	}

	public Object getProperty(String propname) {
		return null;
	}

	public void setProperty(String propname, Object propvalue) {
	}

}
