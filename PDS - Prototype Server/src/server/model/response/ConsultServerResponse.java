package server.model.response;

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
