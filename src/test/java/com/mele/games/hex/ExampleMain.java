package com.mele.games.hex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mele.games.animation.ERenderPass;
import com.mele.games.animation.SpriteFactory;
import com.mele.games.animation.SpriteFactoryDescriptor;
import com.mele.games.hex.ui.HexArrayController;
import com.mele.games.hex.ui.HexArrayRenderer;
import com.mele.games.hex.ui.HexEventDetail;

/**
 * Helper class - test listener.
 * 
 * @author Al Mele
 *
 */
class TestEventListener implements IHexEventListener {
	protected ArrayList<HexEventDetail> detail = new ArrayList<HexEventDetail>();
	protected boolean keepDefault = true;
	
	@Override
	public boolean cellEvent(HexEventDetail eventDetail) {
		detail.add(eventDetail);
		return true;
	}
	
	public HexEventDetail getHexEventDetail(int index)  {
		return detail.get(index);
	}
}

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
		TestEventListener evListener = new TestEventListener();
		hexControl.registerHexEventListener(evListener);
		
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

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		ExampleMain tf = new ExampleMain();
		
		SpriteFactoryDescriptor sd = new SpriteFactoryDescriptor();
		sd.addImageFrames("/com/mele/hexrender/Boulder.png", 1, 0);
		sd.setRenderPass(ERenderPass.BOTTOM);
		SpriteFactory.registerSprite("BOULDER", sd);			
		
		tf.hexControl.size(5, 6);
		tf.hexControl.setAutoscale(true);
		tf.hexControl.registerHexEventListener(new TestEventListener());
		
		Font font = new Font("Serif", Font.BOLD, 12);
		tf.hexControl.getCellAt(1, 1).setLabelFont(font);
		tf.hexControl.getCellAt(1, 1).getCell().setLabel("TEST");
		tf.hexControl.getCellAt(1, 1).getCell().setType(new TestType());
		tf.hexControl.getCellAt(4, 4).getCell().setType(new TestType());
		
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
