package model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R3 sprint 2 - 28/04/2016
 * @author Kappa-V
 */
public class GetAccountsServerResponse extends ServerResponse {
	private List<String> accounts;
	
	public GetAccountsServerResponse(List<String> accounts) {
		this.accounts = accounts;
	}

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
	
	// Constructor and adder for easier server-side construction
	public GetAccountsServerResponse() {
		this.accounts = new ArrayList<>();
	}
	
	public void addAccount(String account) {
		accounts.add(account);
	}
}