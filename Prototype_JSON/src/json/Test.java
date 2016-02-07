package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Commande;
import model.CourseImpossibleException;
import model.Livraison;
import model.Vehicule;

public class Test {
	private static final String defaultInputPath = "test/in.json";
	
	/**
	 * @param inputFilePath : the path to the file used as input
	 * @param prettyPrint : if true, the result will be pretty-printed
	 * @return : the parsed Java Object from that file
	 */
	public static Commande testFromJson(String inputFilePath) {
		try {
			// Getting the json string from the input file
			File inputFile = new File(inputFilePath);
			Scanner in = new Scanner(inputFile);
			
			String jsonQuery = "";
			while(in.hasNextLine())
				jsonQuery += in.nextLine();
			
			in.close();
			
			
			// Parsing the json string into a Java Object using gson
			Gson gson = new Gson();
			Commande commande = gson.fromJson(jsonQuery, Commande.class);
			
			return commande;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @param inputFilePath : the path to the file used as input
	 * @param prettyPrint : if true, the result will be pretty-printed
	 * @return : the json string of the response
	 */
	public static String testToJson(String inputFilePath, boolean prettyPrint) {
		// Generating the parameters for the Livraison constructor
		Vehicule moto = new Vehicule("moto", 50, 50, 25, 5.0, 3.2);
		Vehicule camionette = new Vehicule("camionette", 200, 100, 150, 400.0, 8.9);
		Vehicule camion33T = new Vehicule("camion 33 tonnes", 1500, 200, 400, 33000, 28.7);
		List<Vehicule> vehiculesDisponibles = new ArrayList<>();
		vehiculesDisponibles.add(moto);
		vehiculesDisponibles.add(camionette);
		vehiculesDisponibles.add(camion33T);
		
		Commande commande = testFromJson(inputFilePath);
		
		if(commande != null) {
			try {
				// Generating the Java Object response
				Livraison livraison = new Livraison(commande, vehiculesDisponibles);
				
				
				// Generating the json response using gson
				Gson gson = (prettyPrint)? new GsonBuilder().setPrettyPrinting().create() : new Gson();
				String response = gson.toJson(livraison);
				
				return response;
			} catch (CourseImpossibleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * @param args : You can give the path to the input file as a command line parameter.
	 */
	public static void main(String[] args) {
		String inputPath;
		if(args.length != 0) {
			inputPath = args[0];
		} else {
			inputPath = defaultInputPath;
		}
		
		String response = testToJson(inputPath, true);
		
		if(response != null) {
			try {
				// Writing the response in the output file
				File outputFile = new File("test/out.json");
				PrintWriter out = new PrintWriter(outputFile);
				out.write(response);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			System.out.println("Test terminé. Ouvrir le fichier test/out.json pour visualiser la réponse.");
		}
	}
}
