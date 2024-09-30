//user management page for admin

package com.hotelmanagement;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserManagement frame = new UserManagement();
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
	public UserManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920, 1080);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Back ");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Dashboard d = new Dashboard();
				d.setVisible(true);
			}
		});
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(1422, 28, 83, 35);
		contentPane.add(lblNewLabel_1);

		// Scroll Pane and Table Setup
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 94, 804, 466);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		table.setBackground(new Color(255, 255, 255));
		tableModel.addColumn("User ID");
		tableModel.addColumn("Username");
		tableModel.addColumn("Contact");
		tableModel.addColumn("Question");
		tableModel.addColumn("Answer");
		tableModel.addColumn("Status");
		scrollPane.setViewportView(table);

		// Load the initial user data
		loadUserData();

		// Activate Button
		JButton activatebtn = new JButton("Activate");
		activatebtn.setBackground(new Color(128, 255, 0));
		activatebtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		activatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					int userId = (int) tableModel.getValueAt(selectedRow, 0);
					updateUserStatus(userId, true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a user to activate.");
				}
			}
		});
		activatebtn.setBounds(117, 630, 169, 51);
		contentPane.add(activatebtn);

		// Deactivate Button
		JButton deactivatebtn = new JButton("Deactivate");
		deactivatebtn.setForeground(new Color(255, 255, 255));
		deactivatebtn.setBackground(new Color(255, 0, 0));
		deactivatebtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		deactivatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					int userId = (int) tableModel.getValueAt(selectedRow, 0);
					updateUserStatus(userId, false);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a user to deactivate.");
				}
			}
		});
		deactivatebtn.setBounds(407, 630, 169, 51);
		contentPane.add(deactivatebtn);

		// Refresh Button
		JButton refreshbtn = new JButton("Refresh");
		refreshbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		refreshbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadUserData();
			}
		});
		refreshbtn.setBounds(666, 630, 169, 51);
		contentPane.add(refreshbtn);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
		lblNewLabel.setBounds(0, -45, 1540, 880);
		contentPane.add(lblNewLabel);
	}

	// Method to load user data from the database
	private void loadUserData() {
		// Clear the table before loading data
		tableModel.setRowCount(0);


		try 
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
			
			String sql = "SELECT user_id, username, contact, question, answer, status FROM users";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			// Populate the table with user data
			while (rs.next()) {
				int userId = rs.getInt("user_id");
				String username = rs.getString("username");
				String contact = rs.getString("contact");
				String question = rs.getString("question");
				String answer = rs.getString("answer");
				String status = rs.getBoolean("status") ? "Active" : "Deactivated";
				tableModel.addRow(new Object[] { userId, username, contact, question, answer, status });
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		}
	}

	// Method to update user status in the database
	// Method to update user status in the database
	private void updateUserStatus(int userId, boolean activate) {

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

	        // SQL query to update the user status
	        String sql = "UPDATE users SET status = ? WHERE user_id = ?";
	        PreparedStatement pstmt = con.prepareStatement(sql);

	        // Set the parameters for the query
	        pstmt.setBoolean(1, activate);
	        pstmt.setInt(2, userId);

	        // Execute the update statement
	        int rowsUpdated = pstmt.executeUpdate();  // Use executeUpdate() instead of executeQuery()
	        if (rowsUpdated > 0) {
	            String message = activate ? "User activated successfully!" : "User deactivated successfully!";
	            JOptionPane.showMessageDialog(null, message);
	            loadUserData();  // Refresh the table after update
	        }

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
	    }
	}
}








































