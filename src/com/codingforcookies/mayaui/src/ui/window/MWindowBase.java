package com.codingforcookies.mayaui.src.ui.window;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaclientapi.src.MayaAPI;
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
		this.y = MayaAPI.SCREEN_HEIGHT - y;
		this.width = width;
		this.height = height;
		
		components = new ArrayList<UIComponent>();
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
			UIComponent component = components.get(i);
			
			float mouseX = Mouse.getX() - x;
			float mouseY = y - Mouse.getY();
			
			if(mouseX > 0 && mouseX < width &&
					mouseY > 0 && mouseY < height) {
				float componentX = component.x + 3F;
				float componentY = -component.y - (this instanceof MWindow ? ((MWindow)this).titleHeight / 2 : 0F) + 3F;
				
				if(mouseX > componentX && mouseX < componentX + component.width &&
						mouseY > componentY && mouseY < componentY + component.height) {
					component.isHovering = true;
					component.componentEvent.onHover(component, true);
					
					if(Mouse.isButtonDown(0)) {
						if(!component.isGrabbed) {
							component.isGrabbed = true;
							component.componentEvent.onClick(component);
						}
					}
				}else if(component.isHovering) {
					component.isHovering = false;
					component.componentEvent.onHover(component, false);
				}
				
				if(component.isGrabbed) {
					if(!Mouse.isButtonDown(0))
						component.isGrabbed = false;
					else
						component.componentEvent.onDrag(component, mouseX, mouseY);
				}
			}
			
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
		components.add(component.setParent(this));
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