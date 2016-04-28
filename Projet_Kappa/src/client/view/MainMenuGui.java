package client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenuGui extends JFrame implements ActionListener {

	/**
	 * Class that manages the interface with users;
	 * this class is also launching the client application
	 **/
	JButton insert;
	JButton update;
	JButton research;
	JButton delete;

	public MainMenuGui() {

		this.setTitle("BANQUE D'ECHANGE MUTUALISTE");
		JLabel label = new JLabel();

		label.setFont(new Font("Serif", Font.PLAIN, 30));

		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setText("Veuillez selectionner votre opération");

		setLayout(new GridLayout(0, 1));

		this.add(label);
		this.setBackground(Color.darkGray);
		this.setSize(new Dimension(500, 500));

		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.DARK_GRAY);

		insert = new JButton("Inserer un nouveau client");
		update = new JButton("Effectuer un retrait/depot");
		research = new JButton("Voir le solde d'un compte");
		delete = new JButton("supprimer un compte");

		this.add(insert);
		this.add(update);
		this.add(research);
		this.add(delete);

		insert.addActionListener(this);
		update.addActionListener(this);
		research.addActionListener(this);
		delete.addActionListener(this);

		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		if (button == insert) {
			InsertGui in = new InsertGui();

		}

		else if (button == update) {
			UpdateGui up = new UpdateGui();
		}

		else if (button == research) {
			ResearchGui re = new ResearchGui();
		}

		else if (button == delete) {
			DeleteGui del = new DeleteGui();
		}

	}

	public static void main(String[] args) {
		MainMenuGui customer = new MainMenuGui();
	}

}
