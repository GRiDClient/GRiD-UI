package com.codingforcookies.mayaui.src.ui.theme;

import java.util.HashMap;

public class UITheme {
	public String name = "";
	public String creator = "";
	public String description = "";
	
	public HashMap<String, UIClass> classes;
	
	public UITheme() {
		classes = new HashMap<String, UIClass>();
		
		classes.put("global", new UIClass(null, "global"));
	}
	
	public void unload() {
		classes.clear();
	}

	public boolean hasClass(String key) {
		return classes.containsKey(key);
	}
	
	public UIClass getClass(String key) {
		if(classes.containsKey(key))
			return classes.get(key);
		return classes.get("global");
	}
	
	public void set(String key, String classname, String value) {
		if(key.equals("info")) {
			if(classname.equals("name"))
				name = value;
			else if(name.equals("creator"))
				creator = value;
			else if(name.equals("description"))
				description = value;
		}else
			getHighestClass(key).set(classname, value);
	}
	
	private UIClass getHighestClass(String key) {
		String[] keys = key.split(" ");
		UIClass uiclass = getUIClass(classes, getClass("global"), keys[0]);
		
		for(int i = 1; i < keys.length; i++)
			uiclass = getUIClass(uiclass.classes, uiclass, keys[i]);
		
		return uiclass;
	}
	
	private UIClass getUIClass(HashMap<String, UIClass> classlist, UIClass parent, String key) {
		if(!classlist.containsKey(key))
			classlist.put(key, new UIClass(parent, key));
		return classlist.get(key);
	}
	
	
	public void output() {
		for(String key : classes.keySet())
			outputLower(classes.get(key), 1);
	}
	
	private void outputLower(UIClass uiclass, int level) {
		String spacing = "";
		for(int i = 0; i < level; i++)
			spacing += "  ";
		
		System.out.println(spacing.substring(2) + buildBuildParentList(uiclass, uiclass.name) + ":");
		for(String key : uiclass.values.keySet())
			System.out.println(spacing + key + ": " + uiclass.values.get(key).toString());
		
		for(String key : uiclass.classes.keySet()) {
			outputLower(uiclass.classes.get(key), level + 1);
		}
	}
	
	protected String buildBuildParentList(UIClass uiclass, String parents) {
		if(uiclass.parent != null) {
			parents = uiclass.parent.name + " " + parents;
			return buildBuildParentList(uiclass.parent, parents);
		}
		return parents;
	}
}