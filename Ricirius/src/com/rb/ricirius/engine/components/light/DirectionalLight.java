package com.rb.ricirius.engine.components.light;

import com.rb.ricirius.engine.components.GameComponent;
import com.rb.ricirius.engine.graphics.GraphicsEngine;
import com.rb.ricirius.engine.math.Vector3f;

public class DirectionalLight extends GameComponent {

	private BaseLight base;
	private Vector3f direction;
	
	public DirectionalLight(BaseLight base, Vector3f direction) {
		this.base = base;
		this.direction = direction.normalized();
	}
	
	@Override
	public void addToGraphicsEngine(GraphicsEngine graphicsEngine) {
		graphicsEngine.addDirectionalLight(this);
	}
	
	public void setBase(BaseLight base) { this.base = base; }
	public BaseLight getBase() { return base; }
	public void setDirection(Vector3f direction) { this.direction = direction.normalized(); }
	public Vector3f getDirection() { return direction; }
}