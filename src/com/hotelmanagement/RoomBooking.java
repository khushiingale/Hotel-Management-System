//room booking page for user

package com.hotelmanagement;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomBooking extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable roomTable;
    private DefaultTableModel roomTableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RoomBooking frame = new RoomBooking();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RoomBooking() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(128, 128, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel NewLabel_1 = new JLabel("Back");
        NewLabel_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		Registration d = new Registration();
				d.setVisible(true);
        	}
        });
        
        JLabel lblNewLabel_2 = new JLabel("Logout");
        lblNewLabel_2.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		MainPage mp = new MainPage();
        		mp.setVisible(true);
        	}
        });
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setBounds(1423, 71, 83, 20);
        contentPane.add(lblNewLabel_2);
        NewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
        NewLabel_1.setForeground(new Color(255, 255, 255));
        NewLabel_1.setBounds(1423, 26, 83, 35);
        contentPane.add(NewLabel_1);
        
        JLabel lblNewLabel_1 = new JLabel("Any Feedback");
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		UserFeedback f = new UserFeedback();
        		f.setVisible(true);
        	}
        });
        lblNewLabel_1.setForeground(new Color(0, 0, 139));
        lblNewLabel_1.setBackground(new Color(128, 0, 0));
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel_1.setBounds(784, 705, 207, 48);
        contentPane.add(lblNewLabel_1);

        // Room Filter Options
        JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{"Select Bed Type", "Single Bed", "Double Bed"});
        roomTypeComboBox.setBackground(new Color(255, 255, 255));
        roomTypeComboBox.setFont(new Font("Tahoma", Font.BOLD, 18));
        roomTypeComboBox.setBounds(30, 30, 207, 61);
        contentPane.add(roomTypeComboBox);

        JTextField priceRangeField = new JTextField();
        priceRangeField.setBounds(306, 30, 207, 61);
        contentPane.add(priceRangeField);

        JButton filterButton = new JButton("Filter Rooms");
        filterButton.setBackground(new Color(128, 255, 128));
        filterButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        filterButton.setBounds(597, 30, 207, 61);
        contentPane.add(filterButton);

        // Room Table Setup
        JScrollPane roomScrollPane = new JScrollPane();
        roomScrollPane.setBounds(30, 184, 1045, 453);
        contentPane.add(roomScrollPane);

        roomTableModel = new DefaultTableModel();
        roomTable = new JTable(roomTableModel);
        roomTableModel.addColumn("Room ID");
        roomTableModel.addColumn("Room Number");
        roomTableModel.addColumn("Room Type");
        roomTableModel.addColumn("Bed Type");
        roomTableModel.addColumn("Price");
        roomTableModel.addColumn("Availability");
        roomScrollPane.setViewportView(roomTable);

        // Load Room Data
        loadRoomData();

        // Book Room Button
        JButton bookRoomButton = new JButton("Book Room");
        bookRoomButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        bookRoomButton.setBounds(30, 684, 192, 77);
        contentPane.add(bookRoomButton);

        bookRoomButton.addActionListener(e -> {
            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow != -1) {
                int roomId = (int) roomTableModel.getValueAt(selectedRow, 0);
                // Implement booking logic (prompt for dates, etc.)
                bookRoom(roomId);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a room to book.");
            }
        });

        filterButton.addActionListener(e -> {
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String priceRange = priceRangeField.getText();
            filterRoomData(roomType, priceRange);
        });

        // Refresh Button to reload room data
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        refreshButton.setBounds(337, 692, 192, 61);
        contentPane.add(refreshButton);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
        lblNewLabel.setBounds(0, 0, 1540, 845);
        contentPane.add(lblNewLabel);
        
        refreshButton.addActionListener(e -> loadRoomData());
    }

    // Method to load all available room data
    private void loadRoomData() {
        roomTableModel.setRowCount(0);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
            String sql = "SELECT room_id, room_number, room_type, bed, price, availability FROM rooms WHERE availability = TRUE";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                String roomNumber = rs.getString("room_number");
                String roomType = rs.getString("room_type");
                String bedType = rs.getString("bed");
                double price = rs.getDouble("price");
                boolean availability = rs.getBoolean("availability");
                roomTableModel.addRow(new Object[]{roomId, roomNumber, roomType, bedType, price, availability ? "Available" : "Not Available"});
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

     
    private void filterRoomData(String roomType, String priceRange) {
      roomTableModel.setRowCount(0); // Clear the table

      try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

          // Construct the SQL query
          StringBuilder sql = new StringBuilder("SELECT room_id, room_number, room_type, bed, price, availability FROM rooms WHERE availability = 1");

          if (!roomType.equals("All")) {
              sql.append(" AND bed = ?");  // Adjusted to match bed type
          }
          if (!priceRange.isEmpty()) {
              sql.append(" AND price BETWEEN ? AND ?");
          }

          PreparedStatement ps = con.prepareStatement(sql.toString());
          int index = 1;
          if (!roomType.equals("All")) {
              ps.setString(index++, roomType); // Ensure this matches your room type exactly
          }
          if (!priceRange.isEmpty()) {
              String[] range = priceRange.split("-");
              if (range.length == 2) {
                  ps.setDouble(index++, Double.parseDouble(range[0].trim()));
                  ps.setDouble(index, Double.parseDouble(range[1].trim()));
              } else {
                  JOptionPane.showMessageDialog(this, "Invalid price range format. Use 'min-max'.");
                  return;
              }
          }

          // Debugging output
          System.out.println("Executing query: " + ps.toString());

          ResultSet rs = ps.executeQuery();

          while (rs.next()) {
              int roomId = rs.getInt("room_id");
              String roomNumber = rs.getString("room_number");
              String roomTypeFetched = rs.getString("room_type");
              String bedType = rs.getString("bed");
              double price = rs.getDouble("price");
              boolean availability = rs.getBoolean("availability");
              roomTableModel.addRow(new Object[]{roomId, roomNumber, roomTypeFetched, bedType, price, availability ? "Available" : "Not Available"});
          }

          if (roomTableModel.getRowCount() == 0) {
              JOptionPane.showMessageDialog(this, "No rooms found for the selected criteria.");
          }

      } catch (Exception ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
      }
  }


    
    
    
    
    
    
    // Method to book a room
    private void bookRoom(int roomId) {
        String checkInDate = JOptionPane.showInputDialog("Enter Check-in Date (YYYY-MM-DD):");
        String checkOutDate = JOptionPane.showInputDialog("Enter Check-out Date (YYYY-MM-DD):");

        int userId = 1; // Replace with actual user ID
        double totalPrice = 100.00; // Calculate the price based on your logic

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

            // Check if the room is available for the given date range
            String checkAvailabilitySql = "SELECT * FROM bookings WHERE room_id = ? AND (check_in_date BETWEEN ? AND ? OR check_out_date BETWEEN ? AND ?)";
            PreparedStatement checkPs = con.prepareStatement(checkAvailabilitySql);
            checkPs.setInt(1, roomId);
            checkPs.setString(2, checkInDate);
            checkPs.setString(3, checkOutDate);
            checkPs.setString(4, checkInDate);
            checkPs.setString(5, checkOutDate);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // If the query returns a result, the room is already booked for the selected dates
                JOptionPane.showMessageDialog(null, "Room is already booked for the selected dates.");
                return;
            }

            // If room is available, proceed with the booking
            String bookingSql = "INSERT INTO bookings (user_id, room_id, check_in_date, check_out_date, status, total_price) VALUES (?, ?, ?, ?, 'confirmed', ?)";
            PreparedStatement pstmt = con.prepareStatement(bookingSql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, roomId);
            pstmt.setString(3, checkInDate);
            pstmt.setString(4, checkOutDate);
            pstmt.setDouble(5, totalPrice);
            pstmt.executeUpdate();

            // Mark the room as unavailable
            String updateRoomAvailabilitySql = "UPDATE rooms SET availability = FALSE WHERE room_id = ?";
            PreparedStatement updatePs = con.prepareStatement(updateRoomAvailabilitySql);
            updatePs.setInt(1, roomId);
            updatePs.executeUpdate();

            JOptionPane.showMessageDialog(null, "Room " + roomId + " booked successfully!");

            // Refresh the room data to reflect the new availability
            loadRoomData();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
}






