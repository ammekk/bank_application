package banking_application;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Storage {
	private static final String USERNAME = "emmakelminson";
	private static final String PASSWORD = "user";
	private static final String CONN = "jdbc:mysql://localhost/bankapp";
	public void insert(String name, String username, String password) throws SQLException {
		String queryCust = "INSERT INTO `customers` (name, username, password) VALUES (?, ?, ?)";
		try (Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
			PreparedStatement stmtCust = con.prepareStatement(queryCust)) {
			stmtCust.setString (1, name);
			stmtCust.setString (2, username);
			stmtCust.setString (3, password);	
			stmtCust.execute();	
		}
		catch (SQLException e) {
			System.err.print(e);
		}
	}
	public Customers retrieve(String username) throws SQLException {
		Customers current = null;
		String queryCust = "SELECT name, password FROM customers WHERE username = ?";
		String queryBA = "SELECT account_num, balance FROM bank_accounts WHERE username = ?"
				       + "ORDER BY account_num ASC";
		try (Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
			 PreparedStatement stmtCust = con.prepareStatement(queryCust);
			 PreparedStatement stmtBA = con.prepareStatement(queryBA)) {
			stmtCust.setString (1, username);
			stmtBA.setString (1, username);
			try	(ResultSet rsCust = stmtCust.executeQuery();
				 ResultSet rsBA = stmtBA.executeQuery()) {
				rsCust.next();
				String name = rsCust.getString("name");
				String password = rsCust.getString("password");
				current = new Customers(username, password, name);
				while(rsBA.next()) {
						int accountNum = rsBA.getInt("account_num");
						BigDecimal balance = new BigDecimal(rsBA.getString("balance"));
						current.addAccount(balance, accountNum);
				}
			}
			catch (SQLException e) {
				System.err.print(e);
			}
		}
		catch (SQLException e) {
			System.err.print(e);
		}
		return current;			
	}
	public Boolean contains(String username) throws SQLException {
		Boolean contains = false;
		String query = "SELECT `username` FROM `customers` WHERE username = ?";
		try (Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
			 PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString (1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				contains = rs.next();
			 }
			catch (SQLException e) {
				System.err.print(e);
			}
		}
		catch (SQLException e) {
			System.err.print(e);
		}
		return contains;			
	}
	public String getPassword(String username) throws SQLException {
		String query = "SELECT COUNT(*) FROM customers WHERE username = ?";
		String password = null;
		try (Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
			 PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString (1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) password = rs.getString("password");
			}		
		}
		catch (SQLException e) {
			System.err.print(e);
		}
		return password;
	}	
	public int totalAccounts() throws SQLException {
		String query = "SELECT COUNT(*) FROM bank_accounts";
		int count = 0;
		try (Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {
			 rs.next();
			 count = rs.getInt(1);
		}
		catch (SQLException e) {
			System.err.print(e);
		}
		return count;
	}
	public void addAccount(String username, int accountNum, BigDecimal balance) throws SQLException {
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate= new java.sql.Date(date.getTime());
		String query = "INSERT INTO bank_accounts " + "VALUES ( ?,?,?,? )";
		try (Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
			 PreparedStatement stmt = con.prepareStatement(query)) {
				stmt.setString (1, username);
				stmt.setInt (2, accountNum);
				stmt.setString (3, balance.toString());
				stmt.setDate(4, sqlDate);;	
				stmt.execute();				
			}
			catch (SQLException e) {
				System.err.print(e);
			}
	}
	public void updateBalance(int accountNum, BigDecimal balance) throws SQLException {
		String query = "UPDATE bank_accounts SET balance = ? WHERE account_num = ? ";
		try (Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
			 PreparedStatement stmt = con.prepareStatement(query)) {
			 stmt.setString(1, balance.toString());
			 stmt.setInt(2, accountNum);
			 stmt.execute();
		}
		catch (SQLException e) {
			System.err.print(e);
		}
	}
}
