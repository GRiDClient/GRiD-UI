package com.codingforcookies.gridui.src.ui.theme;

import java.util.HashMap;

public class UITheme {
	public String name = "GRiD";
	public HashMap<String, UIClass> classes;
	
	public UITheme() {
		
	}
	
	public void load() {
		classes = new HashMap<String, UIClass>();
	}
	
	public void unload() {
		classes.clear();
		classes = null;
	}
}