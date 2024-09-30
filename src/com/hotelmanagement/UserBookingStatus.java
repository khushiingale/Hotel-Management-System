//Checking booking status of user , this page is for admin

package com.hotelmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

public class UserBookingStatus extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel bookingTableModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserBookingStatus frame = new UserBookingStatus();
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
    public UserBookingStatus() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Back");
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		Dashboard d = new Dashboard();
				d.setVisible(true);
        	}
        });
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(1423, 26, 83, 35);
        contentPane.add(lblNewLabel_1);
        

        // Initialize table model
        bookingTableModel = new DefaultTableModel(new String[]{"Booking ID", "Room Number", "Check-in Date", "Check-out Date", "Status"}, 0);
        table = new JTable(bookingTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(177, 74, 1141, 550);
        contentPane.add(scrollPane);

        loadData();

        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.setFont(new Font("Tahoma", Font.BOLD, 24));
        btnCheckout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int bookingId = (int) bookingTableModel.getValueAt(selectedRow, 0);
                    updateBookingStatus(bookingId, "checked-out");
                    loadData(); // Refresh the table
                }
            }
        });
        btnCheckout.setBounds(177, 678, 182, 51);
        contentPane.add(btnCheckout);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setForeground(new Color(255, 255, 255));
        btnCancel.setBackground(new Color(255, 0, 0));
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 24));
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int bookingId = (int) bookingTableModel.getValueAt(selectedRow, 0);
                    updateBookingStatus(bookingId, "cancelled");
                    loadData(); // Refresh the table
                }
            }
        });
        btnCancel.setBounds(495, 678, 182, 51);
        contentPane.add(btnCancel);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
        lblNewLabel.setBounds(-140, -20, 1670, 865);
        contentPane.add(lblNewLabel);
    }

    private void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
            String query = "SELECT b.booking_id, r.room_id, b.check_in_date, b.check_out_date, b.status " +
                           "FROM bookings b JOIN rooms r ON b.room_id = r.room_id";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Clear existing data
            bookingTableModel.setRowCount(0);

            // Populate the table model with data from ResultSet
            while (rs.next()) {
                bookingTableModel.addRow(new Object[]{
                    rs.getInt("booking_id"),
                    rs.getInt("room_id"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date"),
                    rs.getString("status")
                });
            }

            ps.close();
            con.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private void updateBookingStatus(int bookingId, String status) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
            String query = "UPDATE bookings SET status = ? WHERE booking_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
