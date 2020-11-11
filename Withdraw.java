package banking_application;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Withdraw implements BankTransactions {
	BankhelperFunctions bf;
	Withdraw(BankhelperFunctions bf) {
		this.bf = bf;
	}
	public Boolean run() throws SQLException {
		System.out.println("Please enter the number of the account you wish to"
				           + " withdraw from");
		int accountNum = bf.displayChooseAccount();
		System.out.println("Please enter the amount you wish to withdraw from"
		                   + " account #" + accountNum);
		BigDecimal withdraw = bf.getAmount();
		if (withdraw.equals(new BigDecimal(-1))) return false;
		withdraw = withdraw.subtract(new BigDecimal(2.00).multiply(withdraw));
		while(!bf.updateBalance(withdraw, accountNum)) {
			withdraw = bf.getAmount();
			if (withdraw.equals(new BigDecimal(-1))) return false;
			withdraw = withdraw.subtract(new BigDecimal(2.00).multiply(withdraw));
		}
		System.out.println("Money will not be dispensed shortly as this is not a real bank.");
		Boolean again = bf.runAgain();
		return again;
	}

}
