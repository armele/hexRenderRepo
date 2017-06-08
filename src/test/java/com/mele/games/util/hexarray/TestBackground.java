package com.mele.games.util.hexarray;

import com.mele.games.utils.ui.SpriteFactory;
import com.mele.games.utils.ui.SpriteFactoryDescriptor;
import com.mele.games.utils.ui.hex.IHexRenderable;

public class TestBackground implements IHexRenderable {

	public TestBackground () {
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/Boulder.png", 1, 0);
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
