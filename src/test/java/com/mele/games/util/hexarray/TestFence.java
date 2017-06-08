package com.mele.games.util.hexarray;

import com.mele.games.utils.hexarray.IHexResident;
import com.mele.games.utils.ui.SpriteFactory;
import com.mele.games.utils.ui.SpriteFactoryDescriptor;
import com.mele.games.utils.ui.hex.IHexRenderable;

public class TestFence implements IHexRenderable, IHexResident {

	public TestFence() {
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/Fence.png", 1, 0);
		SpriteFactory.registerSprite(getSpriteTag(), sd);
	}
	
	
	public String getSpriteTag() {
		return "FENCE";
	}

	public Object getProperty(String propname) {
		return null;
	}

	public void setProperty(String propname, Object propvalue) {
	}

}
