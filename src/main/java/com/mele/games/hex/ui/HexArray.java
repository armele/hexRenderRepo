package com.mele.games.hex.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.hex.IHexResident;

/**
 * This implementation of an array uses a 2-dimensional array to represent a grid of
 * hex spaces.  In the illustration below, adjacent hexes are considered those diagonal
 * and those up and down.
 * 
 * For example, C2 is adjacent to C1, D1, D2, C3, B2 and B1.
 * 
 * Note that letter assignment is vertical, while numeric assignment is measured by the 
 * distance from the top of that column of cells.
 * 
 *   A   B   C   D   E
 *  [A1]	[C1]    [E1]
 *      [B1]    [D1]
 *  [A2]    [C2]    [E2]
 *      [B2]    [D2]
 *  [A3]    [C3]    [E3]
 *  
 * 
 * @author Ayar
 *
 */
public class HexArray implements Iterable<HexCell>, Serializable {
	private static final long serialVersionUID = 1L;
	protected static Logger log = LogManager.getLogger(HexArray.class);
	
	/**
	 *  In the 2-dimensional array, the first subscript represents horizontal traversal (A-E)
	 *  and the second subscript represents vertical traversal (1-3)
	 */
	protected HexCell[][] cellMap = null;
	
	/**
	 * Default constructor - starts with an empty map (no cells)
	 */
	protected HexArray() {
	}
	
	/**
	 * Construct a map with the specified number of columns and rows.
	 * 
	 * @param columns
	 * @param rows
	 */
	protected HexArray(int columns, int rows) {
		cellMap = create(columns, rows);
	}
	
	/**
	 * A list of all items found in the hex array.
	 * 
	 * @return
	 */
	public List<IHexResident> globalResidentList() {
		ArrayList<IHexResident> globalResidentList = new ArrayList<IHexResident>();
		
		for (HexCell[] column : cellMap) {
			for (HexCell cell : column) {
				globalResidentList.addAll(cell.getResidents());
			}
			
		}
		
		return globalResidentList;
	}
	
	/**
	 * Add a resident at the specified point
	 * 
	 * @param resident
	 * @param point
	 */
	protected void put(IHexResident resident, int x, int y) {
		HexCell cell = cellMap[x][y];
		cell.addResident(resident);
	}
	
	/**
	 * Provide the number of columns and the number of cells in the first column.
	 * 
	 * @param columns
	 * @param rows
	 */
	protected HexCell[][] create(int columns, int rows) {
		log.debug("Creating hex array with " + columns +" columns and " + rows + " rows.");
	
		HexCell[][] newMap = new HexCell[columns][rows];
		
		// Map is created using cartesian coordinates.
		for (int x = 0; x < columns; x++) {
			int cells = rows;
			for (int y = 0; y < cells; y++) {
				HexCell point = new HexCell(x, y, this);
				newMap[x][y] = point;
			}
		}
		
		return newMap;
	}
	
	/**
	 * Given new size parameters for a hex map, resize the map to that size, copying
	 * contents that exist within the boundaries of both maps.
	 * 
	 * @param x
	 * @param y
	 */
	protected void resize(int newXSize, int newYSize) {
		HexCell[][] temp = create(newXSize, newYSize);
		
		// Map is created using cartesian coordinates.
		for (int x = 0; x < newXSize; x++) {
			int cells = newYSize;
			for (int y = 0; y < cells; y++) {
				HexCell point = this.getCellAt(x, y);
				if (point != null) {
					temp[x][y] = point;
				} else {
					temp[x][y] = new HexCell(x, y, this);
				}
			}
		}
		
		cellMap = temp;
	}
	
	/**
	 * @return the total number of cells in the map.
	 */
	public int size() {
		return getRows() * getColumns();
	}
	
	/**
	 * The number of cells in each column of the hex map.
	 */	
	public int getRows() {
		int rows = 0;
		
		if ((cellMap != null) && (cellMap.length > 0)) {
			rows = cellMap[0].length;
		}
		
		return rows;
	}

	/**
	 * The number of columns in the hex map
	 */
	public int getColumns() {
		int columns = 0;
		
		if (cellMap != null) {
			columns = cellMap.length;
		}
		
		return columns;
	}
	
	/**
	 * Given an x,y coordinate, return the HexCell object associated
	 * with it in the hex array.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public HexCell getCellAt(int x, int y) {
		HexCell spot = null;

		if (cellMap != null) {
			try
			{
				spot = cellMap[x][y];
			} catch (IndexOutOfBoundsException oob) {
				spot = null;
			}
		}
		
		return spot;
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<HexCell> iterator() {
		ArrayList<HexCell> cellList = new ArrayList<HexCell>();
		if (cellMap != null) {
			for (HexCell[] column : cellMap) {
				for (HexCell cell : column) {
					cellList.add(cell);
				}
			}
		}
		return cellList.iterator();
	}	
}