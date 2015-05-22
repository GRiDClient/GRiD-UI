package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;

/**
 * Maya UI separator component. Displays a line separator in the window.
 * @author Stumblinbear
 */
public class UISeparator extends UIComponent {
	private MayaColor color;
	
	public UISeparator() {
		super("separator");
		
		color = ThemeManager.getTheme().getClass("separator").get("line-color", new MayaColor(), MayaColor.BLACK);
	}
	
	public void update() { }
	
	public void render() {
		if(width >= height)
			RenderHelper.renderBox(x, y, width, 2, color);
		else
			RenderHelper.renderBox(x, y, 2, height, color);
	}
	
	public UISeparator setBounds(float x, float y, float width, float height) {
		return (UISeparator)super.setBounds(x, y, width, height);
	}

	/**
	 * Try to never use this function. Only if ABSOLUTELY necessary. Use the theme file instead!
	 */
	public UISeparator setColor(MayaColor color) {
		this.color = color;
		return this;
	}
}