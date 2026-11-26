package gui;

import service.PaymentService;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import filehandling.FileManager;
import model.User;

public class PaymentManagementForm extends JFrame {

    private final PaymentService paymentService = new PaymentService();
    private final DefaultTableModel tableModel;
    private final JTable paymentTable;
    private final JTextField txtStudentId = new JTextField();
    private final JTextField txtAmount = new JTextField();
    private final JTextField txtPaymentDate = new JTextField();

    public PaymentManagementForm() {
        UiTheme.applyDefaults();

        tableModel = new DefaultTableModel(
                new Object[]{"Student ID", "Student Name", "Amount", "Payment Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paymentTable = new JTable(tableModel);
        UiTheme.styleTable(paymentTable);

        initComponents();
        setTitle("SDAMS - Payment Management");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
        setResizable(true);
        loadPayments();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CONTENT);
        root.add(UiTheme.createHeader("Payment Management",
                "Add, search, view and remove payment records"),
                BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(18, 0));
        body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        body.setOpaque(false);

        JScrollPane tableScroll = UiTheme.createTableScroll(paymentTable);
        body.add(tableScroll, BorderLayout.WEST);

        JPanel form = UiTheme.createCardPanel();
        form.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 6, 6, 6);

        UiTheme.styleField(txtStudentId);
        UiTheme.styleField(txtAmount);
        UiTheme.styleField(txtPaymentDate);
        txtPaymentDate.setToolTipText("Use yyyy-MM-dd, for example 2026-11-19");

        addField(form, c, 0, "Student ID *", txtStudentId);
        addField(form, c, 2, "Amount *", txtAmount);
        addField(form, c, 4, "Payment Date *", txtPaymentDate);

        JPanel actions = new JPanel(new GridLayout(3, 2, 8, 8));
        actions.setOpaque(false);
        JButton btnAdd = UiTheme.createButton("Add Payment", UiTheme.SUCCESS);
        JButton btnSearch = UiTheme.createButton("Search Payment", UiTheme.PRIMARY);
        JButton btnDelete = UiTheme.createButton("Delete Payment", UiTheme.DANGER);
        JButton btnClear = UiTheme.createButton("Clear", new java.awt.Color(71, 85, 105));
        JButton btnViewAll = UiTheme.createButton("View All", UiTheme.PRIMARY);
        JButton btnBack = UiTheme.createButton("Back", new java.awt.Color(71, 85, 105));

        actions.add(btnAdd);
        actions.add(btnSearch);
        actions.add(btnDelete);
        actions.add(btnClear);
        actions.add(btnViewAll);
        actions.add(btnBack);

        c.gridy = 6;
        c.insets = new Insets(14, 6, 6, 6);
        form.add(actions, c);
        body.add(form, BorderLayout.CENTER);

        root.add(body, BorderLayout.CENTER);
        setContentPane(root);

        btnAdd.addActionListener(evt -> addPayment());
        btnSearch.addActionListener(evt -> searchPayments());
        btnDelete.addActionListener(evt -> deletePayment());
        btnClear.addActionListener(evt -> clearForm());
        btnViewAll.addActionListener(evt -> loadPayments());
        btnBack.addActionListener(evt -> dispose());

        paymentTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()
                    && paymentTable.getSelectedRow() >= 0) {
                int row = paymentTable.convertRowIndexToModel(
                        paymentTable.getSelectedRow());
                txtStudentId.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                txtAmount.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                txtPaymentDate.setText(String.valueOf(tableModel.getValueAt(row, 3)));
            }
        });
    }

    private void addField(JPanel panel, GridBagConstraints c, int gridY,
                          String labelText, JTextField field) {
        c.gridy = gridY;
        c.insets = new Insets(6, 6, 2, 6);
        panel.add(UiTheme.createFieldLabel(labelText), c);
        c.gridy = gridY + 1;
        c.insets = new Insets(2, 6, 10, 6);
        panel.add(field, c);
    }

    private void addPayment() {
        String studentId = txtStudentId.getText().trim();
        String amount = txtAmount.getText().trim();
        String date = txtPaymentDate.getText().trim();

        if (studentId.isEmpty() || amount.isEmpty() || date.isEmpty()) {
            showError("Student ID, Amount and Payment Date are required.");
            return;
        }

        try {
            BigDecimal value = new BigDecimal(amount);
            if (value.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Amount must be greater than zero.");
                return;
            }
            amount = value.setScale(2, RoundingMode.HALF_UP).toPlainString();
            LocalDate.parse(date);
        } catch (NumberFormatException ex) {
            showError("Amount must be a valid number.");
            return;
        } catch (DateTimeParseException ex) {
            showError("Payment Date must use yyyy-MM-dd.");
            return;
        }

        if (paymentService.addPayment(studentId, amount, date)) {
            JOptionPane.showMessageDialog(this,
                    "Payment added successfully.",
                    "Payment Added",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadPayments();
        } else {
            showError("The payment could not be added.");
        }
    }

    private void searchPayments() {
        String query = txtStudentId.getText().trim();
        if (query.isEmpty()) {
            showError("Enter a Student ID to search.");
            return;
        }

        tableModel.setRowCount(0);
        int matches = 0;
        for (String[] payment : paymentService.readPayments()) {
            if (payment[0].toLowerCase(java.util.Locale.ROOT)
                    .contains(query.toLowerCase(java.util.Locale.ROOT))) {
                tableModel.addRow(new Object[]{
                    payment[0], findStudentName(payment[0]), payment[1], payment[2]
                });
                matches++;
            }
        }

        if (matches == 0) {
            tableModel.addRow(new Object[]{"No matching payments", "", "", ""});
        }
        setTitle("SDAMS - Payment Management (" + matches + " found)");
    }

    private void deletePayment() {
        String studentId = txtStudentId.getText().trim();
        if (studentId.isEmpty()) {
            showError("Select or enter a Student ID to delete.");
            return;
        }

        int confirmed = JOptionPane.showConfirmDialog(this,
                "Delete all payment records for Student ID " + studentId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirmed != JOptionPane.YES_OPTION) {
            return;
        }

        if (paymentService.deletePayment(studentId)) {
            JOptionPane.showMessageDialog(this,
                    "Payment record deleted.",
                    "Payment Deleted",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadPayments();
        } else {
            showError("No matching payment record was found.");
        }
    }

    private void loadPayments() {
        tableModel.setRowCount(0);
        ArrayList<String[]> payments = paymentService.readPayments();
        if (payments.isEmpty()) {
            tableModel.addRow(new Object[]{"No payment records", "", "", ""});
        } else {
            for (String[] payment : payments) {
                tableModel.addRow(new Object[]{
                    payment[0], findStudentName(payment[0]), payment[1], payment[2]
                });
            }
        }
        setTitle("SDAMS - Payment Management");
    }

    private String findStudentName(String studentId) {
        for (User user : new FileManager().readUsers()) {
            if ("Student".equals(user.getRole())
                    && user.getUserId().equalsIgnoreCase(studentId)) {
                return user.getName();
            }
        }
        return "Unknown Student";
    }

    private void clearForm() {
        txtStudentId.setText("");
        txtAmount.setText("");
        txtPaymentDate.setText("");
        paymentTable.clearSelection();
        txtStudentId.requestFocusInWindow();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() ->
                new PaymentManagementForm().setVisible(true));
    }
}
