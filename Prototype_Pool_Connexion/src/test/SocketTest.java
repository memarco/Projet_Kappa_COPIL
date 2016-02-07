package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/** 
 * Sample test code for the Socket and ServerSocket classes in Java.</br>
 * What's important to remember : </br>
 * -After using ServerSocket.accept(), start a new thread</br>
 * -in.readLine() blocks the current thread you until you receive a new command</br>
 * -use out.println() rather than out.print(), else you have to know how to terminate a line (is it EOF ? \n\r ? etc...)</br>
 * -<b>ALWAYS</b> close all sockets, inputs and outputs after you are done.
 */
public class SocketTest {
	private static final int port = (int)(Math.random() * 10000) + 5000;
	
	public static void main(String[] args) {
		//Server thread (so they can be executed simultaneously)
		new Thread(new Runnable() {
			public void run() {
				try {
					final ServerSocket server = new ServerSocket(port);
					
					//Prepared cleanup - just in case the program is CTRL+C'd.
					Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
						public void run() {
							try {
								server.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}));
					
					// Main loop - this is where the server accepts new connections and handles them.
					do {
						//Blocks the current thread until a client tries to connect.
						final Socket S = server.accept();
						
						// The new client is handled in a new thread so that the server can continue accepting connections.
						new Thread(new Runnable() {
							public void run() {
								try {
									//Initialization
									PrintWriter out = new PrintWriter(S.getOutputStream(), true);
									BufferedReader in = new BufferedReader(new InputStreamReader(S.getInputStream()));

									//Protocol - usually much more complex than that.
									System.out.println("Client said " + in.readLine());
									out.println("ok !");
									
									// Cleanup
									out.close();
									in.close();
									S.close();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									
								}
							}
						}).start();
					} while(false); //Stop after 1 connection for test purposes. Usually, you'd have another condition here.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		//Quick wait - this makes sure the server thread and the ServerSocket both have time to be initialized
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//Do nothing
		}
		
		// Client thread (so they can be executed simultaneously)
		new Thread(new Runnable() {
			public void run() {
				try {
					//Connection attempt
					Socket S = new Socket("localhost", port);
					PrintWriter out = new PrintWriter(S.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(S.getInputStream()));
					
					//Protocol - usually much more complex than that
					out.println("Hello !");
					System.out.println("Server responded " + in.readLine());
					
					//Cleanup
					out.close();
					in.close();
					S.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
