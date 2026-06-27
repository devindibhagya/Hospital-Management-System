package modules;

import database.DBConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Settings extends JPanel {
    private JPasswordField txtCurrentPass, txtNewPass;
    private JTextField txtNewUser;
    private JPasswordField txtNewUserPass;
    private JComboBox<String> cmbRole;
    private String currentUserRole;

    public Settings(String userRole) {
        this.currentUserRole = userRole;
        
        setLayout(new GridLayout(1, 2, 25, 25));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(new Color(245, 247, 250)); // Main Background

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = new Color(127, 140, 141);

        
        // --- 1. LEFT PANEL: Change Password Card ---
        
        JPanel passCard = new JPanel(new GridBagLayout());
        passCard.setBackground(Color.WHITE);
        passCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblPassTitle = new JLabel("CHANGE ACCOUNT PASSWORD");
        lblPassTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPassTitle.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 20, 5);
        passCard.add(lblPassTitle, gbc);

        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.gridwidth = 2;

        // Current Password
        JLabel lblCurrent = new JLabel("Current Password");
        lblCurrent.setFont(labelFont); lblCurrent.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 1; passCard.add(lblCurrent, gbc);
        txtCurrentPass = new JPasswordField(); stylePasswordField(txtCurrentPass);
        gbc.gridx = 0; gbc.gridy = 2; passCard.add(txtCurrentPass, gbc);

        // New Password
        JLabel lblNew = new JLabel("New Password");
        lblNew.setFont(labelFont); lblNew.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 3; passCard.add(lblNew, gbc);
        txtNewPass = new JPasswordField(); stylePasswordField(txtNewPass);
        gbc.gridx = 0; gbc.gridy = 4; passCard.add(txtNewPass, gbc);

        // Update Button
        gbc.gridx = 0; gbc.gridy = 5; gbc.insets = new Insets(25, 5, 0, 5);
        JButton btnUpdatePass = new JButton("UPDATE PASSWORD");
        btnUpdatePass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnUpdatePass.setForeground(Color.WHITE);
        btnUpdatePass.setBackground(new Color(230, 126, 34)); // Professional Orange
        btnUpdatePass.setFocusPainted(false);
        btnUpdatePass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdatePass.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        passCard.add(btnUpdatePass, gbc);

        add(passCard);

   
        // --- 2. RIGHT PANEL: Create New Account Card ---
        
        JPanel userCard = new JPanel(new GridBagLayout());
        userCard.setBackground(Color.WHITE);
        userCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));

        gbc.insets = new Insets(0, 5, 20, 5);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblUserTitle = new JLabel("CREATE NEW SYSTEM ACCOUNT");
        lblUserTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblUserTitle.setForeground(new Color(44, 62, 80));
        userCard.add(lblUserTitle, gbc);

        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.gridwidth = 2;

        // New Username
        JLabel lblUser = new JLabel("New Username");
        lblUser.setFont(labelFont); lblUser.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 1; userCard.add(lblUser, gbc);
        txtNewUser = new JTextField();
        txtNewUser.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNewUser.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 214, 221), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        gbc.gridx = 0; gbc.gridy = 2; userCard.add(txtNewUser, gbc);

        // Account Password
        JLabel lblUserPass = new JLabel("Account Password");
        lblUserPass.setFont(labelFont); lblUserPass.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 3; userCard.add(lblUserPass, gbc);
        txtNewUserPass = new JPasswordField(); stylePasswordField(txtNewUserPass);
        gbc.gridx = 0; gbc.gridy = 4; userCard.add(txtNewUserPass, gbc);

        // Role Selection
        JLabel lblRole = new JLabel("Assign System Role");
        lblRole.setFont(labelFont); lblRole.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 5; userCard.add(lblRole, gbc);
        String[] roles = {"Admin", "Doctor", "Receptionist"};
        cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 6; userCard.add(cmbRole, gbc);

        // Register Button
        gbc.gridx = 0; gbc.gridy = 7; gbc.insets = new Insets(25, 5, 0, 5);
        JButton btnCreateUser = new JButton("REGISTER SYSTEM USER");
        btnCreateUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCreateUser.setForeground(Color.WHITE);
        btnCreateUser.setBackground(new Color(46, 204, 113)); // Professional Green
        btnCreateUser.setFocusPainted(false);
        btnCreateUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCreateUser.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        userCard.add(btnCreateUser, gbc);

        // -----If admin lock the card for security ------
        if (!userRole.equalsIgnoreCase("Admin")) {
            txtNewUser.setEnabled(false);
            txtNewUserPass.setEnabled(false);
            cmbRole.setEnabled(false);
            btnCreateUser.setEnabled(false);
            userCard.setToolTipText("Only Administrators can access this section.");
        }

        add(userCard);

        // --- Action Listeners ---
        btnUpdatePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordLogic();
            }
        });

        btnCreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewUserLogic();
            }
        });
    }

    // Password Field Styling Helper
    private void stylePasswordField(JPasswordField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 214, 221), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }

    private void changePasswordLogic() {
        String currentPass = new String(txtCurrentPass.getPassword());
        String newPass = new String(txtNewPass.getPassword());

        if (currentPass.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill password fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE users SET password = ? WHERE password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, newPass);
            pst.setString(2, currentPass);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Password Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtCurrentPass.setText("");
                txtNewPass.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Current password incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void createNewUserLogic() {
        String username = txtNewUser.getText();
        String password = new String(txtNewUserPass.getPassword());
        String role = cmbRole.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "New " + role + " Account Registered Successfully!");
                txtNewUser.setText("");
                txtNewUserPass.setText("");
                cmbRole.setSelectedIndex(0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Username already exists or Database Error: " + ex.getMessage());
        }
    }
}
