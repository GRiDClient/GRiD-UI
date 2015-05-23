package com.codingforcookies.mayaui.src.ui;

import com.codingforcookies.mayaui.src.ui.theme.MAlign;

/**
 * Common abstract class for render components.
 * @author Stumblinbear
 */
public abstract class MayaRender extends MayaUpdate {
	public float x, y, width, height;
	
	public MAlign anchor = MAlign.BOTTOMLEFT;
	public MayaRender setAnchor(MAlign anchor) {
		this.anchor = anchor;
		return this;
	}
	
	public abstract void render();
	
	public void onWindowResized(int changewidth, int changeheight) {
		
	}
}