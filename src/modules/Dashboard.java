package modules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    
    private JPanel mainContent; 
    private String userRole;

    public Dashboard(String userRole) {
        this.userRole = userRole;

        // Basic Window Settings
        setTitle("Hospital Management System - Dashboard (" + userRole + ")");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on screen
        setLayout(new BorderLayout());

        // --- 1. Top Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185)); // Professional Blue
        headerPanel.setPreferredSize(new Dimension(1000, 70));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("  HOSPITAL MANAGEMENT SYSTEM", JLabel.LEFT);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JLabel lblWelcome = new JLabel("Welcome, " + userRole + "   ", JLabel.RIGHT);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        headerPanel.add(lblWelcome, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. Side Navigation Menu Panel ---
        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(new Color(44, 62, 80)); // Dark Charcoal Blue
        sideMenu.setPreferredSize(new Dimension(200, 600));
        
        sideMenu.setLayout(new GridLayout(7, 1, 5, 5)); 

        // Navigation Buttons
        JButton btnPatients = createMenuButton("Patient Management");
        JButton btnDoctors = createMenuButton("Doctors List");
        JButton btnAppointments = createMenuButton("Appointments");
        JButton btnBilling = createMenuButton("Prescription & Billing"); // අලුත් මොඩියුලය
        JButton btnSettings = createMenuButton("Settings");
        JButton btnLogout = createMenuButton("Logout");
        btnLogout.setBackground(new Color(192, 57, 43)); // Flat Red for Logout

        // Adding components to side menu
        sideMenu.add(btnPatients);
        sideMenu.add(btnDoctors);
        sideMenu.add(btnAppointments);
        sideMenu.add(btnBilling);
        sideMenu.add(btnSettings);
        sideMenu.add(new JLabel("")); // Spacer
        sideMenu.add(btnLogout);

        add(sideMenu, BorderLayout.WEST);

        // --- 3. Main Content Area (Center Display Panel) ---
        mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        
        
        mainContent.add(new HomeSummary(), BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);

        // --- Navigation Button Action Listeners ---
        
        // 1. Patient Management Click Event
        btnPatients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainContent.removeAll();
                mainContent.add(new PatientManagement(), BorderLayout.CENTER);
                mainContent.revalidate();
                mainContent.repaint();
            }
        });

        // 2. Doctors List Click Event
        btnDoctors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainContent.removeAll();
                mainContent.add(new DoctorsList(), BorderLayout.CENTER);
                mainContent.revalidate();
                mainContent.repaint();
            }
        });

        // 3. Appointments Click Event
        btnAppointments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainContent.removeAll();
                mainContent.add(new Appointments(), BorderLayout.CENTER);
                mainContent.revalidate();
                mainContent.repaint();
            }
        });

        // 4. Prescription & Billing Click Event
        btnBilling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainContent.removeAll();
                mainContent.add(new BillingModule(), BorderLayout.CENTER);
                mainContent.revalidate();
                mainContent.repaint();
            }
        });

        // 5. Settings Click Event
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainContent.removeAll();
                mainContent.add(new Settings(userRole), BorderLayout.CENTER);
                mainContent.revalidate();
                mainContent.repaint();
            }
        });

        // 6. Logout Click Event
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close Dashboard Window
                new Login().setVisible(true); // Reopen Login Window
            }
        });
    }

    // Helper method to style navigation buttons consistently
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 73, 94));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
