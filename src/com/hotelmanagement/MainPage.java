//Main Page of Project

package com.hotelmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					MainPage frame = new MainPage();
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
	public MainPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920,1080);
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JEditorPane dtrpnWelcome = new JEditorPane();
		dtrpnWelcome.setText("Welcome !!!");
		dtrpnWelcome.setBounds(1328, 128, 107, 19);
		contentPane.add(dtrpnWelcome);
		
		JButton loginbtn = new JButton("Login");
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login l = new Login();
				l.setVisible(true);
			}
		});
		loginbtn.setForeground(new Color(255, 255, 255));
		loginbtn.setBackground(new Color(128, 128, 64));
		loginbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		loginbtn.setBounds(1136, 114, 136, 49);
		contentPane.add(loginbtn);
		
		JButton signupbtn = new JButton("Sign Up");
		signupbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Signup s = new Signup();
				s.setVisible(true);
			}
		});
		signupbtn.setForeground(new Color(255, 255, 255));
		signupbtn.setBackground(new Color(128, 128, 64));
		signupbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		signupbtn.setBounds(978, 114, 131, 49);
		contentPane.add(signupbtn);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\Untitled design (1).png"));
		lblNewLabel.setBounds(-134, -40, 1843, 1029);
		contentPane.add(lblNewLabel);
	}
}
