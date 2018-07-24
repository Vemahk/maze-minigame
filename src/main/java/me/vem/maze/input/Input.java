package me.vem.maze.input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import me.vem.maze.math.Vector;
import me.vem.maze.threading.ClientThread;

public class Input implements KeyListener, MouseListener, MouseMotionListener{

	private static Input instance;
	public static Input getInstance() {
		if(instance == null)
			instance = new Input();
		return instance;
	}
	
	/* --== KEY LISTENING BEING ==-- */
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(Setting setting : Setting.all)
			if(setting.getKeyCode() == key)
				setting.setPressed();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(Setting setting : Setting.all)
			if(setting.getKeyCode() == key) {
				setting.setReleased();
				setting.toggle();
				setting.run();
			}
	}
	
	/* --==   KEY LISTENING END   ==-- 
	 * 
	 * --== MOUSE LISTENING BEGIN ==--*/
	
	private Point onMouseDown;
	private Vector initCamPos;
	
	public void mousePressed(MouseEvent e) {
		onMouseDown = e.getPoint();
		initCamPos = ClientThread.getInstance().getCamera().getPos().copy();
	}

	public void mouseReleased(MouseEvent e) {
		onMouseDown = null;
		initCamPos = null;
	}

	public void mouseDragged(MouseEvent e) {
		if(onMouseDown == null) return;
		Vector dv = new Vector(onMouseDown.x - e.getX(), onMouseDown.y - e.getY());
		ClientThread.getInstance().getCamera().getPos().set(initCamPos.add(dv));
	}

	/* --==  MOUSE LISTENING END  ==-- */
	
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) { }
}
