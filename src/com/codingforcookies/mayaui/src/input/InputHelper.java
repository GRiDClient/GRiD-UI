package com.codingforcookies.mayaui.src.input;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.codingforcookies.mayaui.src.events.EventHandler;
import com.codingforcookies.mayaui.src.events.KeyEventState;

public class InputHelper {
    private static InputHelper input = new InputHelper();
    
    private ArrayList<KeyEventState> mouseEvents;
    private ArrayList<KeyEventState> keyboardEvents;
    
    public InputHelper() {
        mouseEvents = new ArrayList<KeyEventState>();
        for(int i = 0; i < Mouse.getButtonCount(); i++)
            mouseEvents.add(KeyEventState.NONE);
        
        keyboardEvents = new ArrayList<KeyEventState>();
        for(int i = 0; i < Keyboard.KEYBOARD_SIZE; i++)
            keyboardEvents.add(KeyEventState.NONE);
    }
    
    private void Update() {
        resetKeys();
        
        for(int i = 0; i < Keyboard.KEYBOARD_SIZE; i++) {
            if(Keyboard.isKeyDown(i)) {
                keyboardEvents.set(i, KeyEventState.DOWN);
                EventHandler.triggerKeyEvent(i, KeyEventState.DOWN);
            }
        }
        
        while(Keyboard.next()) {
            int key = Keyboard.getEventKey();
            if(key < 0)
            	continue;

            if(Keyboard.getEventKeyState()) {
                if(!Keyboard.isRepeatEvent()) {
                    keyboardEvents.set(key, KeyEventState.PRESSED);
                    EventHandler.triggerKeyEvent(key, KeyEventState.PRESSED);
                }
            }else{
                keyboardEvents.set(key, KeyEventState.RELEASED);
                EventHandler.triggerKeyEvent(key, KeyEventState.RELEASED);
            }
        }
        
        resetMouse();
        
        for(int i = 0; i < Mouse.getButtonCount(); i++){
            if(Mouse.isButtonDown(i)) {
                mouseEvents.set(i, KeyEventState.DOWN);
                EventHandler.triggerKeyEvent(i, KeyEventState.DOWN);
            }
        }
        
        while(Mouse.next()) {
            int button = Mouse.getEventButton();
            if(button < 0) continue;
            if(Mouse.getEventButtonState()) {
                mouseEvents.set(button, KeyEventState.PRESSED);
                EventHandler.triggerKeyEvent(button, KeyEventState.PRESSED);
            }else{
                mouseEvents.set(button, KeyEventState.RELEASED);
                EventHandler.triggerKeyEvent(button, KeyEventState.PRESSED);
            }
        }
    }
    
    private void resetKeys() {
        for(int i = 0; i < Keyboard.KEYBOARD_SIZE; i++)
            keyboardEvents.set(i, KeyEventState.NONE);
    }
    
    private void resetMouse() {
        for(int i = 0; i < Mouse.getButtonCount(); i++)
            mouseEvents.set(i, KeyEventState.NONE);
    }
    
    private boolean KeyDown(int key){
        return keyboardEvents.get(key) == KeyEventState.DOWN;
    }
    
    private boolean KeyPressed(int key) {
        return keyboardEvents.get(key) == KeyEventState.PRESSED;
    }
    
    private boolean KeyReleased(int key) {
        return keyboardEvents.get(key) == KeyEventState.RELEASED;
    }
    
    private boolean MouseButtonDown(int key) {
        return mouseEvents.get(key) == KeyEventState.DOWN;
    }
    
    private boolean MouseButtonPressed(int key) {
        return mouseEvents.get(key) == KeyEventState.PRESSED;
    }
    
    private boolean MouseButtonReleased(int key) {
        return mouseEvents.get(key) == KeyEventState.RELEASED;
    }
    
    public static boolean isKeyDown(int key) {
        return input.KeyDown(key);
    }
    
    public static boolean isKeyPressed(int key) {
        return input.KeyPressed(key);
    }
    
    public static boolean isKeyReleased(int key) {
        return input.KeyReleased(key);
    }
    
    public static boolean isButtonDown(int key) {
        return input.MouseButtonDown(key);
    }
    
    public static boolean isButtonPressed(int key) {
        return input.MouseButtonPressed(key);
    }
    
    public static boolean isButtonReleased(int key) {
        return input.MouseButtonReleased(key);
    }
    
    public static void update() {
        input.Update();
    }
}