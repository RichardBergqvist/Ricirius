package com.rb.ricirius.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

import java.util.ArrayList;

import com.rb.ricirius.engine.GameObject;
import com.rb.ricirius.engine.components.Camera;
import com.rb.ricirius.engine.components.light.*;
import com.rb.ricirius.engine.graphics.shader.*;
import com.rb.ricirius.engine.math.Vector3f;

public class GraphicsEngine {

	private Camera mainCamera;
	private Vector3f ambientLight;
	private DirectionalLight activeDirectionalLight;
	private PointLight activePointLight;
	private SpotLight activeSpotLight;
	
//	private PointLight[] pointLightList;
	
	private ArrayList<DirectionalLight> directionalLights;
	private ArrayList<PointLight> pointLights;
	private ArrayList<SpotLight> spotLights;
	
	public GraphicsEngine() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_CLAMP);
		glEnable(GL_TEXTURE_2D);
		
		directionalLights = new ArrayList<DirectionalLight>();
		pointLights = new ArrayList<PointLight>();
		spotLights = new ArrayList<SpotLight>();
		
		mainCamera = new Camera((float) Math.toRadians(70), (float) Window.getWidth() / (float) Window.getHeight(), 0.01F, 1000);
		
		ambientLight = new Vector3f(0.1F, 0.1F, 0.1F);
//		activeDirectionalLight = new DirectionalLight(new BaseLight(new Vector3f(0, 0, 1), 0.4F), new Vector3f(1, 1, 1));
//		directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(1, 0, 0), 0.4F), new Vector3f(-1, 1, -1));
//		
//		int lightFieldWidth = 5;
//		int lightFieldDepth = 5;
//		
//		float lightFieldStartX = 0;
//		float lightFieldStartY = 0;
//		float lightFieldStepX = 7;
//		float lightFieldStepY = 7;
//		
//		pointLightList = new PointLight[lightFieldWidth * lightFieldDepth];
//		
//		for (int i = 0; i < lightFieldWidth; i++) {
//			for (int j = 0; j < lightFieldDepth; j++) {
//				pointLightList[i * lightFieldWidth + j] = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.4F),
//						                                  new Attenuation(0, 0, 1),
//						                                  new Vector3f(lightFieldStartX + lightFieldStepX * i, 0, lightFieldStartY + lightFieldStepY * j), 100);
//			}
//		}
//		
//		activePointLight = pointLightList[0];
//		activeSpotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(0, 1, 1), 0.4F),
//				    new Attenuation(0, 0, 0.1F),
//				    new Vector3f(lightFieldStartX, 0, lightFieldStartY), 100),
//				    new Vector3f(1, 0, 0), 0.7F);
	}
	
	public void input(float delta) {
		mainCamera.input(delta);
	}
	
	public void render(GameObject object) {
		clearScreen();
		
		clearLightList();
		object.addToGraphicsEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		Shader forwardDirectional = ForwardDirectional.getInstance();
		Shader forwardPoint = ForwardPoint.getInstance();
		Shader forwardSpot = ForwardSpot.getInstance();
		forwardAmbient.setGraphicsEngine(this);
		forwardDirectional.setGraphicsEngine(this);
		forwardPoint.setGraphicsEngine(this);
		forwardSpot.setGraphicsEngine(this);
		
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for (DirectionalLight light : directionalLights) {
			activeDirectionalLight = light;
			object.render(forwardDirectional);
		}
		
		for (PointLight light : pointLights) {
			activePointLight = light;
			object.render(forwardPoint);
		}
		
		for (SpotLight light : spotLights) {
			activeSpotLight = light;
			object.render(forwardSpot);
		}
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	private void clearLightList() {
		directionalLights.clear();
		pointLights.clear();
		spotLights.clear();
	}
	
	public void addDirectionalLight(DirectionalLight directionalLight) { directionalLights.add(directionalLight); }
	public void addPointLight(PointLight pointLight) { pointLights.add(pointLight); }
	public void addSpotLight(SpotLight spotLight) { spotLights.add(spotLight); }
	
	private static void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void setTextures(boolean enabled) {
		if (enabled)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
	}
	
	private static void unbindTextures() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private static void setClearColor(Vector3f color) {
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0F);
	}
	
	public Vector3f getAmbientLight() { return ambientLight; }
	public DirectionalLight getActiveDirectionalLight() { return activeDirectionalLight; }
	public PointLight getActivePointLight() { return activePointLight; }
	public SpotLight getActiveSpotLight() { return activeSpotLight; }
	public void setMainCamera(Camera mainCamera) { this.mainCamera = mainCamera; }
	public Camera getMainCamera() { return mainCamera; }
	public static String getOpenGLVersion() { return glGetString(GL_VERSION); }
}