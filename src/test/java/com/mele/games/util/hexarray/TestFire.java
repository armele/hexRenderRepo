package com.mele.games.util.hexarray;

import com.mele.games.utils.hexarray.IHexResident;
import com.mele.games.utils.ui.SpriteFactory;
import com.mele.games.utils.ui.SpriteFactoryDescriptor;
import com.mele.games.utils.ui.hex.IHexRenderable;

public class TestFire implements IHexRenderable, IHexResident {

	public TestFire() {
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/Fire_0.png", 2, 0);
		sd.addImageFrames("/Fire_1.png", 2, 0);
		sd.addImageFrames("/Fire_2.png", 2, 0);
		sd.addImageFrames("/Fire_1.png", 2, 0);
		
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
