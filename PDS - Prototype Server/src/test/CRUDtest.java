package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import server.ConnectionPool;
import server.MessageHandler;
import server.model.query.ConsultQuery;
import server.model.query.DeleteQuery;
import server.model.query.NewCustomerQuery;
import server.model.query.WithdrawalQuery;

public class CRUDtest {
	/**
	 * Tests for the MessageHandler class
	 * @param args
	 */
	public static void main(String[] args) {
		// CONSULT
		System.out.println("CONSULT {\"account_id\":2}  ->  " + MessageHandler.handleConsultQuery(new ConsultQuery(2)));
		System.out.println("CONSULT {\"account_id\":3}  ->  " + MessageHandler.handleConsultQuery(new ConsultQuery(3)));
		
		// WITHDRAWAL
		System.out.println("WITHDRAWAL {\"account_id\":2, \"value\":1523.0}  ->  " + MessageHandler.handleWithdrawalQuery(new WithdrawalQuery(2, 1523)));
		System.out.println("WITHDRAWAL {\"account_id\":2, \"value\":-1523.0}  ->  " + MessageHandler.handleWithdrawalQuery(new WithdrawalQuery(2, -1523)));
		
		// NEWCUSTOMER
		System.out.println("NEWCUSTOMER  ->  " + MessageHandler.handleNewCustomerQuery(new NewCustomerQuery("Barack", "Obama", 52, NewCustomerQuery.Gender.M, "President of the United States of America", "The White House, Washington DC")));
		
		// DELETE
		System.out.println("DELETE {\"account_id\":2}  ->  " + MessageHandler.handleDeleteQuery(new DeleteQuery(2)));
		System.out.println("DELETE {\"account_id\":10000}  ->  " + MessageHandler.handleDeleteQuery(new DeleteQuery(10000)));
		
		
		
		
		
		
		// Cleanup
		ConnectionPool cp = ConnectionPool.getInstance();
		Connection co = cp.acquire();
		
		try {
			Statement st = co.createStatement();
			
			String SQLquery1 = "INSERT INTO ACCOUNTS VALUES (2, 1, 100101, '05/01/16', 13000)";
			String SQLquery2 = "DELETE FROM CUSTOMERS WHERE FIRST_NAME='Barack'";
			
			st.executeUpdate(SQLquery1);
			st.executeUpdate(SQLquery2);
			
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cp.release(co);
		
		cp.cleanup();
	}
}
