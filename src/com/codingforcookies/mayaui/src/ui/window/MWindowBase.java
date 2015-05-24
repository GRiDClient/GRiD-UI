package com.codingforcookies.mayaui.src.ui.window;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.base.MayaRender;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.components.UIComponent;

/**
 * Base class for Maya UI windows and panels.
 * @author Stumblinbear
 */
public class MWindowBase extends MayaRender {
	/**
	 * The theme class for the window.
	 */
	public UIClass uiclass;
	
	/**
	 * A list of all the components in the window.
	 */
	protected List<UIComponent> components;
	
	/**
	 * The title of the window.
	 */
	public String title = "";
	
	public MWindowBase(String title, float x, float y, float width, float height) {
		this.title = title;
		
		this.x = x;
		this.y = MayaUI.SCREEN_HEIGHT - y;
		this.width = width;
		this.height = height;
		
		components = new ArrayList<UIComponent>();
		
		init();
	}
	
	/**
	 * The initialization function. Add components here.
	 */
	public void init() {
		
	}
	
	/**
	 * Updates all components in the window.
	 */
	public void update(float delta) {
		for(int i = 0; i < components.size(); i++) {
			components.get(i).update(delta);
			if(components.get(i).scheduledForDrop) {
				components.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Renders all components in the window.
	 */
	public void render() {
		GL11.glTranslatef(x, y, 0F);
		drawComponents();
	}
	
	/**
	 * Add a component to the window.
	 */
	public void addComponent(UIComponent component) {
		components.add(component);
	}
	
	/**
	 * Returns a list of all the components in the window
	 */
	public List<UIComponent> getComponents() {
		return components;
	}

	/**
	 * For render functions that override the super render function. Just draws the components.
	 */
	public void drawComponents() {
		for(UIComponent component : components)
			component.startRender();
	}
}