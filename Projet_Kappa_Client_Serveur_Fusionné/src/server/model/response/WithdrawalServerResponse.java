package server.model.response;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R1 sprint 4 - 06/02/2016
 * @author Kappa-V
 */
public class WithdrawalServerResponse extends ConsultServerResponse {
	public WithdrawalServerResponse(double balance) {
		super(balance);
	}
}
