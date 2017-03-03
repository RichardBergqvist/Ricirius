package com.rb.ricirius.game;

import com.rb.ricirius.engine.CoreEngine;

public class Main {
	
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(new TestGame(), "Ricirius", 800, 600, 60);
		engine.createWindow();
		engine.start();
	}
}