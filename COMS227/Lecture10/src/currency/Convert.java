package currency;

public class Convert {
	public static final double EUR_RATE = 1.5;
	public static final double RUB_RATE = 2.0;
	public static final double YEN_RATE = 10.0;
	
//	public static double convert(double amount, Currency currency) {
//		return currency.convert(amount);
//	}
	
	public static void main(String[] args) {
		Currency eur = new Currency(EUR_RATE);
		
		System.out.println(eur.convert(1.99));

	}

}
