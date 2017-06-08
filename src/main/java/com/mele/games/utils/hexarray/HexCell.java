package com.mele.games.utils.hexarray;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mele.games.utils.ui.hex.IHexRenderable;

/**
 * Represents one cell in a hex array.  For example,
 * in the hex array represented below, C2 would be one
 * cell.
 * 
 *   A   B   C   D   E
 *  [A1]	[C1]    [E1]
 *      [B1]    [D1]
 *  [A2]    [C2]    [E2]
 *      [B2]    [D2]
 *  [A3]    [C3]    [E3]
 *  
 *  The "diagonal" describes the cell along the left side of
 *  the hex arrangement in which the target cell resides.
 *  For example [C2] is on the same diagonal as [A3] - so its
 *  "diagonal" is 3.
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
	
	protected List<IHexResident> residentList = new ArrayList<IHexResident>();
	
	protected ICellType type = null;
	
	protected HexArray map = null;
	
	public HexCell(HexArray map) {
		this.map = map;
	}
	
	public HexCell(int x, int y, HexArray map) {
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return
	 */
	public int getDiagonal() {
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
	 * @return
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
	 * @return the backgroundImage
	 */
	public IHexRenderable getBackgroundImage() {
		IHexRenderable backgroundImage = null;
		if (type != null) {
			backgroundImage = type.getBackgroundImage();
		}
		return backgroundImage;
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		Color bkColor = null;
		if (type != null) {
			bkColor = type.getBackgroundColor();
		}
		return bkColor;
	}

	/**
	 * @param newResident
	 */
	public void addResident(IHexResident newResident) {
		// rendered = false;
		residentList.add(newResident);
	}
	
	public List<IHexResident> getResidentList() {
		return residentList;
	}
}
