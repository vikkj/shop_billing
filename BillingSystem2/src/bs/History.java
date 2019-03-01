package bs;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.Color;



public class History extends JFrame {
	ResultSet set_com,set_com1;
	private JPanel contentPane;
	private JTextField invoice_no;
	
	private DefaultTableModel model_sale=new DefaultTableModel();
	private JTable table_sale=new JTable();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					History frame = new History();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void tableColumnSize()
	{
        table_sale.getColumnModel().getColumn(0).setPreferredWidth(25);
		
		table_sale.getColumnModel().getColumn(1).setPreferredWidth(60);
		table_sale.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		table_sale.getColumnModel().getColumn(3).setPreferredWidth(50);
		table_sale.getColumnModel().getColumn(4).setPreferredWidth(50);
		/*table_sale.getColumnModel().getColumn(5).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(6).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(7).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(8).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(9).setPreferredWidth(100);
		*/
		
	}

	
	
	
	Connection connection=null;
	private JScrollPane scrollPane;
	private JTextField customer_name;
	private JTextField customer_no;
	private JTextField customerName;
	private JTextField customerNo;
	private JTextField invoiceNo;
	private JTextField invoiceDate;
	private JTextField discount_amt;
	private JTextField total_amt;
	public String total;
	public void commonMethod(String query)
	{
		try {
			Statement st=connection.createStatement();
			ResultSet set=st.executeQuery(query);
			if(set.next())
			{
				total=set.getString(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void getDetailWithCustomerName()
	{
		try {
			
			Statement st=connection.createStatement();
			set_com=st.executeQuery("select cus_name,cus_no,invoice,invoicedate from invoice_table where cus_name='"+customer_name.getText()+"'");
			if(set_com.next())
			{
				customerName.setText(set_com.getString(1));
				customerNo.setText(set_com.getString(2));
				invoiceNo.setText(set_com.getString(3));
				invoiceDate.setText(set_com.getString(4));
				
				try {// discount amount
					commonMethod("select sum(round(((mrp_price*quantity)/100)*discount,2)) from invoice_table where invoice='"+invoice_no.getText()+"'");
					discount_amt.setText(total);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				try {//total amount
					commonMethod("select sum(round((mrp_price*quantity)-((mrp_price*quantity)/100)*discount,2)) from invoice_table where invoice='"+invoice_no.getText()+"'");
					total_amt.setText(total);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void getDetailWithCustomerNumber() {

		try {
			
			Statement st=connection.createStatement();
			set_com=st.executeQuery("select cus_name,cus_no,invoice,invoicedate from invoice_table where cus_no='"+customer_no.getText()+"'");
			if(set_com.next())
			{
				customerName.setText(set_com.getString(1));
				customerNo.setText(set_com.getString(2));
				invoiceNo.setText(set_com.getString(3));
				invoiceDate.setText(set_com.getString(4));
				
				try {// discount amount
					commonMethod("select sum(round(((mrp_price*quantity)/100)*discount,2)) from invoice_table where invoice='"+invoice_no.getText()+"'");
					discount_amt.setText(total);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				try {//total amount
					commonMethod("select sum(round((mrp_price*quantity)-((mrp_price*quantity)/100)*discount,2)) from invoice_table where invoice='"+invoice_no.getText()+"'");
					total_amt.setText(total);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Create the frame.
	 */
	public History() {
		
		
		try {
			Class.forName("org.h2.Driver");
			connection=DriverManager.getConnection("jdbc:h2:~/test","sa","");
			
			//JOptionPane.showMessageDialog(null,"Connection Sucesssfull");
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//JFrame.getWindows();
		/*this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setVisible(true);*/
		/*Toolkit tk= Toolkit.getDefaultToolkit();
		int xsize=(int) tk.getScreenSize().getWidth();
		int ysize=(int) tk.getScreenSize().getHeight();*/
		//this.setSize(xsize,ysize);
		
		
		/*JFrame.setDefaultLookAndFeelDecorated(isAlwaysOnTop(true));
		JFrame.setDefaultLookAndFeelDecorated(isResizable(false));
		JFrame.setDefaultLookAndFeelDecorated(isVisible());*/
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 100, 1500, 900); 
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("Label.foreground"), UIManager.getColor("Label.foreground"), UIManager.getColor("Label.foreground"), UIManager.getColor("Label.foreground")));
		layeredPane.setBounds(0, 53, 1488, 226);
		contentPane.add(layeredPane);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No.");
		lblInvoiceNo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInvoiceNo.setBounds(127, 87, 127, 52);
		layeredPane.add(lblInvoiceNo);
		
		invoice_no = new JTextField();
		invoice_no.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				 Object row[]=new Object[20];

	               try {
	            	   getDetailWithCustomerName();
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);            		  
	            	   }
	            	   
	          	    int i=1;
	       			Statement st=connection.createStatement();
	       			set_com=st.executeQuery("select product_name,batchno,mrp_price,quantity from invoice_table where invoice='"+invoice_no.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				row[4]=set_com.getString(4);
	       				/*row[5]=set_com.getString(5);
	       				row[6]=set_com.getString(6);
	       				row[7]=set_com.getString(7);
	       				row[8]=set_com.getString(8);
	       				row[9]=set_com.getString(9);
	       				row[10]=set_com.getString(10);
	       					*/
	       				model_sale.addRow(row);	
	       		
	       			}
	       			
	       		} catch (Exception e2) {
	       			// TODO: handle exception
	       			e2.printStackTrace();
	       		}

			}
		});
		invoice_no.setColumns(10);
		invoice_no.setBounds(301, 89, 285, 52);
		layeredPane.add(invoice_no);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCustomerName.setBounds(701, 42, 173, 52);
		layeredPane.add(lblCustomerName);
		
		customer_name = new JTextField();
		customer_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
             if(e.getKeyCode()==KeyEvent.VK_ENTER)
             {
 
				 Object row[]=new Object[20];
 
	               try {
	            	   getDetailWithCustomerName();
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);            		  
	            	   }
	            	   Statement st=connection.createStatement();
	            	   set_com1=st.executeQuery("select invoice from invoice_table where cus_name='"+customer_name.getText()+"'");
	            	   
	            	   	
	            	   while(set_com1.next())
	            	   {
	            	    if(invoice_no.getText().equals(set_com1.getString(1)))
	                 	{
	            		
	            	
	          	         int i=1;
	       			     Statement st1=connection.createStatement();
	       			     set_com=st1.executeQuery("select product_name,batchno,mrp_price,quantity from invoice_table where invoice='"+invoice_no.getText()+"'");
	       		         while(set_com.next())
	       			     {
	       				   row[0]=i++;
	       				   row[1]=set_com.getString(1);
	       				   row[2]=set_com.getString(2);
	       				   row[3]=set_com.getString(3);
	       				   row[4]=set_com.getString(4);
	       				
	       				   model_sale.addRow(row);	
	       		         }
	       		          break;
	       			    }
	            	  
	            	    else {
	            	    	JOptionPane.showMessageDialog(null, "No Record Found");
	            	    	break;
	            	    }
	            	   } 
	       		      } catch (Exception e2) {
	       			    // TODO: handle exception
	       			    e2.printStackTrace();
	       		        }
	              
              }
			}
					
				
			
		});
		customer_name.setColumns(10);
		customer_name.setBounds(914, 42, 285, 52);
		layeredPane.add(customer_name);
		
		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 Object row[]=new Object[20];

	               try {
	            	   getDetailWithCustomerName();
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);            		  
	            	   }
	            	   
	          	    int i=1;
	       			Statement st=connection.createStatement();
	       			set_com=st.executeQuery("select product_name,batchno,mrp_price,quantity from invoice_table where invoice='"+invoice_no.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				row[4]=set_com.getString(4);
	       				/*row[5]=set_com.getString(5);
	       				row[6]=set_com.getString(6);
	       				row[7]=set_com.getString(7);
	       				row[8]=set_com.getString(8);
	       				row[9]=set_com.getString(9);
	       				row[10]=set_com.getString(10);
	       					*/
	       				model_sale.addRow(row);	
	       		
	       			}
	       			
	       		} catch (Exception e2) {
	       			// TODO: handle exception
	       			e2.printStackTrace();
	       		}
			}
		});
		button.setBounds(1245, 41, 152, 52);
		layeredPane.add(button);
		
		JLabel lblCustomerNo = new JLabel("Customer No.");
		lblCustomerNo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCustomerNo.setBounds(701, 135, 173, 52);
		layeredPane.add(lblCustomerNo);
		
		customer_no = new JTextField();
		customer_no.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
	             {
	 
					 Object row[]=new Object[20];
	 
		               try {
		            	   getDetailWithCustomerNumber();
		            	   int k=model_sale.getRowCount();
		            	   //JOptionPane.showMessageDialog(null, k);
		            	   while(k>0)
		            	   {
		            		   k--;
		            		   model_sale.removeRow(k);            		  
		            	   }
		            	   Statement st=connection.createStatement();
		            	   set_com1=st.executeQuery("select invoice from invoice_table where cus_no='"+customer_no.getText()+"'");
		            	   
		            	   	
		            	   while(set_com1.next())
		            	   {
		            	    if(invoice_no.getText().equals(set_com1.getString(1)))
		                 	{
		            		
		            	
		          	         int i=1;
		       			     Statement st1=connection.createStatement();
		       			     set_com=st1.executeQuery("select product_name,batchno,mrp_price,quantity from invoice_table where invoice='"+invoice_no.getText()+"'");
		       		         while(set_com.next())
		       			     {
		       				   row[0]=i++;
		       				   row[1]=set_com.getString(1);
		       				   row[2]=set_com.getString(2);
		       				   row[3]=set_com.getString(3);
		       				   row[4]=set_com.getString(4);
		       				
		       				   model_sale.addRow(row);	
		       		         }
		       		          break;
		       			    }
		            	  
		            	    else {
		            	    	JOptionPane.showMessageDialog(null, "No Record Found");
		            	    	break;
		            	    }
		            	   } 
		       		      } catch (Exception e2) {
		       			    // TODO: handle exception
		       			    e2.printStackTrace();
		       		        }
		              
	              }

	               
			}
			
		});
		customer_no.setColumns(10);
		customer_no.setBounds(914, 135, 285, 52);
		layeredPane.add(customer_no);
		
		JButton button_1 = new JButton("Search");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Object row[]=new Object[20];

	               try {
	            	   getDetailWithCustomerNumber();
	            	   int k=model_sale.getRowCount();
	            	   //JOptionPane.showMessageDialog(null, k);
	            	   while(k>0)
	            	   {
	            		   k--;
	            		   model_sale.removeRow(k);            		  
	            	   }
	            	   
	          	    int i=1;
	       			Statement st=connection.createStatement();
	       			set_com=st.executeQuery("select product_name,batchno,mrp_price,quantity from invoice_table where invoice='"+invoice_no.getText()+"'");
	       			while(set_com.next())
	       			{
	       				row[0]=i++;
	       				row[1]=set_com.getString(1);
	       				row[2]=set_com.getString(2);
	       				row[3]=set_com.getString(3);
	       				row[4]=set_com.getString(4);
	       				/*row[5]=set_com.getString(5);
	       				row[6]=set_com.getString(6);
	       				row[7]=set_com.getString(7);
	       				row[8]=set_com.getString(8);
	       				row[9]=set_com.getString(9);
	       				row[10]=set_com.getString(10);
	       					*/
	       				model_sale.addRow(row);	
	       		
	       			}
	       			
	       		} catch (Exception e2) {
	       			// TODO: handle exception
	       			e2.printStackTrace();
	       		}
			}
		});
		button_1.setBounds(1245, 134, 152, 52);
		layeredPane.add(button_1);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("Label.foreground"), UIManager.getColor("Label.foreground"), UIManager.getColor("Label.foreground"), UIManager.getColor("Label.foreground")));
		layeredPane_1.setBounds(0, 277, 1486, 581);
		contentPane.add(layeredPane_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 177, 1452, 309);
		layeredPane_1.add(scrollPane);
        scrollPane.setViewportView(table_sale);
		
		table_sale.setModel(model_sale);
		Object column_names[]= {"sr","Product","Batch no","Rate","Qty"};
		model_sale.setColumnIdentifiers(column_names);
		tableColumnSize();
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBounds(1317, 461, 117, 25);
		layeredPane_1.add(btnPrint);
		
		customerName = new JTextField();
		customerName.setColumns(10);
		customerName.setBounds(244, 36, 285, 52);
		layeredPane_1.add(customerName);
		
		JLabel label = new JLabel("Customer Name");
		label.setFont(new Font("Dialog", Font.BOLD, 16));
		label.setBounds(31, 36, 173, 52);
		layeredPane_1.add(label);
		
		JLabel label_1 = new JLabel("Customer No.");
		label_1.setFont(new Font("Dialog", Font.BOLD, 16));
		label_1.setBounds(837, 35, 173, 52);
		layeredPane_1.add(label_1);
		
		customerNo = new JTextField();
		customerNo.setColumns(10);
		customerNo.setBounds(1050, 35, 285, 52);
		layeredPane_1.add(customerNo);
		
		JLabel label_2 = new JLabel("Invoice No.");
		label_2.setFont(new Font("Dialog", Font.BOLD, 16));
		label_2.setBounds(31, 113, 127, 52);
		layeredPane_1.add(label_2);
		
		invoiceNo = new JTextField();
		invoiceNo.setColumns(10);
		invoiceNo.setBounds(244, 114, 285, 52);
		layeredPane_1.add(invoiceNo);
		
		JLabel label_3 = new JLabel("Invoice Date");
		label_3.setFont(new Font("Dialog", Font.BOLD, 16));
		label_3.setBounds(837, 112, 127, 52);
		layeredPane_1.add(label_3);
		
		invoiceDate = new JTextField();
		invoiceDate.setColumns(10);
		invoiceDate.setBounds(1050, 114, 285, 52);
		layeredPane_1.add(invoiceDate);
		
		JLabel lblDiscountValue = new JLabel("Discount Value");
		lblDiscountValue.setFont(new Font("Dialog", Font.BOLD, 16));
		lblDiscountValue.setBounds(115, 496, 143, 40);
		layeredPane_1.add(lblDiscountValue);
		
		discount_amt = new JTextField();
		discount_amt.setColumns(10);
		discount_amt.setBounds(316, 498, 213, 40);
		layeredPane_1.add(discount_amt);
		
		total_amt = new JTextField();
		total_amt.setColumns(10);
		total_amt.setBounds(955, 496, 213, 40);
		layeredPane_1.add(total_amt);
		
		JLabel lblTotalValue = new JLabel("Total value");
		lblTotalValue.setFont(new Font("Dialog", Font.BOLD, 16));
		lblTotalValue.setBounds(780, 496, 143, 40);
		layeredPane_1.add(lblTotalValue);
		
		JButton btnPrint_1 = new JButton("Print");
		btnPrint_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				
				try {
				     File file = new File("/home/vinay/file.txt");

				     try{    
				    	 
				    	 int i,j,n=0,m=0;
				    	 float mrp,quan,cost;
				    	 
				    	 n=model_sale.getRowCount();
				    	 m=model_sale.getColumnCount();
				    	
				    	
				           FileWriter fw=new FileWriter("/home/vinay/Printout's/History/History("+invoice_no.getText()+").txt",true); 
				     
				     	  BufferedWriter bw = new BufferedWriter(fw);
				     	  PrintWriter pw = new PrintWriter(bw);
				           pw.println("Customer Name: "+customerName.getText().toString()+"\nCustomer Number: "
				     	               +customerNo.getText());  
				           for(i=0;i<n;i++)
				           {
				        	  for(j=0;j<m;j++)
				        	  {
				        		  pw.println(model_sale.getColumnName(j) +" :- " + model_sale.getValueAt(i, j));
				        		  
				        		  
				        	  }
				        	  mrp=Float.parseFloat(model_sale.getValueAt(i, 3).toString());
				        	  quan=Float.parseFloat(model_sale.getValueAt(i, 4).toString());
				        	  cost=mrp*quan;
				        	  pw.println("\nCost :- "+cost);
				        	  pw.println();
				           }
				           pw.println("Discout Amount: "+discount_amt.getText());
				           pw.print("Total Amount: "+total_amt.getText());
				           pw.close();   
				           JOptionPane.showMessageDialog(null, "Print Sucessful");
				          }catch(Exception e1){System.out.println(e1);}    
				          System.out.println("Success...");    
				      
				    /* If file gets created then the createNewFile() 
				      * method would return true or if the file is 
				      * already present it would return false*/
				      
			             boolean fvar = file.createNewFile();
				     if (fvar){
				          System.out.println("File has been created successfully");
				     }
				     else{
				          System.out.println("File already present at the specified location");
				     }
			    	} catch (IOException e2) {
			    		System.out.println("Exception Occurred:");
				        e2.printStackTrace();
				  }
			}
		});
		btnPrint_1.setBounds(1286, 505, 137, 31);
		layeredPane_1.add(btnPrint_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Label.disabledForeground"));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground")));
		panel.setBounds(0, 0, 1488, 58);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("HISTORY");
		lblNewLabel.setBounds(679, 12, 158, 34);
		lblNewLabel.setForeground(UIManager.getColor("Menu.background"));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 23));
		panel.add(lblNewLabel);
	}
}