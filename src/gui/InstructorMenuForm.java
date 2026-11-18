package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InstructorMenuForm extends javax.swing.JFrame {

    public InstructorMenuForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Instructor Dashboard");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        JPanel sidebar = UiTheme.createSidebar("Instructor");
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        javax.swing.JButton btnOverview = UiTheme.createNavigationButton("Overview");
        javax.swing.JButton btnClasses = UiTheme.createNavigationButton("My Classes");
        javax.swing.JButton btnRatings = UiTheme.createNavigationButton("Ratings");
        javax.swing.JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);
        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnClasses);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnRatings);
        sidebar.add(navigation, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 18, 12));
        logoutPanel.add(btnLogout);
        sidebar.add(logoutPanel, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.add(UiTheme.createHeader("Instructor Dashboard",
                "Classes, students and performance"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 2, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Classes",
                String.valueOf(UiTheme.countLines("data/classes.txt")),
                "Available class records"));
        cards.add(UiTheme.createStatCard("Total Ratings",
                String.valueOf(UiTheme.countLines("data/ratings.txt")),
                "Student rating records"));
        content.add(cards, BorderLayout.NORTH);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnLogout.addActionListener(evt -> {
            new LoginForm().setVisible(true);
            dispose();
        });
        btnClasses.addActionListener(evt
                -> new FileTableForm("Class Records",
                        "data/classes.txt",
                        new String[]{"Class ID", "Name", "Schedule", "Instructor"})
                        .setVisible(true));
        btnRatings.addActionListener(evt
                -> new FileTableForm("Rating Records",
                        "data/ratings.txt",
                        new String[]{"Student ID", "Instructor ID", "Rating"})
                        .setVisible(true));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new InstructorMenuForm().setVisible(true));
    }
}
