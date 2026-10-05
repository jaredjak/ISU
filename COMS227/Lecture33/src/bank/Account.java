package bank;

public class Account {
	private int id;
	private double balance;
	
	public Account(int id, double openingBalance) {
		this.id = id;
		balance = openingBalance;
	}
	
	public void deposit(double amount) {
		this.balance += amount;
	}
	
	public void withdraw(double amount) {
		this.balance = Math.max(0, this.balance - amount);
	}

	public double getBalance() {
		return balance;
	}

	// Zap because useless and can cause potential issues
//	public void setBalance(double balance) {
//		this.balance = balance;
//	}
}

class SavingAccount extends Account {
	private double minBalance = 100;
	
	public SavingAccount(int id, double openingBalance, double minBalance) {
		// super always comes first
		super(id, openingBalance);
		this.minBalance = minBalance;
	}
	
	@Override
	public void withdraw(double amount) {
		// WRONG
//		balance = Math.max(minBalance, balance - amount);
		// RIGHT BUT UGLY
//		setBalance(Math.max(minBalance, getBalance() - amount));
		// RIGHT BUT NOT UGLY
		if (getBalance() - amount >= minBalance) {
			super.withdraw(amount);
		}
	}
}

class CheckingAccount extends Account {
	private double maxWithdraw;
	
	public CheckingAccount(int id, double openingBalance, double maxWithdraw) {
		// super always comes first
		super(id, openingBalance);
		this.maxWithdraw = maxWithdraw;
	}
	
	@Override
	public void withdraw(double amount) {
		if (amount <= maxWithdraw) {
			// UGLY
//			setBalance(Math.max(0, getBalance() - amount));
			// LESS UGLY
			super.withdraw(amount);
		}
	}
}
