package modules;

import database.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public Login() {
        // Basic Window Settings
        setTitle("Hospital Management System - Login");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
        
        // Main Container Panel with Background Color
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250)); // Light grayish blue
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // --- 1. Header Banner Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185)); // Professional Blue
        headerPanel.setPreferredSize(new Dimension(450, 130));
        headerPanel.setLayout(new GridBagLayout()); // Centering text perfectly
        
        JLabel lblHeaderTitle = new JLabel("HOSPITAL SYSTEM");
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblHeaderTitle.setForeground(Color.WHITE);
        
        JLabel lblSubTitle = new JLabel("Secure User Authentication");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubTitle.setForeground(new Color(200, 230, 255));

        // Adding text to header using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        headerPanel.add(lblHeaderTitle, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        headerPanel.add(lblSubTitle, gbc);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Form Input Panel ---
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(35, 40, 35, 40));
        formPanel.setLayout(new GridLayout(6, 1, 0, 8)); // Stacked layout

        // Username Label & Field
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(new Color(127, 140, 141)); // Muted Gray
        
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 214, 221), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Password Label & Field
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPass.setForeground(new Color(127, 140, 141));
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 214, 221), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Login Button
        btnLogin = new JButton("LOGIN TO SYSTEM");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(46, 204, 113)); // Modern Emerald Green
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Footer Text
        JLabel lblFooter = new JLabel("© 2026 Hospital Management System", JLabel.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblFooter.setForeground(new Color(149, 165, 166));

        // Adding components to the white form card
        formPanel.add(lblUser);
        formPanel.add(txtUsername);
        formPanel.add(lblPass);
        formPanel.add(txtPassword);
        formPanel.add(new JLabel("")); // Spacer
        formPanel.add(btnLogin);

        // Card view effect by adding margin
        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setBorder(new EmptyBorder(20, 25, 20, 25));
        cardWrapper.setBackground(new Color(245, 247, 250));
        cardWrapper.add(formPanel, BorderLayout.CENTER);

        mainPanel.add(cardWrapper, BorderLayout.CENTER);
        mainPanel.add(lblFooter, BorderLayout.SOUTH);

        // Button Click Event
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Username and Password!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + role + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                Dashboard dash = new Dashboard(role);
                dash.setVisible(true);
                
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}