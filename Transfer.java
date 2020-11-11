package banking_application;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Transfer implements BankTransactions {
	BankhelperFunctions bf;
	Transfer(BankhelperFunctions bf) {
		this.bf = bf;
	}
	public Boolean run() throws SQLException {
		int accountNumW = 0;
		int accountNumD = 0;
		BigDecimal withdraw;
		Boolean firsttime = true;
		if (!bf.canTransfer()) {
            return false;	
		}
		while(accountNumW == accountNumD) {
			if(!firsttime)
				System.out.println("Whats the point of transfering between the"
				            	+ "same account. Lets try again.");
			System.out.println("Please enter the number of the account you wish to"
			           + " transfer from");
			accountNumW = bf.displayChooseAccount();
			if (accountNumW == -1) return false;
			System.out.println("Please enter the number of the account you wish to"
			           + " withdraw from");
			accountNumD = bf.displayChooseAccount();
			if (accountNumW == -1) return false;
			firsttime = false;
		}
		System.out.println("Please enter the amount you wish to withdraw from account # " 
		                   + accountNumW + " to give to account #" + accountNumD);
		withdraw = bf.getAmount();
		if (!bf.transfer(accountNumW, accountNumD, withdraw)) return false;
		return bf.runAgain();
	}
}
