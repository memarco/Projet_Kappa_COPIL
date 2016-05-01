package lendingscenario;

import java.sql.Date;
import java.util.Scanner;

public class GetInformation {

	public static Information Insert() {
		Scanner scanner1 = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);
		Scanner sc = new Scanner(System.in);
		System.out.println("Donnez le montant du pr�t: ");
		float amount_orrowed = scanner1.nextFloat();
		System.out.println("Donnez la dur�e du pr�t: ");
		int duration = sc.nextInt();
		System.out.println("Donnez la periodicit� du pr�t: ");
		String periodicity = scanner2.nextLine();
		System.out.println("Donnez le pourcentage de l'assurance: ");
		int value_insurance = sc.nextInt();
		System.out.println("Donnez la fr�quence de r�vision du taux pour la premi�re fois: ");
		int first_time = scanner1.nextInt();
		System.out.println("Donnez la fr�quence de r�vision du taux pour les fois suivantes: ");
		int all_other_times = scanner2.nextInt();
		
		Information information = new Information(amount_orrowed, duration, periodicity, value_insurance, first_time,
				all_other_times);
		
		System.out.println(information.toString());
		
		return information;
	
	}
}