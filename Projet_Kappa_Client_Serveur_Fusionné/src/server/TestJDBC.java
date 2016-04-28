package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBC {

	public static void main(String[] args) throws SQLException {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "PDS", "PDS");

		Statement state = con.createStatement();

		String SQLquery = "INSERT INTO CUSTOMERS( customer_id, first_name, last_name, age, sex, activity, adress) "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
		//
		PreparedStatement preparedstatement = con.prepareStatement(SQLquery);
		
		ResultSet rs = state.executeQuery("select max(customer_id) as customer_id from customers");
		int max=-1 ;
		while(rs.next()){
			 max =rs.getInt("customer_id")+1;	
		}
		
		//
		preparedstatement.setInt(1, max);
		preparedstatement.setString(2, "boubacar ndiaye");
		preparedstatement.setString(3, "boubaar ndiaye");
		preparedstatement.setInt(4, 48);
		preparedstatement.setString(5, "F");
		preparedstatement.setString(6, "fou");
		preparedstatement.setString(7, "foufou");

		preparedstatement.executeUpdate();
	
		state.close();
		con.close();

	}
}
