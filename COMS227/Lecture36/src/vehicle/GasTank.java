package vehicle;

public class GasTank implements Fillable{
	
	private int capacity;
	private int level;
	
	public GasTank(int takeCapacity) {
		this.capacity = takeCapacity;
		this.level = 0;
	}

	@Override
	public void fill(int amount) {
		this.level = Math.min(this.level + amount, capacity);
	}

	@Override
	public void fillToTop() {
		this.level = capacity;
	}



}
