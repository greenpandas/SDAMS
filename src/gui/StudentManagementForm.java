package gui;

import filehandling.FileManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import model.User;
import service.StudentService;

public class StudentManagementForm extends javax.swing.JFrame {

    private JTextField txtStudentId;
    private JTextField txtName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentManagementForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Student Management");
        setSize(1180, 720);
        setLocationRelativeTo(null);
        loadStudents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CONTENT);
        root.add(UiTheme.createHeader("Student Management",
                "Add, search, update and remove student accounts"),
                BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(18, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        tableModel = new DefaultTableModel(
                new Object[]{"Student ID", "Name", "Username"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        UiTheme.styleTable(studentTable);
        JScrollPane tableScroll = new JScrollPane(studentTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(UiTheme.BORDER));
        tableScroll.setPreferredSize(new Dimension(590, 0));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UiTheme.CARD);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1;

        txtStudentId = new JTextField();
        txtName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        addField(form, c, 0, "Student ID", txtStudentId);
        addField(form, c, 2, "Name", txtName);
        addField(form, c, 4, "Username", txtUsername);
        addField(form, c, 6, "Password", txtPassword);

        JPanel actions = new JPanel(new java.awt.GridLayout(2, 2, 8, 8));
        actions.setOpaque(false);
        JButton btnAdd = UiTheme.createButton("Add", UiTheme.SUCCESS);
        JButton btnSearch = UiTheme.createButton("Search", UiTheme.PRIMARY);
        JButton btnUpdate = UiTheme.createButton("Update", UiTheme.PRIMARY);
        JButton btnDelete = UiTheme.createButton("Delete", UiTheme.DANGER);
        JButton btnClear = UiTheme.createButton("Clear", new java.awt.Color(100, 116, 139));
        JButton btnViewAll = UiTheme.createButton("View All", UiTheme.PRIMARY);
        JButton btnBack = UiTheme.createButton("Back", new java.awt.Color(71, 85, 105));
        actions.add(btnAdd);
        actions.add(btnSearch);
        actions.add(btnUpdate);
        actions.add(btnDelete);
        actions.add(btnClear);
        actions.add(btnViewAll);

        c.gridy = 8;
        c.gridwidth = 2;
        form.add(actions, c);
        c.gridy = 9;
        form.add(btnBack, c);

        body.add(tableScroll, BorderLayout.WEST);
        body.add(form, BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);
        setContentPane(root);

        btnAdd.addActionListener(evt -> addStudent());
        btnSearch.addActionListener(evt -> searchStudent());
        btnUpdate.addActionListener(evt -> updateStudent());
        btnDelete.addActionListener(evt -> deleteStudent());
        btnClear.addActionListener(evt -> clearFields());
        btnViewAll.addActionListener(evt -> loadStudents());
        btnBack.addActionListener(evt -> backToAdministrator());

        studentTable.getSelectionModel().addListSelectionListener(
                (ListSelectionEvent event) -> {
                    if (!event.getValueIsAdjusting()
                            && studentTable.getSelectedRow() >= 0) {
                        int row = studentTable.getSelectedRow();
                        txtStudentId.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                        txtName.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                        txtUsername.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                        searchPassword();
                    }
                });
    }

    private void addField(JPanel panel, GridBagConstraints c, int row,
            String labelText, JTextField field) {
        c.gridy = row;
        c.gridx = 0;
        c.gridwidth = 2;
        JLabel label = new JLabel(labelText);
        label.setForeground(UiTheme.TEXT);
        label.setFont(UiTheme.SEGOU_UI_BOLD);
        panel.add(label, c);
        c.gridy = row + 1;
        field.setPreferredSize(new Dimension(260, 38));
        panel.add(field, c);
    }

    private void addStudent() {
        if (!validateRequiredFields()) {
            return;
        }
        boolean added = new StudentService().addStudent(
                txtStudentId.getText().trim(),
                txtName.getText().trim(),
                txtUsername.getText().trim(),
                new String(txtPassword.getPassword()));
        if (added) {
            JOptionPane.showMessageDialog(this,
                    "Student added successfully.");
            loadStudents();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Student could not be added.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchStudent() {
        String id = txtStudentId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter a student ID.", "Validation",
                    JOptionPane.WARNING_MESSAGE);
            txtStudentId.requestFocus();
            return;
        }
        for (User user : new FileManager().readUsers()) {
            if ("Student".equals(user.getRole())
                    && user.getUserId().equals(id)) {
                txtName.setText(user.getName());
                txtUsername.setText(user.getUsername());
                txtPassword.setText(user.getPassword());
                selectTableRow(id);
                return;
            }
        }
        JOptionPane.showMessageDialog(this,
                "Student not found.", "Search",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStudent() {
        if (!validateRequiredFields()) {
            return;
        }
        boolean updated = new StudentService().updateStudent(
                txtStudentId.getText().trim(),
                txtName.getText().trim(),
                txtUsername.getText().trim(),
                new String(txtPassword.getPassword()));
        if (updated) {
            JOptionPane.showMessageDialog(this,
                    "Student updated successfully.");
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Student not found.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String id = txtStudentId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Select or enter a student ID.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this,
                "Delete student " + id + "? This cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            boolean deleted = new StudentService().deleteStudent(id);
            if (deleted) {
                JOptionPane.showMessageDialog(this,
                        "Student deleted successfully.");
                loadStudents();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Student not found.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateRequiredFields() {
        if (txtStudentId.getText().trim().isEmpty()
                || txtName.getText().trim().isEmpty()
                || txtUsername.getText().trim().isEmpty()
                || txtPassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void searchPassword() {
        String id = txtStudentId.getText().trim();
        for (User user : new FileManager().readUsers()) {
            if ("Student".equals(user.getRole())
                    && user.getUserId().equals(id)) {
                txtPassword.setText(user.getPassword());
                return;
            }
        }
    }

    private void selectTableRow(String id) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (id.equals(String.valueOf(tableModel.getValueAt(row, 0)))) {
                studentTable.setRowSelectionInterval(row, row);
                return;
            }
        }
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        studentTable.clearSelection();
        txtStudentId.requestFocus();
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        ArrayList<User> users = new FileManager().readUsers();
        for (User user : users) {
            if ("Student".equals(user.getRole())) {
                tableModel.addRow(new Object[]{
                    user.getUserId(), user.getName(), user.getUsername()
                });
            }
        }
    }

    private void backToAdministrator() {
        new AdminMenuForm().setVisible(true);
        dispose();
    }
}
