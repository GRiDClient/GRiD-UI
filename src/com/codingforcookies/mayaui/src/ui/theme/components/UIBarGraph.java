package com.codingforcookies.mayaui.src.ui.theme.components;

import java.util.HashMap;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;

public class UIBarGraph extends UIComponent {
	private HashMap<String, UIBarGraphBar> bars = new HashMap<String, UIBarGraphBar>();

	public UIBarGraphBar addBar(String name, MayaColor color) {
		return addBar(name, color, 0);
	}
	
	public UIBarGraphBar addBar(String name, MayaColor color, int value) {
		UIBarGraphBar bar = new UIBarGraphBar(name, color);
		bar.value = value;
		bars.put(name, bar);
		return bar;
	}
	
	public void removeBar(String name) {
		bars.remove(name);
	}

	public void updateBar(String name, int value) {
		bars.get(name).value = value;
	}
	
	public UIBarGraph(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void update() {
		float total = 1;
		for(String bar : bars.keySet())
			total += bars.get(bar).value;
		
		for(String bar : bars.keySet())
			bars.get(bar).percent = bars.get(bar).value / total;
	}
	
	public void render() {
		int num = 0;
		
		for(String bar : bars.keySet()) {
			UIBarGraphBar thebar = bars.get(bar);
			
			thebar.color.use();
			float i = (width / bars.size());
			RenderHelper.renderBox(x + i * num + i / 2 - 8, y + height - thebar.percent * height, 15, thebar.percent * height);
			
			num++;
		}
	}
}