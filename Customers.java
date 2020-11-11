package banking_application;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
public class Customers {	
	private String username;
	private String password;
	private String name;
	private int numOfAccounts;
	private int mostRecentAccountNum = 0;
	private Map<Integer, BankAccount> accounts;
	Customers(String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.accounts = new HashMap<Integer, BankAccount>();
		this.numOfAccounts = 0;	
	}
	public String getUsername() {
		return this.username;
		}
	public String getPassword() {
		return this.password;
	}
	public String getName() {
		return this.name;
	}
	public int getMostRecentAccountNum() {
		return mostRecentAccountNum;
	}
	public BigDecimal getBalance(int accountNum) {
		BankAccount ba = accounts.get(accountNum);
		return ba.getBalance();
	}
	public BankAccount getBankAccount(int accountNum) {
		return accounts.get(accountNum);
	}
	public SortedSet<BankAccount> getAccountInfo() {
		SortedSet<BankAccount> accountInfo = new TreeSet<BankAccount>();
		for(Map.Entry<Integer, BankAccount> ba: accounts.entrySet()) {
			BankAccount temp = (BankAccount)ba.getValue();
			accountInfo.add(temp);
		}
		return accountInfo;
	}
	public int numOfAccounts() {
		return this.numOfAccounts;
	}
	void addAccount(BigDecimal balance, int accountNum) {
		mostRecentAccountNum = accountNum;
		balance = balance.setScale(2, RoundingMode.FLOOR);
		accounts.put(accountNum, new BankAccount(balance, accountNum));
		numOfAccounts++;
	}
	public BigDecimal updateAccountBalance(int accountNum, BigDecimal updateamount) throws SQLException {
		BankAccount ba = accounts.get(accountNum);
		if ((((ba.getBalance()).add(updateamount)).compareTo(new BigDecimal(0))) == -1) 
			return new BigDecimal(-1);
		ba.updateAccountBalance(updateamount);
		return ba.getBalance();	
	}	
	public String toString(){
		String cust = "Name: "+ name + " Username: " + username + " Password: " +  password + " \n";
		for (BankAccount ba: getAccountInfo())
		{
			cust += (ba + "\n");
		}
		return cust;
	}
}

