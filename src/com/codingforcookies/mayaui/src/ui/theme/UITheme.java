package com.codingforcookies.mayaui.src.ui.theme;

import java.util.HashMap;

public class UITheme {
	public String name = "";
	public String creator = "";
	public String description = "";
	
	public HashMap<String, UIClass> basics;
	public HashMap<String, UIClass> classes;
	
	public UITheme() {
		basics = new HashMap<String, UIClass>();
		classes = new HashMap<String, UIClass>();
	}
	
	public void unload() {
		basics.clear();
		classes.clear();
	}
	
	public UIClass get(String key) {
		if(!key.startsWith(".")) {
			if(basics.containsKey(key))
				return basics.get(key);
		}else{
			if(classes.containsKey(key))
				return classes.get(key);
		}
		return null;
	}
	
	public void set(KeyType type, String key, String classname, String value) {
		UIClass uiclass;
		
		switch(type) {
			case BASIC:
				if(!basics.containsKey(key))
					basics.put(key, new UIClass(key));
				uiclass = basics.get(key);
				break;
			case CLASS:
				if(!classes.containsKey(key))
					classes.put(key, new UIClass(key));
				uiclass = classes.get(key);
				break;
			default:
				return;
		}
		
		if(key.equals("info")) {
			if(classname.equals("name"))
				name = value;
			else if(name.equals("creator"))
				creator = value;
			else if(name.equals("description"))
				description = value;
		}else
			uiclass.set(classname, value);
	}
}