package model.query;

import util.JsonImpl;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R3 sprint 1 - 18/04/2016
 * @author Kappa-V
 * @Changes
 * 		R1 sprint 4 -> R3 sprint 1 : </br>
 * 			-Moved from server.model.query to model.query
 * 			-Implemented toString
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
	
	@Override
	public String toString() {
		return "DELETE " + JsonImpl.toJson(this);
	}
}
