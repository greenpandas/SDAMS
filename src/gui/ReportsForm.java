package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import filehandling.FileManager;
import model.User;

public class ReportsForm extends JFrame {

    private final DefaultTableModel reportModel =
            new DefaultTableModel(new Object[]{"Report", "Value"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
    private final JTable reportTable = new JTable(reportModel);

    public ReportsForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Reports");
        setSize(900, 560);
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);
        showRevenueReport();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CONTENT);
        root.add(UiTheme.createHeader("Reports",
                "Operational summaries for the academy"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 2, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Revenue",
                "$" + UiTheme.readPaymentRevenueExact("data/payments.txt").toPlainString(),
                "From payment records"));
        java.math.BigDecimal revenue =
                UiTheme.readPaymentRevenueExact("data/payments.txt");
        int paymentCount = UiTheme.countLines("data/payments.txt");
        java.math.BigDecimal average = paymentCount == 0
                ? java.math.BigDecimal.ZERO
                : revenue.divide(java.math.BigDecimal.valueOf(paymentCount),
                        2, java.math.RoundingMode.HALF_UP);
        cards.add(UiTheme.createStatCard("Average Payment",
                "$" + average.toPlainString(),
                paymentCount + " total payments"));
        root.add(cards, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(12, 12));
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(0, 24, 24, 24));

        UiTheme.styleTable(reportTable);
        JScrollPane tableScroll = new JScrollPane(reportTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(UiTheme.BORDER));
        tableScroll.setPreferredSize(new Dimension(0, 190));
        south.add(tableScroll, BorderLayout.CENTER);

        JPanel actions = new JPanel(new GridLayout(1, 4, 10, 10));
        actions.setOpaque(false);
        JButton revenueButton =
                UiTheme.createButton("Revenue Report", UiTheme.PRIMARY);
        JButton staffButton =
                UiTheme.createButton("Staff Report", UiTheme.PRIMARY);
        JButton exportButton =
                UiTheme.createButton("Export", UiTheme.WARNING);
        JButton backButton =
                UiTheme.createButton("Back", new java.awt.Color(71, 85, 105));
        actions.add(revenueButton);
        actions.add(staffButton);
        actions.add(exportButton);
        actions.add(backButton);
        south.add(actions, BorderLayout.SOUTH);

        root.add(south, BorderLayout.SOUTH);
        setContentPane(root);

        revenueButton.addActionListener(evt -> showRevenueReport());
        staffButton.addActionListener(evt -> showStaffReport());
        exportButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this,
                        "CSV export can be added once the report view is finalized.",
                        "Export",
                        JOptionPane.INFORMATION_MESSAGE));
        backButton.addActionListener(evt -> dispose());
    }

    private void showRevenueReport() {
        reportModel.setRowCount(0);
        java.math.BigDecimal revenue =
                UiTheme.readPaymentRevenueExact("data/payments.txt");
        int payments = UiTheme.countLines("data/payments.txt");
        java.math.BigDecimal average = payments == 0
                ? java.math.BigDecimal.ZERO
                : revenue.divide(java.math.BigDecimal.valueOf(payments),
                        2, java.math.RoundingMode.HALF_UP);
        reportModel.addRow(new Object[]{"Total Revenue",
                "$" + revenue.toPlainString()});
        reportModel.addRow(new Object[]{"Total Payments",
                String.valueOf(payments)});
        reportModel.addRow(new Object[]{"Average Payment",
                "$" + average.toPlainString()});
    }

    private void showStaffReport() {
        reportModel.setRowCount(0);
        int staff = 0;
        int administrators = 0;
        int instructors = 0;
        ArrayList<User> users = new FileManager().readUsers();
        for (User user : users) {
            String role = user.getRole();
            if ("Administrator".equals(role)) {
                administrators++;
                staff++;
            } else if ("Instructor".equals(role)) {
                instructors++;
                staff++;
            } else if (!"Student".equals(role) && !"Manager".equals(role)) {
                staff++;
            } else if ("Manager".equals(role)) {
                staff++;
            }
        }
        reportModel.addRow(new Object[]{"Total Staff", String.valueOf(staff)});
        reportModel.addRow(new Object[]{"Total Administrators",
                String.valueOf(administrators)});
        reportModel.addRow(new Object[]{"Total Instructors",
                String.valueOf(instructors)});
    }
}
