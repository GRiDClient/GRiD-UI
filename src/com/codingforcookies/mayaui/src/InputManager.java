package com.codingforcookies.mayaui.src;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class InputManager {
	public static Vector2f getMousePosition() {
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
}