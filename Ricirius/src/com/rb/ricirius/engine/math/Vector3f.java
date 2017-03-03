package com.rb.ricirius.engine.math;

public class Vector3f {

	private float x;
	private float y;
	private float z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float dot(Vector3f vector) {
		return x * vector.getX() + y * vector.getY() + z * vector.getZ();
	}
	
	public Vector3f cross(Vector3f vector) {
		float x_ = y * vector.getZ() - z * vector.getY();
		float y_ = z * vector.getX() - x * vector.getZ();
		float z_ = x * vector.getY() - y * vector.getX();
		
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f normalized() {
		float length = length();
		return new Vector3f(x / length, y / length, z / length);
	}
	
	public Vector3f rotate(float angle, Vector3f axis) {
		float sinHalfAngle = (float) Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float) Math.cos(Math.toRadians(angle / 2));
		
		float rX = axis.getX() * sinHalfAngle;
		float rY = axis.getY() * sinHalfAngle;
		float rZ = axis.getZ() * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.conjugate();
		Quaternion w = rotation.mul(this).mul(conjugate); 
		
		x = w.getX();
		y = w.getY();
		z = w.getZ();
		
		return this;
	}
	
	public Vector3f lerp(Vector3f vector, float lerpFactor) {
		return vector.sub(this).mul(lerpFactor).add(this);
	}
	
	public boolean equals(Vector3f vector) {
		return x == vector.getX() && y == vector.getY() && z == vector.getZ();
	}
	
	public Vector3f add(Vector3f vector) {
		return new Vector3f(x + vector.getX(), y + vector.getY(), z + vector.getZ());
	}
	
	public Vector3f add(float f) {
		return new Vector3f(x + f, y + f, z + f);
	}
	
	public Vector3f sub(Vector3f vector) {
		return new Vector3f(x - vector.getX(), y - vector.getY(), z - vector.getZ());
	}
	
	public Vector3f sub(float f) {
		return new Vector3f(x - f, y - f, z - f);
	}
	
	public Vector3f mul(Vector3f vector) {
		return new Vector3f(x * vector.getX(), y * vector.getY(), z * vector.getZ());
	}
	
	public Vector3f mul(float f) {
		return new Vector3f(x * f, y * f, z * f);
	}
	
	public Vector3f div(Vector3f vector) {
		return new Vector3f(x / vector.getX(), y / vector.getY(), z / vector.getZ());
	}
	
	public Vector3f div(float f) {
		return new Vector3f(x / f, y / f, z / f);
	}
	
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	public String toString() { return "(" + x + ", " + y + ", " + z + ")"; }
	public Vector2f getXY() { return new Vector2f(x, y); }
	public Vector2f getYZ() { return new Vector2f(y, z); }
	public Vector2f getZX() { return new Vector2f(z, x); }
	public Vector2f getYX() { return new Vector2f(y, x); }
	public Vector2f getZY() { return new Vector2f(z, y); }
	public Vector2f getXZ() { return new Vector2f(x, z); }
	public void setX(float x) { this.x = x; }
	public float getX() { return x; }
	public void setY(float y) { this.y = y; }
	public float getY() { return y; }
	public void setZ(float z) { this.z = z; }
	public float getZ() { return z; }
}