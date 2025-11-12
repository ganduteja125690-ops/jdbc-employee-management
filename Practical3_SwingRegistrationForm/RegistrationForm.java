import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class RegistrationForm extends JFrame implements ActionListener {
    // Components
    private JLabel lblName, lblEmail, lblPhone, lblPassword, lblConfirmPassword, lblGender, lblTitle;
    private JTextField txtName, txtEmail, txtPhone;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;
    private JButton btnSubmit, btnReset;
    private JTextArea txtResult;
    
    public RegistrationForm() {
        // Frame setup
        setTitle("Registration Form with Validation");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));
        
        // Title
        lblTitle = new JLabel("User Registration Form");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(25, 25, 112));
        lblTitle.setBounds(150, 20, 300, 30);
        add(lblTitle);
        
        // Name
        lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 80, 120, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(200, 80, 300, 25);
        add(txtName);
        
        // Email
        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 120, 120, 25);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(200, 120, 300, 25);
        add(txtEmail);
        
        // Phone
        lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(50, 160, 120, 25);
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(200, 160, 300, 25);
        add(txtPhone);
        
        // Password
        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 200, 120, 25);
        add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(200, 200, 300, 25);
        add(txtPassword);
        
        // Confirm Password
        lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setBounds(50, 240, 150, 25);
        add(lblConfirmPassword);
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(200, 240, 300, 25);
        add(txtConfirmPassword);
        
        // Gender
        lblGender = new JLabel("Gender:");
        lblGender.setBounds(50, 280, 120, 25);
        add(lblGender);
        rbMale = new JRadioButton("Male");
        rbMale.setBounds(200, 280, 80, 25);
        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(300, 280, 100, 25);
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        add(rbMale);
        add(rbFemale);
        
        // Buttons
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(200, 330, 100, 30);
        btnSubmit.setBackground(new Color(34, 139, 34));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(this);
        add(btnSubmit);
        
        btnReset = new JButton("Reset");
        btnReset.setBounds(320, 330, 100, 30);
        btnReset.setBackground(new Color(220, 20, 60));
        btnReset.setForeground(Color.WHITE);
        btnReset.addActionListener(this);
        add(btnReset);
        
        // Result Area
        txtResult = new JTextArea();
        txtResult.setBounds(50, 380, 500, 250);
        txtResult.setEditable(false);
        txtResult.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtResult.setBackground(new Color(245, 245, 245));
        add(txtResult);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            validateAndSubmit();
        } else if (e.getSource() == btnReset) {
            resetForm();
        }
    }
    
    private void validateAndSubmit() {
        StringBuilder errors = new StringBuilder();
        
        // Validate Name
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            errors.append("\u2717 Name is required\n");
        } else if (name.length() < 3) {
            errors.append("\u2717 Name must be at least 3 characters\n");
        } else if (!name.matches("[a-zA-Z ]+")) {
            errors.append("\u2717 Name should contain only letters\n");
        }
        
        // Validate Email
        String email = txtEmail.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (email.isEmpty()) {
            errors.append("\u2717 Email is required\n");
        } else if (!Pattern.matches(emailRegex, email)) {
            errors.append("\u2717 Invalid email format\n");
        }
        
        // Validate Phone
        String phone = txtPhone.getText().trim();
        if (phone.isEmpty()) {
            errors.append("\u2717 Phone number is required\n");
        } else if (!phone.matches("[0-9]{10}")) {
            errors.append("\u2717 Phone number must be 10 digits\n");
        }
        
        // Validate Password
        String password = new String(txtPassword.getPassword());
        if (password.isEmpty()) {
            errors.append("\u2717 Password is required\n");
        } else if (password.length() < 6) {
            errors.append("\u2717 Password must be at least 6 characters\n");
        }
        
        // Validate Confirm Password
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        if (!password.equals(confirmPassword)) {
            errors.append("\u2717 Passwords do not match\n");
        }
        
        // Validate Gender
        if (!rbMale.isSelected() && !rbFemale.isSelected()) {
            errors.append("\u2717 Please select gender\n");
        }
        
        // Display result
        if (errors.length() > 0) {
            txtResult.setText("VALIDATION ERRORS:\n\n" + errors.toString());
            txtResult.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "Please fix the errors!", "Validation Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String successMessage = "REGISTRATION SUCCESSFUL!\n\n" +
                    "\u2713 Name: " + name + "\n" +
                    "\u2713 Email: " + email + "\n" +
                    "\u2713 Phone: " + phone + "\n" +
                    "\u2713 Gender: " + gender + "\n" +
                    "\u2713 Password: " + "*".repeat(password.length()) + "\n\n" +
                    "Thank you for registering!";
            txtResult.setText(successMessage);
            txtResult.setForeground(new Color(0, 128, 0));
            JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void resetForm() {
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        genderGroup.clearSelection();
        txtResult.setText("");
        JOptionPane.showMessageDialog(this, "Form has been reset!", "Reset", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}
