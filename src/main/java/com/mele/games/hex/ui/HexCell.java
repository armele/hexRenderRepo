package com.mele.games.hex.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.mele.games.hex.EHexVector;
import com.mele.games.hex.IHexResident;

/**
 * Represents one cell in a hex array.  For example,
 * in the hex array represented below, C2 would be one
 * cell, at location 2, 1 (Zero-based)
 * 
 *   A   B   C   D   E
 *  [A1]	[C1]    [E1]
 *      [B1]    [D1]
 *  [A2]    [C2]    [E2]
 *      [B2]    [D2]
 *  [A3]    [C3]    [E3]
 *  
 
 * @author Ayar
 *
 */
public class HexCell implements Serializable {
	private static final long serialVersionUID = 1L;
	// TODO: [Long Term] Support "border" concept (the outer line of the cell, which may be shared between cells)
	
	// How many hexes in (across the top)
	protected int x = 0;
	
	// How many hexes down (along the left border)
	protected int y = 0;
	
	protected HashMap<String, Object> properties = new HashMap<String, Object>();
	protected Set<IHexResident> residents = new HashSet<IHexResident>();
	
	protected ICellType type = null;
	protected CellRenderer renderer = null;
	protected HexArray map = null;
	protected String label = null;
	
	/**
	 * @param map
	 */
	protected HexCell(HexArray map) {
		this.map = map;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param map
	 */
	protected HexCell(int x, int y, HexArray map) {
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	/**
	 * @return the x coordinate of this cell within the map
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate of this cell within the map
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * The "diagonal" is an indicator of what line of adjacent hexes this cell lies upon. 
	 * These lines start at 0,0 (upper left hex) and run from SW to NE.
	 * 
	 *  For example [C2] is on the same diagonal as [A3] - so its
	 *  "diagonal" is 2 (since the diagonal is zero-based).
	 *  
	 *   A   B   C   D   E
	 *  [A1]	[C1]    [E1]
	 *      [B1]    [D1]
	 *  [A2]    [C2]    [E2]
	 *      [B2]    [D2]
	 *  [A3]    [C3]    [E3]
	 * 
	 * @return the diagonal on which this hex is located
	 */
	protected int getDiagonal() {
		return ((getX()+ 1)/2) + getY();
	}
	
	
	/**
	 * @return the type
	 */
	public ICellType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ICellType type) {
		// rendered = false;
		this.type = type;
	}

	/**
	 * Returns the distance (in number of hexes) between this 
	 * hex and the hex "hp"
	 * @param hp
	 * @return
	 */
	public Integer distance(HexCell hp) {
		Integer distance = null;
		if (hp != null) {
			int xdist = Math.abs(hp.getX() - this.getX());
			int ydist = Math.abs(hp.getY() - this.getY());
			double lift = xdist / 2.0;  // The amount of change on a "y" axis you get by moving in a along the X axis 
			
			// Not obvious - but a hex geometry using this coordinate system has a line
			// on which a cell which varies by only one point on each axis measures 
			// is actually two cells away.  (For example compare B2 to C1 in the comments above.) 
			// Furthermore, this varies depending on whether you are on a "down" column or an "up" column
		
			if (getX() % 2 == 1) {  // On a "down" column
				if (hp.getY() > this.getY()) {
					lift = lift + .5;
				}
			} else {
				if (hp.getY() < this.getY()) {
					lift = lift + .5;
				}
			}

			ydist = Math.max(ydist - (int) lift, 0);
			distance = new Integer(xdist + ydist);
		}
		return distance;
	}
	
	/**
	 * For a given HexCell, return the vector from this hex
	 * that the target HexCell lies upon. For example, in the
	 * chart above, C3.onVector(D2) would return NORTHEAST.
	 * 
	 * Returns null if the point does not lie on a straight-path
	 * vector
	 * 
	 * @param point
	 * @return
	 */
	public EHexVector onVector(HexCell point) {
		EHexVector vector = null;
		int targetDiagonal = point.getDiagonal();
		int sourceDiagonal = getDiagonal();
		
		if (point != this) {
			int diffX = point.getX() - this.getX();
			int diffDiag = targetDiagonal - sourceDiagonal;
			
			if (diffX == 0) {
				if (diffDiag > 0) {
					vector = EHexVector.SOUTH;
				} else {
					vector = EHexVector.NORTH;
				}
			} else if (diffDiag == 0) {
				if (diffX > 0) {
					vector = EHexVector.NORTHEAST;
				} else {
					vector = EHexVector.SOUTHWEST;
				}
			} else if (diffX == diffDiag) {
				if (diffX > 0) {
					vector = EHexVector.SOUTHEAST;
				} else {
					vector = EHexVector.NORTHWEST;
				}
			}
		}
		
		return vector;
	}
	
	/**
	 * Given a vector, return the point of a potential adjacent cell.
	 * Note - the actual hex map MAY NOT INCLUDE a hex at this point!
	 * 
	 * @param vector
	 * @return the adjacent cell (if any)
	 */
	public HexCell adjacent(EHexVector vector) {
		HexCell point = null;
		int newx = -1;
		int newDiag = -1;
		int diag = getDiagonal();
		
		switch (vector) {
		case NORTH: 
			newDiag = diag-1;
			newx = x;
			break;
		case NORTHEAST:
			newx = x+1;
			newDiag = diag;
			break;
		case SOUTHEAST:
			newx = x+1;
			newDiag = diag+1;
			break;
		case SOUTH: 
			newx = x;
			newDiag = diag+1;
			break;
		case SOUTHWEST:
			newx = x-1;
			newDiag = diag;
			break;
		case NORTHWEST: 
			newx = x-1;
			newDiag = diag-1;
			break;
		}
		
		if (map != null) {
			point = map.getCellAt(newx, (newDiag - ((newx+1)/2)));		
		}
	
		return point;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HexCell other = (HexCell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}

	/**
	 * Add a new resident (game piece) at this location on the game board.
	 * 
	 * @param newResident
	 * @return true if successfully added
	 */
	public boolean addResident(IHexResident newResident) {
		if (newResident != null) {
			newResident.setCell(this);
		}
		return residents.add(newResident);
	}
	

	/**
	 * Removes the specified resident (game piece) from this location on the game board.
	 * 
	 * @param newResident
	 * @return true if successfully removed
	 */
	public boolean removeResident(IHexResident resident) {
		// If the resident cell is set, but not to this cell, don't clear the cell
		// on the resident (because perhaps the add to the new cell was done before the remove from this one.
		if (resident != null && resident.getCell() != null && resident.getCell().equals(this)) {
			resident.setCell(null);
		}		
		return residents.remove(resident);
	}	
	public Set<IHexResident> getResidents() {
		return residents;
	}

	/**
	 * @return the renderer responsible for drawing this cell
	 */
	public CellRenderer getRenderer() {
		return renderer;
	}

	/**
	 * @param renderer the renderer responsible for drawing this cell
	 */
	protected void setRenderer(CellRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Allows you to specify a property as a key/value pair.  This has no inherent use within the hex map structure,
	 * but supports flexible extension of a HexCell for game content.
	 * 
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, Object value) {
		properties.put(key, value);
	}
	
	/**
	 * Returns the given property of the HexCell
	 * 
	 * @param key
	 * @return
	 */
	public Object getProperty(String key) {
		return properties.get(key);
	}
	
	/**
	 * @return the map of all properties set on this cell.
	 */
	public HashMap<String, Object> getProperties() {
		return properties;
	}
	
	/**
	 * Using this cell as an origin point, return an array
	 * of all the points in the map along a given vector,
	 * starting with the closest cell first.
	 * 
	 * @param origin
	 * @return
	 */
	public ArrayList<HexCell> pathFromCell(EHexVector vector) {
		ArrayList<HexCell> vectorList = new ArrayList<HexCell>();
		
		for (HexCell nextCell = this.adjacent(vector); nextCell != null;) {
			vectorList.add(nextCell);
			nextCell = nextCell.adjacent(vector);
		}
		
		return vectorList;
	}	
}
