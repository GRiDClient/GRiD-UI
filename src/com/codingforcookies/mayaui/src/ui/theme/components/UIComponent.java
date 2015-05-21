package com.codingforcookies.mayaui.src.ui.theme.components;

public abstract class UIComponent {
	public float x, y, width, height;
	
	public UIComponent(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void update();
	public abstract void render();
}