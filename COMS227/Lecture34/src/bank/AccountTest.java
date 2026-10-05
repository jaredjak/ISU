//package bank;
//
//public class AccountTest {
//	
//	public static void chargeFee(Account acc) {
//		acc.withdraw(100);
//	}
//
//	public static void main(String[] args) {
//		CheckingAccount myChecking = new CheckingAccount(10, 0, 10000);
//		SavingAccount mySaving = new SavingAccount(10, 0, 100);
//		
//		chargeFee(mySaving);
//		chargeFee(myChecking);
//		
//		
//		Account acc = new Account(10, 0);
//		
//		Account acc;
//		// This works because they are subclasses of Account (is a)
//		acc = myChecking;
//		acc = mySaving;
		
//		chargeFee(myChecking);
//		chargeFee(mySaving);
		
//		myChecking.deposit(1000);
//		mySaving.deposit(1000);
//		
//		if (myChecking.equals(mySaving)) {
//			System.out.println("Account balances are the same");
//		} else {
//			System.out.println("Accounts are different");
//		}
//		
//		if (myChecking.equals("Bob")) {
//			System.out.println("Hi Bob");
//		} else {
//			System.out.println("We miss Bob");
//		}
//	}
//
//}
