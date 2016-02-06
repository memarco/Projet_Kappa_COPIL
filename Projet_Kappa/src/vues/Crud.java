package vues;


	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.Properties;

	public class Crud{

		public void insert(String first_name, String last_name, int age,
				String sexe, String activity, String address)
				throws FileNotFoundException, IOException {

			Properties props = new Properties();
			props.load(new FileInputStream("connection.properties"));

			String theDburl = props.getProperty("dburl");
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");

			Statement myStmt = null;
			ResultSet myRs = null;
			int maxId = 0;

			try {
				Connection myConn = DriverManager.getConnection(theDburl, theUser,
						thePassword);
				myStmt = myConn.createStatement();
				myRs = myStmt
						.executeQuery("select max(CUSTOMER_ID) as CUSTOMER_ID from CUSTOMERS");

				while (myRs.next()) {
					maxId = Integer.parseInt(myRs.getString("CUSTOMER_ID")) + 1;
					
				}

				myRs = myStmt
						.executeQuery(" Insert into Customers(customer_id,first_name,last_name,age,sex,activity,adress)"
								+ "values "
								+ "("
								+ maxId
								+ ",'"
								+ first_name
								+ "','"
								+ last_name
								+ "','"
								+ age
								+ "','"
								+ sexe
								+ "','" + activity + "','" + address + "')");
				
				//myRs.setString(1,JT_first_name.getText());
				System.out.println(" Customer Inserted ");
			}

			catch (Exception e) {
				e.toString();
			}
		}

		public void display_Customers() throws FileNotFoundException, IOException {

			Properties props = new Properties();
			props.load(new FileInputStream("connection.properties"));

			String theDburl = props.getProperty("dburl");
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");

			Statement myStmt = null;
			ResultSet myRs = null;

			try {
				Connection myConn = DriverManager.getConnection(theDburl, theUser,
						thePassword);
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery("Select * from Customers");
				System.out.println("--------------------------------------");
				System.out.println("             LIST OF THE CUSTOMERS               ");
				System.out.println("--------------------------------------");

				while (myRs.next()) {
					System.out.println(myRs.getString("FIRST_NAME") + " "
							+ myRs.getString("LAST_NAME") + " "
							+ myRs.getString("ADRESS"));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void increaseBalance(int accountNumber, int balanceIncrease) throws FileNotFoundException, IOException {
				Properties props = new Properties();
				props.load(new FileInputStream("connection.properties"));

				String theDburl = props.getProperty("dburl");
				String theUser = props.getProperty("user");
				String thePassword = props.getProperty("password");

				Statement myStmt = null;
				ResultSet myRs = null;
				try {
					Connection myConn = DriverManager.getConnection(theDburl, theUser,
							thePassword);
					//myStmt = myConn.createStatement();
					PreparedStatement ps = myConn.prepareStatement("Update ACCOUNTS SET BALANCE =  (BALANCE + ?) WHERE ACCOUNT_NUM = ?");
					ps.setInt(1, balanceIncrease);
					ps.setInt(2, accountNumber);
					
					ps.executeUpdate();
					System.out.println(" Account updtated ");
				} catch (Exception e) {
					e.toString();
				}
			}
		

		public void decreaseBalance(int accountNumber, int balanceIncrease) throws FileNotFoundException, IOException {
			Properties props = new Properties();
			props.load(new FileInputStream("connection.properties"));

			String theDburl = props.getProperty("dburl");
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");

			Statement myStmt = null;
			ResultSet myRs = null;
			try {
				Connection myConn = DriverManager.getConnection(theDburl, theUser,
						thePassword);
				//myStmt = myConn.createStatement();
				PreparedStatement ps = myConn.prepareStatement("Update ACCOUNTS SET BALANCE =  (BALANCE - ?) WHERE ACCOUNT_NUM = ?");
				ps.setInt(1, balanceIncrease);
				ps.setInt(2, accountNumber);
				
				ps.executeUpdate();
				System.out.println(" Account updtated ");
			} catch (Exception e) {
				e.toString();
			}
		}

		public void delete(int accountNumber) throws FileNotFoundException,
				IOException {
			Properties props = new Properties();
			props.load(new FileInputStream("connection.properties"));

			String theDburl = props.getProperty("dburl");
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");

			Statement myStmt = null;
			ResultSet myRs = null;
			int maxId = 0;

			try {
				Connection myConn = DriverManager.getConnection(theDburl, theUser,
						thePassword);
				myStmt = myConn.createStatement();
				myRs = myStmt
						.executeQuery(" delete from Accounts where ACCOUNT_NUM = "
								+ accountNumber);

				System.out.println(" Account deleted ");
			} catch (Exception e) {
				e.toString();
			}
		}

	}


