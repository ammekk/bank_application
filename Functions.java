package banking_application;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {
	protected Storage storage;
	protected Customers current;
	Functions(Storage storage, Customers current) {
		this.storage = storage;
		this.current = current;
	}
	public int validate(String userinfo, Pattern pattern, String error2) throws SQLException {
		Boolean contains = false;
		Matcher matcher = pattern.matcher(userinfo);
		if (!matcher.matches()) {
			return 1;
		}
		if (error2 != null) contains = storage.contains(userinfo);
		if (contains) return 2;
		return 0;		
	}
	public void addAccount(BigDecimal balance, int accountNum) throws SQLException {
		current.addAccount(balance, accountNum);
		storage.addAccount(current.getUsername(), accountNum, balance);
		System.out.println("Here is a visualization of your new bank account:");
		System.out.println(current.getBankAccount(accountNum));
	}
	public int newAccountNum() throws SQLException {
		return 1010101 + storage.totalAccounts();
	}
}
