package modules;

import database.DBConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class DoctorsList extends JPanel {
    private JTextField txtName, txtSpecial, txtContact;
    private JTable table;
    private DefaultTableModel tableModel;

    public DoctorsList() {
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

        JLabel lblTitle = new JLabel("ADD NEW DOCTOR");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 20, 5);
        formCard.add(lblTitle, gbc);

        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.gridwidth = 2;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = new Color(127, 140, 141);

        // Name
        JLabel lblName = new JLabel("Doctor Name");
        lblName.setFont(labelFont); lblName.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 1; formCard.add(lblName, gbc);
        txtName = new JTextField(); styleTextField(txtName);
        gbc.gridx = 0; gbc.gridy = 2; formCard.add(txtName, gbc);

        // Specialization
        JLabel lblSpec = new JLabel("Specialization");
        lblSpec.setFont(labelFont); lblSpec.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 3; formCard.add(lblSpec, gbc);
        txtSpecial = new JTextField(); styleTextField(txtSpecial);
        gbc.gridx = 0; gbc.gridy = 4; formCard.add(txtSpecial, gbc);

        // Contact
        JLabel lblContact = new JLabel("Contact Number");
        lblContact.setFont(labelFont); lblContact.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 5; formCard.add(lblContact, gbc);
        txtContact = new JTextField(); styleTextField(txtContact);
        gbc.gridx = 0; gbc.gridy = 6; formCard.add(txtContact, gbc);

        // Save Button
        gbc.gridx = 0; gbc.gridy = 7; gbc.insets = new Insets(20, 5, 0, 5);
        JButton btnSave = new JButton("SAVE DOCTOR");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(new Color(41, 128, 185)); // Professional Blue
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        formCard.add(btnSave, gbc);

        add(formCard, BorderLayout.WEST);

        // --- Right Panel ---
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTableTitle = new JLabel("HOSPITAL MEDICAL STAFF / DOCTORS LIST");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTableTitle.setForeground(new Color(44, 62, 80));
        lblTableTitle.setBorder(new EmptyBorder(0, 0, 15, 0));
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Specialization", "Contact"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(tableScroll, BorderLayout.CENTER);

        add(tableCard, BorderLayout.CENTER);
        loadDoctorData();

        btnSave.addActionListener(e -> saveDoctorData());
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

    private void saveDoctorData() {
        String name = txtName.getText();
        String special = txtSpecial.getText();
        String contact = txtContact.getText();

        if (name.isEmpty() || special.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO doctors (name, specialization, contact) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, special);
            pst.setString(3, contact);

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Doctor Added Successfully!");
                txtName.setText(""); txtSpecial.setText(""); txtContact.setText("");
                loadDoctorData();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadDoctorData() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                tableModel.addRow(new String[]{rs.getString("id"), rs.getString("name"), rs.getString("specialization"), rs.getString("contact")});
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}