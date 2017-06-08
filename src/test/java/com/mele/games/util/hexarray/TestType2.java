package com.mele.games.util.hexarray;

import java.awt.Color;

import com.mele.games.utils.hexarray.ICellType;
import com.mele.games.utils.ui.hex.IHexRenderable;

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
