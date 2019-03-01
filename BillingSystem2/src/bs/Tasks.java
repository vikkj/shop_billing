package bs;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Tasks extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tasks frame = new Tasks();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tasks() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 300, 700, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(188, 143, 143));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Generate Invoice");
		btnNewButton_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
					new GenerateInvoice().setVisible(true);
				}
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GenerateInvoice().setVisible(true);
			}
		});
		btnNewButton_2.setFont(new Font("Dialog", Font.BOLD, 15));
		btnNewButton_2.setBounds(509, 391, 179, 127);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("History");
		btnNewButton_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
					new History().setVisible(true);
				}
			}
		});
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new History().setVisible(true);
			}
		});
		btnNewButton_3.setForeground(SystemColor.activeCaption);
		btnNewButton_3.setFont(new Font("Dialog", Font.BOLD, 15));
		btnNewButton_3.setBounds(12, 391, 179, 127);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Product Details");
		btnNewButton_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
				new ProductDetails().setVisible(true);
				}
			}
		});
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductDetails().setVisible(true);
			}
		});
		btnNewButton_4.setFont(new Font("Dialog", Font.BOLD, 15));
		btnNewButton_4.setBounds(509, 94, 179, 127);
		contentPane.add(btnNewButton_4);
		
		JButton btnCutomerDetails = new JButton("Customer Details");
		btnCutomerDetails.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
					new CustomerDetails().setVisible(true);
				}
			}
		});
		btnCutomerDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CustomerDetails().setVisible(true);
			}
		});
		btnCutomerDetails.setFont(new Font("Dialog", Font.BOLD, 15));
		btnCutomerDetails.setBounds(12, 94, 179, 127);
		contentPane.add(btnCutomerDetails);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/home/vinay/eclipse-workspace/BillingSystem2/images/images.jpeg"));
		lblNewLabel.setBounds(209, 203, 275, 200);
		contentPane.add(lblNewLabel);
	}
}