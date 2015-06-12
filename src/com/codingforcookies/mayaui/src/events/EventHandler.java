package com.codingforcookies.mayaui.src.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.codingforcookies.mayaui.src.events.classes.EventBase;
import com.codingforcookies.mayaui.src.events.classes.MayaEvent;

public class EventHandler {
	private static HashMap<Integer, Method[][]> keyListeners = new HashMap<Integer, Method[][]>();
	private static HashMap<String, EventBase[]> eventListeners = new HashMap<String, EventBase[]>();
	
	public static void registerEventHandler(Class<?> eventClass) throws Exception {
		for(Method method : eventClass.getMethods()) {
			if(method.isAnnotationPresent(KeyListener.class)) {
				KeyListener keyListener = method.getAnnotation(KeyListener.class);

				ArrayList<Method> methods = new ArrayList<Method>();
				
				if(keyListeners.containsKey(keyListener.key()))
					for(Method premethods : keyListeners.get(keyListener.key())[keyListener.keystate().ordinal()])
						methods.add(premethods);
				
				methods.add(method);
				
				Method[][] methodList;
				if(keyListeners.containsKey(keyListener.key()))
					methodList = keyListeners.get(keyListener.key());
				else
					methodList = new Method[KeyEventState.values().length - 1][0];
				methodList[keyListener.keystate().ordinal()] = methods.toArray(new Method[methods.size()]);
				
				keyListeners.put(keyListener.key(), methodList);
			}else if(method.isAnnotationPresent(MayaEvent.class)) {
				Class<?> paramType = method.getParameterTypes()[0];
				String baseType = ((EventBase)paramType.newInstance()).getName();
				
				ArrayList<EventBase> methods = new ArrayList<EventBase>();
				
				if(eventListeners.containsKey(baseType))
					for(EventBase premethods : eventListeners.get(baseType))
						methods.add(premethods);
				
				methods.add(((EventBase)paramType.newInstance()).setMethod(method));
				
				eventListeners.put(baseType, methods.toArray(new EventBase[methods.size() - 1]));
			}
		}
	}
	
	public static void triggerKeyEvent(int key, KeyEventState kes) {
		try {
			if(keyListeners.containsKey(key)) {
				for(Method method : keyListeners.get(key)[kes.ordinal()])
					method.invoke(null, new Object[0]);
			}
		} catch(Exception e) { }
	}
	
	public static void triggerEvent(String event) {
		try {
			if(eventListeners.containsKey(event))
				for(EventBase eventbase : eventListeners.get(event))
					eventbase.call();
		} catch(Exception e) { }
	}
}