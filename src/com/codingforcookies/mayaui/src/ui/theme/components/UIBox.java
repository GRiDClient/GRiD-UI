package com.codingforcookies.mayaui.src.ui.theme.components;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;

/**
 * Maya UI box component. Displays a simple box in the window.
 * @author Stumblinbear
 */
public class UIBox extends UIComponent {
	private MayaColor color;
	
	public UIBox(MayaColor color) {
		super("box");
		
		this.color = color;
	}
	
	public void update(float delta) { }
	
	public void render() {
		GL11.glTranslatef(x, y, 0F);
		RenderHelper.renderWithTheme(isHovering ? hoverClass : uiclass, width, height, color);
	}

	public UIBox setBounds(float x, float y, float width, float height) {
		return (UIBox)super.setBounds(x, y, width, height);
	}

	/**
	 * Try to never use this function. Only if ABSOLUTELY necessary. Use the theme file instead!
	 */
	public UIBox setColor(MayaColor color) {
		this.color = color;
		return this;
	}
}