package server.model.response;

import util.JsonImpl;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R1 sprint 4 - 06/02/2016
 * @author Kappa-V
 */
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
