package banking_application;

import java.util.HashMap;
import java.util.LinkedList;

public class NewUserCmds {
	private HashMap<String, LinkedList<String>> cmds = new HashMap<String, LinkedList<String>>();
	public String[] functions = new String[4];
	private LinkedList<String[]> holder = new LinkedList<String[]>();
	NewUserCmds() {
		initializeData();
		int i = 0;
		for (String[] data: holder) {
			cmds.put(functions[i], new LinkedList<String>());
			populateCmds(cmds.get(functions[i++]), data);
		}
	}
	private void initializeData() {
		String[] nameCmds = new String[3];
		String[] usernameCmds = new String[4];
		String[] passwordCmds = new String[3];
		String[] balanceCmds = new String[4];
		functions[0] = "name";
		functions[1] = "username";
		functions[2] = "password";
		functions[3] = "balance";
		nameCmds[2] = "Please enter valid first and last name seperated with a space"
  		             + " and only alphabetic characters or enter -1 to go back.";
		nameCmds[1] = "Please enter First and Last Name.";
		nameCmds[0] = "[a-zA-z]+[ ][a-zA-Z]+";
		usernameCmds[3] = "Username has already been chosen :'(. "
			            	+ "Please choose a different one.";
		usernameCmds[2]= "Invalid entry. Please stick to the above guidlines to "
				          + "select a username or press -1 to go back."
   				          + "Please choose a different one.";
		usernameCmds[1] = "Please create a username between 6-20 characters. Use only "
				           + "letters and numbers";
		usernameCmds[0] = "^[a-zA-Z0-9]{6,21}$";
		passwordCmds[2] = "Invalid entry. Please stick to above guidelines when selecting "
			            	+ "a password or enter -1 to go back.";
		passwordCmds[1] = "Please create a password between 5-12 characters. Use only "
		                + "letters, numbers, and special characters: '$', '_', '@'";
		passwordCmds[0] = "^[a-zA-Z0-9$@_]{5,12}$";
		balanceCmds[2] = "Invalid entry. Please enter a valid positive balance of form "
			         	+ "\\\"123.55\\\" or -1 to go back";
		balanceCmds[1] = "Please enter the amount of your initial deposit for your new "
				         + "bank account";
		balanceCmds[0] = "^(\\d+)?([.]?\\d{0,2})?$";	
		holder.add(nameCmds);
		holder.add(usernameCmds);
		holder.add(passwordCmds);
		holder.add(balanceCmds);		
	}
	private void populateCmds(LinkedList<String> temp, String[] data) {
		for (String command: data) {
			temp.add(command);
		}
	}
	public LinkedList<String> get(String function){
		return cmds.get(function);
	}
	public int size(){
		return cmds.size();
	}
}
