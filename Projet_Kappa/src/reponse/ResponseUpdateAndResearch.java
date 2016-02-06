package reponse;

public class ResponseUpdateAndResearch {
	public int balance;

	public ResponseUpdateAndResearch(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "ResponseUpdate [balance=" + balance + "]";
	}

}
