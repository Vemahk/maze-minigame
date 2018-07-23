package me.vem.maze.input;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

public class Setting {
	
	public static final Queue<Setting> all = new LinkedList<>();
	
	public static final Setting QUICKCLOSE = new Setting("Quick Close", KeyEvent.VK_ESCAPE, ActionSet.SHUTDOWN);
	public static final Setting MOVE_UP = new Setting("Move Up", KeyEvent.VK_W);
	public static final Setting MOVE_DOWN = new Setting("Move Down", KeyEvent.VK_S);
	public static final Setting MOVE_RIGHT = new Setting("Move Right", KeyEvent.VK_D);
	public static final Setting MOVE_LEFT = new Setting("Move Left", KeyEvent.VK_A);
	
	private byte state;
	private String name;
	private int key;

	private Runnable action;
	
	private Setting(String name, int initKey, Runnable action) {
		this.name = name;
		this.key = initKey;
		this.action = action;
		
		all.add(this);
	}
	
	private Setting(String name, int initKey) {
		this(name, initKey, null);
	}
	
	public String getDisplay() {
		return name;
	}
	
	public int getKeyCode() {
		return key;
	}
	
	public String getKeyDisplay() {
		return KeyEvent.getKeyText(key);
	}
	
	public Setting setAction(Runnable action) {
		this.action = action;
		return this;
	}
	
	public void resetAction() { this.action = null; }
	
	public boolean isPressed() { return (state & 1) == 1; }
	public boolean isToggled() { return (state & 2) == 2; }
	
	public void setPressed() { state |= 1; }
	public void setReleased() { state &= -2; }
	public void toggle() { state ^= 2; }
	
	public void run() {
		if(action != null)
			action.run();
	}
	
}
