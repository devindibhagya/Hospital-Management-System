package modules;

import database.DBConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class BillingModule extends JPanel {
    private JTextField txtPatientName, txtAmount;
    private JTextArea txtPrescription;
    private JTable table;
    private DefaultTableModel tableModel;

    public BillingModule() {
        setLayout(new BorderLayout(25, 25));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(new Color(245, 247, 250)); // Main Background Color

    
        JPanel formCard = new JPanel();
        formCard.setBackground(Color.WHITE);
        formCard.setLayout(new GridBagLayout());
        formCard.setPreferredSize(new Dimension(380, 500));
        
        // Card Border (Soft shadow effect/Compound Border)
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form Title
        JLabel lblFormTitle = new JLabel("CREATE NEW INVOICE");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFormTitle.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 20, 5);
        formCard.add(lblFormTitle, gbc);
        
        // Reset Insets for fields
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.gridwidth = 1;

        // Label Styling Helper
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = new Color(127, 140, 141);

        // Patient Name Field
        JLabel lblName = new JLabel("Patient Name");
        lblName.setFont(labelFont);
        lblName.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(lblName, gbc);

        txtPatientName = new JTextField();
        styleTextField(txtPatientName);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formCard.add(txtPatientName, gbc);

        // Prescription Field
        JLabel lblPresc = new JLabel("Prescription / Medical Notes");
        lblPresc.setFont(labelFont);
        lblPresc.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formCard.add(lblPresc, gbc);

        txtPrescription = new JTextArea(5, 15);
        txtPrescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPrescription.setLineWrap(true);
        txtPrescription.setWrapStyleWord(true);
        JScrollPane prescScroll = new JScrollPane(txtPrescription);
        prescScroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 214, 221), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formCard.add(prescScroll, gbc);

        // Total Amount Field
        JLabel lblAmount = new JLabel("Total Amount (LKR)");
        lblAmount.setFont(labelFont);
        lblAmount.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        formCard.add(lblAmount, gbc);

        txtAmount = new JTextField();
        styleTextField(txtAmount);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        formCard.add(txtAmount, gbc);

        // Spacer before button
        gbc.gridx = 0; gbc.gridy = 7; gbc.insets = new Insets(15, 5, 0, 5);
        JButton btnSave = new JButton("GENERATE & PRINT BILL");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(new Color(230, 126, 34)); // Professional Orange/Coral
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        formCard.add(btnSave, gbc);

        add(formCard, BorderLayout.WEST);

  #
       
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTableTitle = new JLabel("RECENT INVOICES & BILLING HISTORY");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTableTitle.setForeground(new Color(44, 62, 80));
        lblTableTitle.setBorder(new EmptyBorder(0, 0, 15, 0));
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        // Table Model Settings
        String[] columns = {"Invoice ID", "Patient Name", "Prescription", "Amount", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table cells read-only කිරීම
            }
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30); // Rows වලට හොඳ ඉඩක් ලබාදීම
        table.setGridColor(new Color(240, 240, 240));
        table.setSelectionBackground(new Color(52, 152, 219, 40)); // Soft blue row selection
        table.setSelectionForeground(Color.BLACK);

        // Custom Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 35));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(tableScroll, BorderLayout.CENTER);

        add(tableCard, BorderLayout.CENTER);

        // Database Load
        loadBillingData();

        // Button Action Listener
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBillingData();
            }
        });
    }

    // Text Field වල Flat Design එක හදන Helper Method එකක්
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 214, 221), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }

    private void saveBillingData() {
        String name = txtPatientName.getText();
        String prescription = txtPrescription.getText();
        String amountStr = txtAmount.getText();
        String todayDate = LocalDate.now().toString();

        if (name.isEmpty() || prescription.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO billing (patient_name, prescription, amount, billing_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, prescription);
            pst.setDouble(3, amount);
            pst.setString(4, todayDate);

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Invoice Generated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtPatientName.setText("");
                txtPrescription.setText("");
                txtAmount.setText("");
                loadBillingData();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBillingData() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM billing");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("patient_name"),
                    rs.getString("prescription"),
                    "LKR " + String.format("%.2f", rs.getDouble("amount")),
                    rs.getDate("billing_date"),
                    rs.getString("status")
                });
            }
        } catch (Exception ex) {
            System.out.println("Error loading billing data: " + ex.getMessage());
        }
    }
}
