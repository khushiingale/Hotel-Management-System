//profile page for user
package com.hotelmanagement;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;

public class Registration extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel frame;
    private JTextField emailbox;
    private JTextField usernamebox;
    private JTextField contacttxt;
    private JTextField dobbox;
    private JTextField namebox; // New JTextField for Name
    private JButton roombookingbtn;
    private JRadioButton malebtn;
    private JRadioButton femalebtn;
    private JComboBox<String> countrytxt;
    private JLabel lblNewLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Registration frame = new Registration();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Registration() {
        setTitle("Booking");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame = new JPanel();
        frame.setBackground(new Color(255, 128, 128));
        frame.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(frame);
        frame.setLayout(null);

        createUIComponents();
        roombookingbtn.setEnabled(false); // Room Booking Button initially disabled

        JButton btnNewButton = new JButton("Logout");
        btnNewButton.addActionListener(e -> {
            MainPage mp = new MainPage();
            mp.setVisible(true);
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton.setBounds(1122, 577, 156, 56);
        frame.add(btnNewButton);
        
        JLabel lblNewLabel_1 = new JLabel("User Profile");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblNewLabel_1.setBounds(572, 44, 439, 79);
        frame.add(lblNewLabel_1);
        
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
        lblNewLabel_3.setBounds(-109, 0, 1649, 845);
        frame.add(lblNewLabel_3);
    }

    private void createUIComponents() {
        // Labels and Text Fields
        JLabel txtname = new JLabel("Name");
        txtname.setFont(new Font("Tahoma", Font.BOLD, 18));
        txtname.setBounds(48, 183, 89, 24);
        frame.add(txtname);

        namebox = new JTextField();
        namebox.setBounds(228, 174, 337, 50);
        frame.add(namebox);
        namebox.setColumns(10);

        JLabel dob = new JLabel("DOB");
        dob.setFont(new Font("Tahoma", Font.BOLD, 18));
        dob.setBounds(642, 175, 89, 41);
        frame.add(dob);

        JLabel gender = new JLabel("Gender");
        gender.setFont(new Font("Tahoma", Font.BOLD, 18));
        gender.setBounds(48, 298, 127, 50);
        frame.add(gender);

        JLabel contactno = new JLabel("Contact No");
        contactno.setFont(new Font("Tahoma", Font.BOLD, 18));
        contactno.setBounds(48, 417, 142, 50);
        frame.add(contactno);

        JLabel emailtxt = new JLabel("Email");
        emailtxt.setFont(new Font("Tahoma", Font.BOLD, 18));
        emailtxt.setBounds(642, 430, 106, 24);
        frame.add(emailtxt);

        emailbox = new JTextField();
        emailbox.setBounds(798, 421, 200, 50);
        frame.add(emailbox);
        emailbox.setColumns(10);

        JButton verifyBtn = new JButton("Verify");
        verifyBtn.setBounds(1008, 440, 89, 31);
        verifyBtn.addActionListener(new VerifyEmailActionListener());
        frame.add(verifyBtn);

        malebtn = new JRadioButton("Male");
        malebtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        malebtn.setBounds(228, 298, 117, 50);
        frame.add(malebtn);

        femalebtn = new JRadioButton("Female");
        femalebtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        femalebtn.setBounds(403, 298, 111, 50);
        frame.add(femalebtn);

        ButtonGroup btgrp = new ButtonGroup();
        btgrp.add(malebtn);
        btgrp.add(femalebtn);

        contacttxt = new JTextField();
        contacttxt.setBounds(228, 418, 339, 50);
        frame.add(contacttxt);
        contacttxt.setColumns(10);

        dobbox = new JTextField();
        dobbox.setBounds(800, 174, 337, 50);
        frame.add(dobbox);
        dobbox.setColumns(10);

        countrytxt = new JComboBox<>();
        countrytxt.setEditable(true);
        countrytxt.setMaximumRowCount(15);
        countrytxt.setModel(new DefaultComboBoxModel<>(new String[]{"Select Your Country", "Brazil", "Bangladesh", "Canada", "Germany", "France", "India", "Japan", "SriLanka", "Ukraine", "USA"}));
        countrytxt.setBounds(798, 302, 337, 50);
        frame.add(countrytxt);

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Tahoma", Font.BOLD, 18));
        submit.addActionListener(new SubmitActionListener());
        submit.setBounds(48, 578, 156, 56);
        frame.add(submit);

        roombookingbtn = new JButton("Room Booking");
        roombookingbtn.addActionListener(e -> {
            RoomBooking rb = new RoomBooking();
            rb.setVisible(true);
        });
        roombookingbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        roombookingbtn.setBounds(557, 578, 176, 56);
        frame.add(roombookingbtn);
    }

    private class VerifyEmailActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailbox.getText();
            fetchUserData(email);
        }
    }

    private void fetchUserData(String email) {
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an email.");
            return; // Exit if email is empty
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

            // Query to fetch user data based on email
            String query = "SELECT name, dob, contact_no, gender, country FROM user_profiles WHERE email = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Populate the fields if user exists
                namebox.setText(rs.getString("name"));
                dobbox.setText(rs.getString("dob"));
                contacttxt.setText(rs.getString("contact_no"));
                String gender = rs.getString("gender");
                if ("Male".equals(gender)) {
                    malebtn.setSelected(true);
                } else {
                    femalebtn.setSelected(true);
                }
                countrytxt.setSelectedItem(rs.getString("country"));
            } else {
                // Clear fields if user does not exist
                clearFields();
                JOptionPane.showMessageDialog(frame, "No user found with this email.");
            }

            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching user data: " + ex.getMessage());
        }
    }

    private void clearFields() {
        namebox.setText("");
        dobbox.setText("");
        contacttxt.setText("");
        malebtn.setSelected(false);
        femalebtn.setSelected(false);
        countrytxt.setSelectedItem("Select Your Country");
    }

    private class SubmitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String u = namebox.getText();
            String d = dobbox.getText();
            String c = contacttxt.getText();
            String eb = emailbox.getText();
            String gender = malebtn.isSelected() ? "Male" : femalebtn.isSelected() ? "Female" : "";
            String country = (String) countrytxt.getSelectedItem();

            // Validate input fields
            if (u.isEmpty() || d.isEmpty() || c.isEmpty() || eb.isEmpty() || gender.isEmpty() || country.equals("Select Your Country")) {
                JOptionPane.showMessageDialog(frame, "Please fill all the data");
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

                // Check if the user already exists
                String checkQuery = "SELECT submission_count FROM user_profiles WHERE email = ?";
                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                checkStmt.setString(1, eb);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Update existing user submission count
                    int currentCount = rs.getInt("submission_count");
                    String updateQuery = "UPDATE user_profiles SET submission_count = ? WHERE email = ?";
                    PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                    updateStmt.setInt(1, currentCount + 1);
                    updateStmt.setString(2, eb);
                    updateStmt.executeUpdate();
                } else {
                    // Insert new user
                    String insertQuery = "INSERT INTO user_profiles (name, dob, gender, country, contact_no, email) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                    insertStmt.setString(1, u);
                    insertStmt.setString(2, d);
                    insertStmt.setString(3, gender);
                    insertStmt.setString(4, country);
                    insertStmt.setString(5, c);
                    insertStmt.setString(6, eb);
                    insertStmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(frame, "Data submitted successfully");
                clearFields();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error submitting data: " + ex.getMessage());
            }
        }
    }
}




























