package me.vem.maze.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

import me.vem.maze.App;
import me.vem.maze.threading.ServerThread;
import me.vem.utils.logging.Logger;

public class SocketHost extends ServerSocket{

	public static final int theport = 9090;
	
	private static SocketHost instance;
	public static SocketHost getInstance() {
		if(!App.isServer)
			return null;
		if(instance == null) {
			try {
				instance = new SocketHost(theport);
			} catch (IOException e) { e.printStackTrace(); }
		}
		return instance;
	}
	
	private LinkedList<ClientHandler> openClients;
	
	public SocketHost(int port) throws IOException {
		super(port);
		
		openClients = new LinkedList<>();
		
		new Thread(() -> {
			while(ServerThread.getInstance().isAlive()) {
				try{
					ClientHandler ci = new ClientHandler(accept());
					openClients.add(ci);
					ci.start();
					
					Logger.info("Client connected.");
				} catch (IOException e) { e.printStackTrace(); }
			}
		}).start();
	}
	
	public void distribute(byte[] out) throws IOException {
		for(ClientHandler ci : openClients)
			ci.write(out);
	}
}
