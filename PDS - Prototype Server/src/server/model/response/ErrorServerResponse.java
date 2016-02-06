package server.model.response;

import util.JsonImpl;

public class ErrorServerResponse extends ServerResponse {
	private String message;
	
	public ErrorServerResponse(String message) {
		this.message = message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ERR " + JsonImpl.toJson(this);
	}
}