//package com.hotelmanagement;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.ButtonGroup;
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JTextField;
//import javax.swing.border.EmptyBorder;
//import javax.swing.JButton;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import javax.swing.ImageIcon;
//
//public class Registration extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private JPanel frame;
//    private JTextField emailbox;
//    private JTextField usernamebox;
//    private JTextField contacttxt;
//    private JTextField dobbox;
//    private JButton roombookingbtn;
//    private JRadioButton malebtn;
//    private JRadioButton femalebtn;
//    private JComboBox<String> countrytxt;  // Store JComboBox as an instance variable
//    private JLabel lblNewLabel;
//
//    public Registration() {
//        setTitle("Booking");
//        setSize(1920, 1080);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame = new JPanel();
//        frame.setBackground(new Color(255, 128, 128));
//        frame.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(frame);
//        frame.setLayout(null);
//
//        // UI Components
//        createUIComponents();
//
//        // Room Booking Button initially disabled
//        roombookingbtn.setEnabled(false);
//        
//        JButton btnNewButton = new JButton("Logout");
//        btnNewButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                MainPage mp = new MainPage();
//                mp.setVisible(true);
//            }
//        });
//        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
//        btnNewButton.setBounds(1122, 577, 156, 56);
//        frame.add(btnNewButton);
//        
//        lblNewLabel = new JLabel("");
//        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
//        lblNewLabel.setBounds(-257, 0, 1816, 855);
//        frame.add(lblNewLabel);
//    }
//
//    private void createUIComponents() {
//        JLabel txtname = new JLabel("Name");
//        txtname.setFont(new Font("Tahoma", Font.BOLD, 18));
//        txtname.setBounds(48, 183, 89, 24);
//        frame.add(txtname);
//
//        JLabel dob = new JLabel("DOB");
//        dob.setFont(new Font("Tahoma", Font.BOLD, 18));
//        dob.setBounds(642, 175, 89, 41);
//        frame.add(dob);
//
//        JLabel gender = new JLabel("Gender");
//        gender.setFont(new Font("Tahoma", Font.BOLD, 18));
//        gender.setBounds(48, 298, 127, 50);
//        frame.add(gender);
//
//        JLabel contactno = new JLabel("Contact No");
//        contactno.setFont(new Font("Tahoma", Font.BOLD, 18));
//        contactno.setBounds(48, 417, 142, 50);
//        frame.add(contactno);
//
//        JLabel emailtxt = new JLabel("Email");
//        emailtxt.setFont(new Font("Tahoma", Font.BOLD, 18));
//        emailtxt.setBounds(642, 430, 106, 24);
//        frame.add(emailtxt);
//
//        JLabel country = new JLabel("Country");
//        country.setFont(new Font("Tahoma", Font.BOLD, 18));
//        country.setBounds(642, 303, 106, 41);
//        frame.add(country);
//
//        countrytxt = new JComboBox<>();  // Initialize here
//        countrytxt.setEditable(true);
//        countrytxt.setMaximumRowCount(15);
//        countrytxt.setModel(new DefaultComboBoxModel<>(new String[]{
//            "Select Your Country", "Brazil", "Bangladesh", "Canada", "Germany",
//            "France", "India", "Japan", "SriLanka", "Ukraine", "USA"
//        }));
//        countrytxt.setBounds(798, 302, 337, 50);
//        frame.add(countrytxt);
//
//        emailbox = new JTextField();
//        emailbox.setBounds(798, 421, 337, 50);
//        frame.add(emailbox);
//        emailbox.setColumns(10);
//
//        malebtn = new JRadioButton("Male");
//        malebtn.setFont(new Font("Tahoma", Font.BOLD, 18));
//        malebtn.setBounds(228, 298, 117, 50);
//        frame.add(malebtn);
//
//        femalebtn = new JRadioButton("Female");
//        femalebtn.setFont(new Font("Tahoma", Font.BOLD, 18));
//        femalebtn.setBounds(403, 298, 111, 50);
//        frame.add(femalebtn);
//
//        ButtonGroup btgrp = new ButtonGroup();
//        btgrp.add(malebtn);
//        btgrp.add(femalebtn);
//
//        usernamebox = new JTextField();
//        usernamebox.setBounds(228, 174, 337, 50);
//        frame.add(usernamebox);
//        usernamebox.setColumns(10);
//
//        contacttxt = new JTextField();
//        contacttxt.setBounds(228, 418, 339, 50);
//        frame.add(contacttxt);
//        contacttxt.setColumns(10);
//
//        dobbox = new JTextField();
//        dobbox.setBounds(800, 174, 337, 50);
//        frame.add(dobbox);
//        dobbox.setColumns(10);
//
//        JButton submit = new JButton("Submit");
//        submit.setFont(new Font("Tahoma", Font.BOLD, 18));
//        submit.addActionListener(new SubmitActionListener());
//        submit.setBounds(48, 578, 156, 56);
//        frame.add(submit);
//
//        roombookingbtn = new JButton("Room Booking");
//        roombookingbtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                RoomBooking rb = new RoomBooking();
//                rb.setVisible(true);
//            }
//        });
//        roombookingbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
//        roombookingbtn.setBounds(557, 578, 176, 56);
//        frame.add(roombookingbtn);
//    }
//
//    private class SubmitActionListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String u = usernamebox.getText();
//            String d = dobbox.getText();
//            String c = contacttxt.getText();
//            String eb = emailbox.getText();
//            String gender = malebtn.isSelected() ? "Male" : femalebtn.isSelected() ? "Female" : "";
//            String country = (String) countrytxt.getSelectedItem();  // Use instance variable directly
//
//            // Validate input fields
//            if (u.isEmpty() || d.isEmpty() || c.isEmpty() || eb.isEmpty() || gender.isEmpty() || country.equals("Select Your Country")) {
//                JOptionPane.showMessageDialog(frame, "Please fill all the data");
//                return;
//            }
//
//            // Validate contact number (must be numeric and exactly 10 digits)
//            if (!c.matches("\\d{10}")) {
//                JOptionPane.showMessageDialog(frame, "Contact number must be 10 digits long and contain only numbers.");
//                return;
//            }
//
//            // Validate email format (must end with @gmail.com)
//            if (!eb.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
//                JOptionPane.showMessageDialog(frame, "Email must be in the format username@gmail.com");
//                return;
//            }
//
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
//
//                // Check if user exists based on email
//                String checkQuery = "SELECT submission_count FROM user_profiles WHERE email = ?";
//                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
//                checkStmt.setString(1, eb);
//                ResultSet rs = checkStmt.executeQuery();
//
//                if (rs.next()) {
//                    // User exists, update submission_count
//                    int currentCount = rs.getInt("submission_count");
//                    String updateQuery = "UPDATE user_profiles SET submission_count = ? WHERE email = ?";
//                    PreparedStatement updateStmt = con.prepareStatement(updateQuery);
//                    updateStmt.setInt(1, currentCount + 1);
//                    updateStmt.setString(2, eb);
//                    updateStmt.executeUpdate();
//
//                    JOptionPane.showMessageDialog(frame, "User data already exists. Submission count updated.");
//                } else {
//                    // User does not exist, insert new record
//                    String insertQuery = "INSERT INTO user_profiles (name, dob, gender, country, contact_no, email, submission_count) VALUES (?, ?, ?, ?, ?, ?, ?)";
//                    PreparedStatement pstmt = con.prepareStatement(insertQuery);
//                    pstmt.setString(1, u);
//                    pstmt.setString(2, d);
//                    pstmt.setString(3, gender);
//                    pstmt.setString(4, country);
//                    pstmt.setString(5, c);
//                    pstmt.setString(6, eb);
//                    pstmt.setInt(7, 1);  // Initial submission count is 1
//                    pstmt.executeUpdate();
//
//                    JOptionPane.showMessageDialog(frame, "Registered Successfully!");
//                }
//
//                roombookingbtn.setEnabled(true);  // Enable Room Booking button after successful registration
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Error registering: " + ex.getMessage());
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Registration window = new Registration();
//                    window.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
