package server.functional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import server.model.response.ServerResponse;

/**
 * This class handles exactly one client. </br>
 * The life span of this thread is either the same as the Socket it was given in its constructor's life span,
 * or shorter, depending on whether or not an exit signal is received.
 * @version R2 sprint 1 - 18/03/2016
 * @author Kappa-V
 */
public class ProtocolHandler extends Thread {
	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(ProtocolHandler.class);
	
	/**
	 * The socket of this ProtocolHandler's client.
	 */
	private Socket client;
	
	/**
	 * This boolean is used to exit the run() method before the client decides to end the session. It is set to false by the exit() method.
	 */
	private boolean exit = false;
	
	/**
	 * Main constructor for this class.
	 * @param client : the client this ProtocolHandler will handle.
	 */
	public ProtocolHandler(Socket client) {
		this.client = client;
	}
	
	/**
	 * Is called in a new thread when you call the start() method from the Thread superclass. Don't actually call this, call start() instead.</br>
	 * Handles a session from start to finish.
	 */
	@Override
	public void run() {
		logger.trace("Entering ProtocolHandler.run");
		try {
			// initialization
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			// Main loop
			while (!client.isClosed() && !exit) {
				// Read query
				String clientMessage = "";
				do {
					clientMessage += in.readLine(); // Reading the client's query
				} while (in.ready()); // This loop makes it possible to receive 2+ lines long messages (useful in the case of pretty printed JSON)
				
				// Treat query
				ServerResponse serverResponse = MessageHandler.handleMessage(clientMessage);
				
				// Response
				if(!client.isClosed()) {
					if(serverResponse == null) { // handleMessage returns null if clientMessage.equals("BYE")
						client.close();
					} else {
						out.println(serverResponse.toString());
					}
				}
			}
		} catch (IOException e) {
			if(client.isClosed()) {
				logger.info("ProtocolHandler shut down.");
			} else {
				logger.error("ProtocolHandler : IOException caught", e);
				try {
					client.close();
				} catch (IOException e1) {
					logger.warn("Exception caught while attempting to close a socket", e1);
				}
			}
		}
		logger.info("Connection terminated");
		logger.trace("Exiting ProtocolHandler.run");
	}
	
	public void exit() {
		logger.trace("Entering ProtocolHandler.exit");
		exit = true;
		try {
			client.close();
		} catch (IOException e) {
			logger.warn("Exception caught while attempting to close a socket");
		}
		logger.trace("Exiting ProtocolHandler.exit");
	}
}
