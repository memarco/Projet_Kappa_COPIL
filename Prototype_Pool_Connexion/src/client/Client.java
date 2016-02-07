package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import server.ServerProperties;

/**
 * Will connect to the server, and say hey 10 times (separated by a random lapse of time inferior to 5 seconds),
 * then it will say bye and close the socket on his end.
 */
public class Client {
	public static void main(String[] args) {
		String name = args[0];
		
		try {
			//Initialization
			Properties props = ServerProperties.getInstance();
			Socket S = new Socket("localhost", Integer.parseInt(props.getProperty("SERVER_PORT")));
			PrintWriter out = new PrintWriter(S.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(S.getInputStream()));
			
			// Waiting until the server is ready to talk
			if(in.readLine().equals("ok")) {
				System.out.println(name + " connected succesfully");
				
				//Protocol
				for(int i = 0 ; i < 10 ; i ++) {
					//Random wait time to simulate a person
					try {
						Thread.sleep((int)(Math.random() * 5000));
					} catch (InterruptedException e) {
						System.out.println("sleep interrupted");
					}
					
					//Client says hey
					out.println("hey");
					System.out.println(name + " said hey");
					
					//Getting a multi-line response
					String response = "";
					do {
						response += in.readLine() + '\n';
					} while(in.ready());
					System.out.print(name + " received :\n" + response);
				}
			}
			
			//Notifying the server that you're going to disconnect
			out.println("bye");
			System.out.println(name + " said bye");
			
			//Cleanup
			in.close();
			out.close();
			S.close();
			System.out.println(name + "closed its socket");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
