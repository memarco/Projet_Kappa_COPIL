package test;

import client.Client;

/**
 * Simply launches the main() from the Client class in a new thread to make tests easier.
 */
public class ClientTest {
	public void start(final String name) {
		new Thread(new Runnable() {
			public void run() {
				String[] args = new String[1];
				args[0] = name;
				Client.main(args);
			}
		}).start();
	}
}
