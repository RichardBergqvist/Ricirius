package com.rb.ricirius.engine.components.light;

import com.rb.ricirius.engine.components.GameComponent;
import com.rb.ricirius.engine.graphics.GraphicsEngine;
import com.rb.ricirius.engine.math.Vector3f;

public class SpotLight extends GameComponent {

	private PointLight pointLight;
	private Vector3f direction;
	private float cutoff;
	
	public SpotLight(PointLight pointLight, Vector3f direction, float cutoff) {
		this.pointLight = pointLight;
		this.direction = direction.normalized();
		this.cutoff = cutoff;
	}
	
	@Override
	public void addToGraphicsEngine(GraphicsEngine graphicsEngine) {
		graphicsEngine.addSpotLight(this);
	}
	
	public void setPointLight(PointLight pointLight) { this.pointLight = pointLight; }
	public PointLight getPointLight() {	return pointLight; }
	public void setDirection(Vector3f direction) { this.direction = direction.normalized(); }
	public Vector3f getDirection() { return direction; }
	public void setCutoff(float cutoff) { this.cutoff = cutoff; }
	public float getCutoff() { return cutoff; }
}