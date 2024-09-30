////Feedback page for user to provide users
package com.hotelmanagement;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class UserFeedback extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // Declare the combo boxes and text area as instance variables (class-level)
    private JComboBox<String> roomservicebox;
    private JComboBox<String> foodservicebox;
    private JComboBox<String> cleanbox;
    private JTextArea feedbackbox;
    private JTextField usernameField; // New field for username input

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserFeedback frame = new UserFeedback();
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
    public UserFeedback() {
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
        		RoomBooking d = new RoomBooking();
				d.setVisible(true);
        	}
        });
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(1423, 26, 83, 35);
        contentPane.add(lblNewLabel_1);

        // Initialize the combo boxes and text area here
        foodservicebox = new JComboBox<>();
        foodservicebox.setFont(new Font("Tahoma", Font.BOLD, 18));
        foodservicebox.setModel(new DefaultComboBoxModel<>(new String[] {"Select Rating for Food Services", "1 (Poor)", "2 (Can be better)", "3 (Good)", "4 (Very Good)", "5 (Outstanding)"}));
        foodservicebox.setBounds(390, 201, 394, 48);
        contentPane.add(foodservicebox);

        cleanbox = new JComboBox<>();
        cleanbox.setFont(new Font("Tahoma", Font.BOLD, 18));
        cleanbox.setModel(new DefaultComboBoxModel<>(new String[] {"Select Rating for Cleanliness in Hotel", "1 (Poor)", "2 (Can be better)", "3 (Good)", "4 (Very Good)", "5 (Outstanding)"}));
        cleanbox.setBounds(390, 326, 394, 48);
        contentPane.add(cleanbox);

        roomservicebox = new JComboBox<>();
        roomservicebox.setFont(new Font("Tahoma", Font.BOLD, 18));
        roomservicebox.setModel(new DefaultComboBoxModel<>(new String[] {"Select Rating for Room Service", "1 (Poor)", "2 (Can be better)", "3 (Good)", "4 (Very Good)", "5 (Outstanding)"}));
        roomservicebox.setBounds(390, 459, 394, 48);
        contentPane.add(roomservicebox);

        feedbackbox = new JTextArea();
        feedbackbox.setBounds(390, 579, 405, 166);
        contentPane.add(feedbackbox);

        // Add a label and text field for the username input
        JLabel usernamelabel = new JLabel("Username");
        usernamelabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        usernamelabel.setBounds(50, 100, 177, 29);
        contentPane.add(usernamelabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Tahoma", Font.BOLD, 18));
        usernameField.setBounds(390, 100, 394, 54);
        contentPane.add(usernameField);

        JLabel foodservice = new JLabel("Food Service");
        foodservice.setFont(new Font("Tahoma", Font.BOLD, 24));
        foodservice.setBounds(50, 220, 177, 29);
        contentPane.add(foodservice);

        JLabel clean = new JLabel("Cleanliness");
        clean.setFont(new Font("Tahoma", Font.BOLD, 24));
        clean.setBounds(50, 333, 177, 29);
        contentPane.add(clean);

        JLabel roomservice = new JLabel("Room Service");
        roomservice.setFont(new Font("Tahoma", Font.BOLD, 24));
        roomservice.setBounds(50, 466, 177, 29);
        contentPane.add(roomservice);

        JLabel otherfeedback = new JLabel("Overall Any Feedback");
        otherfeedback.setFont(new Font("Tahoma", Font.BOLD, 24));
        otherfeedback.setBounds(50, 632, 287, 29);
        contentPane.add(otherfeedback);

        JLabel feedbackform = new JLabel("Feedback Form");
        feedbackform.setFont(new Font("Tahoma", Font.BOLD, 55));
        feedbackform.setBounds(406, 26, 438, 54);
        contentPane.add(feedbackform);

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Extracting the numeric part of the selected rating
                    String fsb = foodservicebox.getSelectedItem().toString().split(" ")[0];
                    String c = cleanbox.getSelectedItem().toString().split(" ")[0];
                    String rsb = roomservicebox.getSelectedItem().toString().split(" ")[0];
                    String username = usernameField.getText(); // Get the username

                    // Database connection setup
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

                    // Check if the username exists and get the user_id
                    String userQuery = "SELECT user_id FROM users WHERE username = ?";
                    PreparedStatement userPs = con.prepareStatement(userQuery);
                    userPs.setString(1, username);
                    ResultSet rs = userPs.executeQuery();

                    if (rs.next()) {
                        int userId = rs.getInt("user_id");

                        // Now insert into the userfeedback table
                        String feedbackQuery = "INSERT INTO userfeedback(user_id, food_services_rating, cleanliness_rating, room_service_rating, overall_feedback) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement ps = con.prepareStatement(feedbackQuery);

                        ps.setInt(1, userId); // Set user_id
                        ps.setInt(2, Integer.parseInt(fsb)); // Set food_services_rating
                        ps.setInt(3, Integer.parseInt(c));   // Set cleanliness_rating
                        ps.setInt(4, Integer.parseInt(rsb)); // Set room_service_rating
                        ps.setString(5, feedbackbox.getText()); // Set overall_feedback

                        int i = ps.executeUpdate();
                        JOptionPane.showMessageDialog(submit, i + " feedback submitted successfully");

                        ps.close();
                    } else {
                        JOptionPane.showMessageDialog(submit, "Username not found.");
                    }

                    userPs.close();
                    con.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        submit.setFont(new Font("Tahoma", Font.BOLD, 18));
        submit.setBounds(1308, 682, 113, 38);
        contentPane.add(submit);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
        lblNewLabel.setBounds(-251, 0, 1781, 855);
        contentPane.add(lblNewLabel);
    }
}




























