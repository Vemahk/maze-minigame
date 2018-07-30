package me.vem.maze.threading;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import me.vem.maze.App;
import me.vem.maze.Logger;
import me.vem.maze.graphics.Camera;
import me.vem.maze.input.Input;

public class ClientThread extends Thread{

	private static ClientThread instance;
	public static ClientThread getInstance() {
		if(instance == null)
			instance = new ClientThread();
		return instance;
	}

	private static final int FPS = 60;
	
	private Socket client;
	private InputStream fromServer;
	private OutputStream toServer;
	
	private JFrame frame;
	private Camera camera;
	
	public void run() {
		if(App.isServer) {
			try {
				client = new Socket("localhost", 9090);
				fromServer = client.getInputStream();
				toServer = client.getOutputStream();
				
				Logger.info("Connected to localhost server.");
			} catch (IOException e1) { e1.printStackTrace(); }
		
			buildFrame();
		
			while(true) {
				frame.repaint();
				try {
					int nanosleep = 100000000 / FPS;
					Thread.sleep(nanosleep / 1000000, nanosleep % 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else {
			
		}
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		try {
			client.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public JFrame getFrame() { return frame; }
	public Camera getCamera() { return camera; }
	
	private void buildFrame() {
		frame = new JFrame("Mazery!");
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.add(camera = new Camera(screenDim));
		//frame.add(camera = new Camera(new Dimension(512, 512)));
		
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.addKeyListener(Input.getInstance());
		frame.addMouseListener(Input.getInstance());
		frame.addMouseMotionListener(Input.getInstance());
	}

	/**
	 * This writes the byte array 'bs' to the output stream that sends data from the client to the server.
	 * @param bs
	 */
	public void write(byte[] bs) {
		try {
			for(byte b : bs)
				toServer.write(b);
		} catch (IOException e) { e.printStackTrace(); }
	}
}
