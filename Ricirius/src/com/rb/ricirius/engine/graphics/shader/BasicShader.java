package com.rb.ricirius.engine.graphics.shader;

import com.rb.ricirius.engine.graphics.Material;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.math.Matrix4f;

public class BasicShader extends Shader {

	private static final BasicShader instance = new BasicShader();
	
	public BasicShader() {
		addVertexShaderFromFile("basicVertex.vs");
		addFragmentShaderFromFile("basicFragment.fs");
		compileShader();
		
		addUniform("transform");
		addUniform("color");
	}
	
	public void updateUniforms(Transformer transformer, Material material) {
		Matrix4f worldMatrix = transformer.getTransformation();
		Matrix4f projectedMatrix = getGraphicsEngine().getMainCamera().getViewPosition().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("transform", projectedMatrix);
		setUniform("color", material.getColor());
	}
	
	public static BasicShader getInstance() { return instance; }
}