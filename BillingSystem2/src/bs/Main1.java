package bs;


import java.awt.EventQueue;

import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;

public class Main1 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	Connection connection=null;
	private JLabel lblMasterKey;
	private JPasswordField master_key;
	private JLayeredPane layeredPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main1 frame = new Main1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	ResultSet set_com;
	private JLabel lblPressEnter;
	
    private void initialize() {
		
		//code for database connection
		
		     try {
				Class.forName("org.h2.Driver");
				connection=DriverManager.getConnection("jdbc:h2:~/test","sa","");
				
				//JOptionPane.showMessageDialog(null,"Connection Sucesssfull");
				
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
}
	
	public void createTableNew()
	{ // this method only call when there not any table exists in database named USERTABLE
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			ResultSet set=dmd.getTables(null,null,"USERTABLE",null);
			         if(set.next())
			         {
			        	 // if table exists do noting 
			         }
			         else
			         {
			        	 String create_table="create table USERTABLE ("
			        			 + "username varchar2(30),"
			        			 + "password varchar2(15),"
			        			 + "email varchar2(30))";
			        	 
			        	 PreparedStatement statement = connection.prepareStatement(create_table);
			        	 statement.executeUpdate();
			        	 
			        	 /*String owner="create table OWNER (name varchar2(30),number varchar2(11))";
			        	 PreparedStatement statement2=connection.prepareStatement(owner);
			        	 statement2.executeUpdate();*/
			        	 
			        	 //JOptionPane.showMessageDialog(null, "Table Created Sucessfully");     	
			         }	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Create the frame.
	 */
	boolean check_email(String email){
		if (email.contains("@gmail.com")){
			return true;
		}
		else
			return false;
	}

	public Main1() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(600, 300, 700, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground"), UIManager.getColor("List.foreground")));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		initialize();
		
		
		lblMasterKey = new JLabel("Master Key");
		lblMasterKey.setFont(new Font("Dialog", Font.BOLD, 16));
		lblMasterKey.setBounds(67, 33, 162, 29);
		contentPane.add(lblMasterKey);
		
		master_key = new JPasswordField();
		master_key.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						Statement st=connection.createStatement();
						set_com=st.executeQuery("select master_key from owner");
						while(set_com.next())
						{
							//JOptionPane.showMessageDialog(null, set_com.getString(1));
							if(master_key.getText().equals(set_com.getString(1)))
							{
								layeredPane.setVisible(true);
							}
							else {
								JOptionPane.showMessageDialog(null, "You Entered Wrong Master Key");
							}
								
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}	
			}
		});
		master_key.setBounds(331, 31, 269, 35);
		contentPane.add(master_key);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(56, 121, 566, 444);
		contentPane.add(layeredPane);
		layeredPane.setVisible(false);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(12, 89, 162, 29);
		layeredPane.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		
		JLabel lblNewLabel_2 = new JLabel("Confirm Password:");
		lblNewLabel_2.setBounds(13, 145, 193, 35);
		layeredPane.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 16));
		
		JLabel lblNewLabel_3 = new JLabel("Email ID:");
		lblNewLabel_3.setBounds(12, 208, 183, 35);
		layeredPane.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 16));
		
		textField_3 = new JTextField();
		textField_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					try {
						String insert_data="insert into USERTABLE values(?,?,?)";
						PreparedStatement statement = connection.prepareStatement(insert_data);
						statement.setString(1, textField.getText());
						//statement.setString(2, passwordField.getText());
						//statement.setString(3, passwordField_1.getText());
						if (passwordField.getText().toString().equals(passwordField_1.getText().toString()))
							statement.setString(2, passwordField.getText());
						else
						{
							JOptionPane.showMessageDialog(null, "Passwords do not match try again");
							passwordField.setText("");
							passwordField_1.setText("");
						}
						if(check_email(textField_3.getText()))
							statement.setString(3, textField_3.getText());
						else
							JOptionPane.showMessageDialog(null, "The Email is not valid enter valid email(eg. XYZ@gmail.com)");
						//statement.setString(5, date_field.getText());
						//statement.setString(6, mobile_field.getText());
						
						int data_entered=statement.executeUpdate();
						
						if(data_entered>0) //if data inserted successfully than it returns 1
						{
							//JOptionPane.showMessageDialog(null,"Data Inserted Sucessfully");
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
		textField_3.setBounds(276, 209, 269, 35);
		layeredPane.add(textField_3);
		textField_3.setColumns(10);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(276, 146, 269, 35);
		layeredPane.add(passwordField_1);
		passwordField = new JPasswordField();
		passwordField.setBounds(276, 87, 269, 35);
		layeredPane.add(passwordField);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBounds(191, 321, 162, 54);
		layeredPane.add(btnSubmit);
        btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//dispose();
				try {
					String insert_data="insert into USERTABLE values(?,?,?)";
					PreparedStatement statement = connection.prepareStatement(insert_data);
					statement.setString(1, textField.getText());
					//statement.setString(2, passwordField.getText());
					//statement.setString(3, passwordField_1.getText());
					if (passwordField.getText().toString().equals(passwordField_1.getText().toString()))
						statement.setString(2, passwordField.getText());
					else
					{
						JOptionPane.showMessageDialog(null, "Passwords do not match try again");
						passwordField.setText("");
						passwordField_1.setText("");
					}
					if(check_email(textField_3.getText()))
						statement.setString(3, textField_3.getText());
					else
						JOptionPane.showMessageDialog(null, "The Email is not valid enter valid email(eg. XYZ@gmail.com)");
					//statement.setString(5, date_field.getText());
					//statement.setString(6, mobile_field.getText());
					
					int data_entered=statement.executeUpdate();
					
					if(data_entered>0) //if data inserted successfully than it returns 1
					{
						//JOptionPane.showMessageDialog(null,"Data Inserted Sucessfully");
					}
					else {
						JOptionPane.showMessageDialog(null,"Unable to insert data");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				dispose();
				
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("UserName:");
		lblNewLabel.setBounds(12, 36, 162, 29);
		layeredPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		
		textField = new JTextField();
		textField.setBounds(276, 34, 269, 35);
		layeredPane.add(textField);
		textField.setColumns(10);
		
		lblPressEnter = new JLabel("Press Enter ....");
		lblPressEnter.setFont(new Font("Dialog", Font.BOLD, 15));
		lblPressEnter.setForeground(Color.RED);
		lblPressEnter.setBounds(475, 78, 125, 29);
		contentPane.add(lblPressEnter);
		
		createTableNew();
	}
}