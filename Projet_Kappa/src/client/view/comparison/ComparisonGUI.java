package client.view.comparison;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import client.view.MainMenuGUI;
import client.view.Tab;
import model.SessionInformation;
import model.response.GetAccountsServerResponse.Account;
import util.JsonImpl;
import util.KappaProperties;

/**
 * The Tab for the comparison Use Case.
 * @see Tab
 * @author Kappa-V
 * @version R3 sprint 3
 */
@SuppressWarnings("serial") // Is not going to get serialized
public class ComparisonGUI extends Tab {
	private ComparisonGUI thisObject = this;
	private AccountSelectionGUI accountSelectionGUI;
	private JPanel simulationSelectionGUI;
	
	private final CardLayout cards = new CardLayout();
	private final String accountSelectionCardName = "ACC.SEL";
	private final String simulationSelectionCardName = "SIM.SEL";
	
	public ComparisonGUI() {
		super("Comparer des simulations", 1);
	}

	@Override
	public void setSessionInformation(SessionInformation sessionInformation) {
		// Account Selection
		switch (sessionInformation.authorization_level) {
		case 1:
			accountSelectionGUI = new LoginChoiceGUI() {
				public void onSelect(Account A) {
					cards.show(thisObject, simulationSelectionCardName);
				}
			};
			break;
		default:
			accountSelectionGUI = new AdvisorLoginChoiceGUI() {
				public void onSelect(Account A) {
					cards.show(thisObject, simulationSelectionCardName);
				}
			};
			break;
		}
		
		// Simulation selection
		simulationSelectionGUI = new JPanel();
		simulationSelectionGUI.setBackground(Color.BLUE);
		
		// Card layout
		this.setLayout(cards);
		this.add(accountSelectionGUI, accountSelectionCardName);
		this.add(simulationSelectionGUI, simulationSelectionCardName);
		cards.show(thisObject, accountSelectionCardName);
		
		// Session Information dispatching
		accountSelectionGUI.setSessionInformation(sessionInformation);
//		simulationSelectionGUI.setSessionInformation(sessionInformation); // TODO
	}
	
	public static void main(String[] args) {
		// Tools initialization
		try {
			KappaProperties.init();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		JsonImpl.init();
		
		
		Tab tab = new ComparisonGUI();
		Set<Tab> tabs = new HashSet<>();
		tabs.add(tab);
		
		new MainMenuGUI(tabs).setSessionInformation(new SessionInformation(4, "Valentin", null));
	}
}
