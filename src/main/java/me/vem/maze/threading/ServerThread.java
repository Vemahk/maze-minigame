package me.vem.maze.threading;

import me.vem.maze.entity.Player;
import me.vem.maze.entity.Wanderer;
import me.vem.maze.socket.SocketHost;
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
		
		SocketHost.getInstance();
		
		mazeRef = Maze.getInstance();
		mazeRef.build();
		
		while(true) {
			for(Player player : Player.all)
				player.update(UPS);
			
			Wanderer.getInstance().update(UPS);
			
			try {
				int nanosleep = 1000000000 / UPS;
				Thread.sleep(nanosleep / 1000000, nanosleep % 1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
