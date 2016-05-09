package model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Communication class. See the protocol's documentation for more details.</br>
 * Contains the Account subclass.
 * @version R3 sprint 3 - 08/05/2016
 * @author Kappa-V
 * @changes
 * 		R3 sprint 2 -> R3 sprint 3:</br>
 * 			-Added a "name" attribute to the Account subclass</br>
 * 			-Added a copy method. It was needed in CustomerChoiceGUI.</br>
 * 			-Added an empty constructor to Account subclass</br>
 */
public class GetAccountsServerResponse extends ServerResponse {
	private List<Account> accounts;
	
	public GetAccountsServerResponse(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	// Constructor and adder for easier server-side construction
	public GetAccountsServerResponse() {
		this.accounts = new ArrayList<>();
	}
	
	public void addAccount(String account_id, String account_num, String name) {
		accounts.add(new Account(account_id, account_num, name));
	}

	// Inner class
	public static class Account {
		private String account_id;
		private String account_num;
		private String name;
		
		public Account() {
			// Do nothing
		}
		
		public Account(String account_id, String account_num, String name) {
			super();
			this.account_id = account_id;
			this.account_num = account_num;
		}
		
		public String getAccount_id() {
			return account_id;
		}
		public void setAccount_id(String account_id) {
			this.account_id = account_id;
		}
		public String getAccount_num() {
			return account_num;
		}
		public void setAccount_num(String account_num) {
			this.account_num = account_num;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * Copies another Account. Needed in CustomerChoiceGUI.
		 * @param accountToCopy
		 */
		public void copy(Account accountToCopy) {
			setName(accountToCopy.getName());
			setAccount_num(accountToCopy.getAccount_num());
			setAccount_id(accountToCopy.getAccount_id());
		}
	}
}