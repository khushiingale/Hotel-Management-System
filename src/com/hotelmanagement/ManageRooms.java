//Manage room page for admin

package com.hotelmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManageRooms extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField numberbox;
	private JTextField pricebox;
	private JTextField descbox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageRooms frame = new ManageRooms();
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
	public ManageRooms() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920,1080);
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 255));
		contentPane.setForeground(new Color(128, 128, 255));
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
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(1457, 0, 83, 35);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(410, 65, 1082, 459);
		contentPane.add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Room ID");
        model.addColumn("Room Number");
        model.addColumn("Room Type");
        model.addColumn("Bed Type");
        model.addColumn("Price");
        model.addColumn("Availability");
        model.addColumn("Description");
        
       table = new JTable(model);
       scrollPane.setViewportView(table);
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

            String query = "SELECT * FROM rooms";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("room_id"),
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getString("bed"),
                    rs.getDouble("price"),
                    rs.getBoolean("availability") ? "Yes" : "No",
                    rs.getString("description")
                });
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }				
		JComboBox roombox = new JComboBox();
		roombox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		roombox.setModel(new DefaultComboBoxModel(new String[] {"Select Room Type", "AC", "Non AC"}));
		roombox.setBounds(166, 130, 190, 35);
		contentPane.add(roombox);
		
		JComboBox bedbox = new JComboBox();
		bedbox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bedbox.setModel(new DefaultComboBoxModel(new String[] {"Select Bed Type", "Single Bed", "Double Bed"}));
		bedbox.setBounds(166, 198, 190, 35);
		contentPane.add(bedbox);
		
		JComboBox abox = new JComboBox();
		abox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		abox.setModel(new DefaultComboBoxModel(new String[] {"True", "False"}));
		abox.setBounds(166, 344, 190, 35);
		contentPane.add(abox);
			
		JButton add = new JButton("Add Rooms");
		add.setFont(new Font("Tahoma", Font.BOLD, 18));
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				    String room1 = (String) roombox.getSelectedItem();
				    String b = (String) bedbox.getSelectedItem();
				    String a = (String) abox.getSelectedItem();

				    boolean availability = a.equalsIgnoreCase("true") || a.equalsIgnoreCase("available");

				    Class.forName("com.mysql.cj.jdbc.Driver");
				    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system","root","Khushi@123");

				    String query = "INSERT INTO rooms (room_number, room_type, bed, price, availability, description) VALUES (?, ?, ?, ?, ?, ?)";
				    PreparedStatement ps = con.prepareStatement(query);

				    ps.setString(1, numberbox.getText());            
				    ps.setString(2, room1);                          
				    ps.setString(3, b);                              
				    ps.setString(4, pricebox.getText());             
				    ps.setBoolean(5, availability);                  
				    ps.setString(6, descbox.getText());          

				    int i = ps.executeUpdate();
				    JOptionPane.showMessageDialog(add, i + " record added successfully");
				    numberbox.setText(" ");
	                roombox.getSelectedItem();
	                bedbox.getSelectedItem();
	                pricebox.setText(" ");
	                abox.setSelectedItem(Boolean.parseBoolean(null));
	                descbox.setText("");

				    ps.close();
				    con.close();
				} catch (Exception e1) {
				    e1.printStackTrace();
				}
				loadTableData();
			}
		});
		add.setBounds(425, 607, 200, 45);
		contentPane.add(add);
		
		table.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		        if (!event.getValueIsAdjusting()) {
		            int selectedRow = table.getSelectedRow();

		            // Check if a row is selected
		            if (selectedRow != -1) {
		                // Get the values from the selected row in the JTable
		                String roomNumber = table.getValueAt(selectedRow, 1).toString(); // Assuming 2nd column is room_number
		                String roomType = table.getValueAt(selectedRow, 2).toString();  // Assuming 3rd column is room_type
		                String bedType = table.getValueAt(selectedRow, 3).toString();   // Assuming 4th column is bed
		                String price = table.getValueAt(selectedRow, 4).toString();    // Assuming 5th column is price
		                String availability = table.getValueAt(selectedRow, 5).toString(); // Assuming 6th column is availability
		                String description = table.getValueAt(selectedRow, 6).toString(); // Assuming 7th column is description

		                // Populate the text fields with the selected row's values
		                numberbox.setText(roomNumber);
		                roombox.setSelectedItem(roomType);
		                bedbox.setSelectedItem(bedType);
		                pricebox.setText(price);
		                abox.setSelectedItem(Boolean.parseBoolean(availability)); // If availability is stored as boolean
		                descbox.setText(description);
		            }
		        }
		    }

		});

		
		JButton modify = new JButton("Modify Rooms");
		modify.setBackground(new Color(0, 255, 0));
		modify.setFont(new Font("Tahoma", Font.BOLD, 18));
		modify.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            // Check if a row is selected
		            int selectedRow = table.getSelectedRow();
		            if (selectedRow == -1) {
		                JOptionPane.showMessageDialog(null, "No room selected. Please select a room to modify.");
		                return;
		            }

		            // Get selected room ID from the table
		            int roomId = (int) table.getValueAt(selectedRow, 0);

		            // Get input values from the form fields
		            String room1 = (String) roombox.getSelectedItem();
		            String b = (String) bedbox.getSelectedItem();
		            String availabilityString = (String) abox.getSelectedItem();
		            boolean availability = Boolean.parseBoolean(availabilityString);

		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

		            String query="UPDATE rooms SET room_number =?,room_type=?,bed=?,price=?,availability=?,description=? WHERE room_id=?";
		            PreparedStatement p = con.prepareStatement(query);

		            p.setString(1, numberbox.getText());
		            p.setString(2, room1);
		            p.setString(3, b);
		            p.setDouble(4, Double.parseDouble(pricebox.getText()));
		            p.setBoolean(5, availability);
		            p.setString(6, descbox.getText());
		            p.setInt(7, roomId);  

		            int rowsAffected = p.executeUpdate();
	
		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Record updated successfully!");
		                loadTableData();  // Reload table data to reflect changes
		            } else {
		                JOptionPane.showMessageDialog(null, "Update failed. Please try again.");
		            }
		            p.close();
		            con.close();
		        } catch (Exception e2) {
		            e2.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error: " + e2.getMessage());
		        }
		        loadTableData();
		    }
		    
	//	    loadTableData();
		});
	//	loadTableData();
		modify.setBounds(841, 607, 200, 45);
		contentPane.add(modify);

		

		
		JButton delete = new JButton("Delete Rooms");
		delete.setBackground(new Color(255, 0, 0));
		delete.setFont(new Font("Tahoma", Font.BOLD, 18));
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            // Check if a row is selected
		            int selectedRow = table.getSelectedRow();
		            if (selectedRow == -1) {
		                JOptionPane.showMessageDialog(null, "No room selected. Please select a room to modify.");
		                return;
		            }

		            // Get selected room ID from the table
		            int roomId = (int) table.getValueAt(selectedRow, 0);

		         
		            String room1 = (String) roombox.getSelectedItem();
		            String b = (String) bedbox.getSelectedItem();
		            String availabilityString = (String) abox.getSelectedItem();
		            boolean availability = Boolean.parseBoolean(availabilityString);

		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");

		            String query = "DELETE FROM rooms WHERE room_id = ?";
		            PreparedStatement p = con.prepareStatement(query);
		            p.setInt(1, roomId);

		            int rowsAffected = p.executeUpdate();
		      
		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Record Deleted successfully!");
		                loadTableData();  
		            } else {
		                JOptionPane.showMessageDialog(null, "Update failed. Please try again.");
		            }

		            p.close();
		            con.close();
		        } catch (Exception e2) {
		            e2.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error: " + e2.getMessage());
		        }
		        loadTableData();
		    }
		});
		delete.setBounds(1235, 607, 200, 45);
		contentPane.add(delete);
		
		JLabel number = new JLabel("Room Number");
		number.setFont(new Font("Tahoma", Font.BOLD, 18));
		number.setBounds(22, 59, 165, 45);
		contentPane.add(number);
		
		JLabel type = new JLabel("Room Type");
		type.setFont(new Font("Tahoma", Font.BOLD, 18));
		type.setBounds(22, 129, 165, 30);
		contentPane.add(type);
		
		JLabel bed = new JLabel("Bed Type");
		bed.setFont(new Font("Tahoma", Font.BOLD, 18));
		bed.setBounds(22, 197, 160, 30);
		contentPane.add(bed);
		
		JLabel price = new JLabel("Price");
		price.setFont(new Font("Tahoma", Font.BOLD, 18));
		price.setBounds(22, 267, 160, 30);
		contentPane.add(price);
		
		JLabel description = new JLabel("Description");
		description.setFont(new Font("Tahoma", Font.BOLD, 18));
		description.setBounds(22, 414, 160, 30);
		contentPane.add(description);
		
		JLabel available = new JLabel("Availiability");
		available.setFont(new Font("Tahoma", Font.BOLD, 18));
		available.setBounds(22, 343, 165, 30);
		contentPane.add(available);
		
		numberbox = new JTextField();
		numberbox.setBounds(166, 67, 190, 35);
		contentPane.add(numberbox);
		numberbox.setColumns(10);
		
