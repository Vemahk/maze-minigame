package me.vem.maze.math;

import java.awt.Point;

public class Vector {

	private float x;
	private float y;
	
	public Vector() {}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector copy() {
		return new Vector(this);
	}
	
	public float getX() { return x; }
	public int roundX() { return Math.round(x); }
	public int floorX() { return (int) Math.floor(x); }
	public int ceilX () { return (int) Math.ceil (x); }
	
	public float getY() { return y; }
	public int roundY() { return Math.round(y); }
	public int floorY() { return (int) Math.floor(y); }
	public int ceilY () { return (int) Math.ceil (y); }
	
	public Point round() { return new Point(roundX(), roundY()); }
	public Point floor() { return new Point(floorX(), floorY()); }
	public Point ceil() { return new Point(ceilX(), ceilY()); }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector v) {
		set(v.x, v.y);
	}
	
	public void offset(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void offset(Vector dv) {
		offset(dv.x, dv.y);
	}
	
	public void offset(Point p) {
		offset(p.x, p.y);
	}
	
	public void offsetX(float dx) { this.x += dx; }
	public void offsetY(float dy) { this.y += dy; }
	
	public float getMagnetude() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float getMagSq() {
		return x*x + y*y;
	}
	
	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}
	
	public Vector sub(Vector v) {
		return new Vector(x - v.x, y - v.y);
	}
	
	public Vector scale(float scalar) {
		return new Vector(x * scalar, y * scalar);
	}
	
	public float dot(Vector v) { 
		return x * v.x + y * v.y;
	}
	
	public String toString() {
		return String.format("Vector[%.3f, %.3f]", x, y);
	}
	
	/**
	 * Creates an x-y Vector based on a polar vector of magnitude r and direction th (theta).
	 * @param r Magnitude
	 * @param th Angle (in radians)
	 * @return
	 */
	public static Vector fromPolar(float r, float th) {
		return new Vector(r * (float)Math.cos(th), r * (float)Math.sin(th));
	}
}
