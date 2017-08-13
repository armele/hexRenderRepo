package com.mele.games.hex;

import com.mele.games.hex.ui.HexCell;

/**
 * Describes any game aspect that can occupy a hex on the game board.
 * 
 * @author Ayar
 *
 */
public interface IHexResident {
	public HexCell getCell();
	public void setCell(HexCell cell);
}
