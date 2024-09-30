//Bill management page for admin

package com.hotelmanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BillManagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel frame;
    private JTable table;
    private DefaultTableModel model;

    public static void main(String[] args) {
        BillManagement billFrame = new BillManagement();
        billFrame.setVisible(true);
    }

    public BillManagement() {
        setTitle("Bill Management");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame = new JPanel();
        frame.setBackground(new Color(204, 204, 255));
        setContentPane(frame);
        frame.setLayout(null);
        
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
        frame.add(lblNewLabel_1);

        JLabel title = new JLabel("Bill Management");
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setBounds(700, 30, 200, 30);
        frame.add(title);

        // Booking details table
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Booking ID");
        model.addColumn("User ID");
        model.addColumn("Room ID");
        model.addColumn("Check-In Date");
        model.addColumn("Check-Out Date");
        model.addColumn("Total Price");
        model.addColumn("Booking Status");
        model.addColumn("Payment Status");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(184, 79, 1207, 428);
        frame.add(scrollPane);

        JButton loadBtn = new JButton("Load Bookings");
        loadBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
        loadBtn.setBounds(242, 538, 260, 53);
        frame.add(loadBtn);

        JButton calcBillBtn = new JButton("Calculate Bill");
        calcBillBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        calcBillBtn.setBounds(610, 538, 260, 53);
        frame.add(calcBillBtn);

        JButton updatePaymentBtn = new JButton("Update Payment Status");
        updatePaymentBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        updatePaymentBtn.setBounds(996, 538, 260, 53);
        frame.add(updatePaymentBtn);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
        lblNewLabel.setBounds(-72, 0, 1612, 845);
        frame.add(lblNewLabel);

        // Action listener for Load Bookings button
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookings();
            }
        });

        // Action listener for Calculate Bill button
        calcBillBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalBill();
            }
        });

        // Action listener for Update Payment Status button
        updatePaymentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePaymentStatus();
            }
        });
    }

    // Method to establish a connection to the database
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
    }

    // Load booking data from the database
 // Load booking data from the database and calculate total price based on room price
    private void loadBookings() {
        model.setRowCount(0); // Clear previous data

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system","root","Khushi@123");
            String query = "SELECT * FROM bookings";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int roomId = rs.getInt("room_id");
                LocalDate checkInDate = rs.getDate("check_in_date").toLocalDate();
                LocalDate checkOutDate = rs.getDate("check_out_date").toLocalDate();
                String bookingStatus = rs.getString("status");
                String paymentStatus = rs.getString("payment_status");

                // Calculate the number of days between check-in and check-out
                long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

                // Fetch the room price from the rooms table using the room_id
                String roomQuery = "SELECT price FROM rooms WHERE room_id = ?";
                PreparedStatement roomPstmt = con.prepareStatement(roomQuery);
                roomPstmt.setInt(1, roomId);
                ResultSet roomRs = roomPstmt.executeQuery();

                double roomPrice = 0;
                if (roomRs.next()) {
                    roomPrice = roomRs.getDouble("price"); // Fetch the room price
                }

                // Calculate the total price based on room price and number of days
                double totalPrice = roomPrice * days;

                // Set the total price for the current booking in the table
                model.addRow(new Object[] {
                    bookingId,
                    rs.getInt("user_id"),
                    roomId,
                    checkInDate,
                    checkOutDate,
                    totalPrice, // Calculated total price
                    bookingStatus,
                    paymentStatus
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error loading bookings: " + ex.getMessage());
        }
    }

 // Calculate the total bill based on price from bookings table and the number of days
//    private void calculateTotalBill() {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(frame, "Please select a booking to calculate the bill.");
//            return;
//        }
//
//        int bookingId = (int) model.getValueAt(selectedRow, 0); // Get the booking ID
//        LocalDate checkInDate = ((java.sql.Date) model.getValueAt(selectedRow, 3)).toLocalDate();
//        LocalDate checkOutDate = ((java.sql.Date) model.getValueAt(selectedRow, 4)).toLocalDate();
//
//        // Calculate the number of days
//        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
//
//            // Fetch price from bookings table instead of rooms table
//            String query = "SELECT price FROM rooms WHERE room_id = ?";
//            PreparedStatement pstmt = con.prepareStatement(query);
//            pstmt.setInt(1, bookingId);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                double bookingPrice = rs.getDouble("price");
//
//                // Calculate the total price based on booking price
//                double totalPrice = bookingPrice * days;
//                model.setValueAt(totalPrice, selectedRow, 5); // Update the total price in the table
//
//                JOptionPane.showMessageDialog(frame, "The total bill for " + days + " days is: $" + totalPrice);
//            } else {
//                JOptionPane.showMessageDialog(frame, "Booking not found for booking ID: " + bookingId);
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(frame, "Error calculating total bill: " + ex.getMessage());
//        }
//    }

    private void calculateTotalBill() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a booking to calculate the bill.");
            return;
        }

        int bookingId = (int) model.getValueAt(selectedRow, 0);

        // Get the check-in and check-out dates as java.sql.Date and convert to LocalDate
        Object checkInDateObj = model.getValueAt(selectedRow, 3); // check-in date column
        Object checkOutDateObj = model.getValueAt(selectedRow, 4); // check-out date column

        LocalDate checkInDate;
        LocalDate checkOutDate;

        // Check if the dates are already LocalDate or java.sql.Date and convert accordingly
        if (checkInDateObj instanceof java.sql.Date) {
            checkInDate = ((java.sql.Date) checkInDateObj).toLocalDate();
        } else if (checkInDateObj instanceof LocalDate) {
            checkInDate = (LocalDate) checkInDateObj;
        } else {
            throw new IllegalArgumentException("Unsupported date type for check-in date");
        }

        if (checkOutDateObj instanceof java.sql.Date) {
            checkOutDate = ((java.sql.Date) checkOutDateObj).toLocalDate();
        } else if (checkOutDateObj instanceof LocalDate) {
            checkOutDate = (LocalDate) checkOutDateObj;
        } else {
            throw new IllegalArgumentException("Unsupported date type for check-out date");
        }

        // Calculate the number of days between check-in and check-out
        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

            // Fetch price from rooms table based on bookingId (or roomId, depending on your logic)
            String query = "SELECT price FROM rooms WHERE room_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookingId);  // Assuming bookingId or roomId is correctly passed
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double roomPrice = rs.getDouble("price");

                // Calculate the total price based on room price and the number of days
                double totalPrice = roomPrice * days;
                model.setValueAt(totalPrice, selectedRow, 5);  // Update the total price in the table

                JOptionPane.showMessageDialog(frame, "The total bill for " + days + " days is: $" + totalPrice);
            } else {
                JOptionPane.showMessageDialog(frame, "Room not found for booking ID: " + bookingId);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error calculating total bill: " + ex.getMessage());
        }
    }
    
    // Update payment status in the database
    private void updatePaymentStatus() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a booking to update the payment status.");
            return;
        }

        int bookingId = (int) model.getValueAt(selectedRow, 0);
        String currentPaymentStatus = (String) model.getValueAt(selectedRow, 7);

        if ("paid".equalsIgnoreCase(currentPaymentStatus)) {
            JOptionPane.showMessageDialog(frame, "This booking is already marked as 'paid'.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to update the payment status to 'paid'?", "Confirm Update", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.NO_OPTION) {
            return;
        }

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system","root","Khushi@123");
            String query = "UPDATE bookings SET payment_status = 'paid' WHERE booking_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Payment status updated to 'paid'.");
            loadBookings(); // Refresh the table

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error updating payment status: " + ex.getMessage());
        }
    }
}






