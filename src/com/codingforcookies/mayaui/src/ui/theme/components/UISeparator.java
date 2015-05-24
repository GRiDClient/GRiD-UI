package com.codingforcookies.mayaui.src.ui.theme.components;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.RenderHelper;

/**
 * Maya UI separator component. Displays a line separator in the window.
 * @author Stumblinbear
 */
public class UISeparator extends UIComponent {
	public UISeparator() {
		super("separator");
	}
	
	public void update(float delta) { }
	
	public void render() {
		GL11.glTranslatef(x, y, 0F);

		if(width >= height)
			RenderHelper.renderWithTheme(uiclass, width, 0);
		else
			RenderHelper.renderWithTheme(uiclass, 0, height);
	}
	
	public UISeparator setBounds(float x, float y, float width, float height) {
		return (UISeparator)super.setBounds(x, y, width, height);
	}
}