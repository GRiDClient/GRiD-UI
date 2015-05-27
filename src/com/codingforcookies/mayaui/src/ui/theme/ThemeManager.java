package com.codingforcookies.mayaui.src.ui.theme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codingforcookies.mayaclientapi.src.font.MayaFontRenderer;
import com.codingforcookies.mayaclientapi.src.texture.MayaTextureLoader;
import com.codingforcookies.mayaui.src.exceptions.ThemeInvalidException;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionBgColor;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionColor;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionFloat;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionInfo;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionMargin;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionNone;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionParser;
import com.codingforcookies.mayaui.src.ui.theme.parser.border.MOptionBorder;

/**
 * Manages all theme loading, parsing, and storage.
 * @author Stumblinbear
 */
public class ThemeManager {
	private static HashMap<String, File> availableThemes;
	private static UITheme currentTheme;
	
	public static Set<String> getThemes() { return availableThemes.keySet(); }
	public static UITheme getTheme() { return currentTheme; }
	
	public static List<MOptionParser> optionParsers = new ArrayList<MOptionParser>();
	private static MOptionParser runParser(UITheme theme, String keyclass, String key, String value) {
		for(MOptionParser parser : optionParsers)
			if(parser.shouldParse(keyclass, key, value)) {
				MOptionParser ret = parser.clone().parse(theme, keyclass, key, value);
				if(ret == null)
					return null;
				return ret;
			}
		return new MOptionNone().parse(theme, keyclass, key, value);
	}
	
	public static void init() {
		optionParsers.add(new MOptionInfo());
		
		optionParsers.add(new MOptionFloat());
		optionParsers.add(new MOptionMargin());
		optionParsers.add(new MOptionColor());
		optionParsers.add(new MOptionBgColor());
		optionParsers.add(new MOptionBorder());
	}
	
	/**
	 * Set the current theme. Theme must already be loaded!
	 */
	public static boolean setTheme(String name) {
		if(availableThemes.containsKey(name)) {
			System.out.println("Loading theme '" + name + "'");
			currentTheme = processTheme(availableThemes.get(name));
			if(currentTheme != null) {
				System.out.println("Applied theme '" + name + "'");
				
				//currentTheme.output();
				
				return true;
			}
		}
		
		System.err.println("Failed to apply theme '" + name + "'");
		return false;
	}

	private static final File themeLocation = new File("E:/Git/Maya Client/Maya-UI/themes");
	private static final String themePattern = "([^{]+)\\s*\\{\\s*([^}]+)\\s*}";
	
	/**
	 * Load the theme list from the theme directory. 
	 */
	public static void loadThemes() {
		availableThemes = new HashMap<String, File>();
		
		for(File folder : themeLocation.listFiles()) {
			if(folder.isDirectory()) {
				for(File file : folder.listFiles()) {
					String themename = isTheme(file);
					if(themename != null)
						availableThemes.put(themename, file);
				}
			}
		}
	}
	
	/**
	 * If the file appears to be a theme file.
	 */
	private static String isTheme(File file) {
		if(!file.getName().endsWith(".mayatheme"))
			return null;
		
		return processThemeForName(file);
	}
	
	/**
	 * Process the theme, but only retrieve the display name.
	 */
	private static String processThemeForName(File file) {
		return (String)process(file, new String[] { "info", "name" });
	}

	/**
	 * Process the theme.
	 */
	private static UITheme processTheme(File file) {
		return (UITheme)process(file, null);
	}
	
	/**
	 * Parse the theme file. Creates the theme, adds parents, children, and sets values.
	 */
	private static Object process(File file, String[] returnkey) {
		try {
			String content = getFileContents(file);
			if(content == null)
				return null;
			
			UITheme theme = null;
			if(returnkey == null)
				theme = new UITheme();
			
			HashMap<String, String> themevars = new HashMap<String, String>();
			
			Matcher m = Pattern.compile(themePattern).matcher(content);
			while(m.find()) {
				String key = m.group(1).trim();
				key = key.replace(":", " :");
				
				if(returnkey != null)
					if(!key.equals(returnkey[0]))
						continue;
				
				String[] split = m.group(2).split("\n");
				
				for(String line : split) {
					if(line.startsWith("#"))
						continue;
					if(key.equals("vars")) {
						String[] set = line.split(":");
						set[0] = set[0].trim();
						set[1] = set[1].trim();
						
						themevars.put(set[0], set[1]);
					}else{
						String[] set = line.split(":");
						set[0] = set[0].trim();
						set[1] = set[1].trim();
						
						while(set[1].contains("[") && set[1].contains("]")) {
							String varkey = set[1].substring(set[1].indexOf("[") + 1, set[1].indexOf("]"));
							if(themevars.containsKey(varkey))
								set[1] = set[1].replace("[" + varkey + "]", themevars.get(varkey));
							else
								throw new ThemeInvalidException("Variable " + varkey + " does not exist");
						}
						
						if(returnkey != null) {
							if(returnkey[1].equals(set[0]))
								return set[1];
						}else{
							MOptionParser parser = runParser(theme, key, set[0], set[1]);
							theme.set(key, set[0], parser);
						}
					}
				}
			}
			
			if(returnkey != null)
				return null;
			
			File fontFile = new File(file.getParentFile(), "font.png");
			System.out.println("  Loading font...");
			MayaTextureLoader.loadFile("font", fontFile, new Runnable() {
				public void run() {
					MayaFontRenderer.font = MayaTextureLoader.getTexture("font");
				}
			});
			
			if(theme.name == null)
				throw new ThemeInvalidException("Invalid Theme: " + file.getName());
			else
				System.out.println("  Finished processing theme '" + theme.name + "' from file '" + file.getName() + "'");
			
			return theme;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return a file as a string.
	 */
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