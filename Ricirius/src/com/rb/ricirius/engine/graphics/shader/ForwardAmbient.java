package com.rb.ricirius.engine.graphics.shader;

import com.rb.ricirius.engine.graphics.Material;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.math.Matrix4f;

public class ForwardAmbient extends Shader {
	
	private static final ForwardAmbient instance = new ForwardAmbient();
	
	public ForwardAmbient() {
		addVertexShaderFromFile("forward-ambient.vs");
		addFragmentShaderFromFile("forward-ambient.fs");
		compileShader();
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		
		addUniform("MVP");
		addUniform("ambientIntensity");
	}
	
	public void updateUniforms(Transformer transformer, Material material) {
		Matrix4f worldMatrix = transformer.getTransformation();
		Matrix4f projectedMatrix = getGraphicsEngine().getMainCamera().getViewPosition().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("MVP", projectedMatrix);
		setUniform("ambientIntensity", getGraphicsEngine().getAmbientLight());
	}
	
	public static ForwardAmbient getInstance() { return instance; }
}