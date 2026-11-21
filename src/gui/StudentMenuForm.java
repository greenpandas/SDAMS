package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.User;

public class StudentMenuForm extends JFrame {

    private final User currentUser;

    private JButton btnViewClasses;
    private JButton btnBookClass;
    private JButton btnViewBookings;
    private JButton btnViewPayments;
    private JButton btnSubmitRatings;
    private JButton btnLogout;

    public StudentMenuForm() {
        this(null);
    }

    public StudentMenuForm(User currentUser) {
        this.currentUser = currentUser;
        initComponents();
        setTitle("SDAMS - Student Menu");
        setSize(920, 620);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CONTENT);

        // Left Sidebar
        JPanel sidebar = UiTheme.createSidebar("Student Role");

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(UiTheme.SIDEBAR);
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 20, 12));

        btnViewClasses = UiTheme.createNavigationButton("View Classes");
        btnBookClass = UiTheme.createNavigationButton("Book Class");
        btnViewBookings = UiTheme.createNavigationButton("View Bookings");
        btnViewPayments = UiTheme.createNavigationButton("View Payments");
        btnSubmitRatings = UiTheme.createNavigationButton("Submit Ratings");
        btnLogout = UiTheme.createButton("Logout", UiTheme.DANGER);
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        navPanel.add(btnViewClasses);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnBookClass);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnViewBookings);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnViewPayments);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnSubmitRatings);
        navPanel.add(Box.createVerticalGlue());
        navPanel.add(btnLogout);

        sidebar.add(navPanel, BorderLayout.CENTER);
        root.add(sidebar, BorderLayout.WEST);

        // Main Content Area
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(UiTheme.CONTENT);

        String studentName = (currentUser != null && currentUser.getUsername() != null)
                ? currentUser.getUsername()
                : "Student";

        JPanel header = UiTheme.createHeader(
                "Student Dashboard",
                "Welcome, " + studentName + " — manage your classes, bookings, payments, and ratings"
        );
        contentArea.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(UiTheme.CONTENT);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JPanel welcomeCard = new JPanel(new BorderLayout(0, 8));
        welcomeCard.setBackground(UiTheme.CARD);
        welcomeCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));

        JLabel cardTitle = new JLabel("Quick Actions");
        cardTitle.setFont(UiTheme.SEGOU_UI_BOLD);
        cardTitle.setForeground(UiTheme.TEXT);

        JLabel cardDesc = new JLabel(
                "Select an option from the left menu to View Classes, Book Class, View Bookings, View Payments, or Submit Ratings."
        );
        cardDesc.setFont(UiTheme.SEGOU_UI);
        cardDesc.setForeground(UiTheme.MUTED);

        welcomeCard.add(cardTitle, BorderLayout.NORTH);
        welcomeCard.add(cardDesc, BorderLayout.CENTER);

        centerPanel.add(welcomeCard, BorderLayout.NORTH);
        contentArea.add(centerPanel, BorderLayout.CENTER);

        root.add(contentArea, BorderLayout.CENTER);
        setContentPane(root);

        setupListeners();
    }

    private void setupListeners() {
        btnViewClasses.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "View Classes clicked."));

        btnBookClass.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Book Class clicked."));

        btnViewBookings.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "View Bookings clicked."));

        btnViewPayments.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "View Payments clicked."));

        btnSubmitRatings.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Submit Ratings clicked."));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginForm().setVisible(true);
                this.dispose();
            }
        });
    }

    public static void main(String[] args) {
        UiTheme.applyDefaults();
        java.awt.EventQueue.invokeLater(() -> new StudentMenuForm().setVisible(true));
    }
}