package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.query.AuthenticationQuery;
import model.query.ConsultQuery;
import model.query.DeleteQuery;
import model.query.NewCustomerQuery;
import model.query.WithdrawalQuery;
import model.response.AuthenticationServerResponse;
import model.response.ConsultServerResponse;
import model.response.DeleteServerResponse;
import model.response.ErrorServerResponse;
import model.response.NewCustomerServerResponse;
import model.response.ServerResponse;
import model.response.WithdrawalServerResponse;

import org.apache.log4j.Logger;

/**
 * Handles messages by interpreting them, and using JDBC connections acquired from the connection pool to treat them.</br>
 * What's important is that except in the case of the "BYE" message, the server always answers.</br>
 * This version of the protocol uses a two-level verification system : server responsed are prefixed by either
 * "OK" or "ERR" (if the query was ill-formatted, or a server-side issue makes handling it impossible), and if the prefix
 * was "OK", the JSON object contained within the response tells if the operation was carried out properly.
 * @version R3 sprint 1 - 18/04/2016
 * @author Kappa-V
 * @changes
 * 		R2 sprint 1 -> R3 sprint 1 : </br>
 * 			-addition of the handleAuthQuery method</br>
 * 			-removal of the handleMessage method. It was moved to the Session class instead.
 */
public abstract class MessageHandler {
	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(MessageHandler.class);
	
	
	
	
	/**
	 * Tries to use the user id and password in the query to aunthentify.
	 * @param authQuery : the client's query
	 * @return the server's response to the query. 
	 * Typically an AuthenticationServerResponse, but can also be an ErrorServerResponse.
	 */
	public static ServerResponse handleAuthQuery(AuthenticationQuery authQuery) {
		logger.trace("Entering MessageHandler.handleAuthQuery");
		
		// Acquiring the JDBC connection from the pool
		Connection databaseConnection;
		try {
			databaseConnection = ConnectionPool.acquire();
		} catch (IllegalStateException | ClassNotFoundException | SQLException e) {
			logger.trace("Exiting MessageHandler.handleAuthQuery");
			logger.warn("Can't acquire a connection from the pool", e);
			return new ErrorServerResponse("Server-side error. Please retry later.");
		}
		
		try {
			String SQLQuery = "SELECT * FROM USERS "
					+ "WHERE \"Login\" LIKE '" + authQuery.getId() + "'";
			
			Statement statement = databaseConnection.createStatement();
			
			try {
				ResultSet results = statement.executeQuery(SQLQuery);
				
				if(results.next()) {
					if(authQuery.getPassword().equals(results.getString("Password"))) {
						return new AuthenticationServerResponse(results.getInt("Authorization_Level"));
					} else {
						return new AuthenticationServerResponse(false);
					}
				} else {
					return new AuthenticationServerResponse(true);
				}
			} catch (SQLException e) {
				logger.warn("SQLException caught", e);
				throw e;
			} finally {
				statement.close();
			}
		} catch (SQLException e) {
			logger.warn("SQLException caught", e);
			logger.trace("Exiting MessageHandler.handleAuthQuery");
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			ConnectionPool.release(databaseConnection);
		}
	}
	
	/**
	 * Asks the database to delete an account
	 * @param deleteQuery : the client's query
	 * @return the server's response to the query. 
	 */
	public static ServerResponse handleDeleteQuery(DeleteQuery deleteQuery) {
		logger.trace("Entering MessageHandler.handleDeleteQuery");
		
		// Acquiring the JDBC connection from the pool
		Connection databaseConnection;
		try {
			databaseConnection = ConnectionPool.acquire();
		} catch (IllegalStateException | ClassNotFoundException | SQLException e) {
			logger.trace("Exiting MessageHandler.handleDeleteQuery");
			logger.warn("Can't acquire a connection from the pool", e);
			return new ErrorServerResponse("Server-side error. Please retry later.");
		}
		
		try {
			// The SQL query which will be executed. Update when the database metamodel is updated.
			String SQLquery = "DELETE FROM ACCOUNTS WHERE ACCOUNT_ID="+deleteQuery.getAccount_id();
			
			
			// Creating, executing, and closing the statement
			Statement statement = databaseConnection.createStatement();
			
			try {
				if(statement.executeUpdate(SQLquery) != 1) {
					logger.trace("Exiting MessageHandler.handleDeleteQuery");
					return new DeleteServerResponse(DeleteServerResponse.Status.KO);
				}
			} catch (SQLException e) {
				logger.warn("SQLException caught", e);
				throw e;
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
			
			// Now that the cleanup is complete, the method can return
			return new DeleteServerResponse(DeleteServerResponse.Status.OK);
		} catch (SQLException e) {
			logger.warn("SQLException caught", e);
			logger.trace("Exiting MessageHandler.handleDeleteQuery");
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			ConnectionPool.release(databaseConnection);
		}
	}

	
	/**
	 * Asks the database to update the balance value of an account.
	 * @param withdrawalQuery : the client's query
	 * @return the server's response. Can be a WithdrawalServerResponse, or a ErrorServerResponse if a SQL error happened and wasn't supposed to.
	 */
	public static ServerResponse handleWithdrawalQuery(WithdrawalQuery withdrawalQuery) {
		logger.trace("Entering MessageHandler.handleWithdrawalQuery");
		
		// Acquiring the JDBC connection from the pool
		Connection databaseConnection;
		try {
			databaseConnection = ConnectionPool.acquire();
		} catch (IllegalStateException | ClassNotFoundException | SQLException e) {
			logger.warn("Can't acquire a connection from the pool", e);
			logger.trace("Exiting MessageHandler.handleWithdrawalQuery");
			return new ErrorServerResponse("Server-side error.");
		}
		
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
				 * as well as this "catch" block are not used.
				 * We still log it just in case.*/
				logger.warn("Non-problematic exception caught", e);
			}
			
			
			try {
				ResultSet results = statement.executeQuery(SQLquery2);

				double balance;
				if(results.next()) {
					balance = results.getDouble(1);
				} else {
					throw new SQLException("No results");
				}
				logger.trace("Exiting MessageHandler.handleWithdrawalQuery");
				return new WithdrawalServerResponse(balance);
			} catch (Exception e) {
				throw e;
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
		} catch (SQLException e) {
			logger.warn("Exception caught", e);
			logger.trace("Exiting MessageHandler.handleWithdrawalQuery");
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			ConnectionPool.release(databaseConnection);
		}
	}


