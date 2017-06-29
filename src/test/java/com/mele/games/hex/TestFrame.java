package com.mele.games.hex;

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

/**
 * @author Al Mele
 *
 */
public class TestFrame extends Frame {
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
	
	/* (non-Javadoc)
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

}
