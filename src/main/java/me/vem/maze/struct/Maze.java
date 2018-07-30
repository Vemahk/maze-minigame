package me.vem.maze.struct;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Maze {

	public static final int WIDTH = 50;
	public static final int HEIGHT = 30;
	
	private static Maze instance;
	public static Maze getInstance() {
		if(instance == null)
			instance = new Maze(WIDTH, HEIGHT);
		return instance;
	}
	
	public Random rand;
	private Node[][] maze;
	
	private Maze(int w, int h) {
		maze = new Node[w][h];
		rand = new Random();
		for(int x=0;x<w;x++)
			for(int y=0;y<h;y++)
				maze[x][y] = new Node(x,y);
		
		maze[w-1][h-1].setOn(Dir.E);
	}
	
	public void rebuild() {
		for(int x=0;x<WIDTH;x++)
			for(int y=0;y<HEIGHT;y++)
				maze[x][y] = new Node(x,y);
		
		maze[WIDTH-1][HEIGHT-1].setOn(Dir.E);
		build();
	}
	
	public void build() {
		maze[0][0].generate();
	}
	
	public Node get(int x, int y) {
		if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) return null;
		return maze[x][y];
	}
	
	public class Node{
		
		/**
		 * The 4 least significant bits will be used to signify which sides this node has walls on. 4 booleans, simply, in one byte.
		 */
		private byte walls;
		private int x;
		private int y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void generate() {
			Dir[] dirs = Dir.values();
			Collections.shuffle(Arrays.asList(dirs));
			
			for(Dir dir : dirs) {
				Node o = getRelNode(dir);
				if(o != null && o.get(4)) {
					setOn(dir);
					
					walls |= 1<<4; //Set both as 'changed' (the 5th bit marks that).
					o.walls |= 1<<4;
					
					o.generate();
				}
			}
		}
		
		public Node getRelNode(int i) {
			return getRelNode(Dir.values()[i]);
		}
		
		public Node getRelNode(Dir dir) {
			return Maze.this.get(x + dir.dx(), y + dir.dy());
		}
		
		public boolean isWall(Dir dir) {
			return get(dir.bit());
		}
		
		public boolean get(int i) {
			return (walls & (1<<i)) == 0;
		}
		
		public void setOn(Dir dir) {
			walls |= dir.mask();
			Node other = getRelNode(dir);
			if(other != null)
				other.walls |= dir.opp().mask();
		}
		
		public void setOff(int i) {
			walls &= ~(1<<i);
			Node other = getRelNode(i);
			if(other != null)
				other.walls &= ~(1<<(i+2&3));
		}
		
		public String toString() {
			return String.format("Node[%d,%d][%d:%d:%d:%d]", x, y, get(0) ? 1 : 0, get(1) ? 1 : 0, get(2) ? 1 : 0, get(3) ? 1 : 0);
		}
	}
}