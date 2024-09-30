//Login Page for project
package com.hotelmanagement;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JTextField textname = new JTextField();
	private final JPasswordField textpwd = new JPasswordField();
	private final JLabel welcomeback = new JLabel("Welcome Back !!!!");
	private final JButton okbutton = new JButton("Submit");
	private final JButton resetbutton = new JButton("Reset");
	JLabel lblInfo;
	private final JLabel bg = new JLabel("");

	/**
	 * Launch the application.
	 */ 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Login Page");
		setSize(1920,1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		//setBounds(900, 500, 800, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel home = new JLabel("Home");
		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainPage m = new MainPage();
				m.setVisible(true);
			}
		});
		home.setFont(new Font("Tahoma", Font.PLAIN, 18));
		home.setForeground(new Color(128, 64, 64));
		home.setBounds(1405, 678, 62, 21);
		contentPane.add(home);
		
		JComboBox combobox = new JComboBox();
		combobox.setFont(new Font("Tahoma", Font.BOLD, 18));
		combobox.setModel(new DefaultComboBoxModel(new String[] {"Select how you want to Login ", "User", "Admin"}));
		combobox.setBounds(588, 138, 315, 44);
		contentPane.add(combobox);
		
		JLabel box = new JLabel("Login as");
		box.setFont(new Font("Tahoma", Font.BOLD, 18));
		box.setBounds(347, 143, 92, 28);
		contentPane.add(box);
		
		JLabel forgot = new JLabel("forgot password ?");
		forgot.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ForgotPassword fp = new ForgotPassword();
				fp.setVisible(true);
			}
		});
		forgot.setFont(new Font("Tahoma", Font.PLAIN, 13));
		forgot.setForeground(new Color(255, 0, 0));
		forgot.setBackground(new Color(255, 0, 0));
		forgot.setBounds(588, 385, 114, 21);
		contentPane.add(forgot);
		
		JLabel username = new JLabel("Username");
		username.setFont(new Font("Tahoma", Font.BOLD, 18));
		username.setBounds(347, 234, 159, 45);
		contentPane.add(username);
		
		JCheckBox show = new JCheckBox("Show Password");
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(show.isSelected())
				{
					textpwd.setEchoChar((char)0);
				}
				else
				{
					textpwd.setEchoChar('*');
				}
			}
		});
		show.setBounds(909, 353, 123, 21);
		contentPane.add(show);
		
		
		JLabel password = new JLabel("Password");
		password.setFont(new Font("Tahoma", Font.BOLD, 18));
		password.setBounds(347, 341, 127, 39);
		contentPane.add(password);
		textname.setBounds(588, 237, 315, 45);
		contentPane.add(textname);
		textname.setColumns(10);
		textpwd.setBounds(588, 341, 315, 45);
		contentPane.add(textpwd);
		welcomeback.setFont(new Font("Tahoma", Font.BOLD, 24));
		welcomeback.setBounds(544, 39, 252, 36);
		contentPane.add(welcomeback);
		okbutton.setFont(new Font("Tahoma", Font.BOLD, 18));
		okbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textname.getText();
				String password = textpwd.getText();
				try 
				{
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system","root","Khushi@123");
			
					PreparedStatement pst = con.prepareStatement( "select username,password from users where username= ? and password= ?");
					PreparedStatement pmt = con.prepareStatement("select username,password from admin where username= ? and password= ?");
					pst.setString(1, username);
					pst.setString(2, password);
					ResultSet rs = pst.executeQuery();
					pmt.setString(1, username);
					pmt.setString(2, password);
					ResultSet r1 = pmt.executeQuery();
					if(r1.next())
					{
						if(combobox.getSelectedItem()=="Admin")
							{
								dispose();
								Dashboard d = new Dashboard();
								d.setVisible(true);
							}
						else
						{
							JOptionPane.showMessageDialog(null,"Invalid Username or Password","Login Error",JOptionPane.ERROR_MESSAGE);
							
							textname.setText("");
							textpwd.setText("");
						}
						
					}
					if(rs.next())
					{
						if(combobox.getSelectedItem()=="User")
						{
							// Sample login logic checking for active status
							String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND status = true";
							PreparedStatement ps = con.prepareStatement(sql);
							ps.setString(1, username);
							ps.setString(2, password);
							ResultSet rst = ps.executeQuery();

							if (rst.next()) {
							    // User exists and is active
							   // System.out.println("Login successful");
							    dispose();
								Registration r = new Registration();
								r.setVisible(true);
							} else {
							    // Either wrong credentials or account deactivated
							    JOptionPane.showMessageDialog(null, "Login failed: Your account is deactivated");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Invalid Username or Password","Login Error",JOptionPane.ERROR_MESSAGE);
							
							textname.setText("");
							textpwd.setText("");
						}
						
					}
					
				}
				catch (Exception e1)
				{
					
					e1.printStackTrace();
				}
			
			}
		});
		okbutton.setBounds(233, 468, 114, 45);
		contentPane.add(okbutton);
		resetbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textname.setText("");
				textpwd.setText("");
				lblInfo.setText("");
			
			}
		});
		resetbutton.setFont(new Font("Tahoma", Font.BOLD, 18));
		resetbutton.setBounds(588, 468, 114, 45);
		contentPane.add(resetbutton);
		
		lblInfo = new JLabel("");
		lblInfo.setBounds(100, 239, 282, 14);
		contentPane.add(lblInfo);
		
		JButton signupbtn = new JButton("Sign Up");
		signupbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Signup s = new Signup();
				s.setVisible(true);
			}
		});
		signupbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		signupbtn.setBounds(958, 468, 111, 45);
		contentPane.add(signupbtn);
		
		JLabel new_here = new JLabel("New here ? Create new account?");
		new_here.setFont(new Font("Tahoma", Font.PLAIN, 13));
		new_here.setForeground(new Color(255, 0, 0));
		new_here.setBounds(964, 516, 200, 14);
		contentPane.add(new_here);
		bg.setBackground(new Color(240, 240, 240));
		bg.setIcon(new ImageIcon("C:\\Users\\HP\\Desktop\\Images\\bg.png"));
		bg.setBounds(0, 0, 1573, 864);
		
		contentPane.add(bg);
		
	
	}
}
