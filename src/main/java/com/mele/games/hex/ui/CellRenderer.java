package com.mele.games.hex.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.animation.ERenderPass;
import com.mele.games.animation.Sprite;
import com.mele.games.animation.SpriteFactory;
import com.mele.games.hex.HexCell;
import com.mele.games.hex.IHexResident;

/**
 * Isolates the rendering logic for cells from the underlying game logic content.
 * This is a tool of the "view" in the MVC pattern.
 * 
 * @author Al Mele
 *
 */
public class CellRenderer {
	protected static Logger log = LogManager.getLogger(CellRenderer.class);	
	protected HexCell cell = null;
	protected HexArrayRenderer renderer = null;
	protected boolean selected = false;
	protected boolean hovered = false;
	protected boolean rendered = false;
	
	/**
	 * @param viewRender
	 * @param modelCell
	 */
	public CellRenderer(HexArrayRenderer viewRender, HexCell modelCell) {
		this.cell = modelCell;
		this.renderer = viewRender;
	}

	/**
	 * @return the cell
	 */
	public HexCell getCell() {
		return cell;
	}

	/**
	 * @param cell the cell to set
	 */
	public void setCell(HexCell cell) {
		this.cell = cell;
	}
	
	/**
	 * @param cell
	 * @param p
	 * @param g
	 */
	public void draw(ERenderPass pass, Polygon p, Graphics g) {
		Color save = g.getColor();
		
		if (cell != null) {	
			
			// Cell backgrounds are painted on the middle pass only.
			if (ERenderPass.BOTTOM.equals(pass)) {
				
				Color color = cell.getBackgroundColor();
				
				if (color != null) {
					g.setColor(color);	
					g.fillPolygon(p);
				} else {
					Color transparent = new Color(255,255,255,255);
					g.setColor(transparent);
					g.fillPolygon(p);
				}
				
				IHexRenderable bkgImg = cell.getBackgroundImage();
				if (bkgImg != null) {
					renderItem( pass, cell, p, g,(IHexRenderable) bkgImg);
				}					
			}	
			
			if (ERenderPass.TOP.equals(pass)) {
				if (isSelected()) {
					Graphics2D g2 = (Graphics2D)g;
					Color color = new Color(renderer.getSelectionColor().getRed(), renderer.getSelectionColor().getGreen(), renderer.getSelectionColor().getBlue(), 127);
					Stroke saveStroke = g2.getStroke();
					g2.setStroke(new BasicStroke(10));
					g2.setColor(color);
					g2.drawPolygon(p);
					g2.setStroke(saveStroke);					
				} else if (isHovered()) {
					Graphics2D g2 = (Graphics2D)g;
					Color color = new Color(renderer.getSelectionColor().getRed(), renderer.getSelectionColor().getGreen(), renderer.getSelectionColor().getBlue(), 64);
					g2.setColor(color);
					g2.fillPolygon(p);					
				}				
				
				g.setColor(renderer.getLineColor());
				g.drawPolygon(p);
				
			}
			
			List<IHexResident> residentList = cell.getResidentList();
			if (residentList != null) {
				for (IHexResident resident : residentList) {
					if (resident instanceof IHexRenderable) {
						renderItem( pass,  cell,  p,  g, (IHexRenderable)resident);
					}
				}
			}
		
			setRendered(true);
			
		}
		
		g.setColor(save);
	}
	
	/**
	 * @param pass
	 * @param point
	 * @param p
	 * @param g
	 * @param renderItem
	 */
	protected void renderItem(ERenderPass pass, HexCell point, Polygon p, Graphics g, IHexRenderable renderItem) {
		HashMap<IHexRenderable, Sprite> spriteMap = renderer.getSpriteMap();
		
		if (spriteMap != null) {
			Sprite sprite = spriteMap.get(renderItem);
			
			if (IHexRenderable.PROPVAL_TRUE.equals(renderItem.getProperty(IHexRenderable.PROPKEY_NEWTYPE)) || sprite == null) {
				sprite = SpriteFactory.letThereBeSprite(renderItem);
				renderItem.setProperty(IHexRenderable.PROPKEY_NEWTYPE, IHexRenderable.PROPVAL_FALSE);  // Clear this flag once loaded.
				spriteMap.put(renderItem, sprite);
			}
			
			if (sprite != null) {
				if (sprite.getDescriptor().getRenderPass().equals(pass)) {
					sprite.render(p, g);	
				}
			}		
		}
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the rendered
	 */
	public boolean isRendered() {
		return rendered;
	}

	/**
	 * @param rendered the rendered to set
	 */
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	/**
	 * @return the hovered
	 */
	public boolean isHovered() {
		return hovered;
	}

	/**
	 * @param hovered the hovered to set
	 */
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return cell.hashCode();
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
		CellRenderer other = (CellRenderer) obj;
		if (cell == null) {
			if (other.cell != null)
				return false;
		} else if (!cell.equals(other.cell)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CellRenderer [cell=" + cell + ", selected=" + selected + ", hovered=" + hovered + "]";
	}	
	
}
