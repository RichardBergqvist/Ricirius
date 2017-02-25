package com.rb.ricirius.engine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {

	public static void createWindow(String title, int width, int height) {
		Display.setTitle(title);
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void render() {
		Display.update();
	}
	
	public static void dispose() {
		Display.destroy();
	}
	
	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
	
	public static String getTitle() {
		return Display.getTitle();
	}
	
	public static int getWidth() {
		return Display.getDisplayMode().getWidth();
	}
	
	public static int getHeight() {
		return Display.getDisplayMode().getHeight();
	}
}