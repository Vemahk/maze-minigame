package me.vem.maze.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import me.vem.maze.entity.Player;
import me.vem.maze.math.Vector;
import me.vem.maze.struct.Maze;

public class Camera extends JPanel{

	private static final long serialVersionUID = 5404135365651869980L;
	private static final int sqSize = 32;
	
	private Vector pos;
	
	public Camera(Dimension dim) {
		this.setPreferredSize(dim);
		this.setBackground(Color.BLACK);
		
		pos = new Vector(Maze.WIDTH / 2 * sqSize, Maze.HEIGHT / 2 * sqSize);
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Player player = Player.getInstance();
		int prx = getRelX(player.getPos().getX());
		int pry = getRelY(player.getPos().getY());
		int pw = getRelSize(player.getWidth());
		int ph = getRelSize(player.getHeight());
		
		g.setColor(Color.RED);
		g.fillRect(prx, pry, pw, ph);
		
		g.setColor(Color.WHITE);
		
		Maze maze = Maze.getInstance();
		for(int x=0;x<Maze.WIDTH;x++) {
			for(int y=0;y<Maze.HEIGHT;y++) {
				Maze.Node node = maze.get(x, y);
				
				int relX = getRelX(x);
				int relY = getRelY(y);
				
				if(node.get(0)) //UP
					this.drawRoundedLine(g, relX, relY, relX + sqSize, relY, 2);
				if(node.get(1)) //RIGHT
					this.drawRoundedLine(g, relX + sqSize, relY, relX + sqSize, relY + sqSize, 2);
				if(node.get(2)) //
					this.drawRoundedLine(g, relX, relY + sqSize, relX + sqSize, relY + sqSize, 2);
				if(node.get(3))
					this.drawRoundedLine(g, relX, relY, relX, relY + sqSize, 2);
			}
		}
		
		
	}
	
	private int getRelX(double x) { 
		return (int)(x * sqSize) - pos.floorX() + this.getWidth()/2;
	}
	
	private int getRelY(double y) {
		return (int)(y * sqSize) - pos.floorY() + this.getHeight()/2;
	}
	
	private int getRelSize(float f) {
		return (int)(f * sqSize);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param r
	 */
	private void drawRoundedLine(Graphics g, int x1, int y1, int x2, int y2, int r) {
		g.fillRoundRect(x1-r+1, y1-r+1, 2 * r - 1 + (x2-x1), 2 * r - 1 + (y2-y1), r, r);
	}
}
