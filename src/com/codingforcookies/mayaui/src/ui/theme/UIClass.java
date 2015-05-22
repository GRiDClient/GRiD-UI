package com.codingforcookies.mayaui.src.ui.theme;

import java.util.HashMap;

import com.codingforcookies.mayaui.src.MayaUI;

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
	
	public String name;
	
	/**
	 * A hashmap of all values in a class.
	 */
	protected HashMap<String, Object> values;

	protected UIClass(UIClass parent, String name) {
		classes = new HashMap<String, UIClass>();
		this.parent = parent;
		
		this.name = name;
		values = new HashMap<String, Object>();
	}
	
	/**
	 * Parse and set a value.
	 */
	public void set(String name, Object value) {
		try {
			if(value instanceof String) {
				value = MayaUI.parseConfigValue(value.toString());
				
				if(name.contains("color")) {
					((MayaColor)value).setAlpha(get("opacity", new Float(0F), 1F));
				}else if(name.equals("opacity")) {
					MayaColor color = get("background-color", new MayaColor(), new MayaColor()).clone();
					color.setAlpha(Float.parseFloat(value.toString()));
					value = color;
					name = "background-color";
				}else if(name.startsWith("border")) {
					MBorder tmpborder = get("border", new MBorder(), new MBorder()).clone();
					tmpborder.parse(name, value.toString());
					value = tmpborder;
					name = "border";
				}
			}
			
			values.put(name, value);
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
	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype) {
		if(!values.containsKey(name)) {
			if(parent == null)
				return null;
			return parent.get(name, returntype);
		}
		return (T)values.get(name);
	}
	
	/**
	 * Get a value with a fallback default value.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype, T defaultValue) {
		if(!values.containsKey(name)) {
			if(parent == null)
				return defaultValue;
			return parent.get(name, returntype, defaultValue);
		}
		return (T)values.get(name);
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
	
	public String toString() {
		return "UIClass(" + ThemeManager.getTheme().buildParentList(this, name) + ")";
	}
}