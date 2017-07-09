package com.mele.games.hex;

import java.awt.Color;

import com.mele.games.hex.ui.CellTypeMetadata;
import com.mele.games.hex.ui.ICellType;
import com.mele.games.hex.ui.IHexRenderable;

@CellTypeMetadata(symbol="-")
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
