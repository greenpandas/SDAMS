package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import service.InstructorService;

public class InstructorMenuForm extends javax.swing.JFrame {

    private final InstructorService instructorService = new InstructorService();

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

        JButton btnOverview = UiTheme.createNavigationButton("Overview");
        JButton btnCreateSchedule = UiTheme.createNavigationButton("Create Schedule");
        JButton btnViewSchedule = UiTheme.createNavigationButton("View Schedule");
        JButton btnReportIssue = UiTheme.createNavigationButton("Report Issue");
        JButton btnRatings = UiTheme.createNavigationButton("View Ratings");
        JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);

        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnCreateSchedule);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnViewSchedule);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnReportIssue);
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

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Classes",
                String.valueOf(UiTheme.countLines("data/classes.txt")),
                "Available class records"));
        cards.add(UiTheme.createStatCard("Total Ratings",
                String.valueOf(UiTheme.countLines("data/ratings.txt")),
                "Student rating records"));
        cards.add(UiTheme.createStatCard("Reported Issues",
                String.valueOf(UiTheme.countLines("data/issues.txt")),
                "Facility issue records"));
        content.add(cards, BorderLayout.NORTH);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnCreateSchedule.addActionListener(evt -> createSchedule());
        btnViewSchedule.addActionListener(evt -> showRecords(
                "Class Schedule", "data/classes.txt",
                new String[]{"Class ID", "Name", "Date", "Time"}));
        btnReportIssue.addActionListener(evt -> reportIssue());
        btnRatings.addActionListener(evt -> showRecords(
                "Rating Records", "data/ratings.txt",
                new String[]{"Student ID", "Instructor ID", "Rating"}));
        btnLogout.addActionListener(evt -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    private void createSchedule() {
        JPanel form = createForm("Class ID", "Class Name", "Date", "Time");
        JTextField[] fields = fieldsOf(form);
        if (showForm(form, "Create Schedule")) {
            boolean saved = instructorService.createSchedule(
                    fields[0].getText(), fields[1].getText(),
                    fields[2].getText(), fields[3].getText());
            showSaveResult(saved, "Schedule created.",
                    "Could not save the schedule.");
        }
    }

    private void reportIssue() {
        JPanel form = createForm("Issue ID", "Issue Description");
        JTextField[] fields = fieldsOf(form);
        if (showForm(form, "Report Issue")) {
            boolean saved = instructorService.reportIssue(
                    fields[0].getText(), fields[1].getText());
            showSaveResult(saved, "Issue reported.",
                    "Could not save the issue.");
        }
    }

    private JPanel createForm(String... labels) {
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 8));
        for (String labelText : labels) {
            JLabel label = UiTheme.createFieldLabel(labelText);
            JTextField field = new JTextField();
            UiTheme.styleField(field);
            label.setLabelFor(field);
            form.add(label);
            form.add(field);
        }
        return form;
    }

    private JTextField[] fieldsOf(JPanel form) {
        JTextField[] fields = new JTextField[form.getComponentCount() / 2];
        int fieldIndex = 0;
        for (int i = 1; i < form.getComponentCount(); i += 2) {
            fields[fieldIndex++] = (JTextField) form.getComponent(i);
        }
        return fields;
    }

    private boolean showForm(JPanel form, String title) {
        while (JOptionPane.showConfirmDialog(this, form, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
                == JOptionPane.OK_OPTION) {
            boolean valid = true;
            for (int i = 1; i < form.getComponentCount(); i += 2) {
                String value = ((JTextField) form.getComponent(i)).getText().trim();
                if (value.isEmpty() || value.contains(",")) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return true;
            }
            showError("Complete every field and remove any commas.");
        }
        return false;
    }

    private void showRecords(String title, String filePath, String[] columns) {
        new FileTableForm(title, filePath, columns).setVisible(true);
    }

    private void showSaveResult(boolean saved, String successMessage,
            String errorMessage) {
        if (saved) {
            JOptionPane.showMessageDialog(this, successMessage, "Instructor Dashboard",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            showError(errorMessage);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Instructor Dashboard",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new InstructorMenuForm().setVisible(true));
    }
}
