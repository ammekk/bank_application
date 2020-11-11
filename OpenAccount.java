package banking_application;

import java.math.BigDecimal;
import java.sql.SQLException;

public class OpenAccount implements BankTransactions {
	BankhelperFunctions bf;
	OpenAccount(BankhelperFunctions bf) {
		this.bf = bf;
	}
	public Boolean run() throws SQLException {
		int accountNum = 0;
		BigDecimal deposit;
		int choice = bf.openAccountOptions();
		if (choice == -1) return false;
		if (choice == 1) {
			System.out.println("Please enter the account you wish to "
					+ "transfer from");
			accountNum = bf.displayChooseAccount();
			if (accountNum == -1) return false;		
		}
		System.out.println("Please enter the amount you wish to deposit "
				            + "into your new account");
		deposit = bf.getAmount();
		if (choice == 1) {
			if(!bf.transfer(accountNum, 0, deposit)) return false;
		}
		else { 
			accountNum = bf.newAccountNum();
			bf.addAccount(deposit, accountNum);
		}
		Boolean again = bf.runAgain();
		return again;
	}

}
