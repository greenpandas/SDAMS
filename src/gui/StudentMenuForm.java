package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class StudentMenuForm extends javax.swing.JFrame {

    public StudentMenuForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Student Dashboard");
        setSize(1180, 720);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        JPanel sidebar = UiTheme.createSidebar("Student");
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        javax.swing.JButton btnOverview = UiTheme.createNavigationButton("Overview");
        javax.swing.JButton btnClasses = UiTheme.createNavigationButton("Available Classes");
        javax.swing.JButton btnBookings = UiTheme.createNavigationButton("My Bookings");
        javax.swing.JButton btnRating = UiTheme.createNavigationButton("Submit Rating");
        javax.swing.JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);
        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnClasses);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnBookings);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnRating);
        sidebar.add(navigation, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 18, 12));
        logoutPanel.add(btnLogout);
        sidebar.add(logoutPanel, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.add(UiTheme.createHeader("Student Dashboard",
                "Classes, bookings and academy activity"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Bookings",
                String.valueOf(UiTheme.countLines("data/bookings.txt")),
                "Academy booking records"));
        cards.add(UiTheme.createStatCard("Available Classes",
                String.valueOf(UiTheme.countLines("data/classes.txt")),
                "Published class records"));
        cards.add(UiTheme.createStatCard("Attendance",
                String.valueOf(UiTheme.countLines("data/attendance.txt")),
                "Recorded attendance entries"));
        content.add(cards, BorderLayout.NORTH);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnLogout.addActionListener(evt -> {
            new LoginForm().setVisible(true);
            dispose();
        });
        btnClasses.addActionListener(evt
                -> new FileTableForm("Available Classes",
                        "data/classes.txt",
                        new String[]{"Class ID", "Name", "Schedule", "Instructor"})
                        .setVisible(true));
        btnBookings.addActionListener(evt
                -> new FileTableForm("Booking Records",
                        "data/bookings.txt",
                        new String[]{"Student ID", "Class ID", "Status"})
                        .setVisible(true));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new StudentMenuForm().setVisible(true));
    }
}
