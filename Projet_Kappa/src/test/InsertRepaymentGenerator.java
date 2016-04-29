package test;

/**
 * Generates SQL code to artificially populate the Repayment table with inexact, but believable info based on a loan's info.
 */
public class InsertRepaymentGenerator {
	public static void main(String[] args) {
		// Parameters:
		String LoanName = "Simu achat maison 2 - paiement ts les 4 mois";
		float capital = 28000;
		boolean steady = true; // False -> degressive
		float repaymentConstant = 267.79f;
		int year = 2016;
		int day = 28;
		int month = 8;
		int remainingRepayments = 132;
		int repaymentFrequency = 12;
		float rate = 5.5f;
		float insurance = 1.5f;
		
		
		//Treatment :
		for(int i = 0 ; i < remainingRepayments ; i++) {
			float interestThisMonth = 0.0f;
			float capitalThisMonth = 0.0f;

			interestThisMonth = capital*rate/100/12;
			if(steady) {
				capitalThisMonth = repaymentConstant - interestThisMonth - insurance;
			} else { //degressive
				capitalThisMonth = repaymentConstant;
			}
			capital = capital - capitalThisMonth;
			
			String date = "'" + day + "-" + month + "-" + year + "'"; 
			month = month + 12/repaymentFrequency;
			if(month > 12) {
				month = month - 12;
				year = year + 1;
			}
			
			System.out.println("INSERT INTO REPAYMENTS VALUES (REPAYMENTS_SEQ.NEXTVAL, (SELECT LOAN_ID FROM LOANS WHERE NAME='" + LoanName + "'), " + date 
					+ ", " + capitalThisMonth + ", " + interestThisMonth + ", " + insurance + ");");
		}
	}
}
