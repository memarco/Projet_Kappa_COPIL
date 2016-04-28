package gui;

public class Accounts {
/**
 * This class contains all the informations about account
 * account number and the balance
 **/
	private int account_id;
	private int value;

	public Accounts(int account_id, int value) {

		this.account_id = account_id;
		this.value = value;
	}

	public Accounts() {
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getvalue() {
		return value;
	}

	public void setvalue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Accounts [account_id=" + account_id + ", value=" + value + "]";
	}

	public Accounts(int account_id) {
		super();
		this.account_id = account_id;
	}

}
