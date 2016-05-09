package client.view.comparison;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.view.SessionSpecific;
import model.SessionInformation;
import model.query.ClientQuery;
import model.response.GetAccountsServerResponse;
import model.response.GetAccountsServerResponse.Account;
import util.JsonImpl;

/**
 * An abstract class for JPanels which are used to go fetch account numbers from the server. There are at least two ways to do this,
 * but the communication code is the same, so it is factorized here.</br>
 * The basic idea is that subclasses should implement a constructor with the layout definition, including at least the components
 * which have a setter here, plus their search parameters. Those search parameters will be used in their implementation of the 
 * generateQuery() method.</br>
 * onSelect should stay abstract in subclasses, so that it can be defined by the class which uses an AccountSelectionGUI.
 * @author Kappa-V
 * @version R3 sprint 3 - 09/05/2016
 */
@SuppressWarnings("serial") // Is not going to get serialized.
public abstract class AccountSelectionGUI extends JPanel implements SessionSpecific {
	private JButton sendQueryButton;
	private JPanel resultsPanel;
	private JButton selectButton;
	private final Account chosenAccount = new Account();
	
	/**
	 * This method is used by the setSessionInformation method. It is used to grant the ability to subclasses to re-use 
	 * the setSessionInformation method. Subclasses should override the constructor, and this method.</br>
	 * @return the query object
	 */
	protected abstract ClientQuery generateQuery();
	
	/**
	 * This method is called when the user has selected an account and pressed the select button.</br>
	 * It should stay abstract in AccountSelectionGUI's subclasses so that the object which uses an 
	 * AccountSelectionGUI can define the onSelect behavior itself.
	 * @param A : the chosen account.
	 */
	public abstract void onSelect(Account A);
	
	// Getters and Setters
	public void setSendQueryButton(JButton sendQueryButton) {
		this.sendQueryButton = sendQueryButton;
	}
	public void setResultsPanel(JPanel resultsPanel) {
		this.resultsPanel = resultsPanel;
	}
	public void setSelectButton(JButton selectButton) {
		this.selectButton = selectButton;
	}
	public JButton getSendQueryButton() {
		return sendQueryButton;
	}
	public JPanel getResultsPanel() {
		return resultsPanel;
	}
	public JButton getSelectButton() {
		return selectButton;
	}
	
	/**
	 * Creates the listeners
	 */
	@Override
	public void setSessionInformation(final SessionInformation sessionInformation) {
		final AccountSelectionGUI thisObject = this;
		
		sendQueryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// All network operations are carried out in a thread so that the GUI doesn't freeze.
				new Thread(new Runnable() {
					public void run() {
						// Flow initialization
						PrintWriter out;
						BufferedReader in;
						try {
							out = new PrintWriter(sessionInformation.getSocket().getOutputStream(), true);
							in = new BufferedReader(new InputStreamReader(sessionInformation.getSocket().getInputStream()));
						} catch (Exception e1) { // Reached if an IO exception occurs, or if the socket is not connected anymore
							JOptionPane.showMessageDialog(thisObject, "Erreur: connection au serveur interrompue. Vérifiez votre connection Internet, puis essayez de vous re-connecter.");
							return;
						}
						
						// Server transaction
						ClientQuery query = generateQuery();
						out.println(query.toString());
						GetAccountsServerResponse response;
						try {
							String message = in.readLine();
							
							// Prefix/Content identification
							int prefixEnd = message.indexOf(' ');
							if(prefixEnd == -1) {
								throw new Exception("No prefix");
							}
							
							String prefix = message.substring(0, prefixEnd);
							String content = message.substring(prefixEnd + 1);
							
							switch (prefix) {
							case "OK":
								response = JsonImpl.fromJson(content, GetAccountsServerResponse.class);
								break;
							default:
								throw new Exception("ERR or UNAUTHORIZED");
							}
						} catch (IOException e) {
							JOptionPane.showMessageDialog(thisObject, "Erreur: Connexion au serveur interrompue. Vérifiez votre connection Internet, puis essayez de vous re-connecter.");
							return;
						} catch (Exception e) {
							JOptionPane.showMessageDialog(thisObject, "Erreur: Essayez de télécharger la nouvelle version de ce logiciel.");
							return;
						} finally {
							// Good practice : the cleanup code is in a finally block
							out.close();
							try { in.close(); } catch (IOException e) {	/* Do nothing */ }
						}
						
						// GUI update
						selectButton.setEnabled(true);
						List<Account> accounts = response.getAccounts();
						final Set<JLabel> allLabels = new HashSet<>();
						int i = 0;
						for(final Account account : accounts) {
							i += 2;
							
							final JLabel accountNumLabel = new JLabel(account.getAccount_num());
							resultsPanel.add(accountNumLabel, "2, " + i);
							
							final JLabel nameLabel = new JLabel(account.getName());
							resultsPanel.add(nameLabel, "4, " + i);
							
							allLabels.add(accountNumLabel);
							allLabels.add(nameLabel);
							
							MouseListener listener = new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent event) {
									for(JLabel label : allLabels) {
										label.setBackground(Color.WHITE);
									}

									accountNumLabel.setBackground(Color.BLUE);
									nameLabel.setBackground(Color.BLUE);
									
									chosenAccount.copy(account);
								}
							};
							
							accountNumLabel.addMouseListener(listener);
							nameLabel.addMouseListener(listener);
						}
					}
				}).start();
			}
		});
		
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// All network operations are carried out in a thread so that the GUI doesn't freeze.
				new Thread(new Runnable() {
					public void run() {
						onSelect(chosenAccount);
					}
				}).start();
			}
		});
	}
}
