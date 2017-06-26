package com.mele.games.hex.ui;

import java.awt.Window;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.hex.HexArray;
import com.mele.games.hex.IHexEventListener;

/**
 * Responsible for synchronizing the state between the Model (HexArray) and View (HexArrayRenderer)
 * 
 * @author Al Mele
 *
 */
public class HexArrayController {
	protected static Logger log = LogManager.getLogger(HexArrayController.class);
	
	protected HexArray model = new HexArray();
	protected HexArrayRenderer view = new HexArrayRenderer();
	protected HexInputListener input = new HexInputListener(this);
	protected CellRenderer currentCell = null;
	protected boolean snap = true;
	
	protected IHexEventListener heListener = null;
	protected DefaultHexListener defaultListener = new DefaultHexListener(this);
	
	/**
	 * @param parentWin
	 */
	public HexArrayController(Window parentWin) {
		parentWin.addMouseListener(input);	
		parentWin.addComponentListener(input);
		parentWin.addMouseMotionListener(input);
		view.setParentWindow(parentWin);
		view.setController(this);
	}

	/**
	 * @return the model
	 */
	public HexArray getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(HexArray model) {
		this.model = model;
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
	public void setView(HexArrayRenderer view) {
		this.view = view;
	}
	
	/**
	 * Initializes the view (hex array renderer) to the underlying game state (model).
	 */
	public void init() {
		view.init(model);
	}
	
	/**
	 * Resizes the game board (retaining pieces that exist in both areas)
	 * 
	 * @param newXSize
	 * @param newYSize
	 */
	public void resize(int newXSize, int newYSize) {
		model.resize(newXSize, newYSize);
		init();
	}
	
	public boolean isAutoscale() {
		return view.isAutoscale();
	}
	
	/**
	 * @param auto
	 */
	public void setAutoscale(boolean auto) {
		view.setAutoscale(auto);
	}

	/**
	 * @return whether or not the parent window will be automatically resized to fit the hex map
	 */
	public boolean isSnap() {
		return snap;
	}

	/**
	 * @param snap "true" if the parent window will be automatically resized to fit the hex map, otherwise "false"
	 * TODO: Implement this functionality.
	 */
	public void setSnap(boolean snap) {
		this.snap = snap;
	}
	
	/**
	 * Called by the view prior to rendering.
	 */
	public void onPrePaint() {
		
	}
	
	/**
	 * Called by the view subsequent to rendering
	 */
	public void onPostPaint() {

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
	 * @return the currentCell
	 */
	public CellRenderer getCurrentCell() {
		return currentCell;
	}

	/**
	 * @param currentCell the currentCell to set
	 */
	public void setCurrentCell(CellRenderer currentCell) {
		this.currentCell = currentCell;
	}
	
	/**
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
	public IHexEventListener getHexDefaultListener() {
			return defaultListener;
	}	
}
