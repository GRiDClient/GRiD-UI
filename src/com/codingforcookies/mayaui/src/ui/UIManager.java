package com.codingforcookies.mayaui.src.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UIManager {
	public List<MayaRender> renders;
	public Stack<MayaUpdate> updates;
	
	public UIManager() {
		renders = new ArrayList<MayaRender>();
		updates = new Stack<MayaUpdate>();
	}

	public void doRenderUI() {
		for(MayaRender render : renders)
			render.render();
	}

	public void doUpdateUI() {
		int size = updates.size();
		
		for(int i = 0; i < size; i++) {
			if(!updates.peek().scheduledForDrop)
				updates.add(0, updates.peek());
			updates.pop().update();
		}
	}
	
	public void newRender(MayaRender mayaRender) {
		renders.add(mayaRender);
	}
	
	public void newRender(int zindex, MayaRender mayaRender) {
		renders.add(zindex, mayaRender);
	}
	
	public void newUpdate(MayaUpdate mayaUpdate) {
		newUpdate(MayaPriority.NORMAL, mayaUpdate);
	}
	
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

	public void newWindow(MayaWindow mayaWindow) {
		newWindow(MayaPriority.NORMAL, 0, mayaWindow);
	}

	public void newWindow(int zindex, MayaWindow mayaWindow) {
		newWindow(MayaPriority.NORMAL, zindex, mayaWindow);
	}

	public void newWindow(MayaPriority priority, MayaWindow mayaWindow) {
		newWindow(priority, 0, mayaWindow);
	}
	
	public void newWindow(MayaPriority priority, int zindex, MayaWindow mayaWindow) {
		newRender(zindex, mayaWindow);
		newUpdate(priority, mayaWindow);
	}
}