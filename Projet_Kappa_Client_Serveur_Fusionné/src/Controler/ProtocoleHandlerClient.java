package Controler;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.query.ConsultQuery;
import model.query.DeleteQuery;
import model.query.NewCustomerQuery;
import model.query.NewCustomerQuery.Gender;
import model.query.WithdrawalQuery;
import model.response.ConsultServerResponse;
import model.response.ResponseDeleteAndInsert;

/**
 * This class manage the transmission and reception of data from the IHM to the server. 
 * 
 * @version 2015-2016
 * @author Kappa
 *
 */
public class ProtocoleHandlerClient {
/**
 * It's the socket for client/server communication.
 */
	Socket socket = getS();

	private Socket getS() {
		try {
			return new Socket("localhost", 8153);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * his method retrieve customer information, 
	 * transformed into GSON object and then send it to the server thanks to the socket .
	 * @param lastname
	 * @param firstname
	 * @param age
	 * @param sexe
	 * @param adresse
	 * @param activity
	 * @return
	 * @throws Exception 
	 */
	public String insert(String lastname, String firstname, int age, String sexe,
			String adresse, String activity) throws Exception {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		NewCustomerQuery cusJson = new NewCustomerQuery(lastname, firstname, age, sexe, adresse,
				activity);

		String req = gson.toJson(cusJson);

		req = "NEWCUSTOMER " + req;
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		out.println(req);
		System.out.println(req);
		

		// to read the response
		String response = "";
		try{
			
			do {
				response += in.readLine() + '\n';
				System.out.println(response);
			} while (in.ready());
			
				
		}
	
		catch(Exception e ){
		 System.out.println(response );
		}
		
		/**
		 *  Checks the response from the server
		 */
		System.out.println("reponse "+response);
		try {
			switch (getStatus(response)) {
			case "KO":
				JFrame frame=new JFrame("JOptionPane showMessageDialog");
				frame.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame, "Demande non aboutie, Veuillez réessayer");
				
				break;
				
			case "":
				System.out.println("aucune reponse du serveur");

			default:
				JFrame frame1=new JFrame("JOptionPane showMessageDialog");
				frame1.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame1, "Client ajouté");
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("aucune reponse : "+getStatus(response));
		} 

		// bye to the serveur
		out.print("BYE");

		in.close();
		out.close();
		socket.close();

		return response;
	}
	
/**
 * This method get an account number, create a Gson object,
 *  and send it to the server.
 * @param numCount
 * @return
 * @throws IOException
 */
public String Research(int numCount) throws IOException{
	
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		
		ConsultQuery ac1 = new ConsultQuery(numCount);
		
		Gson gson =  new GsonBuilder().setPrettyPrinting().create() ;
		
		gson = new Gson();
		
		
		String req =gson.toJson(ac1);
		
		req = "CONSULT "+req;
		
		out.println(req);
		
		
		/**
		 * Read the response of the server.
		 */
		String response = "";
		do {
			response += in.readLine() + '\n';
		} while (in.ready());
		
		System.out.println(response);

		try {
			System.out.println(getResponse(response));
			System.out.println(response);
			JFrame frame=new JFrame("JOptionPane showMessageDialog");
			frame.setSize(new Dimension(800,800));
			JOptionPane.showMessageDialog(frame, "Votre solde est de "+getResponse(response));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// say bye to the serveur
		
		out.print("BYE");


		
		in.close();
		out.close();
		socket.close();
		
		return response;
	}


	public String delete(int account_num) throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		DeleteQuery cusJson = new DeleteQuery(account_num);

		String req = gson.toJson(cusJson);

		req = "DELETE " + req;

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		out.println(req);

		// to read the response
		String response = "";
		do {
			response += in.readLine() + '\n';// TODO paramétré le temps
												// d'attente max
		} while (in.ready());
		
		System.out.println(response);
		try {
			System.out.println(getStatus(response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			switch (getStatus(response)) {
			case "KO":
				JFrame frame=new JFrame("JOptionPane showMessageDialog");
				frame.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame, "Compte n'a pas été supprimé");
				
				break;

			default:
				JFrame frame1=new JFrame("JOptionPane showMessageDialog");
				frame1.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame1, "Compte Supprimé");
				break;
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bye to the serveur
		out.print("BYE");

		in.close();
		out.close();
		socket.close();

		return response;
	}

	public String Update(int numCount, double amount) throws IOException {

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		String response;
		WithdrawalQuery ac1 = new WithdrawalQuery(numCount, amount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		gson = new Gson();

		String req = gson.toJson(ac1);

		req = "WITHDRAWAL " + req;
		System.out.println(req);

		out.println(req);

		// to read the response
		response = "";
		do {
			response += in.readLine() + '\n';// TODO paramétré le temps
												// d'attente max
		} while (in.ready());

		try {
		//	System.out.println(getResponse(response));
			System.out.println(response);
			JFrame frame=new JFrame("JOptionPane showMessageDialog");
			frame.setSize(new Dimension(800,800));
			JOptionPane.showMessageDialog(frame, "Votre Opération à bien été effectué\n"
					+ "votre solde est de "+getResponse(response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bye to the serveur
		out.print("BYE");

		in.close();
		out.close();
		socket.close();

		return response;
	}

	/**
	 * this method retrieve the response from the server.
	 * extract the label to know the nature of the response from the server.
	 * return the balance or an error message
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public double getResponse(String response) throws Exception {
		double balance =0 ;

		int prefixEnd = response.indexOf(' ');

		String prefix = response.substring(0, response.indexOf(' '));
		String content = response.substring(prefixEnd + 1);

		switch (prefix) {
		case "OK":
			ConsultServerResponse consultQuery;

			Gson gson = new Gson();
			
			consultQuery= gson.fromJson(content, ConsultServerResponse.class);
		
			balance = consultQuery.getBalance();
		//	System.out.println(consultQuery.toString());
			
			break;
		
		case "ERR":
			System.out.println("Veuillez vérifier le numéro de compte");
			
		break;
		default: System.out.println("Demande non aboutie");
		}

		return balance;
	}
	
	
	/**
	 * this method retrieve the response from the server.
	 * extract the label to know the nature of the response from the server.
	 * return the status.
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getStatus(String response) throws Exception {
	 String status = null ;

		int prefixEnd = response.indexOf(' ');

		String prefix = response.substring(0, response.indexOf(' '));
		String content = response.substring(prefixEnd + 1);

		switch (prefix) {
		case "OK":
			ResponseDeleteAndInsert consultQuery;

			Gson gson = new Gson();
			
			consultQuery= gson.fromJson(content, ResponseDeleteAndInsert.class);
		
			status = consultQuery.getStatus();
			System.out.println(consultQuery.toString());
			
			break;
		
		case "KO":
			System.out.println("Numéro de compte invalide");
			
		break;
		default: System.out.println("Demande non aboutie");
		}

		return status;
	}

}
