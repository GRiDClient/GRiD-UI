package com.codingforcookies.mayaui.src.ui.theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionParser;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionRuntime;

/**
 * Holds class value and information, parent class, as well as child classes.
 * @author Stumblinbear
 */
public class UIClass {
	/**
	 * Hashmap of next-level children classes.
	 */
	protected HashMap<String, UIClass> classes;
	/**
	 * The parent class. Highest level is "global".
	 */
	protected UIClass parent;

	public UIClass getParent() {
		return parent;
	}
	
	public String name;
	
	/**
	 * A hashmap of all values in a class.
	 */
	protected HashMap<String, MOptionParser> values;
	/**
	 * Contains the values to run at which time. PreRender, PostRender, etc.
	 */
	protected List<String>[] valueTimes;
	
	@SuppressWarnings("unchecked")
	protected UIClass(UIClass parent, String name) {
		classes = new HashMap<String, UIClass>();
		this.parent = parent;
		
		this.name = name;
		values = new HashMap<String, MOptionParser>();
		
		valueTimes = new ArrayList[MOptionRuntime.values().length];
		for(int i = 0; i < valueTimes.length; i++)
			valueTimes[i] = new ArrayList<String>();
	}
	
	/**
	 * Parse and set a value.
	 */
	public void set(String key, MOptionParser value) {
		try {
			if(value != null && ((MOptionParser)value).getRuntime() != null)
				for(MOptionRuntime runtime : ((MOptionParser)value).getRuntime())
					valueTimes[runtime.ordinal()].add(key);
			values.put(key, value);
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * If has a child class.
	 */
	public boolean hasClass(String name) {
		return classes.containsKey(name);
	}
	
	/**
	 * Get a child class.
	 */
	public UIClass getClass(String name) {
		if(!classes.containsKey(name)) {
			if(this.name == "global")
				return this;
			return parent.getClass(name);
		}
		return classes.get(name);
	}

	/**
	 * Get a value.
	 */
	public MOptionParser get(String name) {
		if(!values.containsKey(name)) {
			if(parent == null)
				return null;
			return parent.get(name);
		}
		
		return values.get(name);
	}
	
	/**
	 * Get the class of a value.
	 */
	public Class<?> type(String name) {
		return values.get(name).getClass();
	}
	
	/**
	 * If class contains a value.
	 */
	public boolean has(String name) {
		return values.containsKey(name);
	}
	
	public void run(MOptionRuntime runtime, float width, float height) {
		run(runtime, width, height, true);
	}
	
	private void run(MOptionRuntime runtime, float width, float height, boolean top) {
		if(parent != null)
			parent.run(runtime, width, height, false);
		for(String str : valueTimes[runtime.ordinal()]) {
			if(values.get(str) == null)
				continue;
			
			if(!top ? values.get(str).shouldRunParented() : true)
				values.get(str).run(runtime, width, height);
		}
	}
	
	public String toString() {
		return "UIClass(" + ThemeManager.getTheme().buildParentList(this, name) + ")";
	}
}