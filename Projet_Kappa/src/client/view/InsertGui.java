package client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import client.controller.ProtocolHandler;
import model.query.NewCustomerQuery.Gender;

public class InsertGui extends JFrame implements ActionListener {

	/**
	 * This class manages the insertion of new customer in our database
	 */

	// ---------- for insert ----------------
	JLabel first_name = new JLabel("NOM:");
	JTextField JT_first_name = new JTextField(10);

	JLabel last_name = new JLabel("PRENOM:");
	JTextField JT_last_name = new JTextField(10);

	JLabel age = new JLabel("AGE:");
	JTextField JT_age = new JTextField(10);

	ButtonGroup group = new ButtonGroup();
	JLabel sexe = new JLabel("sexe M/F");
	JRadioButton button1 = new JRadioButton("M");
	JRadioButton button2 = new JRadioButton("F");

	JLabel activity = new JLabel("Activité:");
	JTextField JT_activity = new JTextField(10);

	JLabel adress = new JLabel("Adresse:");
	JTextField JT_adress = new JTextField(10);

	JButton exit = new JButton("Quitter");
	JButton back = new JButton("Retour");
	JButton submit = new JButton("Valider");

	public InsertGui() {

		// ---- for insert a customers
		JPanel panel1 = new JPanel();
		this.setSize(300, 300);
		this.setTitle("Insertion");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		panel1.setLayout(new GridLayout(0, 2));
		JPanel controlPanel = new JPanel();

		panel1.add(first_name);
		panel1.add(JT_first_name);
		panel1.add(last_name);
		panel1.add(JT_last_name);
		panel1.add(age);
		panel1.add(JT_age);

		button1.setMnemonic(KeyEvent.VK_C);
		button2.setMnemonic(KeyEvent.VK_M);
		panel1.add(sexe);
		panel1.add(controlPanel);
		group.add(button1);
		group.add(button2);
		controlPanel.add(button1);
		controlPanel.add(button2);
		panel1.add(adress);
		panel1.add(JT_adress);
		panel1.add(activity);
		panel1.add(JT_activity);

		panel1.add(submit);
		panel1.add(exit);
		panel1.add(back);

		submit.addActionListener(this);
		exit.addActionListener(this);
		back.addActionListener(this);
		this.add(panel1, BorderLayout.CENTER);

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == back) {
			this.setVisible(false);
			MainMenuGui c = new MainMenuGui();

		} else if (e.getSource() == exit) {
			System.exit(0);

		}

		else if (e.getSource() == submit) {

			System.out.println("valider");
			final ProtocolHandler c = new ProtocolHandler();

			final String nameFIRST = JT_first_name.getText();
			final String nameLAST = JT_last_name.getText();
			final int ageCH = Integer.parseInt(JT_age.getText());
			final String sexe;

			if (button1.isSelected()) {
				sexe = Gender.M.toString();
			} else {
				sexe = Gender.F.toString();
			}

			final String ActivityCH = JT_activity.getText();
			final String adressCH = JT_adress.getText();

			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println("Je suis là");
						c.insert(nameFIRST, nameLAST, ageCH, sexe, adressCH, ActivityCH);
						
					} catch (Exception e1) {
						System.out.println("imossible d'envoyer la requete");
					}

				}
			}).start();
			
			

			// clear the area's
			JT_first_name.setText("");
			JT_last_name.setText("");
			JT_age.setText("");
			JT_adress.setText("");
			JT_activity.setText("");

		}

	}
	
	public static void main(String[] args) {
		new InsertGui();
	}

}
