package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Acceuil extends JFrame {
	
	public Acceuil() {
		
		this.setTitle("BANQUE D'ECHANGE MUTUALISTE");
		JLabel label = new JLabel();
		
		label.setFont(new Font("Serif", Font.PLAIN, 30));
		
		

		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setText("Veuillez selectionner votre opération");
		
		setLayout(new GridLayout(0,1));
	
		this.add(label);
		this.setBackground(Color.darkGray);
		this.setSize(new Dimension(500,500));
		
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.DARK_GRAY);
		
		JButton insert = new JButton("Inserer un nouveau client");
		

		JButton retrait = new JButton("Effectuer un retrait");
		JButton depot = new JButton("Effectuer un depot");
		JButton solde = new JButton("Voir le solde d'un compte");
		JButton suprimer = new JButton("supprimer un compte");
		
		
		this.add(insert);
		this.add(retrait);
		this.add(depot);
		this.add(solde);
		this.add(suprimer);
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		new Acceuil();
	}
	
	

}
