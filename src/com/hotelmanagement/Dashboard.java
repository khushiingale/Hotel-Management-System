//Dashboard for Admin panel

package com.hotelmanagement;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;

public class Dashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard();
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
	public Dashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920,1080);
	//	setBounds(100, 100, 450, 300);
	//	setBounds(900, 500, 800, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel iconlabel;
        iconlabel = new JLabel("Logout");
        iconlabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		MainPage d = new MainPage();
				d.setVisible(true);
        	}
        });
        iconlabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        iconlabel.setForeground(new Color(255, 255, 255));
        iconlabel.setBounds(1426, 26, 83, 35);
        contentPane.add(iconlabel);
		
		JButton manageroombox = new JButton("Manage Room");
		manageroombox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageRooms m = new ManageRooms();
				m.setVisible(true);
			}
		});
		manageroombox.setFont(new Font("Tahoma", Font.BOLD, 18));
		manageroombox.setForeground(new Color(0, 0, 0));
		manageroombox.setBackground(new Color(255, 255, 255));
		manageroombox.setBounds(41, 103, 240, 59);
		contentPane.add(manageroombox);
		
		JButton bookings = new JButton("View Bookings");
		bookings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserBookingStatus b = new UserBookingStatus();
				b.setVisible(true);
				
			}
		});
		bookings.setBackground(new Color(255, 255, 255));
		bookings.setFont(new Font("Tahoma", Font.BOLD, 18));
		bookings.setBounds(41, 221, 240, 59);
		contentPane.add(bookings);
		
		JButton usermanagement = new JButton("User Management");
		usermanagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManagement u = new UserManagement();
				u.setVisible(true);
			}
		});
		usermanagement.setBackground(new Color(255, 255, 255));
		usermanagement.setFont(new Font("Tahoma", Font.BOLD, 18));
		usermanagement.setBounds(41, 357, 240, 59);
		contentPane.add(usermanagement);
		
		JButton billbtn = new JButton("Bill Management");
		billbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillManagement b = new BillManagement();
				b.setVisible(true);
			}
		});
		billbtn.setBackground(new Color(255, 255, 255));
		billbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		billbtn.setBounds(41, 492, 240, 59);
		contentPane.add(billbtn);
		
		JButton feedback = new JButton("Feedback");
		feedback.setBackground(new Color(128, 255, 0));
		feedback.setFont(new Font("Tahoma", Font.BOLD, 18));
		feedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Feedback fb = new Feedback();
				fb.setVisible(true);
			}
		});
		feedback.setBounds(41, 629, 240, 59);
		contentPane.add(feedback);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
		lblNewLabel.setBounds(-202, -29, 1783, 1052);
		contentPane.add(lblNewLabel);
	}
}
