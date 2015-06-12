package com.codingforcookies.mayaui.src.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.events.EventHandler;
import com.codingforcookies.mayaui.src.input.InputHelper;
import com.codingforcookies.mayaui.src.ui.base.MayaRender;
import com.codingforcookies.mayaui.src.ui.base.MayaUpdate;
import com.codingforcookies.mayaui.src.ui.window.MWindowBase;

/**
 * Handles all rendering and updating for Maya UI windows.
 * @author Stumblinbear
 */
public class UIManager {
	/**
	 * Prevents any window below being updated.
	 */
	public List<MWindowBase> darken = new ArrayList<MWindowBase>();
	
	/**
	 * A list of all components to render.
	 */
	public List<MayaRender> renders;
	/**
	 * A list of all components to update.
	 */
	public Stack<MayaUpdate> updates;
	
	public UIManager() {
		renders = new ArrayList<MayaRender>();
		updates = new Stack<MayaUpdate>();
	}
	
	/**
	 * Do the render functions.
	 */
	public void doRenderUI(float delta) {
		for(int i = 0; i < renders.size(); i++) {
			if(renders.get(i).scheduledForDrop) {
				renders.remove(i);
				i--;
			}else
				renders.get(i).startRender(delta);
		}
		
		if(darken.size() > 0) {
			GL11.glPushMatrix();
			{
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(0F, 0F, 0F, .3F);
				
				RenderHelper.renderBox(0, 0, MayaUI.SCREEN_WIDTH, -MayaUI.SCREEN_WIDTH);
			}
			GL11.glPopMatrix();
			
			for(int i = 0; i < darken.size(); i++)
				darken.get(i).startRender(delta);
		}

		EventHandler.triggerEvent("EventRender");
	}
	
	/**
	 * Execute the update functions starting from the top of the stack, and dropping as needed.
	 */
	public void doUpdateUI(float delta) {
		InputHelper.update();
		
		if(darken.size() == 0) {
			int size = updates.size();
			
			for(int i = 0; i < size; i++) {
				if(!updates.peek().scheduledForDrop)
					updates.add(0, updates.peek());
				updates.pop().update(delta);
			}
		}else
			for(int i = 0; i < darken.size(); i++)
				darken.get(i).update(delta);
		
		EventHandler.triggerEvent("EventUpdate");
	}
	
	/**
	 * Add a new render to the list.
	 */
	public void newRender(MayaRender mayaRender) {
		renders.add(mayaRender);
	}

	/**
	 * Add a new render to the list with an order index; 0 = top
	 */
	public void newRender(int zindex, MayaRender mayaRender) {
		renders.add(renders.size() - zindex, mayaRender);
	}

	/**
	 * Add a new update to the list with normal priority.
	 */
	public void newUpdate(MayaUpdate mayaUpdate) {
		newUpdate(MayaPriority.NORMAL, mayaUpdate);
	}

	/**
	 * Add a new render to the list.
	 */
	public void newUpdate(MayaPriority priority, MayaUpdate mayaUpdate) {
		switch(priority) {
			case HIGH:
				updates.push(mayaUpdate);
				break;
			case NORMAL:
				updates.add(updates.size() / 2, mayaUpdate);
				break;
			case LOW:
				updates.add(updates.size() - 1, mayaUpdate);
				break;
		}
	}
	
	public void createDarken(MWindowBase mayaWindow) {
		mayaWindow.init();
		darken.add(mayaWindow);
	}
	
	/**
	 * Various create window functions.
	 */
	public void createWindow(MWindowBase mayaWindow) {
		createWindow(MayaPriority.NORMAL, 0, mayaWindow);
	}
	
	/**
	 * Various create window functions.
	 */
	public void createWindow(int zindex, MWindowBase mayaWindow) {
		createWindow(MayaPriority.NORMAL, zindex, mayaWindow);
	}
	
	/**
	 * Various create window functions.
	 */
	public void createWindow(MayaPriority priority, MWindowBase mayaWindow) {
		createWindow(priority, 0, mayaWindow);
	}
	
	/**
	 * Adds a window to the render and update lists.
	 */
	public void createWindow(MayaPriority priority, int zindex, MWindowBase mayaWindow) {
		if(MayaUI.getUIManager().isWindowOpen(mayaWindow.title))
			bringWindow(mayaWindow.title);
		else{
			mayaWindow.init();
			newUpdate(priority, mayaWindow);
			newRender(zindex, mayaWindow);
		}
	}
	
	public void removeWindow(MayaRender mayaRender) {
		if(mayaRender instanceof MWindowBase)
			((MWindowBase)mayaRender).onClose();
		for(int i = 0; i < renders.size(); i++)
			if(renders.get(i) == mayaRender) {
				renders.remove(i);
				break;
			}
		for(int i = 0; i < updates.size(); i++)
			if(updates.get(i) == mayaRender) {
				updates.remove(i);
				break;
			}
	}
	
	/**
	 * Force a window to the front of the render list.
	 */
	public void bringWindow(MWindowBase mayaWindow) {
		for(int i = 0; i < renders.size(); i++)
			if(renders.get(i) == mayaWindow) {
				renders.add(renders.get(i));
				renders.remove(i);
			}
	}
	
	/**
	 * Force a window to the front of the render list.
	 */
	public void bringWindow(String windowTitle) {
		for(int i = 0; i < renders.size(); i++) {
			if(!(renders.get(i) instanceof MWindowBase))
				continue;
			
			if(((MWindowBase)renders.get(i)).title == windowTitle) {
				renders.add(renders.get(i));
				renders.remove(i);
			}
		}
	}
	
	public boolean isWindowOpen(String name) {
		for(int i = 0; i < renders.size(); i++)
			if(renders.get(i) instanceof MWindowBase && ((MWindowBase)renders.get(i)).title.equals(name))
				return true;
		return false;
	}

	public void onWindowResized(boolean skipEvent, int changewidth, int changeheight) {
		for(MWindowBase render : darken) {
			switch(render.anchor) {
				case TOPLEFT:
					render.y += changeheight;
					break;
				case TOPRIGHT:
					render.y += changeheight;
					render.x += changewidth;
					break;
				case BOTTOMRIGHT:
					render.x += changewidth;
					break;
				default:
					break;
			}
			
			if(skipEvent)
				render.onWindowResized(changewidth, changeheight);
		}
		
		for(MayaRender render : renders) {
			switch(render.anchor) {
				case TOPLEFT:
					render.y += changeheight;
					break;
				case TOPRIGHT:
					render.y += changeheight;
					render.x += changewidth;
					break;
				case BOTTOMRIGHT:
					render.x += changewidth;
					break;
				default:
					break;
			}
			
			if(skipEvent)
				render.onWindowResized(changewidth, changeheight);
		}
	}
}