package com.mele.games.hex;

import com.mele.games.hex.IHexResident;
import com.mele.games.hex.ui.HexCell;

public class TestInvisible implements IHexResident {
	protected HexCell currentCell = null;
	
	@Override
	public HexCell getCell() {
		return currentCell;
	}


	@Override
	public void setCell(HexCell cell) {
		currentCell = cell;		
	}
}
