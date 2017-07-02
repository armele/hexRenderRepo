#hexRender
The hexRender project is intended to provide basic support for 2D games that are implemented on a hex-based game board.  This readme file provides a basic overview of the functionality, and describes a few key objects you will interact with.  For complete documentation, refer to the javadocs.

This library is based on a Model/View/Controller pattern, where the Model represents a map of hexes. In the example below, each coordinate pair represents a hex.  The hex (2,1) is adjacent to:
* North: (2,0)
* Northeast: (3,0)
* Southeast: (3,1)
* South: (2,2)
* Southwest: (1,1)
* Northwest: (1,0)

The nature of Hex maps are such that there is no direct east-west adjacency.

---
 
	  (0,0)	   (2,0)   (4,0)
	      (1,0)   (3,0)
	  (0,1)	   (2,1)   (4,1)
	      (1,1)   (3,1)
	  (0,2)	   (2,2)   (4,2)
	      (1,2)    (3,2)
	      
---  

The map as a whole is implemented in a `HexArray`, with each hex represented in the model by a `HexCell` object, and its display handled by a corresponding view object, the `HexArrayRenderer`, which uses a `CellRenderer` to control the display of each `HexCell` object.

##Getting Started
Interactions with the hex map library are facilitated through `HexArrayController`.  Instantiate your HexArrayController by passing in the parent window object in which your hex array map will be painted.

The controller is responsible for synchronizing the state between the Model (`HexArray`) and View (`HexArrayRenderer`), as well as providing some basic high level settings.

The map is made up of a collection of HexCell objects (rendered by CellRenderers), and each cell may have a collection of "residents" (think of them as game pieces) on the map.  Residents conform to the IHexResident interface.  Cells can be accessed from the controller by using "getCellAt()".

Example of a basic display functionality (with no game logic):

---

	import java.awt.Color;
	import java.awt.Frame;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Image;
	import java.awt.Insets;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	
	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;
	
	import com.mele.games.hex.ui.HexArrayController;
	import com.mele.games.hex.ui.HexArrayRenderer;
	
	public class ExampleMain extends Frame {
	
		private static final long serialVersionUID = 1L;
		protected static Logger log = LogManager.getLogger(TestFrame.class);
		public HexArrayController hexControl = new HexArrayController(this);
		protected boolean initialized = false;
		public boolean closed = false;
	
		protected void init() {
			setVisible(true);
			setTitle("Test Hex Frame");
			setSize(320, 320);
	
			HexArrayRenderer har = hexControl.getView();
			har.setSelectionColor(Color.green);
			har.setLineColor(new Color(125, 0, 125));
	
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					closed = true;
				}
			});
	
			hexControl.snap();
	
			initialized = true;
		}
	
		public void display() {
			if (!initialized) {
				init();
			}
	
			update(this.getGraphics());
	
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.Container#update(java.awt.Graphics)
		 */
		public void update(Graphics g) {
			Insets border = this.getInsets();
			HexArrayRenderer har = hexControl.getView();
			har.setOffsetX(border.left);
			har.setOffsetY(border.top);
	
			Image hexCanvas = har.getCanvas();
	
			// Draw the scene onto the screen
			if (hexCanvas != null) {
				Graphics2D offScreenGraphicsCtx = (Graphics2D) hexCanvas.getGraphics();
				har.update(offScreenGraphicsCtx);
				g.drawImage(hexCanvas, border.left, border.top, this);
			}
		}
	
		public static void main(String args[]) {
			TestFrame tf = new TestFrame();
			tf.hexControl.resize(5, 6);
			tf.hexControl.setAutoscale(true);
	
			tf.display();
	
			while (!tf.closed) {
				try {
					Thread.sleep(86);
					tf.display();
	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	
			tf.dispose();
		}
	
	}
---

### HexArrayController
The HexArrayController is responsible for synchronizing the state between the Model (`HexArray`) and View (`HexArrayRenderer`), as well as providing some basic high level settings.

Some important methods you will interact with (see the javadocs for full documentation):
* allCells() - Return all cells in the map, in a list. 
* getCellAt() - Returns the <code>CellRenderer</code> of the cell at the given X,Y coordinate.
* registerHexEventListener() - Used to register a class which will receive and process events thrown from the hex library.
* snap() - Make the borders of the parent window fit the area into which the hex map has been drawn.
* addResident() - Add a resident to the cell at the specified coordinate. 

### HexArray
This class represents the map of cells.  It is the "model" which the controller synchronizes to the view.  Typically there would be no need to interact with this class directly, as you can interact with the cells using <code>getCellAt()</code> from the controller.  However, there are some helpful functions to be aware of:
* globalResidentList() - This will return a list of all residents of any cells in the map.
* pathFromCell() - given a cell and a direction, return the list of all cells in that direction (with the source cell as the starting point)

### HexArrayRenderer
This class is responsible for rendering the map.
* getCanvas() - obtains the image into which the renderer draws graphics.  Refer to the example class above for usage.
* setLineColor() - the color of the line used to draw cell borders.
* setSelectionColor() - the color to highlight the border of a selected cell

### HexCell
This class represents the game state of a single cell in the map.  It has a number of helpful functions:
* addResident() - Allows you to add a new "resident" (think of it as a game piece) into the board at this location
* adjacent() - Given a map direction (defined by EHexVector), return the adjacent cell, if any
* distance() - Return the distance (in hexes) between this hex and the designated hex.

The two most important aspects of a HexCell are its `type` and its `residentList`.  For example, if the hex map represents terrain in your game, the type of the cell might be Mountain, Plains, Desert, etc.  The residents would be the (potentially) mobile game pieces.  So perhaps, Caravan, Trader, Bandit. 

In a Hex-based Minesweeper game, there may be only two types in the entire game (revealed and unrevealed), and residents may be "Bomb" or "Flagged Guess".

### Implementing a Listener
Listeners are registered with the controller to receive events that result from map interactions (whether from user interactions, say with the mouse, or from game interactions).

You will want a listener class that implements IHexEventListener:

---

	class TestEventListener implements IHexEventListener {
		@Override
		public boolean cellEvent(HexEventDetail eventDetail) {
			// Handle your event here
			
			// 'true' if the default behavior for this event should be implemented in addition to any custom logic implemented here.
			return true; 
		}
	}
	
---

and you will want to register that class with the controller:
* hexControl.registerHexEventListener(new TestEventListener());

