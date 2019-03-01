package bs;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class MyMainL {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyMainL window = new MyMainL();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame1;
	private JTextField validate_username;
	private JTextField name_field;
	private JPanel panel;
	private JPasswordField validate_password;
	/**
	 * Create the application.
	 */
	public MyMainL() {
		
		
		initialize();
	}

	Connection connection=null;
	private JTextField textField;
	private JPasswordField master_key;
	private JPasswordField master_keyc;
	
	/**
	 * Initialize the contents of the frame.
	 */
  
	
	public void createTableNew()
	{ // this method only call when there not any table exists in database named USERTABLE
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			ResultSet set=dmd.getTables(null,null,"OWNER",null);
			         if(set.next())
			         {
			        	 // if table exists do noting 
			         }
			         else
			         {
			        	 /*String create_table="create table USERTABLE ("
			        			 + "name varchar2(30),"
			        			 + "username varchar2(20),"
			        			 + "password varchar2(15),"
			        			 + "organization varchar2(40),"
			        			 + "dateofbirth varchar2(12), mobile "
			        			 + "varchar2(11))";
			        	 
			        	 PreparedStatement statement = connection.prepareStatement(create_table);
			        	 statement.executeUpdate();*/
			        	 
			        	 String owner="create table OWNER (name varchar2(30),number varchar2(11),master_key varchar2(50))";
			        	 PreparedStatement statement2=connection.prepareStatement(owner);
			        	 statement2.executeUpdate();
			        	 
			        	// JOptionPane.showMessageDialog(null, "Table Created Sucessfully");
			        	        	
			         }
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	boolean check()
	{
		try {
			String validate="select * from OWNER";
			PreparedStatement statement=connection.prepareStatement(validate);
			//statement.setString(1, validate_username.getText());
			//statement.setString(2, validate_password.getText());
			ResultSet set=statement.executeQuery(); //to retrieve the data use resultset (line no 76)
			
			if(set.next())  //to check username and password only once
			{
				//JOptionPane.showMessageDialog(null, "Login Sucessfull");
				//new Tasks().setVisible(true);
				return true;
			}
			
				//JOptionPane.showMessageDialog(null, "Invalid login credaintal");
				return false;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}
	
	private void initialize() {
		 try {
				Class.forName("org.h2.Driver");
				connection=DriverManager.getConnection("jdbc:h2:~/test","sa","");
				
				//JOptionPane.showMessageDialog(null,"Connection Sucesssfull");
				
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(245, 255, 250));
		frame.setBounds(200, 100, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main1 main=new Main1();
				main.setVisible(true);
				
			}
			
		});
		btnNewButton.setBounds(100, 258, 189, 58);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setForeground(UIManager.getColor("ColorChooser.foreground"));
		lblNewLabel_1.setBounds(912, 234, 101, 33);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 17));
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(UIManager.getColor("ColorChooser.foreground"));
		lblNewLabel_2.setBounds(912, 279, 101, 33);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 17));
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Login");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
				try {
					String validate="select * from USERTABLE where username=? and password=?";
					PreparedStatement statement=connection.prepareStatement(validate);
					statement.setString(1, validate_username.getText());
					statement.setString(2, validate_password.getText());
					ResultSet set=statement.executeQuery(); //to retrieve the data use resultset (line no 76)
					
					if(set.next())  //to check username and password only once
					{
						JOptionPane.showMessageDialog(null, "Login Sucessfull");
						new Tasks().setVisible(true);
					}else
					{
						JOptionPane.showMessageDialog(null, "Invalid login credaintal");
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(1188, 325, 152, 58);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_3 = new JLabel("Already User..");
		lblNewLabel_3.setForeground(UIManager.getColor("ColorChooser.foreground"));
		lblNewLabel_3.setBounds(902, 177, 298, 45);
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 20));
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New User");
		lblNewLabel_4.setForeground(UIManager.getColor("FormattedTextField.caretForeground"));
		lblNewLabel_4.setBounds(116, 181, 114, 58);
		lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD, 20));
		frame.getContentPane().add(lblNewLabel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 406, 349, -237);
		frame.getContentPane().add(scrollPane);
		
		
		panel = new JPanel();
		panel.setToolTipText("");
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		if(check())
		{
			panel.setVisible(false);
		}
		else
			panel.setVisible(true);
		// make variable global
		panel.setBounds(100, 468, 544, 345);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("OWNER");
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel_5.setBounds(12, 28, 100, 43);
		panel.add(lblNewLabel_5);
		
		name_field = new JTextField();
		name_field.setBounds(225, 28, 283, 43);
		panel.add(name_field);
		name_field.setColumns(10);
		
		JButton create_account = new JButton("Create");
		create_account.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					int data_entered=0;
					String insert_data="insert into OWNER values(?,?,?)";
					PreparedStatement statement = connection.prepareStatement(insert_data);
					statement.setString(1, name_field.getText());
					statement.setString(2, textField.getText());
			
				
					if (master_key.getText().equals(master_keyc.getText())) {
						statement.setString(3, master_key.getText());
						data_entered=statement.executeUpdate();
					} else {

						JOptionPane.showMessageDialog(null, "Master Keys Don't Match Please Enter Again");
						master_key.setText("");
						master_keyc.setText("");
						
					}
							
					if(data_entered>0) //if data inserted successfully than it returns 1
					{
						//JOptionPane.showMessageDialog(null,"Data Inserted Sucessfully");
						panel.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null,"Unable to insert data");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		create_account.setBounds(225, 277, 215, 50);
		panel.add(create_account);
		
		textField = new JTextField();
		textField.setBounds(225, 83, 283, 43);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterMasterKey = new JLabel("Enter Master Key");
		lblEnterMasterKey.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEnterMasterKey.setBounds(12, 138, 157, 43);
		panel.add(lblEnterMasterKey);
		
		JLabel lblConfirmMasterKey = new JLabel("Confirm Master Key");
		lblConfirmMasterKey.setFont(new Font("Dialog", Font.BOLD, 14));
		lblConfirmMasterKey.setBounds(12, 193, 176, 43);
		panel.add(lblConfirmMasterKey);
		
		JLabel lblMobileNumber = new JLabel("Mobile Number");
		lblMobileNumber.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMobileNumber.setBounds(12, 83, 157, 43);
		panel.add(lblMobileNumber);
		
		master_key = new JPasswordField();
		master_key.setBounds(225, 138, 283, 43);
		panel.add(master_key);
		
		master_keyc = new JPasswordField();
		master_keyc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					try {
						int data_entered=0;
						String insert_data="insert into OWNER values(?,?,?)";
						PreparedStatement statement = connection.prepareStatement(insert_data);
						statement.setString(1, name_field.getText());
						statement.setString(2, textField.getText());
					
						if (master_key.getText().equals(master_keyc.getText())) {
							statement.setString(3, master_key.getText());
							data_entered=statement.executeUpdate();
						} else {

							JOptionPane.showMessageDialog(null, "Master Keys Don't Match Please Enter Again");
							master_key.setText("");
							master_keyc.setText("");
							
						}
								
						if(data_entered>0) //if data inserted successfully than it returns 1
						{
							//JOptionPane.showMessageDialog(null,"Data Inserted Sucessfully");
							panel.setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null,"Unable to insert data");
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		master_keyc.setBounds(225, 193, 283, 43);
		panel.add(master_keyc);
			
		validate_password = new JPasswordField();
		validate_password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
				{
			
				
				try {
					String validate="select * from USERTABLE where username=? and password=?";
					PreparedStatement statement=connection.prepareStatement(validate);
					statement.setString(1, validate_username.getText());
					statement.setString(2, validate_password.getText());
					ResultSet set=statement.executeQuery(); //to retrieve the data use resultset (line no 76)
					
					if(set.next())  //to check username and password only once
					{
						JOptionPane.showMessageDialog(null, "Login Sucessfull");
						new Tasks().setVisible(true);
					}else
					{
						JOptionPane.showMessageDialog(null, "Invalid login credaintal");
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
		});
		validate_password.setBounds(1065, 280, 273, 33);
		frame.getContentPane().add(validate_password);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_1.setBounds(883, 159, 507, 293);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		validate_username = new JTextField();
		validate_username.setBounds(183, 75, 273, 33);
		panel_1.add(validate_username);
		validate_username.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_2.setBounds(100, 190, 146, 45);
		frame.getContentPane().add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_3.setBounds(355, 24, 728, 81);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblSupermarketBillingSystem = new JLabel("SUPERMARKET BILLING SYSTEM");
		lblSupermarketBillingSystem.setFont(new Font("Abyssinica SIL", Font.BOLD, 35));
		lblSupermarketBillingSystem.setBounds(61, 12, 636, 57);
		panel_3.add(lblSupermarketBillingSystem);
		
		JButton btnContactUs = new JButton("Contact us");
		btnContactUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Vinay Itankar :- 917038426278 \n Vikram Jaiswal :- 917038426278 \n Sayali Borkar :- 91875869348 \n Aditya Deshpande :- 91785465895");
			}
		});
		btnContactUs.setBounds(1297, 755, 146, 58);
		frame.getContentPane().add(btnContactUs);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/home/vinay/eclipse-workspace/BillingSystem2/images/_STU0510.jpg"));
		lblNewLabel.setBounds(0, -12, 1500, 882);
		frame.getContentPane().add(lblNewLabel);
		createTableNew();
		
	}
}