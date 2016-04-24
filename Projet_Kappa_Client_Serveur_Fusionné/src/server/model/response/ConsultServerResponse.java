package server.model.response;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R1 sprint 4 - 06/02/2016
 * @author Kappa-V
 */
import util.JsonImpl;

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

	@Override
	public String toString() {
		return "OK " + JsonImpl.toJson(this);
	}
}
