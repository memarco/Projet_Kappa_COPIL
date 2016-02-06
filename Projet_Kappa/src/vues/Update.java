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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Handler.ProtocoleHandler;

public class Update extends JFrame implements ActionListener {
	
	//-------------- for update---------------
		JLabel id_count= new JLabel("Numéro de compte:");
		JTextField JT_count=new JTextField(10);
		JLabel balance= new JLabel("Donner le montant:");
		JTextField JT_balance=new JTextField(10);
		JButton deposit=new JButton("Depôt");
		JButton withdrawal=new JButton("Retrait");
		JLabel jl_message=new JLabel(" ");
		JButton back=new JButton("Retour");
	
	public Update(){
		this.setSize(200, 200);
		this.setTitle("Modifier");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JPanel panel2=new JPanel();
		this.setLayout(new GridLayout(0,2));
		this.setLocationRelativeTo(null);
		
		
		panel2.add(id_count); panel2.add(JT_count);
		panel2.add(balance);panel2.add(JT_balance);
		panel2.add(deposit);panel2.add(withdrawal);
		panel2.add(back);
		deposit.addActionListener(this);
		withdrawal.addActionListener(this);
		back.addActionListener(this);
		this.add(panel2,BorderLayout.NORTH);
		this.add(jl_message,BorderLayout.SOUTH);
		this.setBounds(10,10,500,400);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==back)
		{
			this.setVisible(false);
			Customers c=new Customers();
			
		}
		
		else if(e.getSource()== deposit)
		{
			

//			JFrame frame=new JFrame("JOptionPane showMessageDialog");
//			JOptionPane.showMessageDialog(frame, "Votre dépôt à bien été effectué");
		System.out.println("valider");	
			
			 int countCH = Integer.parseInt(JT_count.getText());
			int balanceCH = Integer.parseInt(JT_balance.getText());
			
			ProtocoleHandler cH = new ProtocoleHandler();
			try {
				cH.Update(countCH, balanceCH);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
	}
		
		else if(e.getSource()==withdrawal )
		{
//			JFrame frame=new JFrame("JOptionPane showMessageDialog");
//			JOptionPane.showMessageDialog(frame, "Votre retrait à bien été effectué");
		System.out.println("valider");	
			
			 int countCH = Integer.parseInt(JT_count.getText());
			int balanceCH = Integer.parseInt(JT_balance.getText());
			
			ProtocoleHandler cH = new ProtocoleHandler();
			try {
				cH.Update(countCH, -balanceCH);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
	}

}
}
