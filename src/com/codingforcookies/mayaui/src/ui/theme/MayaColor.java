package com.codingforcookies.mayaui.src.ui.theme;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.exceptions.MayaException;
import com.codingforcookies.mayaui.src.exceptions.ThemeInvalidException;

public class MayaColor {
	public static final MayaColor WHITE = new MayaColor(1F, 1F, 1F);
	public static MayaColor GLOBAL_TEXT = WHITE;
	
	private float r, g, b, a;

	public MayaColor() { }
	
	public MayaColor random() {
		Random rand = new Random();
		return new MayaColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1F);
	}

	public MayaColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public MayaColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public MayaColor(String hex) {
		setup(hex, 1F);
	}

	public MayaColor(String hex, float alpha) {
		setup(hex, alpha);
	}

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

	public void setAlpha(float alpha) {
		a = alpha;
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
	
	public MayaColor darker() {
		return new MayaColor(r + .1F, g + .1F, b + .1F, a + .1F);
	}

	public void use() {
		GL11.glColor4f(r, g, b, a);
	}
}