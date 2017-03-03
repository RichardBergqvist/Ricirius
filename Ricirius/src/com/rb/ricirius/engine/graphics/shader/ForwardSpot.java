package com.rb.ricirius.engine.graphics.shader;

import com.rb.ricirius.engine.graphics.Material;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.components.light.*;
import com.rb.ricirius.engine.math.Matrix4f;

public class ForwardSpot extends Shader {

 private static final ForwardSpot instance = new ForwardSpot();
	
	public ForwardSpot() {
		addVertexShaderFromFile("forward-spot.vs");
		addFragmentShaderFromFile("forward-spot.fs");
		compileShader();
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("spotLight.pointLight.base.color");
		addUniform("spotLight.pointLight.base.intensity");
		addUniform("spotLight.pointLight.atten.constant");
		addUniform("spotLight.pointLight.atten.linear");
		addUniform("spotLight.pointLight.atten.exponent");
		addUniform("spotLight.pointLight.position");
		addUniform("spotLight.pointLight.range");
		addUniform("spotLight.direction");
		addUniform("spotLight.cutoff");
	}
	
	public void updateUniforms(Transformer transformer, Material material) {
		Matrix4f worldMatrix = transformer.getTransformation();
		Matrix4f projectedMatrix = getGraphicsEngine().getMainCamera().getViewPosition().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniform("spotLight", getGraphicsEngine().getActiveSpotLight());
		
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
	
	public void setUniform(String uniformName, SpotLight spotLight) {
		setUniform(uniformName + ".pointLight", spotLight.getPointLight());
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
	}
	
	public static ForwardSpot getInstance() { return instance; }
}