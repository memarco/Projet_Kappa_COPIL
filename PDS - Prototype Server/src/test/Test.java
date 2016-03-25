package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import util.ServerProperties;

/**
 * Test main.</br>
 * Fakes a client trying to connect to the server.
 * @version R2 sprint 1 - 17/03/2016
 * @author Kappa-V
 */
public class Test {
	public static void main(String[] args) {
		try {
			ServerProperties.init();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Properties props = ServerProperties.getInstance();
		try {
			Socket S = new Socket("localhost", Integer.parseInt(props.getProperty("SERVER_PORT")));

			PrintWriter out = new PrintWriter(S.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(S.getInputStream()));

			out.println("CONSULT {\"account_id\": 1}");
			System.out.println(in.readLine());
			out.println("CONSULT {\"account_id\": 2}");
			System.out.println(in.readLine());
			//out.println("BYE");
			
			in.close();
			out.close();
			S.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
