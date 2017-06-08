package com.mele.games.utils.ui.hex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Window;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.utils.GameException;
import com.mele.games.utils.hexarray.HexArray;
import com.mele.games.utils.hexarray.HexCell;
import com.mele.games.utils.ui.ERenderPass;
import com.mele.games.utils.ui.Sprite;


/**
 * Responsible for drawing the contents of the hex array (the main game board).  Think of this class
 * as the "view" in a Model/View/Controller pattern.
 * 
 * For assistance on color selection, see: http://www.spelunkcomputing.com/colorexplorerapplet.html
 * 
 * @author Ayar
 *
 */
public class HexArrayRenderer {
	protected static Logger log = LogManager.getLogger(HexArrayRenderer.class);
	protected static final int HEX = 6;
	
	protected HashMap<IHexRenderable, Sprite> spriteMap = new HashMap<IHexRenderable, Sprite>();
	protected HexArrayController controller = null;
	protected Window parentWindow = null;
	protected int offsetX = 0;
	protected int offsetY = 0;
	
	protected Map<Polygon, CellRenderer> visualMap = null;
	protected int cellSize = 25; 
	
	// TODO: Support "Mega Hex" shape (where the collective hexes themselves form a larger hex shape)

	protected double maxX = 0;
	protected double maxY = 0;
	protected double minX = 0;
	protected double minY = 0;	
	protected boolean initialized = false;
	
	/**
	 * When autoscale is "true", the cell size is derived from the window space.
	 * When autoscale is "false", the cell size is used as set.
	 */
	protected boolean autoscale; 
	
	protected Color selectionColor = Color.white;
	protected Color lineColor = Color.black;
	
	
	/**
	 * The canvas onto which the hex map is rendered.
	 */
	protected Image canvas = null;
	
	public HexArrayRenderer () {
		
	}
	
	public HexArrayRenderer (Window parentWin) {
		setParentWindow(parentWindow);
	}
	
	/**
	 * Given the dimensions of the display space and the number of desired rows and columns, determine the maximum
	 * cell size that can be painted into the space.
	 * 
	 * @param offsetX
	 * @param offsetY
	 * @param width
	 * @param height
	 * @param columns
	 * @param rows
	 * @return
	 */
	protected int calcMaxCellSize(int offsetX, int offsetY, int width, int height, int columns, int rows) {
		int maxsize = 0;
		
		double xlimited = 0;
		double ylimited = 0;
		
		int displayWidth = width - offsetX - 1;
		int displayHeight = height - offsetY - 1;
		
		// Odd columns have cellsize*2 width.  Even columns have cellsize width (due to overlap along edges)
		int xscale = 0;
		
		for (int i = 1; i <= columns; i++) {
			xscale = xscale + (i%2 == 1 ? 2 : 1);
		}
		
		xlimited = (double) displayWidth / (double) xscale;
		
		ylimited = (double) displayHeight / ((double)rows * (Math.sqrt(1-(.5*.5)) + 1));
		
		maxsize = Math.min((int)xlimited, (int)ylimited);		
		
		return maxsize;
	}
	
	/**
	 * Create the polygons necessary for illustrating the board.
	 */
	public void init(HexArray hexmap) {
		if (visualMap != null) {
			visualMap.clear();
		}
		
		visualMap = new ConcurrentHashMap<Polygon, CellRenderer>();
		
		maxX = 0;
		maxY = 0;
		minX = 0;
		minY = 0;
		
		if (autoscale) {
			cellSize = calcMaxCellSize(offsetX, offsetY, parentWindow.getWidth(), parentWindow.getHeight(), hexmap.getColumns(), hexmap.getRows());
		}
		
		int referencePointX = cellSize; 
		int referencePointY = 0; 
		
		for (HexCell point : hexmap) {
			if (point != null) {
				Polygon p = new Polygon();

				visualMap.put(p, new CellRenderer(this, point));
				
				double columnSpacing = point.getX() * (cellSize * Math.cos(2 * Math.PI / HEX) + cellSize);
				double rowSpacing = (cellSize * Math.sin(2 * Math.PI / HEX)) * (2 * (point.getY() + 1) - ((point.getX() + 1) % 2));
					
			    for (int i = 0; i < HEX; i++) {
				      p.addPoint(
				    	(int) (referencePointX + columnSpacing + (cellSize * Math.cos(i * 2 * Math.PI / HEX))),
				        (int) (referencePointY + rowSpacing + (cellSize * Math.sin(i * 2 * Math.PI / HEX)))
				      );
				      
				      if (maxX < p.getBounds().getMaxX()) {
				    	  maxX = p.getBounds().getMaxX();
				      }
				      if (maxY < p.getBounds().getMaxY()) {
				    	  maxY = p.getBounds().getMaxY();
				      }
				      if (minX > p.getBounds().getMinX()) {
				    	  minX = p.getBounds().getMinX();
				      }
				      if (minY > p.getBounds().getMinY()) {
				    	  minY = p.getBounds().getMinY();
				      }				      
			    }
			}
		}
		
		canvas = parentWindow.createImage((int)maxX, (int)maxY);
		
		initialized = true;
	}
	
