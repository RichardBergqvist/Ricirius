package com.rb.ricirius.engine;

import java.util.ArrayList;

import com.rb.ricirius.engine.components.GameComponent;
import com.rb.ricirius.engine.graphics.GraphicsEngine;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.graphics.shader.Shader;

public class GameObject {

	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transformer transformer;
	
	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transformer = new Transformer();
	}
	
	public void addChild(GameObject child) {
		children.add(child);
	}
	
	public void addComponent(GameComponent component) {
		components.add(component);
	}
	
	public void input(float delta) {
		for(GameObject child : children)
			child.input(delta);
		for (GameComponent component : components)
			component.input(transformer, delta);
	}
	
	public void update(float delta) {
		for(GameObject child : children)
			child.update(delta);
		for (GameComponent component : components)
			component.update(transformer, delta);
	}
	
	public void render(Shader shader) {
		for(GameObject child : children)
			child.render(shader);
		for (GameComponent component : components)
			component.render(transformer, shader);
	}
	
	public void addToGraphicsEngine(GraphicsEngine graphicsEngine) {
		for(GameObject child : children)
			child.addToGraphicsEngine(graphicsEngine);
		for (GameComponent component : components)
			component.addToGraphicsEngine(graphicsEngine);
	}
	
	public Transformer getTransformer() { return transformer; }
}