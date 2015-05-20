package com.codingforcookies.gridui.src.ui.theme;

public enum KeyType {
	BASIC,
	CLASS;

	public static KeyType parse(String key) {
		return key.startsWith(".") ? CLASS : BASIC;
	}
}
