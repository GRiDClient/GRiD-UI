package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.RenderHelper;

/**
 * Maya UI separator component. Displays a line separator in the window.
 * @author Stumblinbear
 */
public class UISeparator extends UIComponent {
	public UISeparator() {
		super("separator");
	}
	
	public void update() { }
	
	public void render() {
		if(width >= height)
			RenderHelper.renderWithTheme(uiclass, width, 2);
		else
			RenderHelper.renderWithTheme(uiclass, 2, height);
	}
	
	public UISeparator setBounds(float x, float y, float width, float height) {
		return (UISeparator)super.setBounds(x, y, width, height);
	}
}