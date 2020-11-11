package banking_application;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class newUserFunctions  extends Functions{
	private Scanner input;
	private NewUserCmds cmds= new NewUserCmds();
	newUserFunctions(Storage storage, Customers current, Scanner input) {
		super(storage, current);
		this.input = input;
	}
	public String setNewUserInfo(String function) throws SQLException {
		List<String> getCmds = cmds.get(function);
		ListIterator<String> it = getCmds.listIterator();
		String regex = it.next();
		Pattern pattern = Pattern.compile(regex);
		System.out.println(it.next());
		String error1 = it.next();
		String error2 = null;
		Boolean checkUsername = (getCmds.size() == 4);
		String userinfo = input.nextLine();
		if (checkUsername) error2 = it.next();
		int check = 0;	
		while((check = validate(userinfo, pattern, error2)) > 0) {
			if (check == 2) {
				System.out.println(error2);
			}
			else  if (check == 1) {
				System.out.println(error1);
			}
			userinfo = input.nextLine();
			if (userinfo.equals("-1")) break;
		}
		return userinfo;
	}
	public Customers addCust(String name, String username, 
			                 String password, BigDecimal balance) throws SQLException {
		int accountNum = newAccountNum();
		current = new Customers(username, password, name);
		addAccount(balance, accountNum);
		return current;
		
		
	}
}