package banking_application;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankAccount implements Comparable<BankAccount> {
	BigDecimal balance;
	int accountNum;
	
	BankAccount(BigDecimal balance, int accountNum) {
		this.balance = balance.setScale(2, RoundingMode.FLOOR);
		this.accountNum = accountNum;
	}		
	public BigDecimal getBalance() {
		return balance;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void updateAccountBalance(BigDecimal updateamount) {
		balance = balance.add(updateamount);
	}
	@Override
	public int compareTo(BankAccount other) {
		return this.accountNum - other.accountNum;
	}
	@Override
	public String toString(){
		return "Account #" + accountNum + "........." + balance;
	}
}
