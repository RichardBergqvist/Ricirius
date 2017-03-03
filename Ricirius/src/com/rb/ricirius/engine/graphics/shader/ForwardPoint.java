package com.rb.ricirius.engine.graphics.shader;

import com.rb.ricirius.engine.graphics.Material;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.components.light.*;
import com.rb.ricirius.engine.math.Matrix4f;

public class ForwardPoint extends Shader {

 private static final ForwardPoint instance = new ForwardPoint();
	
	public ForwardPoint() {
		addVertexShaderFromFile("forward-point.vs");
		addFragmentShaderFromFile("forward-point.fs");
		compileShader();
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("pointLight.base.color");
		addUniform("pointLight.base.intensity");
		addUniform("pointLight.atten.constant");
		addUniform("pointLight.atten.linear");
		addUniform("pointLight.atten.exponent");
		addUniform("pointLight.position");
		addUniform("pointLight.range");
	}
	
	public void updateUniforms(Transformer transformer, Material material) {
		Matrix4f worldMatrix = transformer.getTransformation();
		Matrix4f projectedMatrix = getGraphicsEngine().getMainCamera().getViewPosition().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniform("pointLight", getGraphicsEngine().getActivePointLight());
		
		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularPower", material.getSpecularPower());
		setUniform("eyePos", getGraphicsEngine().getMainCamera().getPos());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, PointLight pointLight) {
		setUniform(uniformName + ".base", pointLight.getBase());
		setUniformf(uniformName + ".atten.constant", pointLight.getAttenuation().getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getAttenuation().getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getAttenuation().getExponent());
		setUniform(uniformName + ".position", pointLight.getPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
	public static ForwardPoint getInstance() { return instance; }
}