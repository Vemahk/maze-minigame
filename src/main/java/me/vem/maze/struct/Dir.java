package me.vem.maze.struct;

public enum Dir{
	N(0, -1, 0), E(1, 0, 1), S(0, 1, 2), W(-1, 0, 3);
	
	private int dx;
	private int dy;
	private int bit;
	
	private Dir(int dx, int dy, int bit) {
		this.dx = dx;
		this.dy = dy;
		this.bit = bit;
	}
	
	public int dx() { return dx; }
	public int dy() { return dy; }
	public int bit() { return bit; }
	public int mask() { return 1<<bit; }

	public Dir opp() {
		return this==N ? S : (this==S ? N : (this==E ? W : E));
	}
}