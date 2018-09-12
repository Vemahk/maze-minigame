package me.vem.maze.entity;

import java.util.LinkedList;

import me.vem.maze.struct.Dir;
import me.vem.maze.struct.Maze;
import me.vem.maze.struct.Maze.Node;
import me.vem.utils.math.Vector;

public class Wanderer {
	
	private static Wanderer instance;
	public static Wanderer getInstance() {
		if(instance == null)
			instance = new Wanderer(.5f, .5f);
		return instance;
	}
	
	private Vector pos;
	private float speed;
	
	private Vector dim;
	
	public Wanderer(float w, float h) {
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
	
	private int moveTicks = 0;
	private Dir dir;
	
	private LinkedList<Node> history;
	public void update(float tr) {
		if(history==null)
			history = new LinkedList<>();

		if(moveTicks > 0) {
			if(--moveTicks == 0)
				this.pos.set(pos.floorX() + .5f, pos.floorY() + .5f);
			else pos.offset(dir.dx() * speed / tr, dir.dy() * speed / tr);
		}else {
			
			Node cur = Maze.getInstance().get(pos.floorX(), pos.floorY());
			Node target = null;
			Dir tDir = null;
			
			for(Dir pDir : Dir.values()) {
				if(!cur.isWall(pDir)){
					Node pt = cur.getRelNode(pDir); // pt >> Potential Target
					if(target == null || history.indexOf(pt) < history.indexOf(target)) {
						target = pt;
						tDir = pDir;
					}
				}
			}
			
			this.dir = tDir;
			moveTicks = (int)Math.floor(tr / speed);
			if(history.contains(target))
				history.remove(target);
			history.add(target);
		}
	}
	
	public String toString() {
		return String.format("Wanderer[%.3f,%.3f]", pos.getX(), pos.getY());
	}
}

