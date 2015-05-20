package com.codingforcookies.mayaui.src.ui.theme;

import java.util.HashMap;

class UIClass {
	public String name;
	private HashMap<String, Object> values;
	
	protected UIClass() {
		values = new HashMap<String, Object>();
	}
	
	public void set(String name, Object value) {
		values.put(name, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype) {
		if(!values.containsKey(name))
			return null;
		return (T)values.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype, T defaultValue) {
		if(!values.containsKey(name))
			return defaultValue;
		return (T)values.get(name);
	}
}