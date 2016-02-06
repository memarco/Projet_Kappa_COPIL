package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.model.query.ConsultQuery;
import server.model.query.DeleteQuery;
import server.model.query.NewCustomerQuery;
import server.model.query.WithdrawalQuery;
import server.model.response.ConsultServerResponse;
import server.model.response.DeleteServerResponse;
import server.model.response.ErrorServerResponse;
import server.model.response.NewCustomerServerResponse;
import server.model.response.ServerResponse;
import server.model.response.WithdrawalServerResponse;

public abstract class MessageHandler {
	/**
	 * Asks the database to delete an account
	 * @param deleteQuery : the client's query
	 * @return : the server's response to the query. 
	 */
	public static ServerResponse handleDeleteQuery(DeleteQuery deleteQuery) {
		// Acquiring the JDBC connection from the pool
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection databaseConnection = connectionPool.acquire();
		
		try {
			// The SQL query which will be executed. Update when the database metamodel is updated.
			String SQLquery = "DELETE FROM ACCOUNTS WHERE ACCOUNT_ID="+deleteQuery.getAccount_id();
			
			
			// Creating, executing, and closing the statement
			Statement statement = databaseConnection.createStatement();
			
			try {
				if(statement.executeUpdate(SQLquery) != 1) {
					return new DeleteServerResponse(DeleteServerResponse.Status.KO);
				}
			} catch (SQLException e) {
				throw e;
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
			
			// Now that the cleanup is complete, the method can return
			return new DeleteServerResponse(DeleteServerResponse.Status.OK);
		} catch (SQLException e) {
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			connectionPool.release(databaseConnection);
		}
	}

	
	/**
	 * Asks the database to update the balance value of an account.
	 * @param withdrawalQuery : the client's query
	 * @return : the server's response. Can be a WithdrawalServerResponse, or a ErrorServerResponse if a SQL error happened and wasn't supposed to.
	 */
	public static ServerResponse handleWithdrawalQuery(WithdrawalQuery withdrawalQuery) {
		// Acquiring the JDBC connection from the pool
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection databaseConnection = connectionPool.acquire();
		
		try {
			// The SQL queries which will be executed. Update when the database metamodel is updated.
			String SQLquery = "UPDATE ACCOUNTS SET BALANCE=BALANCE" 
						+ ((withdrawalQuery.getValue()>=0)?'+':"") // Handling the value's sign : for negative values, the minus sign is already there
						+ withdrawalQuery.getValue()
						+ " WHERE ACCOUNT_ID="
						+ withdrawalQuery.getAccount_id();
			String SQLquery2 = "SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_ID=" + withdrawalQuery.getAccount_id();
			
			// Creating, executing, and closing the statement
			Statement statement = databaseConnection.createStatement();
			
			try {
				statement.executeUpdate(SQLquery);
			} catch (SQLException e) {
				/* If any error happened at this point (no lines 
				 * were changed, a trigger threw an exception, 
				 * etc...) then the client will know by seeing 
				 * that the balance did not change. 
				 * Therefore, the return value of "executeUpdate"
				 * as well as this "catch" block are not used.*/
			}
			
			
			try {
				ResultSet results = statement.executeQuery(SQLquery2);

				double balance;
				if(results.next()) {
					balance = results.getDouble(1);
				} else {
					throw new SQLException("No results");
				}
				return new WithdrawalServerResponse(balance);
			} catch (Exception e) {
				throw e;
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
		} catch (SQLException e) {
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			connectionPool.release(databaseConnection);
		}
	}

	
	public static ServerResponse handleNewCustomerQuery(NewCustomerQuery newCustomerQuery) {
		// Acquiring the JDBC connection from the pool
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection databaseConnection = connectionPool.acquire();
		
		try {
			// The SQL query which will be executed. Update when the database metamodel is updated.
			String SQLquery = "INSERT INTO CUSTOMERS "
					+ "VALUES(((SELECT MAX(CUSTOMER_ID) FROM CUSTOMERS) + 1), '" 
					+ newCustomerQuery.getFirst_name() + "', '" + newCustomerQuery.getLast_name() + "', "
					+ newCustomerQuery.getAge() + ", '" + newCustomerQuery.getSex() + "', '" 
					+ newCustomerQuery.getActivity() + "', '" + newCustomerQuery.getAdress() + "')";

			// Creating, executing, and closing the statement
			Statement statement = databaseConnection.createStatement();
			
			try {
				if(statement.executeUpdate(SQLquery) != 1)
					throw new SQLException("Unable to insert");
			} catch (SQLException e) {
				e.printStackTrace();
				return new NewCustomerServerResponse(NewCustomerServerResponse.Status.KO);
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
			
			// Now that the cleanup is complete, the method can return
			return new NewCustomerServerResponse(NewCustomerServerResponse.Status.OK);
		} catch (SQLException e) {
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			connectionPool.release(databaseConnection);
		}
	}

	
	public static ServerResponse handleConsultQuery(ConsultQuery consultQuery) {
		// Acquiring the JDBC connection from the pool
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection databaseConnection = connectionPool.acquire();
		
		try {
			// The SQL query which will be executed. Update when the database metamodel is updated.
			String SQLquery = "SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_ID="+consultQuery.getAccount_id();
			
			// Creating, executing, and closing the statement
			Statement statement = databaseConnection.createStatement();
			
			try {
				ResultSet results = statement.executeQuery(SQLquery);
				
				if(results.next()) {
					return new ConsultServerResponse(results.getDouble(1));
				} else {
					throw new SQLException("No results");
				}
			} catch (SQLException e) {
				return new ErrorServerResponse("Account not found");
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
		} catch (SQLException e) {
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			connectionPool.release(databaseConnection);
		}
	}
}
