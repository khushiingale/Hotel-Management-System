//Sign up Page for project

package com.hotelmanagement;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

public class Signup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel signup;
	private JTextField answerbox;
	private JPasswordField pwdbox;
	private JTextField usernamebox;
	private JTextField contactbox;
	//this.hide.setVisible(False);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Signup frame = new Signup();
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
	public Signup() {
		setTitle("Sign Up");
		setSize(1920,1080);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//	setBounds(100, 600, 900, 600);
	//	setBounds(900, 500, 800, 750);
		signup = new JPanel();
		signup.setBackground(new Color(240, 240, 240));
		//signup.setBackground(new Color(840, 840, 840));
		signup.setBorder(new EmptyBorder(15, 15, 15, 15));

		setContentPane(signup);
		signup.setLayout(null);
		
		JCheckBox show = new JCheckBox("Show Password");
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(show.isSelected())
				{
					pwdbox.setEchoChar((char)0);
				}
				else
				{
					pwdbox.setEchoChar('*');
				}
			}
		});
		show.setBounds(826, 173, 123, 21);
		signup.add(show);
		
		JComboBox questionbox = new JComboBox();
		questionbox.setFont(new Font("Tahoma", Font.BOLD, 18));
		questionbox.setModel(new DefaultComboBoxModel(new String[] {"Security Questions", "Your Birth Place", "First School Attended", "Your Favourite Colour", "Your Favourite Food", "Do you ever had pet"}));
		questionbox.setBounds(508, 304, 297, 35);
		signup.add(questionbox);
		
		JLabel answer = new JLabel("Answer");
		answer.setFont(new Font("Tahoma", Font.BOLD, 18));
		answer.setBounds(186, 376, 158, 31);
		signup.add(answer);
		
		JLabel question = new JLabel("Security Question");
		question.setFont(new Font("Tahoma", Font.BOLD, 18));
		question.setBounds(186, 306, 166, 31);
		signup.add(question);
		
		JLabel password = new JLabel("Set Password");
		password.setFont(new Font("Tahoma", Font.BOLD, 18));
		password.setBounds(186, 166, 131, 29);
		signup.add(password);
		
		answerbox = new JTextField();
		answerbox.setBounds(508, 378, 297, 33);
		signup.add(answerbox);
		answerbox.setColumns(10);
		
		JButton submit = new JButton("Submit");
		submit.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		submit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String contact = contactbox.getText();
		            
		            // Regular expression to check if contact contains exactly 10 digits
		            String regex = "\\d{10}"; 
		            
		            if (!contact.matches(regex)) {
		                JOptionPane.showMessageDialog(null, "Please enter a valid 10-digit contact number.");
		                return; // Stop the process if validation fails
		            }

		            // If validation passes, proceed with database insertion
		            String q = (String) questionbox.getSelectedItem();
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system","root","Khushi@123");
		            String query = "INSERT INTO users(username, password, contact, question, answer) VALUES(?,?,?,?,?)";
		            PreparedStatement ps = con.prepareStatement(query);
		            ps.setString(1, usernamebox.getText());
		            ps.setString(2, new String(pwdbox.getPassword()));
		            ps.setString(3, contact);
		            ps.setString(4, q);
		            ps.setString(5, answerbox.getText());
		            int i = ps.executeUpdate();
		            JOptionPane.showMessageDialog(submit, i + " record added successfully");
		            
		            ps.close();
		            con.close();
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
		    }
		});

		
		
		
//		submit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try 
//				{
//					String q = (String) questionbox.getSelectedItem();
//					Class.forName("com.mysql.cj.jdbc.Driver");
//					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system","root","Khushi@123");
//					String query = "INSERT into users(username,password,contact,question,answer) VALUES(?,?,?,?,?)";
//					PreparedStatement ps = con.prepareStatement(query);
//					ps.setString(1,usernamebox.getText());
//					ps.setString(2,pwdbox.getText());
//					ps.setString(3,contactbox.getText());
//					ps.setString(4,q);
//					ps.setString(5,answerbox.getText());
//					int i=ps.executeUpdate();
//					JOptionPane.showMessageDialog(submit, i+"record added Successfully");
//					ps.close();
//					con.close();
//				} 
//				catch (Exception e1) 
//				{
//					
//					e1.printStackTrace();
//				}
//			}
//		});
		submit.setBounds(76, 498, 166, 50);
		signup.add(submit);
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answerbox.setText("");
				pwdbox.setText("");
				usernamebox.setText("");
				contactbox.setText("");
				questionbox.setSelectedIndex(0);
				

			}
		});
		reset.setFont(new Font("Tahoma", Font.BOLD, 18));
		reset.setBounds(403, 498, 166, 50);
		signup.add(reset);
		
		JButton login = new JButton("Login");
		login.setFont(new Font("Tahoma", Font.BOLD, 18));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login l = new Login();
				l.setVisible(true);
			}
		});
		login.setBounds(720, 498, 166, 50);
		signup.add(login);
		
		JLabel lblNewLabel1 = new JLabel("Already a Existing User? ");
		lblNewLabel1.setForeground(new Color(255, 69, 0));
		lblNewLabel1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel1.setBackground(new Color(0, 0, 0));
		lblNewLabel1.setBounds(730, 546, 158, 31);
		signup.add(lblNewLabel1);
		
		JLabel account = new JLabel("Create New Account !!!!!");
		account.setFont(new Font("Tahoma", Font.BOLD, 25));
		account.setBounds(186, 10, 325, 50);
		
		signup.add(account);
		
		pwdbox = new JPasswordField();
		pwdbox.setBounds(508, 168, 297, 31);
		signup.add(pwdbox);
		
//		JLabel lblNewLabel = new JLabel("New label");
//		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Desktop\\background.png"));
//		lblNewLabel.setBounds(0, 0, 786, 713);
//		signup.add(lblNewLabel);
		
		JLabel username = new JLabel("Username");
		username.setFont(new Font("Tahoma", Font.BOLD, 18));
		username.setBounds(186, 96, 166, 31);
		signup.add(username);
		
		JLabel contact = new JLabel("Contact Number");
		contact.setFont(new Font("Tahoma", Font.BOLD, 18));
		contact.setBounds(186, 239, 158, 31);
		signup.add(contact);
		
		usernamebox = new JTextField();
		usernamebox.setBounds(508, 99, 297, 31);
		signup.add(usernamebox);
		usernamebox.setColumns(10);
		
		contactbox = new JTextField();
		contactbox.setBounds(508, 242, 297, 32);
		signup.add(contactbox);
		contactbox.setColumns(10);
		
		JLabel bg = new JLabel("");
		bg.setIcon(new ImageIcon("C:\\Users\\HP\\Desktop\\Images\\bg.png"));
		bg.setBounds(0, 0, 1540, 880);
		signup.add(bg);
	
		

	}
}
