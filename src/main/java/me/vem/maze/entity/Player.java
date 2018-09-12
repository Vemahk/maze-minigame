package me.vem.maze.entity;

import java.util.LinkedList;
import java.util.List;

import me.vem.maze.socket.ClientHandler;
import me.vem.maze.struct.Maze;
import me.vem.maze.struct.Maze.Node;
import me.vem.utils.math.Vector;

public class Player {

	public static List<Player> all = new LinkedList<>();
	
	private ClientHandler handler;
	
	private Vector pos;
	private float speed;
	
	private Vector dim;
	
	public Player(ClientHandler handler) {
		this.pos = new Vector(.5f, .5f);
		this.dim = new Vector(.5f, .5f);
		this.speed = 5;
		
		this.handler = handler;
		
		all.add(this);
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
		
		boolean up = handler.isPressed(1);
		boolean down = handler.isPressed(2);
		boolean right = handler.isPressed(3);
		boolean left = handler.isPressed(4);
		
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
		
		/* Collision Handling */
		if(node.get(3) && (pos.getX() - getWidth()/2 < nx)) pos.setX(nx + getWidth()/2);
		if(node.get(0) && (pos.getY() - getHeight()/2 < ny)) pos.setY(ny + getHeight()/2);
		
		if(node.get(1) && pos.getX() + getWidth()/2 > nx + 1) pos.setX(nx + 1 - getWidth()/2);
		if(node.get(2) && pos.getY() + getHeight()/2 > ny + 1) pos.setY(ny + 1 - getHeight()/2);
	}
	
	public String toString() {
		return String.format("Player[%.3f,%.3f]", pos.getX(), pos.getY());
	}
}
