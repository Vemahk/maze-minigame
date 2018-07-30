package me.vem.maze.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import me.vem.maze.entity.Player;
import me.vem.maze.threading.ServerThread;

public class ClientHandler extends Thread{

	private HashMap<Integer, Integer> keys;
	
	private Socket client;
	
	private InputStream input;
	private OutputStream output;
	
	public ClientHandler(Socket socket) throws IOException {
		this.client = socket;
		
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		
		keys = new HashMap<>();
		new Player(this);
	}
	
	public void write(byte[] toWrite) throws IOException {
		output.write(toWrite);
	}
	
	public void run() {
		while(ServerThread.getInstance().isAlive() && !client.isClosed()) {
			try {
				int id = input.read();
				int state = input.read();
				
				keys.put(id, state);
				//Logger.info(id +"[" + Integer.toBinaryString(state)+"]");
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	public boolean isPressed(int id) {
		if(!keys.containsKey(id)) return false;
		int data = keys.get(id);
		return (data & 1) == 1;
	}
	
	public boolean isToggled(int id) {
		if(!keys.containsKey(id)) return false;
		int data = keys.get(id);
		return (data & 2) == 1;
	}
}
