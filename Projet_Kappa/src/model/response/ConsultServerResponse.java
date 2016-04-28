package model.response;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R3 sprint 1 - 13/04/2016
 * @author Kappa-V
 * @changes
 * 		R1 sprint 4 -> R3 sprint 1 : </br>
 * 			-removed the toString method. It is instead implemented by the superclass ServerResponse.
 */
public class ConsultServerResponse extends ServerResponse {
	private double balance;
	
	public ConsultServerResponse(double balance) {
		this.balance = balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public double getBalance() {
		return balance;
	}
}
