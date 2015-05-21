package com.codingforcookies.mayaui.src.ui.theme;

import java.util.HashMap;

import com.codingforcookies.mayaui.src.MayaUI;

public class UIClass {
	/**
	 * Sub classes
	 */
	protected HashMap<String, UIClass> classes;
	protected UIClass parent;
	
	public String name;
	protected HashMap<String, Object> values;

	protected UIClass(UIClass parent, String name) {
		classes = new HashMap<String, UIClass>();
		this.parent = parent;
		
		this.name = name;
		values = new HashMap<String, Object>();
	}

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

	public boolean hasClass(String name) {
		return classes.containsKey(name);
	}
	
	public UIClass getClass(String name) {
		if(!classes.containsKey(name))
			return parent.getClass("global");
		return classes.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype) {
		if(!values.containsKey(name)) {
			if(parent == null)
				return null;
			return parent.get(name, returntype);
		}
		return (T)values.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype, T defaultValue) {
		if(!values.containsKey(name)) {
			if(parent == null)
				return defaultValue;
			return parent.get(name, returntype, defaultValue);
		}
		return (T)values.get(name);
	}
	
	public Class<?> type(String name) {
		return values.get(name).getClass();
	}
	
	public boolean has(String name) {
		return values.containsKey(name);
	}
	
	public String toString() {
		return "UIClass(" + ThemeManager.getTheme().buildBuildParentList(this, name) + ")";
	}
}