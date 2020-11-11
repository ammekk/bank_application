package banking_application;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BankhelperFunctions extends Functions {
	private Scanner input;	
	BankhelperFunctions(Storage storage, Customers current, Scanner input) {
		super(storage, current);
		this.input = input;
	}
	
	public int displayChooseAccount() throws  SQLException {
		Boolean valid = false;
		String regex = "^[0-9]*$";
		String line;
		Pattern pattern = Pattern.compile(regex);
		int accountNum = -1;
		for(BankAccount ba: current.getAccountInfo()) {
			System.out.println(ba);
		}
		while(!valid) {
			line = input.nextLine();
			if (line.equals("-1")) break;
			if (validate(line, pattern, null)== 0) {
				accountNum = Integer.parseInt(line);
				if (current.getBankAccount(accountNum)!= null) break;
			}
			System.out.println("Please enter account number you"
					           + "wish to use or -1 to go back");			
		}
		return accountNum;
	}
	public int openAccountOptions() {	
		System.out.println("Enter 1 to open new account with money from"
		+ " previously existing account or 2 deposit from outside funds");
		String line = input.nextLine();
		while (!line.equals("1") && !line.equals("2") && !line.equals("-1")) {
			System.out.println("Please enter *1* to transfer from other acount or "
				            	+ "*2* to deposit outside money. Enter *-1* to go back");					
			line = input.nextLine();
		}
		return Integer.parseInt(line);
	}
	public BigDecimal getAmount() throws SQLException {
		String regex = "^(\\d+)?([.]?\\d{0,2})?$";
		Pattern pattern = Pattern.compile(regex);
		String line = input.nextLine();
		while( (validate(line, pattern, null)) > 0) {
			System.out.println("Please enter a valid positive amount "
							+ " of form \"$267.92\" or *-1* to go back");
			line = input.nextLine();
			if (line.equals("-1")) return new BigDecimal(-1);
		}
		return new BigDecimal(line);
	}
	public Boolean updateBalance(BigDecimal amount, int accountNum) throws SQLException {
		BigDecimal balance = current.updateAccountBalance(accountNum, amount);
		if (balance.equals(new BigDecimal(-1))) {
			System.out.println("Sorry you do not have enough money in account #" + accountNum 
	             	+ " to go foward with transaction");
			 System.out.println(current.getBankAccount(accountNum));
			 System.out.println("Please enter a lesser amount.");
			return false;
		}
		System.out.println ("The new balance of account #" 
		                    + accountNum + " is $" + balance);	
		storage.updateBalance(accountNum, balance);
		return true;	
	}
	public Boolean canTransfer() {
		Boolean canTransfer = (current.numOfAccounts() > 1);
		if (!canTransfer) 			
			    System.out.println("Sorry, you only have one account and therefore "
				+ "cannot transfer between accounts. Select another option");
		return canTransfer;
	}
	public Boolean transfer(int accountNumW, int accountNumD, BigDecimal withdraw) throws SQLException {
		BigDecimal deposit = withdraw;
		if (withdraw.equals(new BigDecimal(-1))) return false;
		withdraw = withdraw.subtract(new BigDecimal(2.00).multiply(withdraw));
		while(!updateBalance(withdraw, accountNumW)) {
			withdraw = getAmount();
			deposit = withdraw;
			if (withdraw.equals(new BigDecimal(-1))) return false;
			withdraw = withdraw.subtract(new BigDecimal(2.00).multiply(withdraw));
		}
		if (accountNumD != 0) updateBalance(deposit, accountNumD);
		else {
			accountNumD = newAccountNum();
			addAccount(deposit, accountNumD);
		}
		return true;
	}
	public Boolean runAgain() {
		String line = input.nextLine();
		while (!line.equals("-1") && !line.equals("2")) {
			System.out.println("Please enter *-1* to go back or *2* to make another"
				            	+ " transaction of the same type");
			 line= input.nextLine();
		}
		if (line.equals("-1")) return false;
		return true;
	}
}
