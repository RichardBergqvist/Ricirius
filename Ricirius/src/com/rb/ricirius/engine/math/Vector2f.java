package com.rb.ricirius.engine.math;

public class Vector2f {

	private float x;
	private float y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float dot(Vector2f vector) {
		return x * vector.getX() + y * vector.getY();
	}
	
	public float cross(Vector2f vector) {
		return x * vector.getY() - y * vector.getX();
	}
	
	public Vector2f normalized() {
		float length = length();
		
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
	}
	
	public Vector2f lerp(Vector2f vector, float lerpFactor) {
		return vector.sub(this).mul(lerpFactor).add(this);
	}
	
	public boolean equals(Vector2f vector) {
		return x == vector.getX() && y == vector.getY();
	}
	
	public Vector2f add(Vector2f vector) {
		return new Vector2f(x + vector.getX(), y + vector.getY());
	}
	
	public Vector2f add(float f) {
		return new Vector2f(x + f, y + f);
	}
	
	public Vector2f sub(Vector2f vector) {
		return new Vector2f(x - vector.getX(), y - vector.getY());
	}
	
	public Vector2f sub(float f) {
		return new Vector2f(x - f, y - f);
	}
	
	public Vector2f mul(Vector2f vector) {
		return new Vector2f(x * vector.getX(), y * vector.getY());
	}
	
	public Vector2f mul(float f) {
		return new Vector2f(x * f, y * f);
	}
	
	public Vector2f div(Vector2f vector) {
		return new Vector2f(x / vector.getX(), y / vector.getY());
	}
	
	public Vector2f div(float f) {
		return new Vector2f(x / f, y / f); }
	
	public String toString() { return "(" + x + ", " + y + ")"; }
	public void setX(float x) { this.x = x; }
	public float getX() { return x; }
	public void setY(float y) { this.y = y; }
	public float getY() { return y; }
}