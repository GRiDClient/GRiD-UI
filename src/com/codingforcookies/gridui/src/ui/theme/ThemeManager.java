package com.codingforcookies.gridui.src.ui.theme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codingforcookies.gridui.src.exceptions.GRiDException;
import com.codingforcookies.gridui.src.exceptions.ThemeInvalidException;

public class ThemeManager {
	private static HashMap<String, File> availableThemes;
	private static UITheme currentTheme;
	
	public static Set<String> getThemes() { return availableThemes.keySet(); }
	public static UITheme getTheme() { return currentTheme; }
	
	public static boolean setTheme(String name) {
		if(availableThemes.containsKey(name)) {
			currentTheme = processTheme(availableThemes.get(name));
			if(currentTheme != null) {
				System.out.println("Applied theme '" + name + "'");
				return true;
			}
		}
		
		System.err.println("Failed to apply theme '" + name + "'");
		return false;
	}

	private static final File themeLocation = new File("E:/Git/GRiD Client/GRiD-UI/themes");
	private static final String themePattern = "([^{]+)\\s*\\{\\s*([^}]+)\\s*}";

	public static void loadThemes() {
		availableThemes = new HashMap<String, File>();
		
		for(File file : themeLocation.listFiles()) {
			String themename = isTheme(file);
			if(themename != null)
				availableThemes.put(themename, file);
		}
	}

	private static String isTheme(File file) {
		if(!file.getName().endsWith(".gridtheme")) {
			GRiDException.throwNonClosing(new ThemeInvalidException("Invalid Theme file: " + file.getName()));
			return null;
		}
		
		return processThemeForName(file);
	}
	
	private static String processThemeForName(File file) {
		return (String)process(file, new String[] { "info", "name" });
	}
	
	private static UITheme processTheme(File file) {
		return (UITheme)process(file, null);
	}
	
	private static Object process(File file, String[] returnkey) {
		try {
			String content = getFileContents(file);
			if(content == null)
				return null;
			
			UITheme theme = null;
			if(returnkey == null)
				theme = new UITheme();
			
			Matcher m = Pattern.compile(themePattern).matcher(content);
			while(m.find()) {
				String key = m.group(1).trim();
				
				if(returnkey != null)
					if(!key.equals(returnkey[0]))
						continue;
				
				KeyType type = KeyType.parse(key);
				String[] split = m.group(2).split("\n");
				
				for(String line : split) {
					String[] set = line.split(":");
					set[0] = set[0].trim();
					set[1] = set[1].trim();
					
					if(returnkey != null) {
						if(returnkey[1].equals(set[0]))
							return set[1];
					}else{
						theme.set(type, key, set[0], set[1]);
					}
				}
			}
			
			if(returnkey != null)
				return null;
			
			if(theme.name == null)
				throw new ThemeInvalidException("Invalid Theme: " + file.getName());
			else
				System.out.println("Loaded theme '" + theme.name + "' from file '" + file.getName() + "'");
			
			return theme;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String getFileContents(File file) {
		try {
			InputStream in = new FileInputStream(file);
			InputStreamReader is = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(is);

			String content = "";

			String read;
			while((read = br.readLine()) != null) {
				if(read.contains(":"))
					content += "\n";
				content += read.trim();
			}
			
			br.close();
			is.close();
			in.close();
			
			return content;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}