//package com.hotelmanagement;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.ImageIcon;
//import java.awt.Font;
//import javax.swing.JComboBox;
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.JTextArea;
//import javax.swing.JButton;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.awt.event.ActionEvent;
//import javax.swing.JTextField;
//
//public class UserFeedback extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private JPanel contentPane;
//    
//    // Declare the combo boxes and text area as instance variables (class-level)
//    private JComboBox<String> roomservicebox;
//    private JComboBox<String> foodservicebox;
//    private JComboBox<String> cleanbox;
//    private JTextArea feedbackbox;
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    UserFeedback frame = new UserFeedback();
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
//    public UserFeedback() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(1920, 1080);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//        
//        JLabel lblNewLabel_1 = new JLabel("Back");
//        lblNewLabel_1.addMouseListener(new MouseAdapter() {
//        	@Override
//        	public void mousePressed(MouseEvent e) {
//        		RoomBooking d = new RoomBooking();
//				d.setVisible(true);
//        	}
//        });
//        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
//        lblNewLabel_1.setForeground(new Color(255, 255, 255));
//        lblNewLabel_1.setBounds(1423, 26, 83, 35);
//        contentPane.add(lblNewLabel_1);
//
//        // Initialize the combo boxes and text area here
//        foodservicebox = new JComboBox<>();
//        foodservicebox.setFont(new Font("Tahoma", Font.BOLD, 18));
//        foodservicebox.setModel(new DefaultComboBoxModel<>(new String[] {"Select Rating for Food Services", "1 (Poor)", "2 (Can be better)", "3 (Good)", "4 (Very Good)", "5 (Outstanding)"}));
//        foodservicebox.setBounds(390, 184, 394, 48);
//        contentPane.add(foodservicebox);
//
//        cleanbox = new JComboBox<>();
//        cleanbox.setFont(new Font("Tahoma", Font.BOLD, 18));
//        cleanbox.setModel(new DefaultComboBoxModel<>(new String[] {"Select Rating for Cleanliness in Hotel", "1 (Poor)", "2 (Can be better)", "3 (Good)", "4 (Very Good)", "5 (Outstanding)"}));
//        cleanbox.setBounds(390, 319, 394, 48);
//        contentPane.add(cleanbox);
//
//        roomservicebox = new JComboBox<>();
//        roomservicebox.setFont(new Font("Tahoma", Font.BOLD, 18));
//        roomservicebox.setModel(new DefaultComboBoxModel<>(new String[] {"Select Rating for Room Service", "1 (Poor)", "2 (Can be better)", "3 (Good)", "4 (Very Good)", "5 (Outstanding)"}));
//        roomservicebox.setBounds(390, 459, 394, 48);
//        contentPane.add(roomservicebox);
//
//        feedbackbox = new JTextArea();
//        feedbackbox.setBounds(390, 579, 405, 166);
//        contentPane.add(feedbackbox);
//
//        JLabel foodservice = new JLabel("Food Service");
//        foodservice.setFont(new Font("Tahoma", Font.BOLD, 24));
//        foodservice.setBounds(50, 184, 177, 29);
//        contentPane.add(foodservice);
//
//        JLabel clean = new JLabel("Cleanliness");
//        clean.setFont(new Font("Tahoma", Font.BOLD, 24));
//        clean.setBounds(50, 326, 177, 29);
//        contentPane.add(clean);
//
//        JLabel roomservice = new JLabel("Room Service");
//        roomservice.setFont(new Font("Tahoma", Font.BOLD, 24));
//        roomservice.setBounds(50, 466, 177, 29);
//        contentPane.add(roomservice);
//
//        JLabel otherfeedback = new JLabel("Overall Any Feedback");
//        otherfeedback.setFont(new Font("Tahoma", Font.BOLD, 24));
//        otherfeedback.setBounds(50, 632, 287, 29);
//        contentPane.add(otherfeedback);
//
//        JLabel feedbackform = new JLabel("Feedback Form");
//        feedbackform.setFont(new Font("Tahoma", Font.BOLD, 55));
//        feedbackform.setBounds(410, 56, 438, 54);
//        contentPane.add(feedbackform);
//
//        JButton submit = new JButton("Submit");
//        submit.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    // Extracting the numeric part of the selected rating
//                    String fsb = foodservicebox.getSelectedItem().toString().split(" ")[0];
//                    String c = cleanbox.getSelectedItem().toString().split(" ")[0];
//                    String rsb = roomservicebox.getSelectedItem().toString().split(" ")[0];
//
//                    Class.forName("com.mysql.cj.jdbc.Driver");
//                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
//
//                    String query = "INSERT INTO userfeedback(food_services_rating, cleanliness_rating, room_service_rating, overall_feedback) VALUES (?, ?, ?, ?)";
//                    PreparedStatement ps = con.prepareStatement(query);
//
//                    ps.setInt(1, Integer.parseInt(fsb)); // Set as Integer
//                    ps.setInt(2, Integer.parseInt(c));   // Set as Integer
//                    ps.setInt(3, Integer.parseInt(rsb)); // Set as Integer
//                    ps.setString(4, feedbackbox.getText());
//
//                    int i = ps.executeUpdate();
//                    JOptionPane.showMessageDialog(submit, i + " feedback submitted successfully");
//
//                    ps.close();
//                    con.close();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
//
//        submit.setFont(new Font("Tahoma", Font.BOLD, 18));
//        submit.setBounds(1308, 682, 113, 38);
//        contentPane.add(submit);
//
//        JLabel lblNewLabel = new JLabel("");
//        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
//        lblNewLabel.setBounds(-251, 0, 1781, 855);
//        contentPane.add(lblNewLabel);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
