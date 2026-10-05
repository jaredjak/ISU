package bank;

public class AccountTest {
	
	public static void chargeFee(Account acc) {
		acc.withdraw(100);
	}

	public static void main(String[] args) {
		CheckingAccount myChecking = new CheckingAccount(10, 0, 10000);
		SavingAccount mySaving = new SavingAccount(10, 0, 100);
		
		Account acc;
		
		// This works because they are subclasses of Account (is a)
		acc = myChecking;
		acc = mySaving;
		
		chargeFee(myChecking);
		chargeFee(mySaving);
	}

}