	/**
	 * @param The point on the display window for which an interaction is being initiated.
	 * 
	 * As written, assumes no overlap of polygons in the display.
	 * 
	 * @return The HexCell containing that point.
	 */
	public CellRenderer getCellAtDisplayPoint(Point point) {
		/*
		 * Note that the point passed in will be relative to the parent window,
		 * NOT relative to the graphics rendering.  The offsets are used
		 * to adjust between them.
		 */
		CellRenderer target = null;
		Point adjustedPoint = new Point();
		adjustedPoint.setLocation(point.getX() - offsetX, point.getY() - offsetY);
		
		for (Polygon p : visualMap.keySet()) {
			if (p.contains(adjustedPoint)){
				target = visualMap.get(p);
				break;
			}
		}
		
		return target;
	}
	
	
	/**
	 * @param pass
	 * @param g
	 */
	public void drawHexArray(ERenderPass pass, Graphics g) {
		Color save = g.getColor();
		
		canvas = parentWindow.createImage((int)maxX+1, (int)maxY+1);
		
		for (Polygon p : visualMap.keySet()) {
			CellRenderer crend = (CellRenderer) visualMap.get(p);
			
			if (crend != null) {
				crend.draw(pass, p, g);
			}
			
		}		
		g.setColor(save);
	}
	
	/**
	 * @param g
	 */
	public void update(Graphics g) {
		
		if (!initialized) {
			throw new GameException("Attempted to update using an unitialized HexArrayRenderer");
		}
		
		if (controller != null) {
			controller.onPrePaint();
		}
		
		if (g != null) {
			Color backup = g.getColor();
		    
			// Rendering is done in three phases, so sprites "stack" appropriately with the background graphics.
			drawHexArray(ERenderPass.BOTTOM, g);
			drawHexArray(ERenderPass.MIDDLE, g);
			drawHexArray(ERenderPass.TOP, g);
			
			g.setColor(backup);
		}
		
		if (controller != null) {
			controller.onPostPaint();
		}
	}
	
	public double getMaxX() {
		return maxX + offsetX;
	}
	
	public double getMaxY() {
		return maxY + offsetY;
	}

	public double getMinX() {
		return minX + offsetX;
	}
	
	public double getMinY() {
		return minY + offsetY;
	}
	
	/**
	 * @return the cellSize
	 */
	public int getCellSize() {
		return cellSize;
	}

	/**
	 * @param cellSize the cellSize to set
	 */
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	/**
	 * @return the selectionColor
	 */
	public Color getSelectionColor() {
		return selectionColor;
	}

	/**
	 * @param selectionColor the selectionColor to set
	 */
	public void setSelectionColor(Color selectionColor) {
		this.selectionColor = selectionColor;
	}

	/**
	 * @return the lineColor
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor the lineColor to set
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * @return the X offset that has been set
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * @param the X offset of where the HexArrayRenderer image
	 * is going to be drawn, relative to the parent window in which
	 * it lives. Most likely, this will match the "X" parameter passed
	 * in to Graphics.drawImage during the update.
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @return the Y offset that has been set
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * @param the Y offset of where the HexArrayRenderer image
	 * is going to be drawn, relative to the parent window in which
	 * it lives. Most likely, this will match the "Y" parameter passed
	 * in to Graphics.drawImage during the update.
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * @return the parentWindow
	 */
	public Window getParentWindow() {
		return parentWindow;
	}

	/**
	 * @param parentWindow the parentWindow to set
	 */
	public void setParentWindow(Window parentWin) {
		this.parentWindow = parentWin;
	}

	/**
	 * @return the autoscale
	 */
	public boolean isAutoscale() {
		return autoscale;
	}

	/**
	 * @param autoscale "true" if the cell size should be scaled from the window space available.  "false" to use the set cell size.
	 */
	public void setAutoscale(boolean autoscale) {
		this.autoscale = autoscale;
	}

	/**
	 * @return the canvas onto which the hex map is rendered.
	 */
	public Image getCanvas() {
		return canvas;
	}

	/**
	 * @return the controller
	 */
	public HexArrayController getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(HexArrayController controller) {
		this.controller = controller;
	}
	
	/**
	 * @return
	 */
	public HashMap<IHexRenderable, Sprite> getSpriteMap() {
		return spriteMap;
	}

	/**
	 * @return the visualMap
	 */
	public Map<Polygon, CellRenderer> getVisualMap() {
		return visualMap;
	}

	/**
	 * @param visualMap the visualMap to set
	 */
	public void setVisualMap(Map<Polygon, CellRenderer> visualMap) {
		this.visualMap = visualMap;
	}
	
	
	
}
