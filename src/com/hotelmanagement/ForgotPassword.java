//Forgotpassword page  

package com.hotelmanagement;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class ForgotPassword extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField answerbox;
    private JTextField pwdbox;
    private JComboBox<String> questionbox;
    private JTextField usernamebox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ForgotPassword frame = new ForgotPassword();
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
    public ForgotPassword() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel back = new JLabel("Back");
        back.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		Login d = new Login();
				d.setVisible(true);
        	}
        });
        back.setFont(new Font("Tahoma", Font.BOLD, 18));
        back.setForeground(new Color(64, 0, 64));
        back.setBounds(1423, 26, 83, 35);
        contentPane.add(back);

        // Refresh Button
        JButton refreshbtn = new JButton("Refresh");
        refreshbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                answerbox.setText("");
                pwdbox.setText("");
                usernamebox.setText("");
                questionbox.setSelectedIndex(0);
            }
        });
        
        JLabel lblNewLabel_1 = new JLabel("Forgot Password ?");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel_1.setBounds(386, 33, 343, 36);
        contentPane.add(lblNewLabel_1);
        refreshbtn.setFont(new Font("Tahoma", Font.BOLD, 20));
        refreshbtn.setBounds(350, 617, 135, 56);
        contentPane.add(refreshbtn);

        // Username Field
        usernamebox = new JTextField();
        usernamebox.setBounds(350, 158, 267, 43);
        contentPane.add(usernamebox);
        usernamebox.setColumns(10);

        JLabel username = new JLabel("Username");
        username.setFont(new Font("Tahoma", Font.BOLD, 20));
        username.setBounds(126, 160, 175, 30);
        contentPane.add(username);

        // Submit Button with Action Listener
        JButton submitbtn = new JButton("Submit");
        submitbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernamebox.getText();
                String selectedQuestion = questionbox.getSelectedItem().toString();
                String answer = answerbox.getText();
                String newPassword = pwdbox.getText();

                if (username.isEmpty() || selectedQuestion.equals("Security Questions") || answer.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                    return;
                }

                try {
                    // Connect to the database
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

                    // Check if username, question, and answer match
                    String query = "SELECT * FROM users WHERE username = ? AND question = ? AND answer = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, username);
                    ps.setString(2, selectedQuestion);
                    ps.setString(3, answer);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        // If the question and answer are correct, update the password
                        String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
                        PreparedStatement updatePs = con.prepareStatement(updateQuery);
                        updatePs.setString(1, newPassword);
                        updatePs.setString(2, username);
                        updatePs.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Password updated successfully.");
                        Login l = new Login();
                        l.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect security question or answer.");
                    }

                    ps.close();
                    con.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while updating the password.");
                }
            }
        });
        submitbtn.setFont(new Font("Tahoma", Font.BOLD, 20));
        submitbtn.setBounds(126, 617, 142, 56);
        contentPane.add(submitbtn);

        // Password Field
        pwdbox = new JTextField();
        pwdbox.setBounds(350, 441, 267, 43);
        contentPane.add(pwdbox);
        pwdbox.setColumns(10);

        JLabel pwd = new JLabel("Set New Password");
        pwd.setFont(new Font("Tahoma", Font.BOLD, 20));
        pwd.setBounds(126, 443, 191, 30);
        contentPane.add(pwd);

        // Answer Field
        answerbox = new JTextField();
        answerbox.setBounds(350, 341, 267, 43);
        contentPane.add(answerbox);
        answerbox.setColumns(10);

        JLabel answer = new JLabel("Answer");
        answer.setFont(new Font("Tahoma", Font.BOLD, 20));
        answer.setBounds(126, 341, 191, 30);
        contentPane.add(answer);

        // Security Question ComboBox
        questionbox = new JComboBox<>();
        questionbox.setFont(new Font("Tahoma", Font.BOLD, 20));
        questionbox.setModel(new DefaultComboBoxModel<>(new String[]{
            "Security Questions", "Your Birth Place", "First School Attended", 
            "Your Favourite Colour", "Your Favourite Food", "Do you ever had pet"
        }));
        questionbox.setBounds(350, 250, 267, 43);
        contentPane.add(questionbox);

        JLabel question = new JLabel("Security Question");
        question.setFont(new Font("Tahoma", Font.BOLD, 20));
        question.setBounds(126, 250, 191, 43);
        contentPane.add(question);

        // Background Image
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Desktop\\Images\\bg.png"));
        lblNewLabel.setBounds(-267, 0, 1807, 889);
        contentPane.add(lblNewLabel);
    }
}
