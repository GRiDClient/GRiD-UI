package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.MayaBorder;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class RenderHelper {
	/**
	 * @param theme The theme instance
	 * @param type The name of the thing being rendered ex. window
	 */
	public static void renderWithTheme(UITheme theme, String type, float x, float y, float width, float height) {
		UIClass themeclass = theme.get(type);
		MayaColor color = themeclass.get("background-color", new MayaColor());
		color.use();
	    
		if(color.getAlpha() != 1F) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		
		renderBox(x, y, width, height);
		
		if(themeclass.has("border"))
			themeclass.get("border", new MayaBorder()).render(x, y, width, height);
	    
		if(color.getAlpha() != 1F)
			GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void renderWithTheme(UITheme theme, String type, float x, float y) {
		renderWithTheme(theme, type, x, y, theme.get(type).get("width", new Integer(0)).floatValue(), theme.get(type).get("height", new Integer(0), 0).floatValue());
	}
	
	public static void renderWithTheme(UITheme theme, String type, float x, float y, float width) {
		renderWithTheme(theme, type, x, y, width, theme.get(type).get("height", new Integer(0), 0).floatValue());
	}

	public static void renderBox(float x, float y, float width, float height) {
		GL11.glBegin(GL11.GL_QUADS);
	    {
	        GL11.glVertex2f(x, MayaUI.SCREEN_HEIGHT - y);
			GL11.glVertex2f(x + width, MayaUI.SCREEN_HEIGHT - y);
			GL11.glVertex2f(x + width, MayaUI.SCREEN_HEIGHT - y - height);
			GL11.glVertex2f(x, MayaUI.SCREEN_HEIGHT - y - height);
	    }
	    GL11.glEnd();
	}
}