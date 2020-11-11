package banking_application;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;


public class BankingApplication {
	private Scanner input = new Scanner(System.in);
	private Menu menu = new Menu(input);
	private Customers current;
	private Storage storage = new Storage();
	private boolean firstTime = true;
	private BankhelperFunctions bhf;
	Map<Integer, BankTransactions> mainprog = new HashMap<Integer, BankTransactions>();
	public void runApp() throws SQLException {
		boolean mainProgram = true;
		boolean run = true;
		while(run) {
			int choice = startUp();
			firstTime = false;
			while(choice == -1) {
				choice = startUp();
			}
			if (choice == -2) break;
			mainProgram = true;
			while (mainProgram) {
				choice = menu.showMainMenu();
				if (choice < 7) {
					runBankFunctions(choice);
					continue;
				}
				mainProgram = false;			
			}
			run = menu.showloginorExit();
		}
		System.out.println("Goodbye and thank you for choosing Mindy's Bank!");
		input.close();
		}		
	public int startUp() throws SQLException {
		boolean login = true;
		int choice = menu.showStartUp();
		if (choice == -1) return -2;
		if (choice == 2) {		
			boolean success = newUser(); 
			if (!success) return -1;
			else login = menu.showloginorExit();
		}
		if (!login) return -2;
		current = login();
		if (current == null) {
			return -1;
		}
		return 1;
	}
	
	public boolean newUser() throws SQLException {
		newUserFunctions newUserData = new newUserFunctions(storage, current, input);
		System.out.println("Thank you for choosing Minydy's Bank! To get started,"
            	+ " enter your information and open up a bank account");
		String name = newUserData.setNewUserInfo("name");
		if (name.equals("-1")) return false;
		String username = newUserData.setNewUserInfo("username");
		if (username.equals("-1")) return false;
		String password = newUserData.setNewUserInfo("password");
		if (password.equals("-1")) return false;
		String balancetemp = newUserData.setNewUserInfo("balance");
		if (balancetemp.equals("-1")) return false;
		BigDecimal balance = new BigDecimal(balancetemp);
		Customers newCust = newUserData.addCust(name, username, password, balance);
		storage.insert(name, username, password);
		
		System.out.println("Thanks for joining our fake bank! Your new account number is #"
				         + newCust.getMostRecentAccountNum());
		return true;
		}
	public Customers login() throws SQLException {
		String password = null;
		Boolean match = false;
		Customers currentcust = null;
		while (!match) {
			System.out.println("Please enter username");
			String username = input.nextLine();
			while(!storage.contains(username)) {
				System.out.println("Invalid username. Please enter your username or press -1 to go back");
				username = input.nextLine();
				if (username.equals("-1")) return null;
			}
			currentcust = storage.retrieve(username);
			System.out.println("Welcome " + currentcust.getName() + "! Please enter your password");
			password = input.nextLine();
			while(!currentcust.getPassword().equals(password)) {
				System.out.println("Incorrect password for username " + currentcust.getUsername() + "."
									+ "Please try again or enter -1 to go back or 2 to enter "
									+ "different username");
				password = input.nextLine();
				if (password.equals("-1")) return null;
				if (password.equals("2")) break;					
			}
			if (password.equals("2")) continue;
			match = true;
		}
		return currentcust;
	}
	public void runBankFunctions(int choice) throws SQLException {
		Boolean run = true;
		bhf = new BankhelperFunctions(storage, current, input);
		if (choice < 5) {
			intializeTransactions();
			BankTransactions currentFunc = mainprog.get(choice);
			while(run) {
				run = currentFunc.run();
			}
			return;
		}
		if (choice == 5) {
			menu.viewBalance();
			return;
		}
		if (choice == 6) {
			menu.showCustinfo();
			return;
		}		

	}
	public void intializeTransactions() {
		mainprog.put(1, new Deposit(bhf));
		mainprog.put(2, new Withdraw(bhf));
		mainprog.put(3, new Transfer(bhf));
		mainprog.put(4, new OpenAccount(bhf));
	}
	private class Menu{
		Scanner input;
		Menu(Scanner input) {
			this.input = input;
		}
		public int showStartUp() {
			boolean valid = false;
			System.out.println("Welcome to Mindy's Banking Application");
			System.out.println("If already a member, please enter 1 to sign into account.");
			System.out.println("If new to our Bank, please enter 2 to make an account.");
			if (!firstTime) System.out.println("If done, enter -1 to exit.");
			String line = input.nextLine();
			while(!valid) {
				switch (line) {
				case "1":
					valid = true;
					break;
				case "2":
					valid = true;
					break;
				case "-1":
					if (!firstTime) return Integer.parseInt(line); 
					else line = null;
					break;
				default:
					if (!firstTime) System.out.println("Invalid entry. Please enter 1, 2, or -1 to exit.");
					else System.out.println("Invalid entry. Please enter 1 or 2");
					line = input.nextLine();
					break;
				}
			}
			return Integer.parseInt(line); 
		}
		public int showMainMenu() {
			System.out.println("Please enter the number of the option you would like.");
			System.out.println("1. Make a deposit");
			System.out.println("2. Withdraw");
			System.out.println("3. Transfer Between Accounts");
			System.out.println("4. Open New Account");
			System.out.println("5. View Account Balance"); 
			System.out.println("6. View user info");
			System.out.println("7. Logout");
			String line = input.nextLine();
			while(!line.equals("1") && !line.equals("2") && !line.equals("3") && !line.equals("4") 
				    && !line.equals("5") && !line.equals("6") && !line.equals("7")) {
				System.out.println("Invalid entry. Please enter the number of one of the above options");
				line = input.nextLine();
			}
			int choice = Integer.parseInt(line);
			return choice;	
		}
		public boolean showloginorExit() {
			System.out.println("Press 1 to login or 2 to exit");
			String line = input.nextLine();
			while (line.equals("1") && line.equals("2")) {
				System.out.print("Please press *1* to login or *2* to exit");
				 line = input.nextLine();
			}
			return (Integer.parseInt(line) == 1);
		}
		public void showCustinfo() {
			System.out.println(current);
			System.out.println("Enter -1 to go back");
			String c = input.nextLine();
			while (!c.equals("-1")) {
				System.out.println("Please enter -1 to go back");
				c = input.nextLine();
			}
    	}
    	public void viewBalance() {
    		for (BankAccount ba: current.getAccountInfo())
    		{
    			System.out.println(ba);
    		}
    		System.out.println("Enter -1 to go back");
			String c = input.nextLine();
			while (!c.equals("-1")) {
				System.out.println("Please enter -1 to go back");
				c = input.nextLine();
			}
    	}
	 }
   
	public static void main(String[] args) throws SQLException {
		BankingApplication app = new BankingApplication();
		app.runApp();
    }
}


