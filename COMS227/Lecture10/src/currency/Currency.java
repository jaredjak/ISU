package currency;

public class Currency {
	private double rate;
	
	public Currency(double rate) {
		this.rate = rate;
	}
	
	public double convert(double amount) {
		return amount * rate;
	}

}
