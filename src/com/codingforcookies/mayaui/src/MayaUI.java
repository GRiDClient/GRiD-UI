package com.codingforcookies.mayaui.src;

import javax.swing.UIManager;

import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;

public class MayaUI {
	public static final String version = "Chill Leopard";
	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;
	
	public static void initialize() {
		ThemeManager.loadThemes();
		
		if(!ThemeManager.setTheme("Maya Theme")) {
			System.err.println("Failed to load default theme");
			return;
		}
	}
	
	public static UIManager createUIManager() {
		UIManager uimanager = new UIManager();
		return uimanager;
	}
	
	public static Object parseConfigValue(String str) {
		if(str.endsWith("px")) {
			return Integer.parseInt(str.substring(0, str.length() - 2));
		}else if(str.startsWith("0x")) {
			return Integer.parseInt(str);
		}else if(str.endsWith("%")) {
			return Integer.parseInt(str.substring(0, str.length() - 1)) / 100F;
		}else if(str.startsWith("#")) {
			return new MayaColor(str);
		}else
			return str;
	}
}