package model.response;


/**
 * This class is used for receiving the status returned by the server 
 * when it comes to an insertion or deletion in the database
 * 
 * @version 2015-2016
 * @author Kappa
 *
 */
public class ResponseDeleteAndInsert {
	
	/**
	 * Status: We use to interpret the status of the server returns.
	 * 
	 */

	private String status ;
	
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
