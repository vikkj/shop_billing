package bs;

import java.text.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.annotation.processing.RoundEnvironment;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import java.lang.Math;


public class GenerateInvoice extends JFrame {

	private JPanel contentPane;
	
	Connection connection=null;
	private JTextField gross_amt;
	private JTextField total_amt;
	private JTextField discount_amt;
	private JTextField cgst;
	private JTextField sgst;
	private JTextField igst;
	private JTextField manufacturer;
	private JTextField exp_date;
	private JTextField discount_per;
	private JTextField mrp_price;
	private JTextField invoice_date;
	private JTextField cust_mobileno;
	private JTextField cus_add;
	private JTextField invoice_no;
	private JComboBox product_name;
	private JComboBox gst_per;
	private JComboBox customer_name;
	


	private DefaultTableModel model_sale=new DefaultTableModel();
	private JTable table_sale=new JTable();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerateInvoice frame = new GenerateInvoice();
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
		table_sale.getColumnModel().getColumn(5).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(6).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(7).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(8).setPreferredWidth(100);
		table_sale.getColumnModel().getColumn(9).setPreferredWidth(100);
			
	}
	/**
	 * Create the frame.
	 */
	ResultSet set_com;
	private JScrollPane invoice_scrollPane;
	int i=1;
	
	public void productDropDown()
	{
		try {
			
			Statement st=connection.createStatement();
			set_com=st.executeQuery("select distinct product_name from invoice_table");
			while(set_com.next())
			{
				product_name.addItem(set_com.getString(1));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public void customerDropDown()
	{
		try {
			
			Statement st=connection.createStatement();
			set_com=st.executeQuery("select distinct cus_name from invoice_table");
			while(set_com.next())
			{
				customer_name.addItem(set_com.getString(1));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void getDetailWithCustomerName()
	{
		try {
			
			Statement st=connection.createStatement();
			set_com=st.executeQuery("select address,cus_no from invoice_table where cus_name='"+customer_name.getSelectedItem().toString()+"'");
			if(set_com.next())
			{
				cus_add.setText(set_com.getString(1));
				cust_mobileno.setText(set_com.getString(2));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void getDetailWithMobileNo()
	{
		try {
			
			Statement st=connection.createStatement();
			set_com=st.executeQuery("select address,cus_name from invoice_table where cus_no='"+cust_mobileno.getText()+"'");
			if(set_com.next())
			{
				cus_add.setText(set_com.getString(1));
				customer_name.setSelectedItem(set_com.getString(2));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public String total;
	private JTextField batch_no;
	private JComboBox quantity;
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
	
	public void invoiceAutoIncrement() {
		try {
			Statement st=connection.createStatement();
			set_com=st.executeQuery("select max(invoice) from invoice_table");
			while(set_com.next())
			{
				
				int i=Integer.parseInt(set_com.getString(1));
				i++;
				invoice_no.setText(String.valueOf(i));
		
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	public void autoDateGeneration() {
		Date date = new Date();
		SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy/MM/dd");
		
		invoice_date.setText(dateformat3.format(date));
		
	}
	
	public void getFilledDataWithCalculation() {
		//select mrp_price,quantity,
		//round(mrp_price*quantity,2) before_dis,discount,
		//((mrp_price*quantity)/100)*discount as dis_value,
		//round((mrp_price*quantity)-((mrp_price*quantity)/100)*discount,2) as after_dis,gst,
		//round((((mrp_price*quantity)-((mrp_price*quantity)/100)*discount)*gst)/(100+gst),2) as gst_value,
		//round((mrp_price*quantity)-((mrp_price*quantity)/100)*discount,2) as total  from invoice_table;
		try {//gross amount
			commonMethod("select sum(round(mrp_price*quantity,2)) from invoice_table where invoice='"+invoice_no.getText()+"'");
			gross_amt.setText(total);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
	    
		try {//cgst and sgst amount
			commonMethod("select sum(round(((((mrp_price*quantity)-((mrp_price*quantity)/100)*discount)*gst)/(100+gst))/2,2)) from invoice_table where invoice='"+invoice_no.getText()+"'");
			cgst.setText(total);
			sgst.setText(total);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {// igst amount
			commonMethod("select sum(round((((mrp_price*quantity)-((mrp_price*quantity)/100)*discount)*gst)/(100+gst),2)) from invoice_table where invoice='"+invoice_no.getText()+"'");
			igst.setText(total);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public GenerateInvoice() {
		
		  try {
				Class.forName("org.h2.Driver");
				connection=DriverManager.getConnection("jdbc:h2:~/test","sa","");
				
				//JOptionPane.showMessageDialog(null,"Connection Sucesssfull");
				
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  
		  try {
				DatabaseMetaData dmd = connection.getMetaData();
				ResultSet set=dmd.getTables(null,null,"invoice_table",null);
				         if(set.next())
				         {
				        	 // if table exists do noting 
				         }
				         else
				         {
				        	 String create_table="create table invoice_table ("
				        	 		+ "product_name varchar(100),"
				        	 		+ "batchno varchar(100),"
				        	 		+ "manufacturer varchar(100),"
				        	 		+ "expdate varchar(100),"
				        	 		+ "mrp_price number(19,2),"
				        	 		+ "discount number(10),"
				        	 		+ "gst number(10),"
				        	 		+ "quantity number(19,2),"
				        	 		+ "invoicedate varchar2(50),"
				        	 		+ "invoice varchar(50),"
				        	 		+ "cus_name varchar(100),"
				        	 		+ "cus_no varchar(20),"
				        	 		+ "address varchar(150))";
				        	 
				        	 PreparedStatement statement = connection.prepareStatement(create_table);
				        	 statement.executeUpdate();
				        	 
				        	 
				        	// JOptionPane.showMessageDialog(null, "Table Created Sucessfully");
				        	        	
				         }
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 100, 1500, 900);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane menulayeredPane = new JLayeredPane();
		menulayeredPane.setForeground(UIManager.getColor("List.foreground"));
		menulayeredPane.setBackground(UIManager.getColor("List.foreground"));
		menulayeredPane.setBounds(12, 0, 1476, 61);
		contentPane.add(menulayeredPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(UIManager.getColor("Label.disabledForeground"));
		panel_2.setBounds(-11, 0, 1487, 73);
		menulayeredPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblGenerateInvoice = new JLabel("GENERATE INVOICE");
		lblGenerateInvoice.setForeground(UIManager.getColor("List.background"));
		lblGenerateInvoice.setFont(new Font("FreeSans", Font.BOLD, 22));
		lblGenerateInvoice.setBounds(615, 12, 314, 37);
		panel_2.add(lblGenerateInvoice);
		
		JLayeredPane workSpacelayeredPane_1 = new JLayeredPane();
		workSpacelayeredPane_1.setBounds(12, 59, 1476, 799);
		contentPane.add(workSpacelayeredPane_1);
		workSpacelayeredPane_1.setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 224));
		workSpacelayeredPane_1.add(panel, "name_2736038581701");
		panel.setLayout(null);
		
		//table settings
		invoice_scrollPane = new JScrollPane();
		invoice_scrollPane.setBounds(14, 316, 1450, 362);
		invoice_scrollPane.setViewportBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY));
		panel.add(invoice_scrollPane);
		invoice_scrollPane.setViewportView(table_sale);
		invoice_scrollPane.getPreferredSize();
		
		table_sale.setModel(model_sale);
		Object column_names[]= {"sr","Product","Batch no","Rate","Qty","GST %","Discount","CGST","SGST","IGST","Total"};
		model_sale.setColumnIdentifiers(column_names);
		tableColumnSize();
		
		JLayeredPane invoiceItemslayeredPane = new JLayeredPane();
		invoiceItemslayeredPane.setBounds(12, 12, 1452, 292);
		panel.add(invoiceItemslayeredPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(253, 245, 230));
		panel_1.setLayout(null);
		panel_1.setBounds(0, 12, 1462, 299);
		invoiceItemslayeredPane.add(panel_1);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCustomerName.setBounds(12, 22, 171, 22);
		panel_1.add(lblCustomerName);
		
		customer_name = new JComboBox();
		customer_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDetailWithCustomerName();
			}
		});
		customer_name.setEditable(true);
		customer_name.setBounds(12, 56, 421, 33);
		panel_1.add(customer_name);
		customer_name.setSelectedItem("");
		
		JLabel lblMobileNumber = new JLabel("Mobile Number");
		lblMobileNumber.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMobileNumber.setBounds(12, 92, 160, 27);
		panel_1.add(lblMobileNumber);
		
		cust_mobileno = new JTextField();
		cust_mobileno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
				{
				getDetailWithMobileNo();
				}
			}
		});
		cust_mobileno.setBounds(12, 117, 421, 33);
		panel_1.add(cust_mobileno);
		cust_mobileno.setColumns(10);
		
		JLabel lblCustomerAddress = new JLabel("Customer Address");
		lblCustomerAddress.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCustomerAddress.setBounds(12, 159, 171, 22);
		panel_1.add(lblCustomerAddress);
		
		cus_add = new JTextField();
		cus_add.setBounds(12, 184, 421, 81);
		panel_1.add(cus_add);
		cus_add.setColumns(10);
	
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(598, 176, 1, 1);
		panel_1.add(layeredPane);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		layeredPane_1.setBounds(0, 12, 483, 266);
		panel_1.add(layeredPane_1);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		layeredPane_2.setBounds(510, 11, 940, 266);
		panel_1.add(layeredPane_2);
	    
	    mrp_price = new JTextField();
	    mrp_price.setBounds(24, 221, 167, 33);
	    layeredPane_2.add(mrp_price);
	    mrp_price.setHorizontalAlignment(SwingConstants.RIGHT);
	    mrp_price.setFont(new Font("Dialog", Font.PLAIN, 13));
	    mrp_price.setColumns(10);
	    
	    JLabel label_2 = new JLabel("Batch No./Unique ID.");
	    label_2.setBounds(24, 96, 218, 33);
	    layeredPane_2.add(label_2);
	    label_2.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    JLabel label_6 = new JLabel("MRP Rate");
	    label_6.setBounds(34, 176, 180, 33);
	    layeredPane_2.add(label_6);
	    label_6.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    JLabel label = new JLabel("Product Name");
	    label.setBounds(24, 12, 154, 33);
	    layeredPane_2.add(label);
	    label.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    JLabel lblInvoiceDate = new JLabel("Invoice Date");
	    lblInvoiceDate.setBounds(480, 12, 124, 33);
	    layeredPane_2.add(lblInvoiceDate);
	    lblInvoiceDate.setHorizontalAlignment(SwingConstants.CENTER);
	    lblInvoiceDate.setBorder(UIManager.getBorder("TextField.border"));
	    
	    invoice_date = new JTextField();
	    invoice_date.setBounds(480, 57, 204, 33);
	    layeredPane_2.add(invoice_date);
	    invoice_date.setColumns(10);
	    
	    product_name = new JComboBox();
	    product_name.setBounds(24, 57, 434, 33);
	    layeredPane_2.add(product_name);
	    product_name.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {

	    		
	    		try {
	    			
	    			
	    			//String find_product="SELECT * FROM invoice_table where product_name like 'T%'";
	    			//PreparedStatement ps=connection.prepareStatement(find_product);
	    			//ps.setString(1, product_name.getSelectedItem().toString());
	    			//Statement st=connection.createStatement();
	    			/*set_com=st.executeQuery("SELECT * FROM invoice_table where product_name like 'T%'");
	    			product_name.addItem(set_com.getString(1));*/
	    			
	    		
	    		    //JOptionPane.showMessageDialog(null, set_com.getString(1));
	    			
	    			String product=product_name.getSelectedItem().toString();
	    			Statement st=connection.createStatement();
	    			set_com=st.executeQuery("SELECT * from invoice_table where product_name='"+product+"'");
	    		     
	    			if(set_com.next())
	    			{
	    				batch_no.setText(set_com.getString("batchno"));   				
	    				manufacturer.setText(set_com.getString("MANUFACTURER"));
	    				exp_date.setText(set_com.getString("EXPDATE"));			
	    				mrp_price.setText(set_com.getString("mrp_price"));
	    				gst_per.setSelectedItem(set_com.getString("gst"));
	    					
	    			}
	    			
	    		} catch (Exception e1) {
	    			// TODO: handle exception
	    			e1.printStackTrace();
	    		}
	    	}
	    });
	    product_name.setEditable(true);
	    product_name.setSelectedItem("");
	    
	    manufacturer = new JTextField();
	    manufacturer.setBounds(479, 131, 205, 33);
	    layeredPane_2.add(manufacturer);
	    manufacturer.setFont(new Font("Dialog", Font.PLAIN, 13));
	    manufacturer.setColumns(10);
	    
	    JLabel label_3 = new JLabel("Manufacturer");
	    label_3.setBounds(480, 96, 180, 33);
	    layeredPane_2.add(label_3);
	    label_3.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    discount_per = new JTextField();
	    discount_per.setBounds(295, 221, 167, 33);
	    layeredPane_2.add(discount_per);
	    discount_per.setText("0");
	    discount_per.setHorizontalAlignment(SwingConstants.RIGHT);
	    discount_per.setFont(new Font("Dialog", Font.PLAIN, 13));
	    discount_per.setColumns(10);
	    
	    JLabel label_5 = new JLabel("Discount %");
	    label_5.setBounds(300, 176, 109, 33);
	    layeredPane_2.add(label_5);
	    label_5.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    gst_per = new JComboBox();
	    gst_per.setBounds(538, 221, 146, 33);
	    layeredPane_2.add(gst_per);
	    gst_per.setEditable(true);
	    gst_per.addItem("0");
	    gst_per.addItem("5");
	    gst_per.addItem("12");
	    gst_per.addItem("18");
	    gst_per.addItem("28");
	    
	    JLabel label_10 = new JLabel("GST %");
	    label_10.setBounds(538, 176, 66, 33);
	    layeredPane_2.add(label_10);
	    label_10.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    JLabel label_12 = new JLabel("Invoice No.");
	    label_12.setBounds(710, 12, 124, 33);
	    layeredPane_2.add(label_12);
	    label_12.setHorizontalAlignment(SwingConstants.CENTER);
	    label_12.setBorder(UIManager.getBorder("TextField.border"));
	    
	    invoice_no = new JTextField();
	    invoice_no.setBounds(710, 57, 205, 33);
	    layeredPane_2.add(invoice_no);
	    invoice_no.setColumns(10);
	    
	    JLabel label_4 = new JLabel("Exp Date");
	    label_4.setBounds(710, 96, 84, 33);
	    layeredPane_2.add(label_4);
	    label_4.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    exp_date = new JTextField();
	    exp_date.setBounds(710, 131, 205, 33);
	    layeredPane_2.add(exp_date);
	    exp_date.setFont(new Font("Dialog", Font.PLAIN, 13));
	    exp_date.setColumns(10);
	    
	    JLabel Quantity = new JLabel("Quantity in kg");
	    Quantity.setBounds(710, 176, 180, 33);
	    layeredPane_2.add(Quantity);
	    Quantity.setFont(new Font("Dialog", Font.BOLD, 14));
	    
	    batch_no = new JTextField();
	    batch_no.setBounds(24, 131, 434, 33);
	    layeredPane_2.add(batch_no);
	    batch_no.setColumns(10);
	    
	    quantity = new JComboBox();
	  
	    quantity.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		try {

    				String insertData="insert into invoice_table values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    				PreparedStatement ps=connection.prepareStatement(insertData);
    				ps.setString(1, product_name.getSelectedItem().toString());
    				ps.setString(2, batch_no.getText());
    				ps.setString(3, manufacturer.getText());
    				ps.setString(4, exp_date.getText());
    				ps.setString(5, mrp_price.getText());
    				ps.setString(6, discount_per.getText());
    				ps.setString(7, gst_per.getSelectedItem().toString());
    				ps.setString(8,quantity.getSelectedItem().toString());
    				ps.setString(9,invoice_date.getText());
    				ps.setString(10,invoice_no.getText());
    				ps.setString(11,customer_name.getSelectedItem().toString());
    				ps.setString(12,cust_mobileno.getText());
    				ps.setString(13,cus_add.getText());
    							
        			int inserted=ps.executeUpdate();
    				if(inserted>0)
    				{
    					
    					//JOptionPane.showMessageDialog(null, "Data inserted sucessfully");
    					

    					Object row[]=new Object[20];
    					
    					getFilledDataWithCalculation();
    		         	row[0]=i++;
    		         	
    		         	float mrp,quan,disc,gst;
    		         	disc=Float.parseFloat(discount_per.getText());
    					gst=Float.parseFloat(gst_per.getSelectedItem().toString());
    					mrp=Float.parseFloat(mrp_price.getText());
    				    quan=Float.parseFloat(quantity.getSelectedItem().toString());
    				    
    					row[1]=product_name.getSelectedItem().toString();
    					//row[2]=hsncode.getText();
    					row[2]=batch_no.getText();
    					row[3]=mrp_price.getText();
    					row[4]=quantity.getSelectedItem().toString();
    					////////////////////////////////////////////////////////??????????????????????????????????????????????????????///////////////////////////////////////////////
    					float discPerProduct;
    					discPerProduct=((mrp*quan)/100)*disc;
    					row[5]=gst_per.getSelectedItem().toString();
    					
    					row[6]=discPerProduct;
    					
    					float cgstPerProduct;
    			
    					cgstPerProduct=((((mrp*quan)-((mrp*quan)/100)*disc)*gst)/(100+gst))/2;
    					row[7]=cgstPerProduct;
    			
    					row[8]=cgstPerProduct;
    					
    					float igstPerProduct;
    					igstPerProduct=((((mrp*quan)-((mrp*quan)/100)*disc)*gst)/(100+gst));
    					row[9]=igstPerProduct;
    					/////////////////////////////////////////////////////////////??????????????????????????????????????????????????????///////////////////////////////////////////////
    					float totalPerProduct;
    				    totalPerProduct=mrp*quan;
    					row[10]=totalPerProduct;
    					
    					model_sale.addRow(row);
    					
    				}
    			} catch (Exception e2) {
    				// TODO: handle exception
    			}
	    	}
	    });
	    
	    quantity.setEditable(true);
	    quantity.setBounds(710, 221, 204, 33);
	    quantity.addItem(0.250);
	    quantity.addItem(0.500);
	    quantity.addItem(0.750);
	    quantity.addItem(1);
	    quantity.addItem(2);
	    quantity.addItem(3);
	    quantity.addItem(4);
	    quantity.addItem(5);
	    quantity.addItem(6);
	    quantity.addItem(7);
	    quantity.addItem(8);
	    quantity.addItem(9);
	    quantity.addItem(10);
	    
	    
	    layeredPane_2.add(quantity);
	    
	    JLabel lblYyyymmdd = new JLabel("(YYYY/MM/DD)");
	    lblYyyymmdd.setForeground(Color.RED);
	    lblYyyymmdd.setBounds(806, 96, 111, 33);
	    layeredPane_2.add(lblYyyymmdd);
		
		JLabel lblGrossValue = new JLabel("Gross Value");
		lblGrossValue.setBounds(469, 745, 137, 33);
		lblGrossValue.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lblGrossValue);
		
		JLabel lblTotalValues = new JLabel("Total Value");
		lblTotalValues.setBounds(983, 694, 180, 33);
		lblTotalValues.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lblTotalValues);
		
		gross_amt = new JTextField();
		gross_amt.setBounds(662, 745, 205, 34);
		gross_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		gross_amt.setFont(new Font("Dialog", Font.PLAIN, 13));
		gross_amt.setText("0");
		gross_amt.setColumns(10);
		panel.add(gross_amt);
		
		total_amt = new JTextField();
		total_amt.setBounds(1198, 695, 225, 33);
		total_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		total_amt.setFont(new Font("Dialog", Font.PLAIN, 13));
		total_amt.setText("0");
		total_amt.setColumns(10);
		panel.add(total_amt);
		
		JLabel lblDiscountValue = new JLabel("Discount Value");
		lblDiscountValue.setBounds(469, 699, 154, 33);
		lblDiscountValue.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lblDiscountValue);
		
		discount_amt = new JTextField();
		discount_amt.setBounds(662, 700, 205, 33);
		discount_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		discount_amt.setText("0");
		discount_amt.setFont(new Font("Dialog", Font.PLAIN, 13));
		discount_amt.setColumns(10);
		panel.add(discount_amt);
		
		JLabel lblCgstValue = new JLabel("CGST Value");
		lblCgstValue.setBounds(24, 690, 154, 26);
		lblCgstValue.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lblCgstValue);
		
		cgst = new JTextField();
		cgst.setBounds(179, 690, 205, 21);
		cgst.setText("0");
		cgst.setHorizontalAlignment(SwingConstants.RIGHT);
		cgst.setFont(new Font("Dialog", Font.PLAIN, 13));
		cgst.setColumns(10);
		panel.add(cgst);
		
		JLabel lblSgstValue = new JLabel("SGST Value");
		lblSgstValue.setBounds(24, 725, 154, 21);
		lblSgstValue.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lblSgstValue);
		
		sgst = new JTextField();
		sgst.setBounds(179, 725, 205, 21);
		sgst.setText("0");
		sgst.setHorizontalAlignment(SwingConstants.RIGHT);
		sgst.setFont(new Font("Dialog", Font.PLAIN, 13));
		sgst.setColumns(10);
		panel.add(sgst);
		
		JLabel lblIgstValue = new JLabel("IGST Value");
		lblIgstValue.setBounds(24, 758, 154, 21);
		lblIgstValue.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lblIgstValue);
		
		igst = new JTextField();
		igst.setBounds(179, 758, 205, 21);
		igst.setText("0");
		igst.setHorizontalAlignment(SwingConstants.RIGHT);
		igst.setFont(new Font("Dialog", Font.PLAIN, 13));
		igst.setColumns(10);
		panel.add(igst);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
				     File file = new File("/home/vinay/file.txt");

				     try{    
				    	 
				    	 int i,j,n=0,m=0;
				    	 double mrp,quan,cost,disc,gst,discPerProduct,cgstPerProduct,igstPerProduct;
				    	 
				    	 n=model_sale.getRowCount();
				    	 m=model_sale.getColumnCount();
				    	
				    	
				           FileWriter fw=new FileWriter("/home/vinay/Printout's/Receipts/Receipt("+invoice_no.getText()+").txt",true); 
				     
				     	  BufferedWriter bw = new BufferedWriter(fw);
				     	  PrintWriter pw = new PrintWriter(bw);
				           pw.println("Customer Name: "+customer_name.getSelectedItem().toString()+"\nCustomer Number: "
				     	               +cust_mobileno.getText());  
				           for(i=0;i<n;i++)
				           {
				        	  for(j=0;j<m-5;j++)
				        	  {
				        		  pw.println(model_sale.getColumnName(j) +" :- " + model_sale.getValueAt(i, j));
				        		  
				        		  
				        	  }
				        	  
				        	  //////////////////////////////////////////////////////////////////////////////?????????????????????????????????????///////////////////////////////////////
				        	  mrp=Float.parseFloat(model_sale.getValueAt(i, 3).toString());
				        	  quan=Float.parseFloat(model_sale.getValueAt(i, 4).toString());
				        	  disc=Float.parseFloat(model_sale.getValueAt(i, 6).toString());
				        	  gst=Float.parseFloat(model_sale.getValueAt(i, 5).toString());
				        	 
				        	  discPerProduct=((mrp*quan)/100)*disc;
				        	  cgstPerProduct=((((mrp*quan)-((mrp*quan)/100)*disc)*gst)/(100+gst))/2;
				        	  igstPerProduct=(((mrp*quan)-((mrp*quan)/100)*disc)*gst)/(100+gst);
				        	  cost=mrp*quan;
				      
				        	  pw.printf("Discount amt :- %2f ",Math.round(discPerProduct));
				        	  pw.printf("\nCGST amt :- %2f ",Math.round(cgstPerProduct));
				        	  pw.printf("\nSGST amt :- %2f ",cgstPerProduct);
				        	  pw.printf("\nIGST amt :- %2f ",igstPerProduct);
			                  /////////////////////////////////////////////////////////////////////////////////??????????????????????????????????//////////////////////////////////////////////////////
				        	  pw.println("\nCost :- "+cost);
				        	  pw.println();
				           }
				           pw.println("Discount Amount: "+discount_amt.getText());
				           pw.print("Total Amount: "+total_amt.getText());
				           pw.close();    
				          }catch(Exception e1){System.out.println(e1);}    
				          System.out.println("Success...");   
				          JOptionPane.showMessageDialog(null, "Print Sucessful");
				      
				     /*If file gets created then the createNewFile() 
				      * method would return true or if the file is 
				      * already present it would return false
				      */
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
		btnPrint.setBounds(1217, 753, 164, 33);
		panel.add(btnPrint);
		invoiceAutoIncrement();
		autoDateGeneration();
		productDropDown();
		customerDropDown();
	}
}
