package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import util.ServerProperties;

/**
 * Simple database connection pool implementation. </br>
 * Locks the thread which called acquire() until a connection is available. </br>
 * </br>
 * Functionalities <b>not</b> <i>(yet ?)</i> implemented : </br>
 * -Creating new connections when the current pool is empty, up to a configurable cap</br>
 * -Disposing of unused connections after a time</br>
 * -Verifying if the connection is still valid by the time it is acquired (handles the case where the database is disconnected momentarily)
 */
public class ConnectionPool {
	private static ConnectionPool bean = getInstance();
	
	public static synchronized ConnectionPool getInstance() {
		if (bean != null)
			return bean;
		return new ConnectionPool();
	}
	
	private List<Connection> availableConnections;
	private List<Connection> unavailableConnections; //Necessary for cleanup
	private boolean isCleanedUp = false;
	
	/** Initializes the ConnectionPool instance. It can be used after creation, no need to call an init() method. */
	private ConnectionPool() {
		//Initialization
		availableConnections = new ArrayList<>();
		unavailableConnections = new ArrayList<>();
		

		//Loading properties
		Properties prop = ServerProperties.getInstance();
		
		String DB_DRIVER_NAME = prop.getProperty("DB_DRIVER_NAME");
		String DB_URL = prop.getProperty("DB_URL");
		String DB_CONNECTION_LOGIN = prop.getProperty("DB_CONNECTION_LOGIN");
		String DB_CONNECTION_PASSWORD = prop.getProperty("DB_CONNECTION_PASSWORD");
		int DB_CONNECTION_POOL_SIZE = Integer.parseInt(prop.getProperty("DB_CONNECTION_POOL_SIZE"));
		
		try {
			//Creating JDBC connections
			Class.forName(DB_DRIVER_NAME);
			
			for(int i = 0 ; i < DB_CONNECTION_POOL_SIZE ; i++) {
				Connection newCo = DriverManager.getConnection(DB_URL, DB_CONNECTION_LOGIN, DB_CONNECTION_PASSWORD);
				newCo.setAutoCommit(false); // TODO : check if this is wanted
				availableConnections.add(newCo);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/** This method MUST be called before exiting the program.</br>It can be called twice without raising an exception. */
	public synchronized void cleanup() {
		if(!isCleanedUp) {
			isCleanedUp = true;
			
			for(Connection C : availableConnections) {
				try {
					C.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			for (Connection C : unavailableConnections) {
				try {
					C.rollback();
					C.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			availableConnections = null;
			unavailableConnections = null;
		}
	}
	
	/** Blocks the current thread until a connection is available */
	public synchronized Connection acquire() {
		if(isCleanedUp)
			throw new IllegalStateException("Calling acquire() method from a cleaned up ConnectionPool instance.");
		
		while(availableConnections.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {/* Do nothing */}
		}
		Connection C = availableConnections.get(0);
		availableConnections.remove(0);
		unavailableConnections.add(C);

		//For debug purposes :
		//System.out.println("\t\tACQUIRE avail: " + availableConnections.size() + " ; unav: " + unavailableConnections.size());
		
		return C;
	}
	
	/** Releases a previously acquired connection. */
	public synchronized void release(Connection co) {
		if(isCleanedUp)
			throw new IllegalStateException("Calling release() method from a cleaned up ConnectionPool instance.");
		
		int id = unavailableConnections.indexOf(co);
		if (id != -1) {
			unavailableConnections.remove(id);
		}
		availableConnections.add(co);
		
		// This avoids some problems regarding deadlocks
		try {
			co.commit();
		} catch (SQLException e) {/* Do nothing */}
		
		this.notifyAll();

		//For debug purposes :
		//System.out.println("\t\tRELEASE avail: " + availableConnections.size() + " ; unav: " + unavailableConnections.size());
	}
}
