package Controler;

/**
 * This class is used for receiving the balance returned by the server 
 * when it comes to an Update or Research in the database
 * 
 * @version 2015-2016
 * @author Kappa
 *
 */
public class ResponseUpdateAndResearch {
	/**
	 * It's the amount of the customer account
	 * 
	 */
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
