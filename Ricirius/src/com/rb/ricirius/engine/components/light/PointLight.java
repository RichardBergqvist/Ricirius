package com.rb.ricirius.engine.components.light;

import com.rb.ricirius.engine.components.GameComponent;
import com.rb.ricirius.engine.graphics.Attenuation;
import com.rb.ricirius.engine.graphics.GraphicsEngine;
import com.rb.ricirius.engine.math.Vector3f;

public class PointLight	extends GameComponent {

	private BaseLight base;
	private Attenuation attenuation;
	private Vector3f pos;
	private float range;
	
	public PointLight(BaseLight base, Attenuation attenuation, Vector3f pos, float range) {
		this.base = base;
		this.attenuation = attenuation;
		this.pos = pos;
		this.range = range;
	}
	
	@Override
	public void addToGraphicsEngine(GraphicsEngine graphicsEngine) {
		graphicsEngine.addPointLight(this);
	}
	
	public void setBase(BaseLight base) { this.base = base; }
	public BaseLight getBase() { return base; }
	public void setAttenuation(Attenuation attenuation) { this.attenuation = attenuation; }
	public Attenuation getAttenuation() { return attenuation; }
	public void setPos(Vector3f pos) { this.pos = pos; }
	public Vector3f getPos() { return pos; }
	public void setRange(float range) { this.range = range; } 
	public float getRange() { return range; }
}