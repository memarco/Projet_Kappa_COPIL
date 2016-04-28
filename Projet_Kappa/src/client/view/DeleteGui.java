package client.view;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.controller.ClientSideProtocolHandler;

public class DeleteGui extends JFrame implements ActionListener {
	
	/**
	 * This class manages the removal of our bank account
	 */
	
	//---------------------------- for delete-----------
		JLabel id= new JLabel("Numéro d'identification:");
		JTextField JT_id=new JTextField(10);
		JButton delete=new JButton("Supprimer");
		JButton back=new JButton("Retour");
		
		
		public DeleteGui()
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
				MainMenuGui c=new MainMenuGui();
				
			}
			else if(e.getSource()==delete){
				
				int dialogResult= JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer ce compte?");
				if(dialogResult==JOptionPane.YES_OPTION){
				
				System.out.println("valider");	
				int idCH=Integer.parseInt(JT_id.getText());
				
				ClientSideProtocolHandler  Ph = new ClientSideProtocolHandler();
				
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

