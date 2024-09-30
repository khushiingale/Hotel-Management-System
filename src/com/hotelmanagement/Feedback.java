////Feedback page for admin which contains the feedbacks provided by user
package com.hotelmanagement;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Feedback extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Feedback frame = new Feedback();
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
    public Feedback() {
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

        JButton btnNewButton = new JButton("Load Feedbacks");

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Load JDBC Driver and establish connection
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

                    // SQL Query to fetch the feedback details including the username
                    String query = "SELECT f.feedback_id, u.username, f.food_services_rating, f.cleanliness_rating, f.room_service_rating, f.overall_feedback " +
                                   "FROM userfeedback f " +
                                   "JOIN users u ON f.user_id = u.user_id";
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();

                    // Create a table model to display feedback with proper labeling
                    DefaultTableModel model = new DefaultTableModel();

                    // Add column headers to the table model
                    model.addColumn("Feedback ID");
                    model.addColumn("Username"); // Add the username column
                    model.addColumn("Food Services Rating");
                    model.addColumn("Cleanliness Rating");
                    model.addColumn("Room Service Rating");
                    model.addColumn("Overall Feedback");

                    // Populate the model with data from the ResultSet
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt("feedback_id"),
                            rs.getString("username"), // Get the username from the ResultSet
                            "Food Services: " + rs.getInt("food_services_rating"),
                            "Cleanliness: " + rs.getInt("cleanliness_rating"),
                            "Room Service: " + rs.getInt("room_service_rating"),
                            rs.getString("overall_feedback")
                        });
                    }

                    // Set the model to the JTable
                    table.setModel(model);

                    // Close resources
                    ps.close();
                    con.close();
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        });

        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton.setBounds(114, 700, 213, 51);
        contentPane.add(btnNewButton);

        // Add JScrollPane to display JTable properly
        scrollPane = new JScrollPane();
        scrollPane.setBounds(114, 162, 1326, 496);
        contentPane.add(scrollPane);

        // Initialize the JTable and add it to the scroll pane
        table = new JTable();
        scrollPane.setViewportView(table);

        lblNewLabel = new JLabel("All Feedbacks from users");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel.setBounds(114, 64, 301, 51);
        contentPane.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
        lblNewLabel_1.setBounds(-171, 10, 1723, 845);
        contentPane.add(lblNewLabel_1);
    }
}

















//
//package com.hotelmanagement;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.JButton;
//import javax.swing.JTable;
//import java.awt.Font;
//import javax.swing.JLabel;
//import javax.swing.ImageIcon;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.awt.event.ActionEvent;
//
//public class Feedback extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private JPanel contentPane;
//    private JTable table;
//    private JLabel lblNewLabel;
//    private JLabel lblNewLabel_1;
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Feedback frame = new Feedback();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * Create the frame.
//     */
//    public Feedback() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(1920, 1080);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//
//        JLabel lblNewLabel_1 = new JLabel("Back");
//        lblNewLabel_1.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                Dashboard d = new Dashboard();
//                d.setVisible(true);
//            }
//        });
//        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
//        lblNewLabel_1.setForeground(new Color(255, 255, 255));
//        lblNewLabel_1.setBounds(1423, 26, 83, 35);
//        contentPane.add(lblNewLabel_1);
//
//        JButton btnNewButton = new JButton("Load Feedbacks");
//        
//        
//        btnNewButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    // Load JDBC Driver and establish connection
//                    Class.forName("com.mysql.cj.jdbc.Driver");
//                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
//
//                    // SQL Query to fetch the feedback details
//                    String query = "SELECT feedback_id, food_services_rating, cleanliness_rating, room_service_rating, overall_feedback FROM userfeedback";
//                    PreparedStatement ps = con.prepareStatement(query);
//                    ResultSet rs = ps.executeQuery();
//
//                    // Create a table model to display feedback with proper labeling
//                    DefaultTableModel model = new DefaultTableModel();
//                    
//                    // Add column headers to the table model
//                    model.addColumn("Feedback ID");
//                    model.addColumn("Food Services Rating");
//                    model.addColumn("Cleanliness Rating");
//                    model.addColumn("Room Service Rating");
//                    model.addColumn("Overall Feedback");
//
//                    // Populate the model with data from the ResultSet
//                    while (rs.next()) {
//                        model.addRow(new Object[]{
//                            rs.getInt("feedback_id"),
//                            "Food Services: " + rs.getInt("food_services_rating"),
//                            "Cleanliness: " + rs.getInt("cleanliness_rating"),
//                            "Room Service: " + rs.getInt("room_service_rating"),
//                            rs.getString("overall_feedback")
//                        });
//                    }
//
//                    // Set the model to the JTable
//                    table.setModel(model);
//
//                    // Close resources
//                    ps.close();
//                    con.close();
//                } catch (Exception exp) {
//                    exp.printStackTrace();
//                }
//            }
//        });
//        
//        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
//        btnNewButton.setBounds(114, 700, 213, 51);
//        contentPane.add(btnNewButton);
//
//        table = new JTable();
//        table.setBounds(114, 162, 1326, 496);
//        contentPane.add(table);
//
//        lblNewLabel = new JLabel("All Feedbacks from users");
//        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
//        lblNewLabel.setBounds(114, 64, 301, 51);
//        contentPane.add(lblNewLabel);
//
//        lblNewLabel_1 = new JLabel("");
//        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
//        lblNewLabel_1.setBounds(-171, 10, 1723, 845);
//        contentPane.add(lblNewLabel_1);
//    }
//}
//
//
//



















