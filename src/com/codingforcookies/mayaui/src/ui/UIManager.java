package com.codingforcookies.mayaui.src.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Handles all rendering and updating for Maya UI windows.
 * @author Stumblinbear
 */
public class UIManager {
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
	public void doRenderUI() {
		for(int i = 0; i < renders.size(); i++) {
			if(renders.get(i).scheduledForDrop)
				renders.remove(i);
			else
				renders.get(i).render();
		}
	}
	
	/**
	 * Execute the update functions starting from the top of the stack, and dropping as needed.
	 */
	public void doUpdateUI() {
		int size = updates.size();
		
		for(int i = 0; i < size; i++) {
			if(!updates.peek().scheduledForDrop)
				updates.add(0, updates.peek());
			updates.pop().update();
		}
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
		newRender(zindex, mayaWindow);
		newUpdate(priority, mayaWindow);
	}
	
	/**
	 * Force a window to the front of the render list.
	 */
	public void bringWindow(MWindow mayaWindow) {
		for(int i = 0; i < renders.size(); i++)
			if(renders.get(i) == mayaWindow) {
				renders.add(renders.get(i));
				renders.remove(i);
			}
	}
}