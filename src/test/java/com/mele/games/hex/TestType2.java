package com.mele.games.hex;

import java.awt.Color;

import com.mele.games.hex.ICellType;
import com.mele.games.hex.ui.IHexRenderable;

public class TestType2 implements ICellType {
	IHexRenderable bkImage = null;
	Color bkColor = Color.white;
	
	public void setBackgroundImage(IHexRenderable backgroundImage) {
		bkImage = backgroundImage;
	}

	public Color getBackgroundColor() {
		return bkColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		bkColor = backgroundColor;
	}

	public IHexRenderable getBackgroundImage() {
		return bkImage;
	}

}
