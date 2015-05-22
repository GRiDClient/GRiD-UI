package com.codingforcookies.mayaui.src;

import javax.swing.UIManager;

import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;

/**
 * The main class for Maya UI
 * @author Stumblinbear
 */
public class MayaUI {
	/**
	 * Current running version of MayaUI
	 */
	public static final String version = "Chill Leopard";
	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;
	
	/**
	 * Currently only loads all themes available under /themes/
	 */
	public static void initialize() {
		ThemeManager.loadThemes();
		
		if(!ThemeManager.setTheme("Maya Theme")) {
			System.err.println("Failed to load default theme");
			return;
		}
	}
	
	/**
	 * Create a new UI Manager
	 */
	public static UIManager createUIManager() {
		UIManager uimanager = new UIManager();
		return uimanager;
	}
	
	/**
	 * Parses a configuration value to return it's code-useful value
	 */
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