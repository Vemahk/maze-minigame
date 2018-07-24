package me.vem.maze.entity;

import me.vem.maze.input.Setting;
import me.vem.maze.math.Vector;
import me.vem.maze.struct.Maze;
import me.vem.maze.struct.Maze.Node;

public class Player {

	private static Player instance;
	public static Player getInstance() {
		if(instance == null)
			instance = new Player(.5f, .5f);
		return instance;
	}
	
	private Vector pos;
	private float speed;
	
	private Vector dim;
	
	private Player(float w, float h) {
		this.pos = new Vector(.5f, .5f);
		this.dim = new Vector(w, h);
		this.speed = 5;
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public float getWidth() {
		return dim.getX();
	}
	
	public float getHeight() {
		return dim.getY();
	}
	
	public void update(float tr) {
		
		boolean up = Setting.MOVE_UP.isPressed();
		boolean down = Setting.MOVE_DOWN.isPressed();
		boolean left = Setting.MOVE_LEFT.isPressed();
		boolean right = Setting.MOVE_RIGHT.isPressed();
		
		/* mx --> Move X */
		float mx = (left ^ right ? (left ? -1 : 1) : 0) * speed / tr;
		/* my --> Move Y */
		float my = (up ^ down ? (up ? -1 : 1) : 0) * speed / tr;
		
		/* nx = Node X */
		int nx = pos.floorX();
		/* ny = Node Y */
		int ny = pos.floorY();
		
		Node node = Maze.getInstance().get(nx, ny);
		
		pos.offset(mx, my);
		
		if(node==null) {
			Maze.getInstance().rebuild();
			pos.set(.1f, .1f);
			return;
		}
		if(node.get(3) && (pos.getX() - getWidth()/2 < nx)) pos.setX(nx + getWidth()/2);
		if(node.get(0) && (pos.getY() - getHeight()/2 < ny)) pos.setY(ny + getHeight()/2);
		
		if(node.get(1) && pos.getX() + getWidth()/2 > nx + 1) pos.setX(nx + 1 - getWidth()/2);
		if(node.get(2) && pos.getY() + getHeight()/2 > ny + 1) pos.setY(ny + 1 - getHeight()/2);
	}
	
	public String toString() {
		return String.format("Player[%.3f,%.3f]", pos.getX(), pos.getY());
	}
}
