package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * In this implementation of the protocol, each client is not associated with a connection for the full duration
 * of its session. Instead, every time a transaction is required, a new connection is fetched from the ConnectionPool 
 * instance initialized by the server on launch.</br>
 * </br>
 * Since it is noticeably faster to acquire and release a connection from the pool than it is to open and close it, 
 * this process should be fast enough. 
 */
public class ProtocolHandler {
	private Socket client;
	private ConnectionPool connectionPool;
	
	public ProtocolHandler (Socket client, ConnectionPool connectionPool) {
		this.client = client;
		this.connectionPool = connectionPool;
	}
	
	/** Blocks the current thread. Must be used in a new thread. */
	public void start() {
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			out.println("ok");
			
			@SuppressWarnings("unused") // Is actually used.
			String clientMessage;
			while ((!client.isClosed()) && ((clientMessage = in.readLine()).equals("hey"))) {
				Connection databaseConnection = connectionPool.acquire();
				String response = getStuffFromDatabase(databaseConnection, "EMPLOYEES");
				connectionPool.release(databaseConnection);
				
				out.println(response);
			}
			
			//Client said bye, or the server was stopped
			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Correctly de-serializes a table from the database into a Java String
	 * without memory leaks 
	 */
	private String getStuffFromDatabase(Connection databaseConnection, String tableName) {
		try {
			// Getting the info from the database
			Statement selectQuery = databaseConnection.createStatement();
			ResultSet results = selectQuery.executeQuery("SELECT * FROM " + tableName);
			
			// Reading the metadata to know how to call things - note : no need to close the metadata
			ResultSetMetaData metadata = results.getMetaData();
			String[] columnNames = new String[metadata.getColumnCount()];
			for(int i = 0 ; i < columnNames.length ; i++) {
				columnNames[i] = metadata.getColumnName(i+1);
			}
			
			String res = "";
			while(results.next()) {
				String entry = "[";
				
				for(int i = 0 ; i < columnNames.length ; i++) {
					entry += columnNames[i] + ":" + results.getString(i+1) + ' ';
				}
				res += entry.substring(0, entry.length()-1) + "]\n";
			}
			
			results.close();
			selectQuery.close();
			
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}

/************EXAMPLE***************/
//Client requests connection
//S - ok
//C - hey
//S - blablabla
//C - hey
//S - blablabla2
//C - bye
//Client closes socket
/**********************************/