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
import service.ManagerService;

public class AdminManagementForm extends javax.swing.JFrame {

    private JTextField txtAdminId;
    private JTextField txtName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTable adminTable;
    private DefaultTableModel tableModel;

    public AdminManagementForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Administrator Management");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
        loadAdministrators();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CONTENT);
        root.add(UiTheme.createHeader("Administrator Management",
                "Add, search, update and remove administrator accounts"),
                BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(18, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        tableModel = new DefaultTableModel(
                new Object[]{"Admin ID", "Name", "Username"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adminTable = new JTable(tableModel);
        UiTheme.styleTable(adminTable);
        JScrollPane tableScroll = new JScrollPane(adminTable);
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

        txtAdminId = new JTextField();
        txtName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        addField(form, c, 0, "Administrator ID", txtAdminId);
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

        btnAdd.addActionListener(evt -> addAdministrator());
        btnSearch.addActionListener(evt -> searchAdministrator());
        btnUpdate.addActionListener(evt -> updateAdministrator());
        btnDelete.addActionListener(evt -> deleteAdministrator());
        btnClear.addActionListener(evt -> clearFields());
        btnViewAll.addActionListener(evt -> loadAdministrators());
        btnBack.addActionListener(evt -> backToManager());

        adminTable.getSelectionModel().addListSelectionListener(
                (ListSelectionEvent event) -> {
                    if (!event.getValueIsAdjusting()
                            && adminTable.getSelectedRow() >= 0) {
                        int row = adminTable.getSelectedRow();
                        txtAdminId.setText(String.valueOf(tableModel.getValueAt(row, 0)));
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

    private void addAdministrator() {
        if (!validateRequiredFields()) {
            return;
        }
        boolean added = new ManagerService().addAdministrator(
                txtAdminId.getText().trim(),
                txtName.getText().trim(),
                txtUsername.getText().trim(),
                new String(txtPassword.getPassword()));
        if (added) {
            JOptionPane.showMessageDialog(this,
                    "Administrator added successfully.");
            loadAdministrators();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    "ID or username already exists, or fields are invalid.",
                    "Administrator Not Added", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchAdministrator() {
        String id = txtAdminId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter an administrator ID.", "Validation",
                    JOptionPane.WARNING_MESSAGE);
            txtAdminId.requestFocus();
            return;
        }
        for (User user : new FileManager().readUsers()) {
            if ("Administrator".equals(user.getRole())
                    && user.getUserId().equals(id)) {
                txtName.setText(user.getName());
                txtUsername.setText(user.getUsername());
                txtPassword.setText(user.getPassword());
                selectTableRow(id);
                return;
            }
        }
        JOptionPane.showMessageDialog(this,
                "Administrator not found.", "Search",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateAdministrator() {
        if (!validateRequiredFields()) {
            return;
        }
        boolean updated = new ManagerService().updateAdministrator(
                txtAdminId.getText().trim(),
                txtName.getText().trim(),
                txtUsername.getText().trim(),
                new String(txtPassword.getPassword()));
        if (updated) {
            JOptionPane.showMessageDialog(this,
                    "Administrator updated successfully.");
            loadAdministrators();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Administrator not found or username is already in use.",
                    "Update Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAdministrator() {
        String id = txtAdminId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Select or enter an administrator ID.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this,
                "Delete administrator " + id + "? This cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            new ManagerService().deleteAdministrator(id);
            JOptionPane.showMessageDialog(this,
                    "Administrator deleted successfully.");
            loadAdministrators();
            clearFields();
        }
    }

    private boolean validateRequiredFields() {
        if (txtAdminId.getText().trim().isEmpty()
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
        String id = txtAdminId.getText().trim();
        for (User user : new FileManager().readUsers()) {
            if ("Administrator".equals(user.getRole())
                    && user.getUserId().equals(id)) {
                txtPassword.setText(user.getPassword());
                return;
            }
        }
    }

    private void selectTableRow(String id) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (id.equals(String.valueOf(tableModel.getValueAt(row, 0)))) {
                adminTable.setRowSelectionInterval(row, row);
                return;
            }
        }
    }

    private void clearFields() {
        txtAdminId.setText("");
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        adminTable.clearSelection();
        txtAdminId.requestFocus();
    }

    private void loadAdministrators() {
        tableModel.setRowCount(0);
        ArrayList<User> users = new FileManager().readUsers();
        for (User user : users) {
            if ("Administrator".equals(user.getRole())) {
                tableModel.addRow(new Object[]{
                    user.getUserId(), user.getName(), user.getUsername()
                });
            }
        }
    }

    private void backToManager() {
        new ManagerMenuForm().setVisible(true);
        dispose();
    }
}
