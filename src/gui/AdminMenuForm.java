package gui;

import filehandling.FileManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.User;

public class AdminMenuForm extends javax.swing.JFrame {

    public AdminMenuForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Administrator Dashboard");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        JPanel sidebar = UiTheme.createSidebar("Administrator");
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        javax.swing.JButton btnOverview = UiTheme.createNavigationButton("Overview");
        javax.swing.JButton btnManageStudents = UiTheme.createNavigationButton("Manage Students");
        javax.swing.JButton btnPayments = UiTheme.createNavigationButton("Payments");
        javax.swing.JButton btnStaff = UiTheme.createNavigationButton("View Staff");
        javax.swing.JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);

        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnManageStudents);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnPayments);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnStaff);
        sidebar.add(navigation, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 18, 12));
        logoutPanel.add(btnLogout);
        sidebar.add(logoutPanel, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.add(UiTheme.createHeader("Administrator Dashboard",
                "Manage academy students and payments"), BorderLayout.NORTH);

        int studentCount = 0;
        ArrayList<User> users = new FileManager().readUsers();
        for (User user : users) {
            if ("Student".equals(user.getRole())) {
                studentCount++;
            }
        }

        int staffCount = 0;
        for (User user : users) {
            if (!"Student".equals(user.getRole())) {
                staffCount++;
            }
        }

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Students",
                String.valueOf(studentCount), "Registered student accounts"));
        cards.add(UiTheme.createStatCard("Total Payments",
                String.valueOf(UiTheme.countLines("data/payments.txt")), "Payment records"));
        cards.add(UiTheme.createStatCard("Total Staff",
                String.valueOf(staffCount), "Non-student accounts"));
        content.add(cards, BorderLayout.NORTH);
        content.add(UiTheme.createRecentTablePanel("Recent Payments",
                "data/payments.txt",
                new String[]{"Student ID", "Amount", "Payment Date"}),
                BorderLayout.CENTER);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnManageStudents.addActionListener(evt -> {
            new StudentManagementForm().setVisible(true);
            dispose();
        });
        btnOverview.addActionListener(evt -> {
            AdminMenuForm refreshed = new AdminMenuForm();
            refreshed.setBounds(getBounds());
            refreshed.setVisible(true);
            dispose();
        });
        btnPayments.addActionListener(evt
                -> new PaymentManagementForm().setVisible(true));
        btnStaff.addActionListener(evt
                -> new StaffViewForm().setVisible(true));
        btnLogout.addActionListener(evt -> {
            if (javax.swing.JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to log out?", "Logout",
                    javax.swing.JOptionPane.YES_NO_OPTION)
                    == javax.swing.JOptionPane.YES_OPTION) {
                new LoginForm().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AdminMenuForm().setVisible(true));
    }
}
