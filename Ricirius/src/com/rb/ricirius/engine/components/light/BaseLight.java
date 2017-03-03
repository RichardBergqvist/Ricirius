package com.rb.ricirius.engine.components.light;

import com.rb.ricirius.engine.math.Vector3f;

public class BaseLight {

	private Vector3f color;
	private float intensity;
	
	public BaseLight(Vector3f color, float intensity) {
		this.color = color;
		this.intensity = intensity;
	}
	
	public void setColor(Vector3f color) { this.color = color; }
	public Vector3f getColor() { return color; }
	public void setIntensity(float intensity) { this.intensity = intensity; }
	public float getIntensity() { return intensity; }
}