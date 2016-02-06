package vues;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import Handler.ProtocoleHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Delete extends JFrame implements ActionListener {
	
	
	//---------------------------- for delete-----------
		JLabel id= new JLabel("Numéro d'identidiant:");
		JTextField JT_id=new JTextField(10);
		JButton delete=new JButton("Supprimer");
		JButton back=new JButton("Retour");
		
		
		public Delete()
		{
			
			this.setSize(300, 200);
			this.setTitle("Effacer");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setLayout(new BorderLayout());
			this.setLocationRelativeTo(null);
			
			// For delete
			
			JPanel panel4=new JPanel();
			panel4.setLayout(new FlowLayout());
			panel4.add(id); panel4.add(JT_id);
			panel4.add(delete);
			panel4.add(back);
			delete.addActionListener(this);
			back.addActionListener(this);
			this.add(panel4,BorderLayout.CENTER);
			
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
			else if(e.getSource()==delete){
				
				int dialogResult= JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer ce compte?");
				if(dialogResult==JOptionPane.YES_OPTION){
				
				System.out.println("valider");	
				int idCH=Integer.parseInt(JT_id.getText());
				
				ProtocoleHandler  Ph = new ProtocoleHandler();
				
				try {
					Ph.delete(idCH);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
			
			
		}

		}
}

