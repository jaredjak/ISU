package vehicle;

public class GasCar extends Car implements Fillable {

	private GasTank gasTank;
	
	public GasCar(int capacity) {
		this.gasTank = new GasTank(capacity);
	}
	
	@Override
	public void fill(int amount) {
		gasTank.fill(amount);
	}

	@Override
	public void fillToTop() {
		gasTank.fillToTop();
	}
}
