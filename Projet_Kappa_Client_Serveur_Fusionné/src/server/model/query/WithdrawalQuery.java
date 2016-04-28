package server.model.query;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R1 sprint 4 - 06/02/2016
 * @author Kappa-V
 */
public class WithdrawalQuery {
	private long account_id;
	private double value;
	
	public WithdrawalQuery(long account_id, double value) {
		super();
		this.account_id = account_id;
		this.value = value;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
