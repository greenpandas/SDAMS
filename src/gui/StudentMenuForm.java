package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import service.PaymentService;
import model.User;
import service.StudentService;
import service.BookingService;

public class StudentMenuForm extends javax.swing.JFrame {

    private final User currentStudent;

    public StudentMenuForm() {
        this(null);
    }

    public StudentMenuForm(User currentStudent) {
        this.currentStudent = currentStudent;
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Student Dashboard");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
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
        javax.swing.JButton btnBook = UiTheme.createNavigationButton("Book a Class");
        javax.swing.JButton btnBookings = UiTheme.createNavigationButton("My Bookings");
        javax.swing.JButton btnPayments = UiTheme.createNavigationButton("Payments");
        javax.swing.JButton btnRating = UiTheme.createNavigationButton("Submit Rating");
        javax.swing.JButton btnLogout = UiTheme.createNavigationButton("Logout");
        btnLogout.setBackground(UiTheme.DANGER);
        navigation.add(btnOverview);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnClasses);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnBook);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnBookings);
        navigation.add(Box.createRigidArea(new Dimension(0, 4)));
        navigation.add(btnPayments);
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
        cards.add(UiTheme.createStatCard("My Bookings",
                String.valueOf(countStudentBookings()),
                "Your booking records"));
        cards.add(UiTheme.createStatCard("Available Classes",
                String.valueOf(UiTheme.countLines("data/classes.txt")),
                "Academy class records"));
        cards.add(UiTheme.createStatCard("Payment Records",
                String.valueOf(countStudentPayments()),
                "Payment history entries"));
        content.add(cards, BorderLayout.NORTH);
        content.add(UiTheme.createRecentTablePanel("Recent Classes",
                "data/classes.txt", new String[]{"Class ID", "Class", "Date", "Time"}),
                BorderLayout.CENTER);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnLogout.addActionListener(evt -> {
            if (JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to log out?", "Logout",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                    == JOptionPane.YES_OPTION) {
                new LoginForm().setVisible(true);
                dispose();
            }
        });
        btnBook.addActionListener(evt -> showBookingDialog());
        btnClasses.addActionListener(evt
                -> new FileTableForm("Available Classes",
                        "data/classes.txt",
                        new String[]{"Class ID", "Name", "Schedule", "Instructor"})
                        .setVisible(true));
        btnBookings.addActionListener(evt
                -> new FileTableForm("My Booking Records",
                        "data/bookings.txt",
                        new String[]{"Student ID", "Class ID", "Status"},
                        currentStudent == null ? null : currentStudent.getUserId())
                        .setVisible(true));
        btnPayments.addActionListener(evt -> showPayments());
        btnRating.addActionListener(evt -> showRatingDialog());
        btnOverview.addActionListener(evt -> {
            StudentMenuForm refreshed = new StudentMenuForm(currentStudent);
            refreshed.setBounds(getBounds());
            refreshed.setVisible(true);
            dispose();
        });
    }

    private int countStudentBookings() {
        int count = 0;
        if (currentStudent == null) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 1
                        && data[0].trim().equalsIgnoreCase(currentStudent.getUserId())) {
                    count++;
                }
            }
        } catch (Exception ignored) {
        }
        return count;
    }

    private int countStudentPayments() {
        int count = 0;
        if (currentStudent == null) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/payments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 1
                        && data[0].trim().equalsIgnoreCase(currentStudent.getUserId())) {
                    count++;
                }
            }
        } catch (Exception ignored) {
        }
        return count;
    }

    private String totalStudentPayments() {
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        if (currentStudent == null) {
            return "0.00";
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/payments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 2
                        && data[0].trim().equalsIgnoreCase(currentStudent.getUserId())) {
                    try {
                        total = total.add(new java.math.BigDecimal(data[1].trim()));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return total.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }

    private void showBookingDialog() {
        if (currentStudent == null) {
            JOptionPane.showMessageDialog(this, "Student account details unavailable.");
            return;
        }
        JTextField classId = new JTextField();
        UiTheme.styleField(classId);
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        form.add(new JLabel("Class ID"));
        form.add(classId);
        if (JOptionPane.showConfirmDialog(this, form, "Book a Class",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
                == JOptionPane.OK_OPTION) {
            boolean booked = new BookingService().bookClass(
                    currentStudent.getUserId(), classId.getText());
            if (booked) {
                JOptionPane.showMessageDialog(this, "Class booked successfully.",
                        "Booking", JOptionPane.INFORMATION_MESSAGE);
                refreshDashboard();
            } else {
                JOptionPane.showMessageDialog(this,
                        "The class ID is invalid or this class is already booked.",
                        "Booking", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshDashboard() {
        StudentMenuForm refreshed = new StudentMenuForm(currentStudent);
        refreshed.setBounds(getBounds());
        refreshed.setExtendedState(getExtendedState());
        refreshed.setVisible(true);
        dispose();
    }

    private void showPayments() {
        if (currentStudent == null) {
            JOptionPane.showMessageDialog(this, "Student account details unavailable.");
            return;
        }
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Amount", "Payment Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int records = 0;
        for (String[] payment : new PaymentService().readPayments()) {
            if (payment[0].trim().equalsIgnoreCase(currentStudent.getUserId())) {
                model.addRow(new Object[]{payment[1], payment[2]});
                records++;
            }
        }
        if (records == 0) {
            model.addRow(new Object[]{"No payment records", ""});
        }

        JTable table = new JTable(model);
        UiTheme.styleTable(table);
        JScrollPane scroll = UiTheme.createTableScroll(table);
        scroll.setPreferredSize(new java.awt.Dimension(520, 300));
        JOptionPane.showMessageDialog(this, scroll, "My Payment History",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void showRatingDialog() {
        if (currentStudent == null) {
            JOptionPane.showMessageDialog(this, "Student account details unavailable.");
            return;
        }

        JTextField instructorId = new JTextField();
        JComboBox<Integer> rating = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        UiTheme.styleField(instructorId);
        form.add(new JLabel("Instructor ID"));
        form.add(instructorId);
        form.add(new JLabel("Rating"));
        form.add(rating);

        if (JOptionPane.showConfirmDialog(this, form, "Submit Rating",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
                == JOptionPane.OK_OPTION) {
            boolean saved = new StudentService().submitRating(
                    currentStudent.getUserId(), instructorId.getText(),
                    String.valueOf(rating.getSelectedItem()));
            if (saved) {
                JOptionPane.showMessageDialog(this, "Rating submitted successfully.",
                        "Submit Rating", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Enter a valid instructor ID and rating.",
                        "Submit Rating", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new StudentMenuForm().setVisible(true));
    }
}
