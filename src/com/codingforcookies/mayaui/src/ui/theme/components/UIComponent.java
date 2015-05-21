package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;


public abstract class UIComponent {
	protected UIClass uiclass;
	public float x, y, width, height;
	
	public UIComponent(String uiclass) {
		this.uiclass = ThemeManager.getTheme().getClass(uiclass);
	}
	
	public UIComponent setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = -y;
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	public abstract void update();
	public abstract void render();
}