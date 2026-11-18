package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AttendanceMenuForm extends javax.swing.JFrame {

    public AttendanceMenuForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Attendance Officer Dashboard");
        setSize(1180, 720);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        JPanel sidebar = UiTheme.createSidebar("Attendance Officer");
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        javax.swing.JButton btnOverview = UiTheme.createNavigationButton("Overview");
        javax.swing.JButton btnRecords = UiTheme.createNavigationButton("Attendance Records");
        javax.swing.JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);
        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnRecords);
        sidebar.add(navigation, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 18, 12));
        logoutPanel.add(btnLogout);
        sidebar.add(logoutPanel, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.add(UiTheme.createHeader("Attendance Dashboard",
                "Review and manage attendance records"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        int records = UiTheme.countLines("data/attendance.txt");
        cards.add(UiTheme.createStatCard("Attendance Records",
                String.valueOf(records), "Recorded entries"));
        cards.add(UiTheme.createStatCard("Present", "--", "Daily summary"));
        cards.add(UiTheme.createStatCard("Absent", "--", "Daily summary"));
        content.add(cards, BorderLayout.NORTH);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnRecords.addActionListener(evt
                -> new FileTableForm("Attendance Records",
                        "data/attendance.txt",
                        new String[]{"Student ID", "Date", "Status"})
                        .setVisible(true));
        btnLogout.addActionListener(evt -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AttendanceMenuForm().setVisible(true));
    }
}
