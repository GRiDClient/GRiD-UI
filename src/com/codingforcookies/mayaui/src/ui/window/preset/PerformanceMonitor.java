package com.codingforcookies.mayaui.src.ui.window.preset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.Display;

import com.codingforcookies.mayaui.src.ui.base.MayaUpdate;
import com.codingforcookies.mayaui.src.ui.theme.MAlign;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.components.UIBarGraph;
import com.codingforcookies.mayaui.src.ui.theme.components.UIBox;
import com.codingforcookies.mayaui.src.ui.theme.components.UILabel;
import com.codingforcookies.mayaui.src.ui.theme.components.UISeparator;
import com.codingforcookies.mayaui.src.ui.window.MWindowPanel;

public class PerformanceMonitor extends MWindowPanel {
	private static HashMap<String, Section> sections = new HashMap<String, Section>();

	private static UIBarGraph totalChart, perfUpdateChart, perfRenderChart;
	
	public static PerformanceMonitor instance;
	
	public PerformanceMonitor() {
		super("Performance Monitor", 10, Display.getHeight() - 210, 350, 200);
		instance = this;
	}
	
	public void init() {
		super.init();
		this.anchor = MAlign.BOTTOMLEFT;
		
		updateComponents();
	}
	
	public void update(float delta) {
		PerformanceMonitor.startUpdateSection("perform_mon");
		
		super.update(delta);
		
		String toRemove = null;
		for(Entry<String, Section> entry : sections.entrySet()) {
			Section section = entry.getValue();
			section.update(delta);
			
			switch(section.type) {
				case TOTAL:
					totalChart.updateBar(section.name, section.average);
					break;
				case UPDATE:
					perfUpdateChart.updateBar(section.name, section.average);
					break;
				case RENDER:
					perfRenderChart.updateBar(section.name, section.average);
					break;
			}
			
			if(section.scheduledForDrop)
				toRemove = section.name;
		}
		
		if(toRemove != null) {
			sections.remove(toRemove);
			updateComponents();
		}
		
		PerformanceMonitor.endSection("perform_mon");
	}
	
	private static void updateComponents() {
		PerformanceMonitor pf = PerformanceMonitor.instance;
		if(pf == null || pf.components == null)
			return;
		
		pf.components.clear();
		
		UILabel perfLabel = new UILabel("Performance Monitor", MAlign.CENTER).setBounds(5, 5, 185, 10);
		pf.addComponent(perfLabel);
		
		totalChart = new UIBarGraph().setBounds(5, 17, 30, 175);
		pf.addComponent(totalChart);
		
		perfUpdateChart = new UIBarGraph().setBounds(40, 17, 75, 175);
		pf.addComponent(perfUpdateChart);
		
		perfRenderChart = new UIBarGraph().setBounds(120, 17, 75, 175);
		pf.addComponent(perfRenderChart);
		
		pf.addComponent(new UISeparator().setBounds(198, 20, 0, 180));
		
		
		int i = 0;
		for(Entry<String, Section> entry : sections.entrySet()) {
			Section section = entry.getValue();

			switch(section.type) {
				case TOTAL:
					totalChart.addBar(section.name, section.color);
					break;
				case UPDATE:
					perfUpdateChart.addBar(section.name, section.color);
					break;
				case RENDER:
					perfRenderChart.addBar(section.name, section.color);
					break;
			}
			
			if(section.type != Type.TOTAL) {
				pf.addComponent(new UIBox(section.color).setBounds(210, 20 + i * 15, 10, 10));
				pf.addComponent(new UILabel(section.name).setBounds(230, 20 + i * 15, 110, 10));
				i++;
			}
		}
	}
	
	public static void addSection(String type, String name, MayaColor color) {
		sections.put(name, new Section(type, name, color));
		updateComponents();
	}

	public static void removeSection(String name) {
		sections.get(name).scheduledForDrop = true;
	}
	
	public static void startUpdateSection(String name) {
		if(!sections.containsKey(name))
			addSection("update", name, new MayaColor().random());
		startSection(name);
	}
	
	public static void startRenderSection(String name) {
		if(!sections.containsKey(name))
			addSection("render", name, new MayaColor().random());
		startSection(name);
	}

	public static void endSection(String name) {
		sections.get(name).end();
	}
	
	private static void startSection(String name) {
		sections.get(name).start();
	}
}

class Section extends MayaUpdate {
	private static int MAX_STAY = 200;
	private static int ACCURACY = 50;
	
	public Type type;
	public String name = "";
	public MayaColor color;
	
	
	private long start = 0;
	private List<Float> times = new ArrayList<Float>();
	
	public float prevaverage = 0F;
	public int maxstay = 0;
	
	public float average = 0F;
	
	Section(String type, String name, MayaColor color) {
		this.type = Type.valueOf(type.toUpperCase());
		this.name = name;
		this.color = color;
	}
	
	public void start() {
		start = System.nanoTime();
	}
	
	public void end() {
		times.add(0, (float)(System.nanoTime() - start));
	}
	
	public void update(float delta) {
		if(maxstay > MAX_STAY) {
			scheduledForDrop = true;
			return;
		}
		
		while(times.size() > ACCURACY)
			times.remove(times.size() - 1);
		prevaverage = average;
		average = average() / 1000000000F;
		
		if(prevaverage == average)
			maxstay++;
		else
			maxstay = 0;
	}
	
	public float average() {
		float avg = 0F;
		for(Float flo : times)
			avg += flo;
		return avg / times.size();
	}
}

enum Type {
	TOTAL,
	RENDER,
	UPDATE;
}