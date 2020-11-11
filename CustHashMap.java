package banking_application;
import java.util.Collection;
import java.util.HashMap;

public class CustHashMap{
	HashMap<String, Customers> CustHashMap;
	
	CustHashMap(){
		CustHashMap = new HashMap<String, Customers>();
	}
	public Customers get(String username) {
			return CustHashMap.get(username);
	}
	public void put(String username, Customers current) {
		CustHashMap.put(username,  current);
	}
	public void remove(String username) {
		CustHashMap.remove(username);
	}
	public int size() {
	  	return CustHashMap.size();
	}
	public Boolean containsKey(String username) {
		return CustHashMap.containsKey(username);
	}
	public Collection<Customers> AllCust() {
	    	return CustHashMap.values();
	}
}
