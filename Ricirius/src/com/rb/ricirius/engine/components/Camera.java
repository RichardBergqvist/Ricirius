package com.rb.ricirius.engine.components;

import com.rb.ricirius.engine.graphics.Window;
import com.rb.ricirius.engine.input.Input;
import com.rb.ricirius.engine.math.Matrix4f;
import com.rb.ricirius.engine.math.Vector2f;
import com.rb.ricirius.engine.math.Vector3f;

public class Camera {

	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f pos;
	private Vector3f forward;
	private Vector3f up;
	private Matrix4f projection;
	
	public Camera(float fov, float aspect, float zNear, float zFar) {
		this.pos = new Vector3f(0, 0, 0);
		this.forward = new Vector3f(0, 0, 1).normalized();
		this.up = new Vector3f(0, 1, 0).normalized();
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}
	
	boolean mouseLocked = false;
	Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
	
	public void input(float delta) {
		float sensitivity = 0.5F;
		float movAmt = (float) (10 * delta);
		//float rotAmt = (float) (100 * delta);
		
		if (Input.getKey(Input.KEY_ESCAPE)) {
			Input.setCursor(true);
			mouseLocked = false;
		}
		
		if (Input.getMouseDown(0)) {
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		if (Input.getKey(Input.KEY_W))
			move(getForward(), movAmt);
		if (Input.getKey(Input.KEY_S))
			move(getForward(), -movAmt);
		if (Input.getKey(Input.KEY_A))
			move(getLeft(), movAmt);
		if (Input.getKey(Input.KEY_D))
			move(getRight(), movAmt);
		
		if (mouseLocked) {
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if (rotY)
				rotateY(deltaPos.getX() * sensitivity);
			if (rotX)
				rotateX(-deltaPos.getY() * sensitivity);
			
			if (rotY || rotX)
				Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
		}
	}
	
	public void move(Vector3f dir, float amt) {
		pos = pos.add(dir.mul(amt));
	}
	
	public void rotateY(float angle) {
		Vector3f hAxis = yAxis.cross(forward).normalized();
		forward = forward.rotate(angle, yAxis).normalized();
		up = forward.cross(hAxis).normalized();
	}
	
	public void rotateX(float angle) {
		Vector3f hAxis = yAxis.cross(forward).normalized();
		forward = forward.rotate(angle, hAxis).normalized();
		up = forward.cross(hAxis).normalized();
	}
	
	public Vector3f getLeft() { return forward.cross(up).normalized(); }
	public Vector3f getRight() { return up.cross(forward).normalized(); }
	public void setPos(Vector3f pos) { this.pos = pos; }
	public Vector3f getPos() { return pos; }
	public void setForward(Vector3f forward) { this.forward = forward; }
	public Vector3f getForward() { return forward; }
	public void setUp(Vector3f up) { this.up = up; }
	public Vector3f getUp() { return up; }
	
	public Matrix4f getViewPosition() {
		Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
		
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
}