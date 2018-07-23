package me.vem.maze.threading;

import me.vem.maze.entity.Player;
import me.vem.maze.struct.Maze;

public class ServerThread extends Thread{
	
	private static ServerThread instance;
	public static ServerThread getInstance() {
		if(instance == null)
			instance = new ServerThread();
		return instance;
	}
	
	private static final int UPS = 60;
	
	private Maze mazeRef;
	
	public void run() {
		mazeRef = Maze.getInstance();
		mazeRef.build();
		
		while(true) {
			Player.getInstance().update(UPS);
			
			try {
				Thread.sleep(1000/UPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