//		JComboBox roombox = new JComboBox();
//		roombox.setBounds(166, 130, 190, 35);
//		contentPane.add(roombox);
//		
//		JComboBox bedbox = new JComboBox();
//		bedbox.setBounds(166, 198, 190, 35);
//		contentPane.add(bedbox);
		
		pricebox = new JTextField();
		pricebox.setBounds(166, 268, 190, 35);
		contentPane.add(pricebox);
		pricebox.setColumns(10);
		
//		JComboBox abox = new JComboBox();
//		abox.setBounds(166, 344, 190, 35);
//		contentPane.add(abox);
		
		descbox = new JTextField();
		descbox.setBounds(166, 414, 190, 35);
		contentPane.add(descbox);
		descbox.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\Downloads\\ejxDH4.png"));
		lblNewLabel.setBounds(0, 0, 1540, 845);
		contentPane.add(lblNewLabel);
	}

	private void loadTableData() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); 

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management_system", "root", "Khushi@123");
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");

	        while (rs.next()) {
	            int roomId = rs.getInt("room_id");
	            String roomNumber = rs.getString("room_number");
	            String roomType = rs.getString("room_type");
	            String bed = rs.getString("bed");
	            double price = rs.getDouble("price");
	            boolean availability = rs.getBoolean("availability");
	            String description = rs.getString("description");

	            model.addRow(new Object[]{roomId, roomNumber, roomType, bed, price, availability, description});
	        }

	        rs.close();
	        stmt.close();
	        con.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	private int getSelectedRoomId() {
	    if (table != null) {
	        int selectedRow = table.getSelectedRow();
	        if (selectedRow >= 0) {
	            return (int) table.getValueAt(selectedRow, 0);
	        }
	        
	        

	        
	    }
	    return -1;
	    
	}


}
