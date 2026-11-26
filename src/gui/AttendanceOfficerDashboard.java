package gui;

import filehandling.FileManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class AttendanceOfficerDashboard extends JFrame {

    private static final String ATTENDANCE_FILE = "data/attendance.txt";
    private static final String STUDENTS_FILE = "data/students.txt";

    private JButton btnRecordAttendance;
    private JButton btnViewAttendance;
    private JButton btnLogout;

    public AttendanceOfficerDashboard() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Attendance Officer Dashboard");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());

        // ----- Sidebar with the three buttons -----
        JPanel sidebar = UiTheme.createSidebar("Attendance Officer");

        JButton btnOverview = UiTheme.createNavigationButton("Overview");
        btnRecordAttendance = UiTheme.createNavigationButton("Record Attendance");
        btnViewAttendance = UiTheme.createNavigationButton("View Attendance");
        btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);

        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnRecordAttendance);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnViewAttendance);
        sidebar.add(navigation, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 18, 12));
        logoutPanel.add(btnLogout, BorderLayout.CENTER);
        sidebar.add(logoutPanel, BorderLayout.SOUTH);

        // ----- Main content -----
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Records",
                String.valueOf(UiTheme.countLines(ATTENDANCE_FILE)),
                "All attendance entries"));
        cards.add(UiTheme.createStatCard("Present",
                String.valueOf(countStatus("Present")), "Marked present"));
        cards.add(UiTheme.createStatCard("Absent / Late",
                countStatus("Absent") + " / " + countStatus("Late"),
                "Needs follow-up"));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(UiTheme.createHeader("Attendance Officer Dashboard",
                "Record and review student attendance"), BorderLayout.NORTH);
        top.add(cards, BorderLayout.CENTER);
        content.add(top, BorderLayout.NORTH);
        content.add(UiTheme.createRecentTablePanel("Recent Attendance",
                ATTENDANCE_FILE,
                new String[]{"Student ID", "Date", "Status"}),
                BorderLayout.CENTER);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        // ----- Actions -----
        btnOverview.addActionListener(evt -> refreshDashboard());
        btnRecordAttendance.addActionListener(evt -> showRecordDialog());
        btnViewAttendance.addActionListener(evt
                -> new FileTableForm("Attendance Records", ATTENDANCE_FILE,
                        new String[]{"Student ID", "Date", "Status"})
                        .setVisible(true));
        btnLogout.addActionListener(evt -> logout());
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to log out?", "Logout",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            new LoginForm().setVisible(true);
            dispose();
        }
    }

    // ----- Record Attendance dialog -----
    private void showRecordDialog() {
        JDialog dialog = new JDialog(this, "Record Attendance", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JTextField txtStudentId = new JTextField();
        JTextField txtDate = new JTextField(LocalDate.now().toString());
        JComboBox<String> cmbStatus =
                new JComboBox<>(new String[]{"Present", "Absent", "Late"});
        UiTheme.styleField(txtStudentId);
        UiTheme.styleField(txtDate);
        cmbStatus.setPreferredSize(new Dimension(260, 38));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UiTheme.CONTENT);
        form.setBorder(BorderFactory.createEmptyBorder(24, 24, 12, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(UiTheme.createFieldLabel("Student ID"), gbc);
        gbc.gridx = 1;
        form.add(txtStudentId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(UiTheme.createFieldLabel("Date (YYYY-MM-DD)"), gbc);
        gbc.gridx = 1;
        form.add(txtDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(UiTheme.createFieldLabel("Status"), gbc);
        gbc.gridx = 1;
        form.add(cmbStatus, gbc);

        JButton btnSave = UiTheme.createButton("Save", UiTheme.SUCCESS);
        JButton btnCancel = UiTheme.createButton("Cancel", UiTheme.MUTED);
        JPanel buttons = new JPanel();
        buttons.setBackground(UiTheme.CONTENT);
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 24, 20, 24));
        buttons.add(btnSave);
        buttons.add(btnCancel);

        btnSave.addActionListener(evt -> {
            String studentId = txtStudentId.getText().trim().toUpperCase();
            String date = txtDate.getText().trim();
            String status = (String) cmbStatus.getSelectedItem();

            if (studentId.isEmpty()) {
                showError(dialog, "Please enter a Student ID.");
                return;
            }
            if (!studentExists(studentId)) {
                showError(dialog, "Student ID " + studentId + " was not found.");
                return;
            }
            try {
                LocalDate.parse(date);
            } catch (DateTimeParseException ex) {
                showError(dialog, "Please enter the date as YYYY-MM-DD.");
                return;
            }
            if (attendanceExists(studentId, date)) {
                showError(dialog,
                        "Attendance already exists for this student and date.");
                return;
            }

            new FileManager().saveAttendance(studentId + "," + date + "," + status);
            JOptionPane.showMessageDialog(dialog,
                    "Attendance recorded successfully.");
            dialog.dispose();
            refreshDashboard();
        });
        btnCancel.addActionListener(evt -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(form, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showError(java.awt.Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
    }

    private void refreshDashboard() {
        // Reopen so the stat cards reflect the new record.
        AttendanceOfficerDashboard refreshed = new AttendanceOfficerDashboard();
        refreshed.setBounds(getBounds());
        refreshed.setExtendedState(getExtendedState());
        refreshed.setVisible(true);
        dispose();
    }

    // ----- File helpers -----
    private boolean studentExists(String studentId) {
        try (BufferedReader reader =
                new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].trim().equalsIgnoreCase(studentId)) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    private boolean attendanceExists(String studentId, String date) {
        try (BufferedReader reader =
                new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 2
                        && data[0].trim().equalsIgnoreCase(studentId)
                        && data[1].trim().equalsIgnoreCase(date)) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    private int countStatus(String status) {
        int count = 0;
        try (BufferedReader reader =
                new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && data[2].trim().equalsIgnoreCase(status)) {
                    count++;
                }
            }
        } catch (Exception ignored) {
        }
        return count;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(()
                -> new AttendanceOfficerDashboard().setVisible(true));
    }
}