	/**
	 * Asks the database to create a new customer.
	 * @param withdrawalQuery : the client's query
	 * @return the server's response. Can be a WithdrawalServerResponse, or a ErrorServerResponse if a SQL error happened and wasn't supposed to.
	 */
	public static ServerResponse handleNewCustomerQuery(NewCustomerQuery newCustomerQuery) {
		logger.trace("Entering MessageHandler.handleNewCustomerQuery");
		
		// Acquiring the JDBC connection from the pool
		Connection databaseConnection;
		try {
			databaseConnection = ConnectionPool.acquire();
		} catch (IllegalStateException | ClassNotFoundException | SQLException e) {
			logger.warn("Can't acquire connection", e);
			logger.trace("Exiting MessageHandler.handleNewCustomerQuery");
			return new ErrorServerResponse("Server-side error.");
		}
		
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
				logger.warn("SQL Exception caught", e);
				logger.trace("Exiting MessageHandler.handleNewCustomerQuery");
				return new NewCustomerServerResponse(NewCustomerServerResponse.Status.KO);
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
			
			// Now that the cleanup is complete, the method can return
			logger.trace("Exiting MessageHandler.handleNewCustomerQuery");
			return new NewCustomerServerResponse(NewCustomerServerResponse.Status.OK);
		} catch (SQLException e) {
			logger.warn("Exception caught", e);
			logger.trace("Exiting MessageHandler.handleNewCustomerQuery");
			return new ErrorServerResponse("Database error. Please retry later.");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			ConnectionPool.release(databaseConnection);
		}
	}


	/**
	 * Asks the database to provide the balance value of an account.
	 * @param withdrawalQuery : the client's query
	 * @return the server's response. Can be a WithdrawalServerResponse, or a ErrorServerResponse if a SQL error happened and wasn't supposed to.
	 */
	public static ServerResponse handleConsultQuery(ConsultQuery consultQuery) {
		logger.trace("Entering MessageHandler.handleConsultQuery");
		
		// Acquiring the JDBC connection from the pool
		Connection databaseConnection;
		try {
			databaseConnection = ConnectionPool.acquire();
		} catch (IllegalStateException | ClassNotFoundException | SQLException e) {
			logger.warn("Can't acquire a connection from the pool.", e);
			logger.trace("Exiting MessageHandler.handleConsultQuery");
			return new ErrorServerResponse("Server-side error.");
		}
		
		try {
			// The SQL query which will be executed. Update when the database metamodel is updated.
			String SQLquery = "SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_ID="+consultQuery.getAccount_id();
			
			// Creating, executing, and closing the statement
			Statement statement = databaseConnection.createStatement();
			
			try {
				ResultSet results = statement.executeQuery(SQLquery);
				
				if(results.next()) {
					ServerResponse response = new ConsultServerResponse(results.getDouble(1));
					logger.trace("Exiting MessageHandler.handleConsultQuery");
					return response;
				} else {
					throw new SQLException("No results");
				}
			} catch (SQLException e) {
				logger.trace("Exiting MessageHandler.handleConsultQuery");
				logger.warn("SQL Exception caught", e);
				return new ErrorServerResponse("Account not found");
			} finally {
				// Good practice : the cleanup code is in a finally block.
				statement.close();
			}
		} catch (SQLException e) {
			logger.warn("Exception caught", e);
			logger.trace("Exiting MessageHandler.handleConsultQuery");
			return new ErrorServerResponse("Database error");
		} finally {
			// Good practice : the cleanup code is in a finally block.
			ConnectionPool.release(databaseConnection);
		}
	}
}
