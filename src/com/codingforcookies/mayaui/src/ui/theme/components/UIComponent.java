package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.MayaRender;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;

/**
 * Common class for all Maya UI Components.
 * @author Stumblinbear
 */
public abstract class UIComponent extends MayaRender {
	/**
	 * The theme class for the component.
	 */
	protected UIClass uiclass;
	public float x, y, width, height;
	
	public UIComponent(String uiclass) {
		this.uiclass = ThemeManager.getTheme().getClass(uiclass);
	}

	/**
	 * Set the location, width, and height of the component.
	 */
	public UIComponent setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = -y;
		this.width = width;
		this.height = height;
		
		return this;
	}
}