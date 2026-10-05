package carParts;

public class GasTank {
	private int capacity;
	private int level;
	
	public GasTank(int capacity) {
		this.capacity = capacity;
	}
	
	public GasTank() {
		this(13);
	}

	public int getCapacity() {
		return capacity;
	}

	public void add(int amount) {
		level = Math.min(level + amount, this.getCapacity());
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = Math.min(level, this.getCapacity());
	}

	public static void setLevel(GasTank tank, int level) {
		tank.level = Math.min(level, tank.getCapacity());
	}
	
}
