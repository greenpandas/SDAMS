package gui;

import filehandling.FileManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.User;

public class StaffViewForm extends JFrame {

    private final JTable staffTable;
    private final DefaultTableModel staffModel;
    private final JTextField txtSearch;

    public StaffViewForm() {
        UiTheme.applyDefaults();

        staffModel = new DefaultTableModel(
                new Object[]{"Staff ID", "Name", "Username", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        staffTable = new JTable(staffModel);
        UiTheme.styleTable(staffTable);
        txtSearch = new JTextField();

        initComponents();
        setTitle("SDAMS - Staff Information");
        setSize(900, 560);
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);
        loadStaff();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CONTENT);
        root.add(UiTheme.createHeader("Staff Information",
                "Search and refresh non-student accounts"), BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(staffTable);
        scroll.setBorder(BorderFactory.createLineBorder(UiTheme.BORDER));

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        body.add(scroll, BorderLayout.CENTER);

        JPanel top = new JPanel(new BorderLayout(12, 12));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        JLabel searchLabel = UiTheme.createFieldLabel("Search staff");
        UiTheme.styleField(txtSearch);
        JButton btnSearch = UiTheme.createButton("Search", UiTheme.PRIMARY);
        JButton btnRefresh = UiTheme.createButton("Refresh", UiTheme.SUCCESS);
        JButton btnBack = UiTheme.createButton("Back", new java.awt.Color(71, 85, 105));

        JPanel searchRow = new JPanel(new GridLayout(1, 5, 10, 10));
        searchRow.setOpaque(false);
        searchRow.add(searchLabel);
        searchRow.add(txtSearch);
        searchRow.add(btnSearch);
        searchRow.add(btnRefresh);
        searchRow.add(btnBack);
        top.add(searchRow, BorderLayout.NORTH);
        top.add(UiTheme.createStatCard("Total Staff",
                String.valueOf(countStaff()), "Non-student accounts"),
                BorderLayout.CENTER);
        body.add(top, BorderLayout.NORTH);

        root.add(body, BorderLayout.CENTER);
        setContentPane(root);

        btnSearch.addActionListener(evt -> searchStaff());
        btnRefresh.addActionListener(evt -> loadStaff());
        btnBack.addActionListener(evt -> dispose());
        txtSearch.addActionListener(evt -> searchStaff());
    }

    private void loadStaff() {
        staffModel.setRowCount(0);
        ArrayList<User> users = new FileManager().readUsers();
        for (User user : users) {
            if (!"Student".equals(user.getRole())) {
                staffModel.addRow(new Object[]{
                    user.getUserId(), user.getName(),
                    user.getUsername(), user.getRole()
                });
            }
        }
        setTitle("SDAMS - Staff Information (" + staffModel.getRowCount() + ")");
    }

    private int countStaff() {
        int count = 0;
        for (User user : new FileManager().readUsers()) {
            if (!"Student".equals(user.getRole())) {
                count++;
            }
        }
        return count;
    }

    private void searchStaff() {
        String query = txtSearch.getText().trim().toLowerCase(java.util.Locale.ROOT);
        staffModel.setRowCount(0);
        int matches = 0;
        for (User user : new FileManager().readUsers()) {
            if (!"Student".equals(user.getRole())
                    && (query.isEmpty()
                    || user.getUserId().toLowerCase(java.util.Locale.ROOT).contains(query)
                    || user.getName().toLowerCase(java.util.Locale.ROOT).contains(query)
                    || user.getUsername().toLowerCase(java.util.Locale.ROOT).contains(query)
                    || user.getRole().toLowerCase(java.util.Locale.ROOT).contains(query))) {
                staffModel.addRow(new Object[]{
                    user.getUserId(), user.getName(),
                    user.getUsername(), user.getRole()
                });
                matches++;
            }
        }
        if (matches == 0) {
            staffModel.addRow(new Object[]{"No matching staff", "", "", ""});
        }
        setTitle("SDAMS - Staff Information (" + matches + " found)");
    }
}
