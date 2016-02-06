package reponse;

public class ResponseDeleteAndInsert {
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseDeleteAndInsert(String status) {
		super();
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseDelete [status=" + status + "]";
	}
	

}
