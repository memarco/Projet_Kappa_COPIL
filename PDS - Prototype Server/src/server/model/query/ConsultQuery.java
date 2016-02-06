package server.model.query;

public class ConsultQuery {
	private long account_id;
	
	public ConsultQuery(long account_id) {
		this.account_id = account_id;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}
}
