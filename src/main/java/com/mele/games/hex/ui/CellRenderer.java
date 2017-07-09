package com.mele.games.hex.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.animation.ERenderPass;
import com.mele.games.animation.Sprite;
import com.mele.games.animation.SpriteFactory;
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
	protected Font labelFont = null;
	
	// TODO: Allow client-created renderers which can override this functionality.
	
	/**
	 * @param viewRender
	 * @param modelCell
	 */
	public CellRenderer(HexArrayRenderer viewRender, HexCell modelCell) {
		this.cell = modelCell;
		this.renderer = viewRender;
		
		if (cell != null) {
			cell.setRenderer(this);
		}
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
		
		if (cell != null) {
			cell.setRenderer(this);
		}
	}
	
	/**
	 * @param cell
	 * @param p
	 * @param g
	 */
	public void draw(ERenderPass pass, Polygon p, Graphics g) {
		Color save = g.getColor();
		
		if (cell != null) {	
			
			// Background images and set background colors on the bottom bass
			if (ERenderPass.BOTTOM.equals(pass)) {
				Color color = getBackgroundColor();
				
				if (color != null) {
					g.setColor(color);	
				} else {
					g.setColor(Color.white);
				}
				g.fillPolygon(p);
				
				IHexRenderable bkgImg = getBackgroundImage();
				if (bkgImg != null) {
					renderItem(pass, cell, p, g,(IHexRenderable) bkgImg);
				}					
			}	
			
			// Default color fills and labels on the middle pass
			if (ERenderPass.MIDDLE.equals(pass)) {
				
				Color color = getBackgroundColor();
				
				if (color == null) {
					IHexRenderable bkgImg = getBackgroundImage();
					if (bkgImg == null || renderer.getSpriteMap().get(bkgImg) == null) {
						g.setColor(Color.white);
						g.fillPolygon(p);
					}
				}
				
				
				// Handle drawing cell labels
				if (cell != null && cell.getLabel() != null) {
					Font saveFont = g.getFont();
					
					if (getLabelFont() != null) {
						g.setFont(getLabelFont());
					}
					
					FontMetrics metrics = g.getFontMetrics(g.getFont());
					int txht = metrics.getHeight();
					int txwd = metrics.stringWidth(cell.getLabel());
					g.drawString(cell.getLabel(), (int)(p.getBounds().getCenterX() - (txwd / 2.0)), (int)(p.getBounds().getCenterY() + (txht / 2.0)));
					
					g.setFont(saveFont);
				}
			}
			
			// Residents on the top pass
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
			
			Set<IHexResident> residentList = cell.getResidents();
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
			
			// TODO: Confirm need for property values.
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
		} else {
			log.error("No sprites have been registered.");
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
		log.debug("Diagonal: " + cell.getDiagonal() + " " + toString());
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

	
	/**
	 * @return the background image to be displayed in this cell.
	 */
	protected IHexRenderable getBackgroundImage() {
		IHexRenderable backgroundImage = null;
		if (cell != null && cell.getType() != null) {
			backgroundImage = cell.getType();
		}
		return backgroundImage;
	}

	/**
	 * @return the background color used for painting this cell
	 */
	protected Color getBackgroundColor() {
		Color bkColor = null;
		
		if (cell != null && cell.getType() != null) {
			bkColor = cell.getType().getBackgroundColor();
		}
		return bkColor;
	}

	/**
	 * @return the labelFont
	 */
	public Font getLabelFont() {
		return labelFont;
	}

	/**
	 * @param labelFont the labelFont to set
	 */
	public void setLabelFont(Font labelFont) {
		this.labelFont = labelFont;
	}
	
}
