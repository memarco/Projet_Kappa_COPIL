package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Customers extends JFrame implements ActionListener {

	JButton insert;
	JButton update;
	JButton research;
	JButton delete;

	public Customers() {

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
		JButton s = (JButton) e.getSource();

		if (s == insert) {
			Insert in = new Insert();

		}

		else if (s == update) {
			Update up = new Update();
		}

		else if (s == research) {
			Research re = new Research();
		}

		else if (s == delete) {
			Delete del = new Delete();
		}

	}

	public static void main(String[] args) {
		Customers c = new Customers();
	}

}
