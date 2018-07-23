package me.vem.maze.struct;

public class Maze {

	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	private static Maze instance;
	public static Maze getInstance() {
		if(instance == null)
			instance = new Maze(WIDTH, HEIGHT);
		return instance;
	}
	
	private Node[][] maze;
	
	private Maze(int w, int h) {
		maze = new Node[w][h];
		for(int x=0;x<w;x++)
			for(int y=0;y<h;y++)
				maze[x][y] = new Node(x,y);
	}
	
	public void build() {
		for(int x=0;x<WIDTH;x++)
			for(int y=0;y<HEIGHT;y++)
				for(int i=0;i<4;i++)
					if(Math.random() < .3)
						maze[x][y].setOn(i);
	}
	
	public Node get(int x, int y) {
		if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) return null;
		return maze[x][y];
	}
	
	public static class Node{
		
		/**
		 * The 4 least significant bits will be used to signify which sides this node has walls on. 4 booleans, simply, in one byte.
		 */
		private byte walls;
		private int x;
		private int y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			if(x==0) setOn(3, false);
			if(y==0) setOn(0, false);
			if(x==Maze.WIDTH-1) setOn(1, false);
			if(y==Maze.HEIGHT-1) setOn(2, false);
		}
		
		public Node getRelNode(int i) {
			Dirs dir = Dirs.get(i);
			return Maze.getInstance().get(x + dir.dx, y + dir.dy);
		}
		
		/**
		 * Gets the state of the i'th wall.
		 * 0 - TOP
		 * 1 - RIGHT
		 * 2 - BOTTOM
		 * 3 - LEFT
		 * @param i
		 * @return Whether the wall on the i'th side is set.
		 */
		public boolean get(int i) {
			return (walls & (1<<i)) != 0;
		}
		
		private void setOn(int i, boolean chkOther) {
			walls |= (1<<i);
			if(chkOther) {
				Node other = getRelNode(i);
				if(other != null)
					other.setOn((i + 2) % 4, false);
			}
		}
		
		/**
		 * Sets the state of the i'th wall to 'on' (i.e. the wall will exist).
		 * 0 - TOP
		 * 1 - RIGHT
		 * 2 - BOTTOM
		 * 3 - LEFT
		 * @param i
		 */
		public void setOn(int i) {
			setOn(i, true);
		}
		
		private void setOff(int i, boolean chkOther) {
			int mask = -1 ^ (1<<i);
			walls &= mask;
			if(chkOther) {
				Node other = getRelNode(i);
				if(other != null)
					other.setOff((i + 2) % 4, false);
			}
		}
		
		/**
		 * Sets the state of the i'th wall to 'off' (i.e. the wall will not exist).
		 * 0 - TOP
		 * 1 - RIGHT
		 * 2 - BOTTOM
		 * 3 - LEFT
		 * @param i
		 */
		public void setOff(int i) {
			setOff(i, true);
		}
		
		/**
		 * Toggles the wall on the i'th side.
		 * 0 - TOP
		 * 1 - RIGHT
		 * 2 - BOTTOM
		 * 3 - LEFT
		 * @param i
		 * @return What the state of the wall was.
		 */
		public boolean toggle(int i) {
			boolean out = get(i);
			
			if(out) setOff(i);
			else setOn(i);
			
			return out;
		}
		
		public String toString() {
			return String.format("Node[%d,%d][%d:%d:%d:%d]", x, y, get(0) ? 1 : 0, get(1) ? 1 : 0, get(2) ? 1 : 0, get(3) ? 1 : 0);
		}
	}
	
	public static enum Dirs{
		UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);
		
		private int dx;
		private int dy;
		
		private Dirs(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
		
		public int dx() { return dx; }
		public int dy() { return dy; }
		
		public static Dirs get(int x) {
			return Dirs.values()[x];
		}
	}
}