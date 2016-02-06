package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.model.query.ConsultQuery;
import server.model.query.DeleteQuery;
import server.model.query.NewCustomerQuery;
import server.model.query.WithdrawalQuery;
import server.model.response.ErrorServerResponse;
import server.model.response.ServerResponse;
import util.JsonImpl;

/**
 * Handles the protocol server side, from the moment the Socket is received to the moment it is closed.
 */
public class ProtocolHandler {
	private Socket client;
	
	public ProtocolHandler (Socket client) {
		this.client = client;
	}
	
	/** Blocks the current thread. Must be used in a new thread. */
	public void start() {
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String clientMessage;
			ServerResponse serverResponse;
			do {
				clientMessage = "";
				do {
					clientMessage += in.readLine(); // Reading the client's query
				} while (in.ready()); // This loop makes it possible to receive 2+ lines long messages (useful in the case of pretty printed JSON)
				
				serverResponse = handleMessage(clientMessage);
				if(serverResponse != null) {
					out.println(serverResponse.toString());
				}
			} while (serverResponse != null);
			
			//Client said bye, or the server was stopped
			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Analyses the message, and dispatches its handling to the correct private method.
	 * @param message : the message received from the socket, as is, without any prior treatment
	 * @return : null if the client said "BYE", in which case the protocol handler must be terminated. Else, the response will be returned.
	 */
	private static ServerResponse handleMessage(String message) {
		if(message.equals("BYE"))
			return null;
		
		try {
			int prefixEnd = message.indexOf(' ');
			
			if(prefixEnd == -1)
				return new ErrorServerResponse("Invalid prefix");
			
			String prefix = message.substring(0, message.indexOf(' '));
			String content = message.substring(prefixEnd + 1);
			
			switch(prefix) {
			case "CONSULT":
				ConsultQuery consultQuery = JsonImpl.fromJson(content, ConsultQuery.class);
				return MessageHandler.handleConsultQuery(consultQuery);
			case "NEWCUSTOMER":
				NewCustomerQuery newCustomerQuery = JsonImpl.fromJson(content, NewCustomerQuery.class);
				return MessageHandler.handleNewCustomerQuery(newCustomerQuery);
			case "WITHDRAWAL":
				WithdrawalQuery withdrawalQuery = JsonImpl.fromJson(content, WithdrawalQuery.class);
				return MessageHandler.handleWithdrawalQuery(withdrawalQuery);
			case "DELETE":
				DeleteQuery deleteQuery = JsonImpl.fromJson(content, DeleteQuery.class);
				return MessageHandler.handleDeleteQuery(deleteQuery);
			default:
				return new ErrorServerResponse("Unknown prefix");
			}
		} catch(Exception e) {
			return new ErrorServerResponse("Unknown format error");
		}
	}
}