package vues;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Controler.ProtocoleHandler;

public class ResearchGui extends JFrame implements ActionListener {
/**
 *This class manages the consultation customer's account
 **/
	JLabel jl_text = new JLabel(
			"Indiquez le nom ou l'identifiant de recherche:");
	JTextField name = new JTextField(10);
	JButton by_id = new JButton("Lancer la recherche");
	JButton back = new JButton("Retour");

	public ResearchGui() {
		this.setSize(620, 200);
		this.setTitle("Rechercher");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JPanel panel3 = new JPanel();
		this.setLayout(new GridLayout(0, 2));
		this.setLocationRelativeTo(null);

		panel3.add(jl_text);
		panel3.add(name);
		panel3.add(by_id);

		by_id.addActionListener(this);
		this.add(panel3, BorderLayout.CENTER);
		panel3.add(back);
		back.addActionListener(this);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == back) {
			this.setVisible(false);
			MainMenuGui c = new MainMenuGui();

		} else if (e.getSource() == by_id) {

			System.out.println("valider");
			int account_id = Integer.parseInt(name.getText());

			ProtocoleHandler protocolehandler = new ProtocoleHandler();
			try {
				protocolehandler.Research(account_id);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
