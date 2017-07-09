package com.mele.games.hex;

import java.awt.Color;

import com.mele.games.animation.ERenderPass;
import com.mele.games.animation.SpriteAnimated;
import com.mele.games.animation.SpriteFrame;
import com.mele.games.hex.ui.CellTypeMetadata;
import com.mele.games.hex.ui.ICellType;
import com.mele.games.hex.ui.IHexRenderable;

@CellTypeMetadata(symbol="D")
@SpriteAnimated(spriteTag="TESTTYPE", 
frames = { @SpriteFrame(frameCount = 1, frameVariation = 0, imageName = "/com/mele/hexrender/Boulder.png") },
renderPass = ERenderPass.BOTTOM
	)
public class TestType implements ICellType {
	IHexRenderable bkImage = new TestBackground();
	Color bkColor = Color.cyan;

	public Color getBackgroundColor() {
		return bkColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		bkColor = backgroundColor;
	}

	@Override
	public Object getProperty(String propname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String propname, Object propvalue) {
		// TODO Auto-generated method stub
		
	}

}
