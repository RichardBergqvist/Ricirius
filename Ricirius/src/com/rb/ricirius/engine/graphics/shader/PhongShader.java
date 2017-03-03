package com.rb.ricirius.engine.graphics.shader;

import com.rb.ricirius.engine.graphics.Material;
import com.rb.ricirius.engine.graphics.Transformer;
import com.rb.ricirius.engine.components.light.*;
import com.rb.ricirius.engine.math.Matrix4f;
import com.rb.ricirius.engine.math.Vector3f;

public class PhongShader extends Shader {

	private static final PhongShader instance = new PhongShader();
	
	private static final int MAX_POINT_LIGHTS = 4;
	private static final int MAX_SPOT_LIGHTS = 4;
	private static Vector3f ambientLight = new Vector3f(0.1F, 0.1F, 0.1F);
	private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0), new Vector3f(0, 0, 0));
	private static PointLight[] pointLights = new PointLight[] {};
	private static SpotLight[] spotLights = new SpotLight[] {};
	
	public PhongShader() {
		addVertexShaderFromFile("phongVertex.vs");
		addFragmentShaderFromFile("phongFragment.fs");
		compileShader();
		
		addUniform("transform");
		addUniform("transformProjected");
		addUniform("baseColor");
		addUniform("ambientLight");
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
		
		for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
			addUniform("pointLights[" + i + "].base.color");
			addUniform("pointLights[" + i + "].base.intensity");
			addUniform("pointLights[" + i + "].atten.constant");
			addUniform("pointLights[" + i + "].atten.linear");
			addUniform("pointLights[" + i + "].atten.exponent");
			addUniform("pointLights[" + i + "].position");
			addUniform("pointLights[" + i + "].range");
		}
		
		for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
			addUniform("spotLights[" + i + "].pointLight.base.color");
			addUniform("spotLights[" + i + "].pointLight.base.intensity");
			addUniform("spotLights[" + i + "].pointLight.atten.constant");
			addUniform("spotLights[" + i + "].pointLight.atten.linear");
			addUniform("spotLights[" + i + "].pointLight.atten.exponent");
			addUniform("spotLights[" + i + "].pointLight.position");
			addUniform("spotLights[" + i + "].pointLight.range");
			addUniform("spotLights[" + i + "].direction");
			addUniform("spotLights[" + i + "].cutoff");
		}
	}
	
	public void updateUniforms(Transformer transformer, Material material) {
		Matrix4f worldMatrix = transformer.getTransformation();
		Matrix4f projectedMatrix = getGraphicsEngine().getMainCamera().getViewPosition().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("transformProjected", projectedMatrix);
		setUniform("transform", worldMatrix);
		setUniform("baseColor", material.getColor());
		setUniform("ambientLight", ambientLight);
		setUniform("directionalLight" , directionalLight);
		
		for (int i = 0; i < pointLights.length; i++)
			setUniform("pointLights[" + i + "]", pointLights[i]);
		
		for (int i = 0; i < spotLights.length; i++)
			setUniform("spotLights[" + i + "]", spotLights[i]);
		
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
	
	public static void setAmbientLight(Vector3f ambientLight) { PhongShader.ambientLight = ambientLight; }
	public static Vector3f getAmbientLight() { return ambientLight; }
	public static void setDirectionalLight(DirectionalLight directionalLight) { PhongShader.directionalLight = directionalLight; }
	public static DirectionalLight getDirectionalLight() { return directionalLight; }
	
	public static void setPointLight(PointLight[] pointLights) {
		if (pointLights.length > MAX_POINT_LIGHTS) {
			System.err.println("Error: Point Light limit exceeded. The maximum amount of allowed Point Lights is: " + MAX_POINT_LIGHTS + " You added " + pointLights.length + " Point Lights.");
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		PhongShader.pointLights = pointLights;
	}
	
	public static void setSpotLight(SpotLight[] spotLights) {
		if (spotLights.length > MAX_SPOT_LIGHTS) {
			System.err.println("Error: Spot Light limit exceeded. The maximum amount of allowed Spot Lights is: " + MAX_SPOT_LIGHTS + " You added " + spotLights.length + " Spot Lights.");
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		PhongShader.spotLights = spotLights;
	}
	
	public static PointLight[] getPointLight() { return pointLights; }
	public static PhongShader getInstance() { return instance; }
}