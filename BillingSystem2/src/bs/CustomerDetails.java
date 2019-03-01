package bs;



import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import java.awt.Color;

public class CustomerDetails extends JFrame {

	private JPanel contentPane;

	private DefaultTableModel model_sale=new DefaultTableModel();
	private JTable table_sale=new JTable();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerDetails frame = new CustomerDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection=null;
	private JTextField cust_name;
	private JTextField cust_no;
	private JScrollPane scrollPane;
	ResultSet set_com;
	
	public void customerDetail()
	{
		 Object row[]=new Object[20];

         try {
    			
    	    int i=1;
 			Statement st=connection.createStatement();
 			set_com=st.executeQuery("select distinct cus_name,cus_no,address from invoice_table");
 			while(set_com.next())
 			{
 				row[0]=i++;
 				row[1]=set_com.getString(1);
 				row[2]=set_com.getString(2);
 				row[3]=set_com.getString(3);
 					
 				model_sale.addRow(row);	
 		
 			}
         } catch (Exception e2) {
     			// TODO: handle exception
     			e2.printStackTrace();
     		}
	
	}
	
	private void tableColumnSize()
	{
        table_sale.getColumnModel().getColumn(0).setPreferredWidth(5);
		
		table_sale.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		table_sale.getColumnModel().getColumn(3).setPreferredWidth(100);
	}
	
	/**
	 * Create the frame.
	 */
	public CustomerDetails() {

		try {
			Class.forName("org.h2.Driver");
			connection=DriverManager.getConnection("jdbc:h2:~/test","sa","");
			
			//JOptionPane.showMessageDialog(null,"Connection Sucesssfull");
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 100, 1500, 900);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 248, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.CYAN);
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground")));
		layeredPane.setBounds(10, 59, 1478, 138);
		contentPane.add(layeredPane);
		
		JLabel lblCustomerAme = new JLabel("Customer Name");
		lblCustomerAme.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCustomerAme.setBounds(12, 30, 186, 45);
		layeredPane.add(lblCustomerAme);
		
		cust_name = new JTextField();
		cust_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				 Object row[]=new Object[20];

	               try {
	            	   
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);
	            		  
	            	   }
	            		
	          	    int i=1;
	       			Statement st=connection.createStatement();
	       			set_com=st.executeQuery("select distinct cus_name,cus_no,address from invoice_table where cus_name='"+cust_name.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				
	       					
	       				model_sale.addRow(row);	
	       			
	       			}
	               } catch (Exception e2) {
		       			// TODO: handle exception
		       			e2.printStackTrace();
		       		}
				
			}
		});
		cust_name.setBounds(216, 30, 258, 45);
		layeredPane.add(cust_name);
		cust_name.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Object row[]=new Object[20];

	               try {
	            	   
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);
	            		  
	            	   }
	            	   	
	          	    int i=1;
	       			Statement st=connection.createStatement();
	       			set_com=st.executeQuery("select distinct cus_name,cus_no,address from invoice_table where cus_name='"+cust_name.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				
	       					
	       				model_sale.addRow(row);	
	       			
	       			}
	               } catch (Exception e2) {
		       			// TODO: handle exception
		       			e2.printStackTrace();
		       		}
				}
	               
		});
			
		btnSearch.setBounds(529, 31, 117, 45);
		layeredPane.add(btnSearch);
		
		JLabel lblCustomerMobileNumber = new JLabel("Customer Mobile Number");
		lblCustomerMobileNumber.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCustomerMobileNumber.setBounds(729, 29, 252, 45);
		layeredPane.add(lblCustomerMobileNumber);
		
		cust_no = new JTextField();
		cust_no.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				Object row[]=new Object[20];

	               try {
	          			
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);
	            		  
	            	   }   
	            	   
	          	    int i=1;
	       			Statement st=connection.createStatement();
	       			set_com=st.executeQuery("select distinct cus_name,cus_no,address from invoice_table where cus_no='"+cust_no.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				
	       					
	       				model_sale.addRow(row);	
	       		
	       			}
	               } catch (Exception e2) {
		       			// TODO: handle exception
		       			e2.printStackTrace();
		       		}
				
			}
		});
		cust_no.setColumns(10);
		cust_no.setBounds(999, 29, 258, 45);
		layeredPane.add(cust_no);
		
		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object row[]=new Object[20];

	               try {
	          			
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);
	            		  
	            	   }
	            	      
	          	    int i=1;
	       			Statement st=connection.createStatement();
	       			set_com=st.executeQuery("select distinct cus_name,cus_no,address from invoice_table where cus_no='"+cust_no.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       						
	       				model_sale.addRow(row);	
	       	
	       			}
	               } catch (Exception e2) {
		       			// TODO: handle exception
		       			e2.printStackTrace();
		       		}
				}	
		});
		button.setBounds(1312, 30, 117, 45);
		layeredPane.add(button);
		
		JButton btnShowAllProduct = new JButton("Show All Customers");
		btnShowAllProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				 Object row[]=new Object[20];

		         try {
		        	 

	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);
	            		  
	            	   }
		        	     			
		    	    int i=1;
		 			Statement st=connection.createStatement();
		 			set_com=st.executeQuery("select distinct cus_name,cus_no,address from invoice_table");
		 			while(set_com.next())
		 			{
		 				row[0]=i++;
		 				row[1]=set_com.getString(1);
		 				row[2]=set_com.getString(2);
		 				row[3]=set_com.getString(3);
		 				
		 				model_sale.addRow(row);	 				
				
		 			}
		         } catch (Exception e2) {
		     			// TODO: handle exception
		     			e2.printStackTrace();
		     		}
			}
		});
		btnShowAllProduct.setBounds(549, 94, 409, 25);
		layeredPane.add(btnShowAllProduct);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground")));
		layeredPane_1.setBounds(10, 193, 1478, 665);
		contentPane.add(layeredPane_1);
		layeredPane_1.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1454, 641);
		layeredPane_1.add(scrollPane);
		scrollPane.setViewportView(table_sale);
		table_sale.setModel(model_sale);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Menu.disabledForeground"));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground")));
		panel.setBounds(10, 0, 1476, 63);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CUSTOMER DETAILS");
		lblNewLabel.setForeground(UIManager.getColor("List.background"));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 21));
		lblNewLabel.setBounds(599, 12, 253, 39);
		panel.add(lblNewLabel);
		Object column_names[]= {"sr","Customer name","Mobile Number","Address"};
		model_sale.setColumnIdentifiers(column_names);
		tableColumnSize();
		customerDetail();		
	}

}