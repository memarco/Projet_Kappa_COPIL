package server.model.response;

import util.JsonImpl;

public class NewCustomerServerResponse extends ServerResponse {
	public enum Status {
		OK,
		KO
	}
	
	private Status status;
	
	public NewCustomerServerResponse(Status status) {
		this.status = status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "OK " + JsonImpl.toJson(this);
	}
}
