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

public class ManagerMenuForm extends javax.swing.JFrame {

    public ManagerMenuForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Manager Dashboard");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        JPanel sidebar = UiTheme.createSidebar("Manager");
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        javax.swing.JButton btnOverview = UiTheme.createNavigationButton("Overview");
        javax.swing.JButton btnManageAdmins = UiTheme.createNavigationButton("Manage Administrators");
        javax.swing.JButton btnViewStaff = UiTheme.createNavigationButton("View Staff");
        javax.swing.JButton btnReports = UiTheme.createNavigationButton("Reports");
        javax.swing.JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);

        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnManageAdmins);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnViewStaff);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnReports);
        sidebar.add(navigation, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 18, 12));
        btnLogout.setAlignmentX(LEFT_ALIGNMENT);
        logoutPanel.add(btnLogout);
        sidebar.add(logoutPanel, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.add(UiTheme.createHeader("Manager Dashboard",
                "Overview of academy operations"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 2, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        ArrayList<User> users = new FileManager().readUsers();
        int administrators = 0;
        int staff = 0;
        int students = 0;
        for (User user : users) {
            if ("Administrator".equals(user.getRole())) {
                administrators++;
            } else if ("Student".equals(user.getRole())) {
                students++;
            } else {
                staff++;
            }
        }

        double revenue = UiTheme.readPaymentRevenue("data/payments.txt");
        cards.add(UiTheme.createStatCard("Total Administrators",
                String.valueOf(administrators), "Active administrator accounts"));
        cards.add(UiTheme.createStatCard("Total Students",
                String.valueOf(students), "Registered student accounts"));
        cards.add(UiTheme.createStatCard("Total Staff",
                String.valueOf(staff), "Manager and officer accounts"));
        cards.add(UiTheme.createStatCard("Total Revenue",
                String.format("$%.2f", revenue), "Payment records"));

        content.add(cards, BorderLayout.NORTH);
        JPanel welcome = new JPanel(new BorderLayout());
        welcome.setOpaque(false);
        welcome.setBorder(BorderFactory.createEmptyBorder(12, 24, 24, 24));
        javax.swing.JLabel message = new javax.swing.JLabel(
                "<html><b>Quick actions</b><br>"
                + "Use the navigation panel to manage administrators, staff and reports.</html>");
        message.setForeground(UiTheme.MUTED);
        welcome.add(message, BorderLayout.NORTH);
        content.add(welcome, BorderLayout.CENTER);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnManageAdmins.addActionListener(evt -> {
            new AdminManagementForm().setVisible(true);
            dispose();
        });
        btnViewStaff.addActionListener(evt
                -> new StaffViewForm().setVisible(true));
        btnReports.addActionListener(evt
                -> new ReportsForm().setVisible(true));
        btnLogout.addActionListener(evt -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ManagerMenuForm().setVisible(true));
    }
}
