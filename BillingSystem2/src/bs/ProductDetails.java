package bs;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLayeredPane;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class ProductDetails extends JFrame {

	private JPanel contentPane;
	private JTextField Product_name;
	private JTextField Batch_no;
	private DefaultTableModel model_sale=new DefaultTableModel();
	private JTable table_sale=new JTable();
	private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductDetails frame = new ProductDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	ResultSet set_com;
	Connection connection=null;
	private void tableColumnSize()
	{
        table_sale.getColumnModel().getColumn(0).setPreferredWidth(5);
		
		table_sale.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(3).setPreferredWidth(100);
		
	}
	
	public void productDetail()
	{
		 Object row[]=new Object[20];

         try {
    			
    	    int i=1;
 			Statement st=connection.createStatement();
 			set_com=st.executeQuery("select distinct product_name,batchno,mrp_price,manufacturer,expdate from invoice_table");
 			while(set_com.next())
 			{
 				row[0]=i++;
 				row[1]=set_com.getString(1);
 				row[2]=set_com.getString(2);
 				row[3]=set_com.getString(3);
 				row[4]=set_com.getString(4);
 				row[5]=set_com.getString(5);
 				
 					
 				model_sale.addRow(row);	
 		
 			}
         } catch (Exception e2) {
     			// TODO: handle exception
     			e2.printStackTrace();
     		}
	
	}
	
	/**
	 * Create the frame.
	 */
	public ProductDetails() {
		
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
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(12, 49, 1476, 145);
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.selectionForeground")));
		layeredPane_1.setBackground(new Color(216, 191, 216));
		contentPane.add(layeredPane_1);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Dialog", Font.BOLD, 16));
		lblProductName.setBounds(12, 30, 141, 41);
		layeredPane_1.add(lblProductName);
		
		Product_name = new JTextField();
		Product_name.addKeyListener(new KeyAdapter() {
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
	       			set_com=st.executeQuery("select distinct product_name,batchno,mrp_price,manufacturer,expdate from invoice_table where product_name='"+Product_name.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				row[4]=set_com.getString(4);
	       				row[5]=set_com.getString(5);
	       				
	       					
	       				model_sale.addRow(row);	
	       				
	       				
				
	       			}
	               } catch (Exception e2) {
		       			// TODO: handle exception
		       			e2.printStackTrace();
		       		}
				}
		});
		Product_name.setBounds(194, 29, 262, 46);
		layeredPane_1.add(Product_name);
		Product_name.setColumns(10);
		
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
	       			set_com=st.executeQuery("select distinct product_name,batchno,mrp_price,manufacturer,expdate from invoice_table where product_name='"+Product_name.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				row[4]=set_com.getString(4);
	       				row[5]=set_com.getString(5);
	       					       					
	       				model_sale.addRow(row);	
	       				
	       			}
	               } catch (Exception e2) {
		       			// TODO: handle exception
		       			e2.printStackTrace();
		       		}
				}
				
		});
		
		btnSearch.setBounds(506, 30, 169, 41);
		layeredPane_1.add(btnSearch);
		
		JLabel lblBatchNo = new JLabel("Batch No.");
		lblBatchNo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblBatchNo.setBounds(775, 31, 141, 41);
		layeredPane_1.add(lblBatchNo);
		
		Batch_no = new JTextField();
		Batch_no.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//if(e.getKeyCode()==KeyEvent.VK_ENTER)
														
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
	       			set_com=st.executeQuery("select distinct product_name,batchno,mrp_price,manufacturer,expdate from invoice_table where batchno='"+Batch_no.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				row[4]=set_com.getString(4);
	       				row[5]=set_com.getString(5);
	       				
	       					
	       				model_sale.addRow(row);		       				
	       							
	       			}
	               } catch (Exception e2) {
		       			// TODO: handle exception
		       			e2.printStackTrace();
		       		}
			}							
		});
		Batch_no.setColumns(10);
		Batch_no.setBounds(957, 30, 262, 46);
		layeredPane_1.add(Batch_no);
		
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
		       			set_com=st.executeQuery("select distinct product_name,batchno,mrp_price,manufacturer,expdate from invoice_table where batchno='"+Batch_no.getText()+"'");
		       			while(set_com.next())
		       			{
		       				row[0]=i++;
		       				row[1]=set_com.getString(1);
		       				row[2]=set_com.getString(2);
		       				row[3]=set_com.getString(3);
		       				row[4]=set_com.getString(4);
		       				row[5]=set_com.getString(5);
		       						       					
		       				model_sale.addRow(row);			       						       				
					
		       			}
		               } catch (Exception e2) {
			       			// TODO: handle exception
			       			e2.printStackTrace();
			       		}
					}	
		});
		button.setBounds(1269, 31, 169, 41);
		layeredPane_1.add(button);
		
		JButton btnViewAllProducts = new JButton("View  All Products");
		btnViewAllProducts.addActionListener(new ActionListener() {
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
		 			set_com=st.executeQuery("select distinct product_name,batchno,mrp_price,manufacturer,expdate from invoice_table");
		 			while(set_com.next())
		 			{
		 				row[0]=i++;
		 				row[1]=set_com.getString(1);
		 				row[2]=set_com.getString(2);
		 				row[3]=set_com.getString(3);
		 				row[4]=set_com.getString(4);
		 				row[5]=set_com.getString(5);
		 					 					
		 				model_sale.addRow(row);	
		 		
		 			}
		         } catch (Exception e2) {
		     			// TODO: handle exception
		     			e2.printStackTrace();
		     		}
				
			}
		});
		btnViewAllProducts.setBounds(560, 104, 447, 25);
		layeredPane_1.add(btnViewAllProducts);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBounds(12, 192, 1476, 666);
		layeredPane_2.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.selectionForeground")));
		contentPane.add(layeredPane_2);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1452, 642);
		layeredPane_2.add(scrollPane);
		scrollPane.setViewportView(table_sale);
		table_sale.setModel(model_sale);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Label.disabledForeground"));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground")));
		panel.setBounds(12, 0, 1476, 53);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblProductDetails = new JLabel("PRODUCT DETAILS");
		lblProductDetails.setFont(new Font("Dialog", Font.BOLD, 21));
		lblProductDetails.setForeground(UIManager.getColor("List.background"));
		lblProductDetails.setBounds(683, 12, 246, 29);
		panel.add(lblProductDetails);
		Object column_names[]= {"sr","Product name","Batch No.","MRP Price","Manufacturer","Exp Date"};
		model_sale.setColumnIdentifiers(column_names);
		
		tableColumnSize();
		productDetail();
	}
}