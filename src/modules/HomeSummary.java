package modules;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeSummary extends JPanel {
    private JLabel lblPatientCount, lblDoctorCount, lblAppointmentCount;

    public HomeSummary() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(new Color(245, 247, 250));

        // --- උඩින්ම තියෙන Welcome Banner එක ---
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblWelcome = new JLabel("Welcome to Hospital Management Dashboard");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWelcome.setForeground(new Color(44, 62, 80));
        welcomePanel.add(lblWelcome, BorderLayout.WEST);
        
        add(welcomePanel, BorderLayout.NORTH);

        JPanel cardsContainer = new JPanel(new GridLayout(1, 3, 20, 20));
        cardsContainer.setBackground(new Color(245, 247, 250));

        // 1. Patients Card
        JPanel cardPatients = createSummaryCard("TOTAL PATIENTS", "0", new Color(52, 152, 219)); // Blue
        lblPatientCount = (JLabel) cardPatients.getClientProperty("countLabel");
        cardsContainer.add(cardPatients);

        // 2. Doctors Card
        JPanel cardDoctors = createSummaryCard("TOTAL DOCTORS", "0", new Color(46, 204, 113)); // Green
        lblDoctorCount = (JLabel) cardDoctors.getClientProperty("countLabel");
        cardsContainer.add(cardDoctors);

        // 3. Appointments Card
        JPanel cardAppointments = createSummaryCard("TOTAL APPOINTMENTS", "0", new Color(155, 89, 182)); // Purple
        lblAppointmentCount = (JLabel) cardAppointments.getClientProperty("countLabel");
        cardsContainer.add(cardAppointments);

        add(cardsContainer, BorderLayout.CENTER);

        // ඩේටාබේස් එකෙන් ඇත්තම ගණන් ටික අරන් Cards වලට දානවා
        refreshCounts();
    }

    
    private JPanel createSummaryCard(String title, String count, Color bgColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCount = new JLabel(count);
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblCount.setForeground(Color.WHITE);
        lblCount.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 15))); // ඉඩ තැබීමට
        card.add(lblCount);

        
        card.putClientProperty("countLabel", lblCount);

        return card;
    }

   
    public void refreshCounts() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            // Total Patients
            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM patients");
            if (rs1.next()) lblPatientCount.setText(rs1.getString(1));

            // Total Doctors
            ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM doctors");
            if (rs2.next()) lblDoctorCount.setText(rs2.getString(1));

            // Total Appointments
            ResultSet rs3 = stmt.executeQuery("SELECT COUNT(*) FROM appointments");
            if (rs3.next()) lblAppointmentCount.setText(rs3.getString(1));

        } catch (Exception ex) {
            System.out.println("Error refreshing summary counts: " + ex.getMessage());
        }
    }
}
