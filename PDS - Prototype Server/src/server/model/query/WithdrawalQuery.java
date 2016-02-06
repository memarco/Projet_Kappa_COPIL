package server.model.query;

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
