package com.codingforcookies.gridui.src.ui.theme;

import java.io.File;
import java.util.ArrayList;

import com.codingforcookies.gridui.src.exceptions.GRiDException;
import com.codingforcookies.gridui.src.exceptions.ThemeInvalidException;

public class ThemeManager {
	private static UITheme[] availableThemes;
	private static int currentTheme;
	
	public static UITheme[] getThemes() { return availableThemes; }
	public static UITheme getTheme() { return availableThemes[currentTheme]; }
	
	public static boolean setTheme(String name) {
		for(int i = 0; i < availableThemes.length; i++)
			if(availableThemes[i].name == name) {
				currentTheme = i;
				return true;
			}
		return false;
	}
	
	private static final File themeLocation = new File("E:/Git/GRiD Client/GRiD-UI/themes");
	
	public static void loadThemes() {
		ArrayList<File> files = new ArrayList<File>();
		
		for(File file : themeLocation.listFiles()) {
			if(isTheme(file))
				files.add(file);
		}
		
		availableThemes = processThemes(files);
		
		files.clear();
		files = null;
	}
	
	private static boolean isTheme(File file) {
		if(!file.getName().endsWith(".gridtheme")) {
			GRiDException.throwNonClosing(new ThemeInvalidException("Invalid Theme file: " + file.getName()));
			return false;
		}
		return true;
	}
	
	private static UITheme[] processThemes(ArrayList<File> files) {
		ArrayList<UITheme> processed = new ArrayList<UITheme>();
		
		for(File file : files) {
			UITheme theme = processTheme(file);
			if(theme != null)
				processed.add(theme);
		}
		
		return processed.toArray(new UITheme[processed.size()]);
	}
	
	private static UITheme processTheme(File file) {
		try {
			UITheme theme = new UITheme();
			
			
			
			return theme;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}