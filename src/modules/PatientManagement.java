package modules;

import database.DBConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.*;

public class PatientManagement extends JPanel {
    private JTextField txtName, txtAge, txtContact, txtSearch; 
    private JComboBox<String> cmbGender;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public PatientManagement() {
        setLayout(new BorderLayout(25, 25));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(new Color(245, 247, 250));

        // --- Left Panel: Input Card ---
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

        JLabel lblTitle = new JLabel("REGISTER NEW PATIENT");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 20, 5);
        formCard.add(lblTitle, gbc);

        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.gridwidth = 1;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = new Color(127, 140, 141);

        // Name
        JLabel lblName = new JLabel("Patient Name");
        lblName.setFont(labelFont); lblName.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 1; formCard.add(lblName, gbc);
        txtName = new JTextField(); styleTextField(txtName);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; formCard.add(txtName, gbc);

        // Age
        gbc.gridwidth = 1;
        JLabel lblAge = new JLabel("Age");
        lblAge.setFont(labelFont); lblAge.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 3; formCard.add(lblAge, gbc);
        txtAge = new JTextField(); styleTextField(txtAge);
        gbc.gridx = 0; gbc.gridy = 4; formCard.add(txtAge, gbc);

        // Gender
        JLabel lblGender = new JLabel("Gender");
        lblGender.setFont(labelFont); lblGender.setForeground(labelColor);
        gbc.gridx = 1; gbc.gridy = 3; formCard.add(lblGender, gbc);
        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        cmbGender.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 4; formCard.add(cmbGender, gbc);

        // Contact
        gbc.gridwidth = 2;
        JLabel lblContact = new JLabel("Contact Number");
        lblContact.setFont(labelFont); lblContact.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 5; formCard.add(lblContact, gbc);
        txtContact = new JTextField(); styleTextField(txtContact);
        gbc.gridx = 0; gbc.gridy = 6; formCard.add(txtContact, gbc);

        // Save Button
        gbc.gridx = 0; gbc.gridy = 7; gbc.insets = new Insets(20, 5, 0, 5);
        JButton btnSave = new JButton("SAVE PATIENT DETAILS");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        formCard.add(btnSave, gbc);

        add(formCard, BorderLayout.WEST);

        // --- Right Panel: Table Card ---
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // --- TOP SEARCH BAR CONTAINER ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel lblTableTitle = new JLabel("REGISTERED PATIENTS LIST");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTableTitle.setForeground(new Color(44, 62, 80));
        headerPanel.add(lblTableTitle, BorderLayout.WEST);

        // Search Field Implementation
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchBarPanel.setBackground(Color.WHITE);
        
        JLabel lblSearch = new JLabel("Search: ");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSearch.setForeground(new Color(127, 140, 141));
        searchBarPanel.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 30));
        styleTextField(txtSearch);
        searchBarPanel.add(txtSearch);
        headerPanel.add(searchBarPanel, BorderLayout.EAST);

        tableCard.add(headerPanel, BorderLayout.NORTH);

        // Table Implementation
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Gender", "Contact"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        styleTable(table);

        // Live Filter එක RowSorter එකට සම්බන්ධ කිරීම
        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(tableScroll, BorderLayout.CENTER);

        add(tableCard, BorderLayout.CENTER);
        loadPatientData();

        // --- LIVE SEARCH LOGIC (Document Listener) ---
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { searchTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { searchTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { searchTable(); }

            private void searchTable() {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null); 
                } else {
                    
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        btnSave.addActionListener(e -> savePatientData());
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

    private void savePatientData() {
        String name = txtName.getText();
        String age = txtAge.getText();
        String gender = cmbGender.getSelectedItem().toString();
        String contact = txtContact.getText();

        if (name.isEmpty() || age.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO patients (name, age, gender, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, age);
            pst.setString(3, gender);
            pst.setString(4, contact);

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Patient Registered Successfully!");
                txtName.setText(""); txtAge.setText(""); txtContact.setText("");
                loadPatientData();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadPatientData() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
            while (rs.next()) {
                tableModel.addRow(new String[]{rs.getString("id"), rs.getString("name"), rs.getString("age"), rs.getString("gender"), rs.getString("contact")});
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
