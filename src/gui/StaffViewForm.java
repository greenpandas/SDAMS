package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import filehandling.FileManager;
import model.User;

public class StaffViewForm extends JFrame {

    private JTable staffTable;
    private DefaultTableModel staffModel;

    public StaffViewForm() {
        UiTheme.applyDefaults();
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
                "All non-student accounts"), BorderLayout.NORTH);

        staffModel = new DefaultTableModel(
                new Object[]{"Staff ID", "Name", "Username", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        staffTable = new JTable(staffModel);
        UiTheme.styleTable(staffTable);
        JScrollPane scroll = new JScrollPane(staffTable);
        scroll.setBorder(BorderFactory.createLineBorder(UiTheme.BORDER));

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        body.add(scroll, BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);
        setContentPane(root);
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
    }
}
