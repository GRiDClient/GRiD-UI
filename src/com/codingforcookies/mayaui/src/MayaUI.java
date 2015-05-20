package com.codingforcookies.mayaui.src;

import javax.swing.UIManager;

import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;

public class MayaUI {
	public static final String version = "Chill Leopard";
	
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
}