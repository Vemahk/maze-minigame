package me.vem.maze.input;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import me.vem.maze.threading.ClientThread;

public class Setting {
	
	public static final Queue<Setting> all = new LinkedList<>();
	
	public static final Setting QUICKCLOSE = new Setting(0, "Quick Close", KeyEvent.VK_ESCAPE, ActionSet.SHUTDOWN);
	public static final Setting MOVE_UP = new Setting(1, "Move Up", KeyEvent.VK_W);
	public static final Setting MOVE_DOWN = new Setting(2, "Move Down", KeyEvent.VK_S);
	public static final Setting MOVE_RIGHT = new Setting(3, "Move Right", KeyEvent.VK_D);
	public static final Setting MOVE_LEFT = new Setting(4, "Move Left", KeyEvent.VK_A);
	
	private byte id;
	private byte state;
	private String name;
	private int key;

	private Runnable action;
	
	private Setting(int id, String name, int initKey, Runnable action) {
		this.id = (byte)id;
		this.name = name;
		this.key = initKey;
		this.action = action;
		
		all.add(this);
	}
	
	private Setting(int id, String name, int initKey) {
		this(id, name, initKey, null);
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
	
	public byte getId() { return id; }
	public boolean isPressed() { return (state & 1) == 1; }
	public boolean isToggled() { return (state & 2) == 2; }
	
	public void setPressed() {
		state |= 1;
		writeState();
	}

	public void setReleased() {
		state &= -2;
		writeState();
	}

	public void toggle() {
		state ^= 2;
		writeState();
	}
	
	public void writeState() {
		ClientThread.getInstance().write(new byte[] {id, state});
	}
	
	public void run() {
		if(action != null)
			action.run();
	}
	
}
