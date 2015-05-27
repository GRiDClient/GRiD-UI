package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.base.MayaRender;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;

/**
 * Common class for all Maya UI Components.
 * @author Stumblinbear
 */
public abstract class UIComponent extends MayaRender {
	protected MayaRender parent;
	
	/**
	 * The theme class for the component.
	 */
	protected UIClass uiclass;
	protected UIClass hoverClass;

	public UIComponentEvent componentEvent = new DefaultComponentEvent();
	public boolean isGrabbed = false;
	public boolean isHovering = false;
	
	public UIComponent(String uiclass) {
		this.uiclass = ThemeManager.getTheme().getClass(uiclass);
		hoverClass = !this.uiclass.getClass(":hover").name.equals("global") ? this.uiclass.getClass(":hover") : this.uiclass;
	}
	
	/**
	 * Set the location, width, and height of the component.
	 */
	public UIComponent setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = -y;
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	public UIComponent setParent(MayaRender parent) {
		this.parent = parent;
		return this;
	}
	
	public UIComponent setEvent(UIComponentEvent componentEvent) {
		this.componentEvent = componentEvent;
		return this;
	}
}

class DefaultComponentEvent extends UIComponentEvent {
	public void onClick(UIComponent component) {
		
	}
}