package com.rb.ricirius.engine.graphics.shader;

import com.rb.ricirius.engine.graphics.Material;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.components.light.*;
import com.rb.ricirius.engine.math.Matrix4f;

public class ForwardDirectional extends Shader {

 private static final ForwardDirectional instance = new ForwardDirectional();
	
	public ForwardDirectional() {
		addVertexShaderFromFile("forward-directional.vs");
		addFragmentShaderFromFile("forward-directional.fs");
		compileShader();
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
	}
	
	public void updateUniforms(Transformer transformer, Material material) {
		Matrix4f worldMatrix = transformer.getTransformation();
		Matrix4f projectedMatrix = getGraphicsEngine().getMainCamera().getViewPosition().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);
		
		setUniform("directionalLight", getGraphicsEngine().getActiveDirectionalLight());
		
		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularPower", material.getSpecularPower());
		setUniform("eyePos", getGraphicsEngine().getMainCamera().getPos());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, DirectionalLight directionalLight) {
		setUniform(uniformName + ".base", directionalLight.getBase());
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
	
	public static ForwardDirectional getInstance() { return instance; }
}