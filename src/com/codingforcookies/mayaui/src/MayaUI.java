package com.codingforcookies.mayaui.src;

import com.codingforcookies.mayaui.src.notification.MNotification;
import com.codingforcookies.mayaui.src.notification.MNotificationType;
import com.codingforcookies.mayaui.src.notification.NotificationManager;
import com.codingforcookies.mayaui.src.ui.UIManager;
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
		ThemeManager.init();
		ThemeManager.loadThemes();
		
		if(!ThemeManager.setTheme("Maya Theme")) {
			System.err.println("Failed to load default theme");
			return;
		}
		
		System.out.println("Initializing UIManager...");
		uimanager = new UIManager();
		System.out.println("  Done");
	}
	
	public static float parseConfigFloat(String value) {
		if(value.endsWith("px")) {
			return Float.parseFloat(value.substring(0, value.length() - 2));
		}else if(value.startsWith("0x")) {
			return Float.parseFloat(value);
		}else if(value.endsWith("%")) {
			return Float.parseFloat(value.substring(0, value.length() - 1)) / 100F;
		}else
			return Float.parseFloat(value);
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