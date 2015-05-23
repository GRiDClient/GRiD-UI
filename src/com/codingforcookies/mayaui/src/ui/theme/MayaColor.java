package com.codingforcookies.mayaui.src.ui.theme;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.exceptions.MayaException;
import com.codingforcookies.mayaui.src.exceptions.ThemeInvalidException;

/**
 * Maya's implementation of color.
 * @author Stumblinbear
 */
public class MayaColor implements Cloneable {
	public static final MayaColor BLACK = new MayaColor(0F, 0F, 0F, 1F);
	public static final MayaColor WHITE = new MayaColor(1F, 1F, 1F, 1F);
	public static final MayaColor RED = new MayaColor(1F, 0F, 0F, 1F);
	public static final MayaColor GREEN = new MayaColor(0F, 1F, 0F, 1F);
	public static final MayaColor BLUE = new MayaColor(0F, 0F, 1F, 1F);

	public static MayaColor FALLBACK_COLOR = BLUE;
	
	private float r, g, b, a;

	public MayaColor() {
		r = FALLBACK_COLOR.r;
		g = FALLBACK_COLOR.g;
		b = FALLBACK_COLOR.b;
		a = FALLBACK_COLOR.a;
	}
	
	/**
	 * Generate a random color.
	 */
	public MayaColor random() {
		Random rand = new Random();
		return new MayaColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1F);
	}

	public MayaColor(float r, float g, float b) {
		this(r, g, b, 1F);
	}

	public MayaColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Load a color from a hex color code.
	 */
	public MayaColor(String hex) {
		setup(hex, 1F);
	}
	
	/**
	 * Load a color from a hex color code. Includes alpha.
	 */
	public MayaColor(String hex, float alpha) {
		setup(hex, alpha);
	}

	public MayaColor setColor(String parse) {
		setup(parse, a);
		return this;
	}
	
	/**
	 * Parse the hex string.
	 */
	private void setup(String hex, float alpha) {
		if(hex.startsWith("#"))
			hex = hex.substring(1);
		if(hex.length() < 6) {
			if(hex.length() == 3)
				hex += hex;
			else
				MayaException.throwNonClosing(new ThemeInvalidException("Invalid color " + hex));
		}

		r = Integer.valueOf(hex.substring(0, 2), 16) / 255F;
		g = Integer.valueOf(hex.substring(2, 4), 16) / 255F;
		b = Integer.valueOf(hex.substring(4, 6), 16) / 255F;
		a = alpha;

		if(r > 1F)
			r = 1F;
		if(g > 1F)
			g = 1F;
		if(b > 1F)
			b = 1F;
		if(a > 1F)
			a = 1F;
	}
	
	public MayaColor setAlpha(float alpha) {
		a = alpha;
		return this;
	}
	
	public float getRed() {
		return r;
	}

	public float getGreen() {
		return g;
	}

	public float getBlue() {
		return b;
	}

	public float getAlpha() {
		return a;
	}
	
	/**
	 * Make the color brighter.
	 */
	public MayaColor lighter() {
		r += .1F;
		g += .1F;
		b += .1F;
		return this;
	}

	/**
	 * Make the color darker.
	 */
	public MayaColor darker() {
		r -= .1F;
		g -= .1F;
		b -= .1F;
		return this;
	}
	
	/**
	 * Bind the color to LWJGL.
	 */
	public void use() {
		GL11.glColor4f(r, g, b, a);
	}
	
	public String toString() {
		return "color(r" + r + " g" + g + " b" + b + " a" + a + ")";
	}
	
	public MayaColor clone() {
		try {
			return (MayaColor)super.clone();
		} catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}