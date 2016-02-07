package test;

/**
 * This test initializes a server instance, then creates 100 clients which all connect and interact with it.
 */
public class GeneralTest {
	public static void main(String[] args) {
		new ServerTest().start();
		
		try {
			Thread.sleep(5000); //This gives enough time for the server to initialize its connection pool and serversocket.
		} catch(Exception e) {
			//Do nothing
		}
		
		for(int i = 0 ; i < 100 ; i++)
			new ClientTest().start("" + i);
	}
}
