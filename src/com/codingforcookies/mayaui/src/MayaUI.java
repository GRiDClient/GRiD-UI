package com.codingforcookies.mayaui.src;

import com.codingforcookies.mayaui.src.notification.MNotification;
import com.codingforcookies.mayaui.src.notification.MNotificationType;
import com.codingforcookies.mayaui.src.notification.NotificationManager;
import com.codingforcookies.mayaui.src.ui.UIManager;
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
	
	public static UIManager uimanager;
	public static UIManager getUIManager() {
		return uimanager;
	}
	
	/**
	 * Currently only loads all themes available under /themes/
	 */
	public static void initialize() {
		ThemeManager.loadThemes();
		
		if(!ThemeManager.setTheme("Maya Theme")) {
			System.err.println("Failed to load default theme");
			return;
		}
		
		System.out.println("Initializing UIManager...");
		uimanager = new UIManager();
		System.out.println("  Done");
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
	
	/**
	 * Push a notification to the screen.
	 */
	public static void addNotification(MNotificationType type, String message) {
		new MNotification(type, message).push();
	}

	/**
	 * Push a notification to the screen.
	 */
	public static void addNotification(MNotification mNotification) {
		NotificationManager.addNotification(mNotification);
	}
}