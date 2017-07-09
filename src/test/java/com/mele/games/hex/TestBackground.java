package com.mele.games.hex;

import java.awt.Color;

import com.mele.games.animation.ERenderPass;
import com.mele.games.animation.SpriteAnimated;
import com.mele.games.animation.SpriteFrame;
import com.mele.games.hex.ui.IHexRenderable;

@SpriteAnimated(spriteTag="BOULDER", 
	frames = { @SpriteFrame(frameCount = 1, frameVariation = 0, imageName = "/com/mele/hexrender/Boulder.png") },
	renderPass = ERenderPass.BOTTOM
		)
public class TestBackground implements IHexRenderable {
	protected Color bkg = null;
	
	public TestBackground () {
	
	}
	
	public String getSpriteTagX() {
		return "BOULDER";
	}

	public Object getProperty(String propname) {
		return null;
	}

	public void setProperty(String propname, Object propvalue) {
	}

	@Override
	public Color getBackgroundColor() {
		return bkg;
	}

	@Override
	public void setBackgroundColor(Color backgroundColor) {
		bkg = backgroundColor;
	}

}
