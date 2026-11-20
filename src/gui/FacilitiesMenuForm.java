package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class FacilitiesMenuForm extends javax.swing.JFrame {

    public FacilitiesMenuForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Facilities Officer Dashboard");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        JPanel sidebar = UiTheme.createSidebar("Facilities Officer");
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        javax.swing.JButton btnOverview = UiTheme.createNavigationButton("Overview");
        javax.swing.JButton btnEquipment = UiTheme.createNavigationButton("Equipment");
        javax.swing.JButton btnIssues = UiTheme.createNavigationButton("Facility Issues");
        javax.swing.JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);
        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnEquipment);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnIssues);
        sidebar.add(navigation, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 18, 12));
        logoutPanel.add(btnLogout);
        sidebar.add(logoutPanel, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.add(UiTheme.createHeader("Facilities Dashboard",
                "Equipment and facility issue overview"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 2, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Equipment",
                String.valueOf(UiTheme.countLines("data/equipment.txt")),
                "Equipment records"));
        cards.add(UiTheme.createStatCard("Open Issues",
                String.valueOf(UiTheme.countLines("data/issues.txt")),
                "Reported issues"));
        content.add(cards, BorderLayout.NORTH);
        content.add(UiTheme.createRecentTablePanel("Recent Facility Issues",
                "data/issues.txt",
                new String[]{"Issue ID", "Description"}),
                BorderLayout.CENTER);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnEquipment.addActionListener(evt
                -> new FileTableForm("Equipment Records",
                        "data/equipment.txt",
                        new String[]{"Equipment ID", "Equipment Name"})
                        .setVisible(true));
        btnIssues.addActionListener(evt
                -> new FileTableForm("Facility Issues",
                        "data/issues.txt",
                        new String[]{"Issue ID", "Description"})
                        .setVisible(true));
        btnLogout.addActionListener(evt -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FacilitiesMenuForm().setVisible(true));
    }
}
