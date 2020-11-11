package banking_application;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Deposit implements BankTransactions {
	BankhelperFunctions bf;
	Deposit(BankhelperFunctions bf) {
		this.bf = bf;
	}
	public Boolean run() throws SQLException {
		System.out.println("Please enter the number of the account you "
			            	+ "wish to deposit to");
		int accountNum = bf.displayChooseAccount();
		System.out.println("Please enter the amount you wish to deposit"
		                   + " to account #" + accountNum);
		BigDecimal deposit = bf.getAmount();
		if (deposit.equals(new BigDecimal(-1))) return false;
		bf.updateBalance(deposit, accountNum);
		return bf.runAgain();
	}
}
