package me.vem.maze.threading;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

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
	
	private JFrame frame;
	private Camera camera;
	
	public void run() {
		buildFrame();
		
		while(true) {
			frame.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
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
	
}
