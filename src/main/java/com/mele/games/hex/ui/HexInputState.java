package com.mele.games.hex.ui;

public class HexInputState {
	protected boolean lmousedown;
	protected boolean rmousedown;

	/**
	 * @return whether or not the left mouse button is down
	 */
	public boolean isLeftMousedown() {
		return lmousedown;
	}

	/**
	 * @param mousedown whether or not the left mouse button is currently down.
	 */
	protected void setLeftMousedown(boolean mousedown) {
		this.lmousedown = mousedown;
	}
	/**
	 * @return whether or not the right mouse button is down
	 */
	public boolean isRightMousedown() {
		return rmousedown;
	}

	/**
	 * @param mousedown whether or not the right mouse button is currently down.
	 */
	protected void setRightMousedown(boolean mousedown) {
		this.rmousedown = mousedown;
	}
	
	
}
