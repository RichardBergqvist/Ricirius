package com.rb.ricirius.engine;

import com.rb.ricirius.engine.graphics.GraphicsEngine;
import com.rb.ricirius.engine.graphics.Window;
import com.rb.ricirius.engine.input.Input;
import com.rb.ricirius.engine.util.Time;

public class CoreEngine {
	
	private Game game;
	private String title;
	private int width;
	private int height;
	private double frameTime;
	private boolean isRunning;
	private GraphicsEngine graphicsEngine;
	
	public CoreEngine(Game game, String title, int width, int height, int framerate) {
		this.title = title;
		this.game = game;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0 / framerate;
		this.isRunning = false;
	}
	
	public void createWindow() {
		Window.createWindow(title, width, height);
		this.graphicsEngine = new GraphicsEngine();
	}
	
	public void start() {
		if (isRunning)
			return;	
		run();
	}
	
	public void stop() {
		if (!isRunning)
			return;	
		isRunning = false;
	}
	
	private void run() {
		isRunning = true;
		
		int frames = 0;
		long frameCounter = 0;
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		game.init();
		
		while (isRunning) {
			boolean render = false;
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while (unprocessedTime > frameTime) {
				render = true;
				unprocessedTime -= frameTime;
				
				if (Window.isCloseRequested())
					stop();
				
				game.input((float) frameTime);
				graphicsEngine.input((float) frameTime);
				Input.update();
				
				game.update((float) frameTime);
				 
				if (frameCounter >= 1) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if (render) {
				graphicsEngine.render(game.getRootObject());
				Window.render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		clean();
	}
	
	private void clean() {
		Window.dispose();
	}
}