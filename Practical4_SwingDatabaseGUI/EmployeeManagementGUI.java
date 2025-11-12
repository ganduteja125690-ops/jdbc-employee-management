import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeManagementGUI extends JFrame implements ActionListener {
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/YOUR_DATABASE";
    private static final String DB_USER = "YOUR_USERNAME";
    private static final String DB_PASS = "YOUR_PASSWORD";
    
    // GUI Components
    private JTextField txtId, txtName, txtCity, txtAge;
    private JButton btnInsert, btnUpdate, btnDelete, btnView, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    public EmployeeManagementGUI() {
        // Frame setup
        setTitle("Employee Management System - Database Integration");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));
        
        // Title Label
        JLabel lblTitle = new JLabel("Employee Management System");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(25, 25, 112));
        lblTitle.setBounds(250, 10, 400, 30);
        add(lblTitle);
        
        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(20, 60, 350, 200);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        
        // ID
        JLabel lblId = new JLabel("Employee ID:");
        lblId.setBounds(20, 30, 100, 25);
        inputPanel.add(lblId);
        txtId = new JTextField();
        txtId.setBounds(140, 30, 180, 25);
        inputPanel.add(txtId);
        
        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 65, 100, 25);
        inputPanel.add(lblName);
        txtName = new JTextField();
        txtName.setBounds(140, 65, 180, 25);
        inputPanel.add(txtName);
        
        // City
        JLabel lblCity = new JLabel("City:");
        lblCity.setBounds(20, 100, 100, 25);
        inputPanel.add(lblCity);
        txtCity = new JTextField();
        txtCity.setBounds(140, 100, 180, 25);
        inputPanel.add(txtCity);
        
        // Age
        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(20, 135, 100, 25);
        inputPanel.add(lblAge);
        txtAge = new JTextField();
        txtAge.setBounds(140, 135, 180, 25);
        inputPanel.add(txtAge);
        
        add(inputPanel);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBounds(20, 280, 350, 250);
        
        btnInsert = new JButton("Insert Employee");
        btnInsert.setBackground(new Color(34, 139, 34));
        btnInsert.setForeground(Color.WHITE);
        btnInsert.addActionListener(this);
        buttonPanel.add(btnInsert);
        
        btnUpdate = new JButton("Update Employee");
        btnUpdate.setBackground(new Color(30, 144, 255));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.addActionListener(this);
        buttonPanel.add(btnUpdate);
        
        btnDelete = new JButton("Delete Employee");
        btnDelete.setBackground(new Color(220, 20, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(this);
        buttonPanel.add(btnDelete);
        
        btnView = new JButton("View All Employees");
        btnView.setBackground(new Color(255, 140, 0));
        btnView.setForeground(Color.WHITE);
        btnView.addActionListener(this);
        buttonPanel.add(btnView);
        
        btnClear = new JButton("Clear Fields");
        btnClear.setBackground(new Color(128, 128, 128));
        btnClear.setForeground(Color.WHITE);
        btnClear.addActionListener(this);
        buttonPanel.add(btnClear);
        
        add(buttonPanel);
        
        // Table
        String[] columns = {"ID", "Name", "City", "Age"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 60, 470, 470);
        add(scrollPane);
        
        // Load data initially
        loadTableData();
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnInsert) {
            insertEmployee();
        } else if (e.getSource() == btnUpdate) {
            updateEmployee();
        } else if (e.getSource() == btnDelete) {
            deleteEmployee();
        } else if (e.getSource() == btnView) {
            loadTableData();
        } else if (e.getSource() == btnClear) {
            clearFields();
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    
    private void insertEmployee() {
        if (!validateInputs()) return;
        
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO emp (id, name, city, age) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.setString(2, txtName.getText());
            pstmt.setString(3, txtCity.getText());
            pstmt.setInt(4, Integer.parseInt(txtAge.getText()));
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Employee inserted successfully!");
                loadTableData();
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateEmployee() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID to update!");
            return;
        }
        
        try (Connection conn = getConnection()) {
            String sql = "UPDATE emp SET name = ?, city = ?, age = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtName.getText());
            pstmt.setString(2, txtCity.getText());
            pstmt.setInt(3, Integer.parseInt(txtAge.getText()));
            pstmt.setInt(4, Integer.parseInt(txtId.getText()));
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteEmployee() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM emp WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0); // Clear existing data
        
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM emp");
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getInt("age")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInputs() {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || 
            txtCity.getText().isEmpty() || txtAge.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return false;
        }
        
        try {
            Integer.parseInt(txtId.getText());
            Integer.parseInt(txtAge.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID and Age must be numbers!");
            return false;
        }
        
        return true;
    }
    
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCity.setText("");
        txtAge.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagementGUI());
    }
}
