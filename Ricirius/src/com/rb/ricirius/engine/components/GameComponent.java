package com.rb.ricirius.engine.components;

import com.rb.ricirius.engine.graphics.GraphicsEngine;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.graphics.shader.Shader;

public abstract class GameComponent {

	public void input(Transformer transformer, float delta) {}
	public void update(Transformer transformer, float delta) {}
	public void render(Transformer transformer, Shader shader) {}
	
	public void addToGraphicsEngine(GraphicsEngine graphicsEngine) {}
}
