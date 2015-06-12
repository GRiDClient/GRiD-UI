package com.codingforcookies.mayaui.src.ui.theme.components;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;

/**
 * Maya UI close button component. A clickable button to close windows and panels.
 * @author Stumblinbear
 */
public class UIButtonClose extends UIComponent {
	public UIButtonClose() {
		super("button #close");
	}
	
	public void update(float delta) {
		if(isHovering)
			if(Mouse.isButtonDown(0)) {
				for(int i = 0; i < MayaUI.getUIManager().darken.size(); i++) {
					if(MayaUI.getUIManager().darken.get(i) == parent) {
						MayaUI.getUIManager().darken.remove(i);
						return;
					}
				}
				
				MayaUI.getUIManager().removeWindow(parent);
			}
	}
	
	public void render(float delta) {
		GL11.glTranslatef(x + 5, y - 5, 0F);
		RenderHelper.renderWithTheme(isHovering ? hoverClass : uiclass, width - 10, height - 10);
	}
	
	public UIButtonClose setBounds(float x, float y, float width, float height) {
		return (UIButtonClose)super.setBounds(x, y, width, height);
	}
}