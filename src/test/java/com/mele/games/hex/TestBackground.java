package com.mele.games.hex;

import com.mele.games.animation.SpriteFactory;
import com.mele.games.animation.SpriteFactoryDescriptor;
import com.mele.games.hex.ui.IHexRenderable;

public class TestBackground implements IHexRenderable {

	public TestBackground () {
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/com/mele/hexrender/Boulder.png", 1, 0);
		SpriteFactory.registerSprite(getSpriteTag(), sd);		
	}
	
	public String getSpriteTag() {
		return "BOULDER";
	}

	public Object getProperty(String propname) {
		return null;
	}

	public void setProperty(String propname, Object propvalue) {
	}

}
