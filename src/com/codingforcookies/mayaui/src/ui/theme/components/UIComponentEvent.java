package com.codingforcookies.mayaui.src.ui.theme.components;

public abstract class UIComponentEvent {
	public void onHover(UIComponent component, boolean isHovering) { }
	public abstract void onClick(UIComponent component);
	public void onDrag(UIComponent component, float x, float y) { }
}