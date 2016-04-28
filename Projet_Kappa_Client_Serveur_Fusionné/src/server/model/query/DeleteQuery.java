package server.model.query;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R1 sprint 4 - 06/02/2016
 * @author Kappa-V
 */
public class DeleteQuery {
	private long account_id;
	
	public DeleteQuery(long account_id) {
		this.account_id = account_id;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}
}
