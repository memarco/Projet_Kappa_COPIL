package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.ServerProperties;

public class Server {
	private static final Server bean = getInstance();
	
	// This disallows other classes from creating an instance of Server, but also prepares the cleanup in advance.
	private Server(){
		// Preparing the cleanup in advance - note that this will not be called if the program is interrupted by an exception.
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				cleanup();
			}
		}));
	}
	
	/**
	 * @return the server bean which must be used by all.
	 */
	public static synchronized Server getInstance() {
		if(bean != null)
			return bean;
		return new Server();
	}
	
	
	
	
	private final List<Socket> clients = new ArrayList<>();
	private final ServerSocket serverSocket = getServerSocket();
	private boolean isCleanedUp = false;
	
	// This method is only here to handle the exceptions.
	private synchronized ServerSocket getServerSocket() {
		if (isCleanedUp)
			throw new IllegalStateException("Attempting to get serversocket instance after cleanup");
		
		if(serverSocket != null) {
			return serverSocket;
		}
		
		try {
			return new ServerSocket(Integer.parseInt(ServerProperties.getInstance().getProperty("SERVER_PORT")));
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// Closes all sockets, serversockets, and JDBC connections
	public synchronized void cleanup() {
		if(!isCleanedUp) {
			isCleanedUp = true;
			

			// Connection pool cleanup
			ConnectionPool.getInstance().cleanup();
			
			// ServerSocket closing
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("Error while attempting to close the serversocket...");
				e.printStackTrace();
			}
			
			// Client socket closing
			while(!Collections.synchronizedList(clients).isEmpty()) {
				Socket S = Collections.synchronizedList(clients).remove(0);
				
				if(!S.isClosed()) {
					try {
						S.close();
					} catch (Exception e) {
						System.out.println("Error while attempting to close a socket server side...");
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public synchronized void start() {
		try {
			// Main loop
			do {
				// This blocks the current thread until a client requests a connection.
				final Socket client = serverSocket.accept();
				
				// A new thread is created to handle the new client while still accepting new clients in the current thread.
				// That new thread's lifespan is equal to the client's session duration. 
				// The socket is added, then removed from a list to facilitate cleanup. 
				// This must be done in a thread safe fashion, so the Collections.synchronizedList wrapper is used. 
				new Thread(new Runnable(){
					public void run() {
						Collections.synchronizedList(clients).add(client);
						new ProtocolHandler(client).start();
						Collections.synchronizedList(clients).remove(client);
					}
				}).start();
			} while(true);
		} catch (Exception e) {
			e.printStackTrace();
			
			cleanup();
			System.out.println("The server was interrupted by an exception, but was cleaned up properly.");
		}
	}
	
	public static void main(String[] args) {
		getInstance().start();
	}
}
