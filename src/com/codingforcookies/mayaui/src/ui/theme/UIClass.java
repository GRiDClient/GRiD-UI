package com.codingforcookies.mayaui.src.ui.theme;

import java.util.HashMap;

import com.codingforcookies.mayaui.src.MayaUI;

public class UIClass {
	public String name;
	private HashMap<String, Object> values;

	protected UIClass(String name) {
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
					MayaColor color = get("background-color", new MayaColor(), new MayaColor());
					color.setAlpha(Float.parseFloat(value.toString()));
				}else if(name.startsWith("border")) {
					value = new MayaBorder(name, value.toString());
					name = "border";
				}
			}

			values.put(name, value);
		} catch(Exception e) { e.printStackTrace(); }
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype) {
		if(!values.containsKey(name))
			return null;
		return (T)values.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name, T returntype, T defaultValue) {
		if(!values.containsKey(name)) {
			if(ThemeManager.getTheme() != null && ThemeManager.getTheme().get("global").has(name))
				return ThemeManager.getTheme().get("global").get(name, returntype, defaultValue);
			return defaultValue;
		}
		return (T)values.get(name);
	}
	
	public Class<?> type(String name) {
		return values.get(name).getClass();
	}
	
	public boolean has(String name) {
		return values.containsKey(name);
	}
}