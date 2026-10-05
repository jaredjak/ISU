package carParts;

public class TestCar {

	public static void main(String[] args) {
		GasTank gasTank = new GasTank(13);
		
		GasTank.setLevel(gasTank, 13);
		gasTank.setLevel(13);

	}

}
