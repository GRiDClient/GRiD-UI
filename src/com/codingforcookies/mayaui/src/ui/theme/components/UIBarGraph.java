package com.codingforcookies.mayaui.src.ui.theme.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;

/**
 * Maya UI bar graph component. Displays a bar graph in the window.
 * @author Stumblinbear
 */
public class UIBarGraph extends UIComponent {
	private UIClass uibarclass;
	
	private HashMap<String, UIBarGraphBar> barInfo = new HashMap<String, UIBarGraphBar>();
	private List<String> bars = new ArrayList<String>();
	
	public UIBarGraph() {
		super("bargraph");
		
		uibarclass = uiclass.getClass(".bar");
	}
	
	public void update() {
		float total = 0F;
		
		for(String bar : bars)
			total += barInfo.get(bar).value;
		
		if(total != 0F)
			for(String bar : bars)
				barInfo.get(bar).percent = barInfo.get(bar).value / total;
	}
	
	public void render() {
		int num = 0;
		
		//RenderHelper.renderBox(x, y, width, height);
		
		for(String bar : bars) {
			UIBarGraphBar thebar = barInfo.get(bar);
			
			float i = (width / bars.size());
			
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(x + i * num + 2, y, 0F);
				RenderHelper.renderWithTheme(uibarclass, i - 4, thebar.percent * height, thebar.color);
			}
			GL11.glPopMatrix();
			
			num++;
		}
	}
	
	public UIBarGraph setBounds(float x, float y, float width, float height) {
		return (UIBarGraph)super.setBounds(x, y, width, height);
	}
	
	/**
	 * Add a bar to the graph.
	 */
	public UIBarGraphBar addBar(String name, MayaColor color) {
		return addBar(name, color, 0);
	}
	
	/**
	 * Add a bar to the graph with a set value.
	 */
	public UIBarGraphBar addBar(String name, MayaColor color, float value) {
		UIBarGraphBar bar = new UIBarGraphBar(name, color);
		bar.value = value;
		barInfo.put(name, bar);
		bars.add(name);
		return bar;
	}
	
	/**
	 * Remove a bar from the graph.
	 */
	public void removeBar(String name) {
		barInfo.remove(name);
		bars.remove(name);
	}
	
	/**
	 * Change a bar's value.
	 */
	public void updateBar(String name, float value) {
		barInfo.get(name).value = value;
	}
}