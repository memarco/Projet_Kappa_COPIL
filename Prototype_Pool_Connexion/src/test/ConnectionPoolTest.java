package test;

import java.sql.Connection;

import server.ConnectionPool;

/**
 * This is a test main() for the ConnectionPool class. </br>
 * It initializes an object of that class, and launches 100 
 * threads which will all attempt to acquire a database connection, 
 * wait a random lapse of time inferior to 2 seconds, and release it. </br>
 * </br>
 * The expected result : </br>
 * Only 15 (configurable in the properties file) threads should be allowed
 * to have a connection at once. The others should wait - without reaching
 * a situation of starvation. Eventually, all the threads will be finished.
 * This should take approximately ten seconds on average.</br>
 * </br>
 * Note : there are standard output prints in the ConnectionPool class
 * which are unused right now. De-comment them and recompile to have all
 * the info.
 */
public class ConnectionPoolTest {
	public static void main(String[] args) {
		final ConnectionPool poolCo = new ConnectionPool();
		
		for(int i = 0 ; i < 100 ; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep((int)(Math.random() * 2000));
					} catch (InterruptedException e) {/* Do nothing */}
					System.out.println("Acquiring connection...");
					Connection co = poolCo.acquire();
					System.out.println("Connection acquired. Waiting...");
					try {
						Thread.sleep((int)(Math.random() * 2000));
					} catch (InterruptedException e) {/* Do nothing */}
					System.out.println("Wait over. Releasing connection.");
					poolCo.release(co);
				}
			}).start();
		}
	}
}
