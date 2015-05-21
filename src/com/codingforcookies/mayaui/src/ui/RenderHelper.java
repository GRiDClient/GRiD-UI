package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.theme.MBorder;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class RenderHelper {
	/**
	 * @param theme The theme instance
	 * @param type The name of the thing being rendered ex. window
	 */
	public static void renderWithTheme(UITheme theme, UIClass uiclass, float width, float height) {
		MayaColor color = uiclass.get("background-color", new MayaColor(), MayaColor.GLOBAL_BACKGROUND);
		color.use();
		
		if(color.getAlpha() != 1F) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		
		renderBox(0, 0, width, height);
		
		if(uiclass.has("border"))
			uiclass.get("border", new MBorder()).render(width, height);
		
		if(color.getAlpha() != 1F)
			GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void renderWithTheme(UITheme theme, UIClass uiclass, float width) {
		renderWithTheme(theme, uiclass, width, uiclass.get("height", new Integer(0), 0).floatValue());
	}
	
	public static void renderBox(float x, float y, float width, float height, MayaColor color) {
		color.use();
		renderBox(x, y, width, height);
	}

	public static void renderBox(float x, float y, float width, float height) {
		GL11.glBegin(GL11.GL_QUADS);
	    {
	        GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y - height);
			GL11.glVertex2f(x, y - height);
	    }
	    GL11.glEnd();
	}
}