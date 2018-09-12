package me.vem.maze;


import me.vem.maze.threading.ClientThread;
import me.vem.maze.threading.ServerThread;
import me.vem.utils.logging.Logger;

public class App {
	
	public static final boolean isClient = true;
	public static final boolean isServer = true;
	
	public static final String VERSION = "0.0.2";
	public static final String NAME = "Mazery";
	
	public static void main(String... args) {
		Logger.info("Hello World!");
		Logger.warning("JAVA is the most bestest language.");
		
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
