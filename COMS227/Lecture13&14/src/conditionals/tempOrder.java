package conditionals;

public class tempOrder {
	public static final double PRICE = 10.0;
	public static final double SHIPPING_COST_PER_SHIRT = 2.0;
	public static final int FREE_SHIPPING_THRESHOLD = 25;
	public static final double TAX_RATE = 0.05;
	
	private int numShirts;
	private boolean isResident;
	
	/**
	 * Base price is price times numShirts
	 * Shipping is SHIPPING_COST_PER_SHIRT times numShirts
	 * For orders of 25 or more, shipping is free
	 * 
	 * tax is base price times tax rate
	 * no tax if resident
	 * @return
	 */
	public double orderTotal() {
		double total = numShirts * PRICE;
		
		// calculates shipping
		if (numShirts < FREE_SHIPPING_THRESHOLD) {
			total += numShirts * SHIPPING_COST_PER_SHIRT;
		}
		
		//calculates tax
		if (!isResident) {
			total += numShirts * PRICE * TAX_RATE;
		}
		
		return total;
	}
}
