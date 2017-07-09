package com.mele.games.hex.ui;

import java.awt.Insets;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.hex.IHexEventListener;
import com.mele.games.hex.IHexResident;

/**
 * Responsible for synchronizing the state between the Model (HexArray) and View (HexArrayRenderer)
 * 
 * @author Al Mele
 *
 */
/**
 * @author Al Mele
 *
 */
public class HexArrayController {
	protected static Logger log = LogManager.getLogger(HexArrayController.class);
	
	protected HexArray model = new HexArray();
	protected HexArrayRenderer view = new HexArrayRenderer();
	protected HexInputListener input = new HexInputListener(this);
	protected CellRenderer currentCell = null;
	
	protected IHexEventListener heListener = null;
	protected DefaultHexListener defaultListener = new DefaultHexListener(this);
	
	/**
	 * @param parentWin - Provide the parent window into which the hex rendering will be done.
	 */
	public HexArrayController(Window parentWin) {
		if (parentWin != null) {
			parentWin.addMouseListener(input);	
			parentWin.addComponentListener(input);
			parentWin.addMouseMotionListener(input);
		}
		view.setParentWindow(parentWin);
		view.setController(this);
		init();
	}

	/**
	 * @return the model
	 */
	public HexArray getModel() {
		return model;
	}

	/**
	 * @return the view
	 */
	public HexArrayRenderer getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	protected void setView(HexArrayRenderer view) {
		this.view = view;
	}
	
	/**
	 * Initializes the view (hex array renderer) to the underlying game state (model).
	 */
	protected void init() {
		view.init(model);
	}
	
	/**
	 * Sizes the number of hexes in the game board.
	 * If resizing, this operation retains content in hexes which exist
	 * both in the old and new size matrix. 
	 * 
	 * For example, in a 3x3 board that is resized to 4x4,
	 * all of the original 9 hexes would retain any type
	 * and resident information they had been set up with.
	 * 
	 * @param newXSize
	 * @param newYSize
	 */
	public void size(int newXSize, int newYSize) {
		model.resize(newXSize, newYSize);
		init();
	}
	
	/**
	 * Indicates whether or not the size of the hexes will automatically
	 * be adjusted to fit the space available for the game board.
	 * 
	 * Default = true
	 * 
	 * @return
	 */
	public boolean isAutoscale() {
		return view.isAutoscale();
	}
	
	/**
	 * Configure the autoscaling setting.
	 * 
	 * @param auto Set to <code>true</code> if you would like the hex library to automatically scale the cell size
	 * to the maximum available window space.  Otherwise, false.
	 */
	public void setAutoscale(boolean auto) {
		view.setAutoscale(auto);
	}
	
	/**
	 * Called by the view prior to rendering.
	 */
	protected void onPrePaint() {
		
	}
	
	/**
	 * Called by the view subsequent to rendering
	 */
	protected void onPostPaint() {

	}
	
	/**
	 * Marks all cells in the array as unselected.
	 */
	public void clearSelection() {
		if (view != null && view.getVisualMap() != null) {
			for (CellRenderer cr : view.getVisualMap().values()) {
				cr.setSelected(false);
			}
		}
	}
	

	/**
	 * @return a list of CellRenderer objects corresponding to selected cells.
	 */
	public List <CellRenderer> selectedCells() {
		ArrayList<CellRenderer> sList = new ArrayList<CellRenderer>();
		
		if (view != null && view.getVisualMap() != null) {
			for (CellRenderer cr : view.getVisualMap().values()) {
				if (cr.isSelected()) {
					sList.add(cr);
				}
			}
		}
		
		return sList;
	}	
	
	/**
	 * @return a list of CellRenderer objects corresponding to selected cells.
	 */
	public List <CellRenderer> allCells() {
		ArrayList<CellRenderer> all = new ArrayList<CellRenderer>();
		
		if (view != null && view.getVisualMap() != null) {
			for (CellRenderer cr : view.getVisualMap().values()) {
					all.add(cr);
			}
		}
		
		return all;
	}		
	
	/**
	 * @return a list of all residents in the map
	 */
	public List <IHexResident> allResidents() {
		ArrayList<IHexResident> residents = new ArrayList<IHexResident>();
		
		for (CellRenderer cr : allCells()) {
			residents.addAll(cr.getCell().getResidents());
		}
		
		return residents;
	}
	
	/**
	 * Register a listener of your implementation which will receive all mouse and game events provided
	 * by the hexRender library.  
	 * 
	 * If no listener is registered, the default listener <code>DefaultHexListener</code> will be used.
	 * 
	 * @param listener
	 */
	public void registerHexEventListener(IHexEventListener listener) {
		heListener = listener;
	}
	
	/**
	 * Provides the registered event listener, or the default listener if there is no registered event listener.
	 * 
	 * @return
	 */
	public IHexEventListener getHexEventListener() {
		IHexEventListener whoIsListening = heListener;
		
		if (whoIsListening == null) {
			whoIsListening = defaultListener;
		}
		
		return whoIsListening;
	}
	
	/**
	 * Provides the default hex event listener.
	 * 
	 * @return
	 */
	protected IHexEventListener getHexDefaultListener() {
			return defaultListener;
	}	
	
	/**
	 * Make the borders of the parent window fit the area into which the hex map has been drawn.
	 */
	public void snap() {
		Window parentWin = view.getParentWindow();
		Insets border = parentWin.getInsets();
		parentWin.setSize((int)view.getMaxX() + border.left + border.right, (int)view.getMaxY() + border.top + border.bottom);		
	}
	
	/**
	 * @param x the X coordinate on the HexMap of the renderer to return.
	 * @param y the Y coordinate on the HexMap of the renderer to return.
	 * @return the CellRenderer for the cell at the specified location.
	 */
	public CellRenderer getCellAt(int x, int y) {
		CellRenderer cRend = null;
		
		if (model != null) {
			HexCell cell = model.getCellAt(x, y);
			
			if (cell != null) {
				cRend = cell.getRenderer();
			}
		}
		
		return cRend;
	}
	

	/**
	 * Add a resident at the specified point
	 * 
	 * @param resident
	 * @param x
	 * @param y
	 * @return <code>true</code> if the resident was added successfully, otherwise false
	 */
	public boolean addResident(IHexResident resident, int x, int y) {
		boolean added = false;
		HexCell cell = model.getCellAt(x, y);
		if (cell != null) {
			cell.addResident(resident);
			added = true;
		}
		return added;
	}	
	
	/**
	 * Given the name of a map on the class path as a resource, load the model with the map contents. 
	 * 
	 * @param mapname
	 */
	public void loadFromMap(String mapname) {
		model = MapReader.loadMapTerrain(mapname);
		init();
	}
}
