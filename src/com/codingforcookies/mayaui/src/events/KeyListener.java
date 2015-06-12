package com.codingforcookies.mayaui.src.events;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface KeyListener {
	public int key() default 0;
	public KeyEventState keystate() default KeyEventState.PRESSED;
}