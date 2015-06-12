package com.codingforcookies.mayaui.src.events.classes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class EventBase {
	public abstract String getName();
	
	private Method method;
	
	public EventBase setMethod(Method method) {
		this.method = method;
		return this;
	}
	
	public void call() {
		try {
			method.invoke(null, this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}