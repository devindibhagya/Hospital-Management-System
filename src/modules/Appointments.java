package modules;

import database.DBConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class Appointments extends JPanel {
    private JTextField txtPatientName, txtDate;
    private JComboBox<String> cmbDoctors;
    private JTable table;
    private DefaultTableModel tableModel;

    public Appointments() {
        setLayout(new BorderLayout(25, 25));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(new Color(245, 247, 250));

        // --- Left Panel ---
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        formCard.setPreferredSize(new Dimension(360, 500));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblTitle = new JLabel("SCHEDULE APPOINTMENT");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 20, 5);
        formCard.add(lblTitle, gbc);

        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.gridwidth = 2;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = new Color(127, 140, 141);

        // Patient Name
        JLabel lblName = new JLabel("Patient Name");
        lblName.setFont(labelFont); lblName.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 1; formCard.add(lblName, gbc);
        txtPatientName = new JTextField(); styleTextField(txtPatientName);
        gbc.gridx = 0; gbc.gridy = 2; formCard.add(txtPatientName, gbc);

        // Doctor Combo Box
        JLabel lblDoc = new JLabel("Select Doctor");
        lblDoc.setFont(labelFont); lblDoc.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 3; formCard.add(lblDoc, gbc);
        cmbDoctors = new JComboBox<>();
        cmbDoctors.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fillDoctorComboBox();
        gbc.gridx = 0; gbc.gridy = 4; formCard.add(cmbDoctors, gbc);

        // Appointment Date
        JLabel lblDate = new JLabel("Date (YYYY-MM-DD)");
        lblDate.setFont(labelFont); lblDate.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 5; formCard.add(lblDate, gbc);
        txtDate = new JTextField("2026-06-23"); // Default Current Year/Date style
        styleTextField(txtDate);
        gbc.gridx = 0; gbc.gridy = 6; formCard.add(txtDate, gbc);

        // Book Button
        gbc.gridx = 0; gbc.gridy = 7; gbc.insets = new Insets(20, 5, 0, 5);
        JButton btnBook = new JButton("BOOK APPOINTMENT");
        btnBook.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBook.setForeground(Color.WHITE);
        btnBook.setBackground(new Color(155, 89, 182)); // Professional Purple
        btnBook.setFocusPainted(false);
        btnBook.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBook.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        formCard.add(btnBook, gbc);

        add(formCard, BorderLayout.WEST);

        // --- Right Panel ---
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTableTitle = new JLabel("APPOINTMENTS LOG BOOK");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTableTitle.setForeground(new Color(44, 62, 80));
        lblTableTitle.setBorder(new EmptyBorder(0, 0, 15, 0));
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Patient Name", "Doctor Name", "Date", "Status"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(tableScroll, BorderLayout.CENTER);

        add(tableCard, BorderLayout.CENTER);
        loadAppointmentData();

        btnBook.addActionListener(e -> saveAppointmentData());
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 214, 221), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(240, 240, 240));
        table.setSelectionBackground(new Color(52, 152, 219, 40));
        table.setSelectionForeground(Color.BLACK);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 35));
    }

    private void fillDoctorComboBox() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM doctors");
            while (rs.next()) { cmbDoctors.addItem(rs.getString("name")); }
        } catch (Exception ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    private void saveAppointmentData() {
        String patient = txtPatientName.getText();
        if (cmbDoctors.getSelectedItem() == null) { return; }
        String doctor = cmbDoctors.getSelectedItem().toString();
        String date = txtDate.getText();

        if (patient.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO appointments (patient_name, doctor_name, appointment_date) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, patient);
            pst.setString(2, doctor);
            pst.setString(3, date);

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Appointment Scheduled Successfully!");
                txtPatientName.setText("");
                loadAppointmentData();
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

    private void loadAppointmentData() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");
            while (rs.next()) {
                tableModel.addRow(new String[]{rs.getString("id"), rs.getString("patient_name"), rs.getString("doctor_name"), rs.getString("appointment_date"), rs.getString("status")});
            }
        } catch (Exception ex) { System.out.println("Error: " + ex.getMessage()); }
    }
}