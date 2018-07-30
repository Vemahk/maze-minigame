package me.vem.maze;

import me.vem.maze.threading.*;

import static me.vem.maze.Logger.*;

public class App {
	
	public static final boolean isClient = true;
	public static final boolean isServer = true;
	
	public static final String VERSION = "0.0.2";
	public static final String NAME = "Mazery";
	
	public static void main(String... args) {
		info("Hello World!");
		warning("JAVA is the most bestest language.");
		
		if(isServer)
			ServerThread.getInstance().start();
		
		if(isClient)
			ClientThread.getInstance().start();
	}
	
	public static void shutdown() {
		ClientThread.getInstance().getFrame().dispose();
		
		System.exit(0);
	}
}
