package test;

import server.Server;

/**
 * This test simply consists in calling the main() function from the Server class in a new thread.
 * This allows you to very simply create a fake server as well as a bunch of fake clients which will connect to it,
 * and test the functionality of both.
 */
public class ServerTest {
	public void start() {
		new Thread(new Runnable(){
			public void run() {
				Server.getInstance().start();
			}
		}).start();
	}
}
