package client.view.comparison;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.query.ClientQuery;
import model.query.GetAccountsQuery;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;

/**
 * An account selection GUI in which the accounts are fetched based on the login of their owners.
 * @see AccountSelectionGUI
 * @author Kappa-V
 * @version R3 sprint 3 - 09/05/2016
 */
@SuppressWarnings("serial") // Is not going to get serialized
public abstract class LoginChoiceGUI extends AccountSelectionGUI {
	private final JTextField loginField;
	
	/**
	 * Creates the panel and all its components.
	 */
	public LoginChoiceGUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{190, 90, 67, 0};
		gridBagLayout.rowHeights = new int[]{20, 23, 2, 202, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel loginLabel = new JLabel("Login :");
		GridBagConstraints gbc_loginLabel = new GridBagConstraints();
		gbc_loginLabel.anchor = GridBagConstraints.EAST;
		gbc_loginLabel.insets = new Insets(0, 0, 5, 5);
		gbc_loginLabel.gridx = 0;
		gbc_loginLabel.gridy = 0;
		add(loginLabel, gbc_loginLabel);
		
		loginField = new JTextField();
		GridBagConstraints gbc_loginField = new GridBagConstraints();
		gbc_loginField.anchor = GridBagConstraints.NORTHWEST;
		gbc_loginField.insets = new Insets(0, 0, 5, 5);
		gbc_loginField.gridx = 1;
		gbc_loginField.gridy = 0;
		add(loginField, gbc_loginField);
		loginField.setColumns(10);
		
		
		// AccountSelectionGUI components management 
		JButton sendButton = new JButton("Chercher un compte");
		setSendQueryButton(sendButton);
		GridBagConstraints gbc_sendButton = new GridBagConstraints();
		gbc_sendButton.anchor = GridBagConstraints.NORTH;
		gbc_sendButton.insets = new Insets(0, 0, 5, 0);
		gbc_sendButton.gridwidth = 3;
		gbc_sendButton.gridx = 0;
		gbc_sendButton.gridy = 1;
		add(getSendQueryButton(), gbc_sendButton);
//		SwingUtilities.getRootPane(getSendQueryButton()).setDefaultButton(getSendQueryButton()); // Makes it possible to press enter // TODO : raises a nullpointerexception. Fix.
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.anchor = GridBagConstraints.NORTH;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 3;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		JLabel resultsLabel = new JLabel("R\u00E9sultats de la recherche :");
		scrollPane.setColumnHeaderView(resultsLabel);
		
		JPanel panel = new JPanel();
		setResultsPanel(panel);
		scrollPane.setViewportView(getResultsPanel());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JButton selectButton = new JButton("Sélectionner");
		setSelectButton(selectButton);
		getSelectButton().setEnabled(false);
		GridBagConstraints gbc_selectButton = new GridBagConstraints();
		gbc_selectButton.anchor = GridBagConstraints.EAST;
		gbc_selectButton.gridx = 2;
		gbc_selectButton.gridy = 4;
		add(getSelectButton(), gbc_selectButton);
	}
	
	@Override
	protected ClientQuery generateQuery() {
		return new GetAccountsQuery(loginField.getText());
	}
}
