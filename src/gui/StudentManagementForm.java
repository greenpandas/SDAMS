package gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.User;
import filehandling.FileManager;
import service.StudentService;

public class StudentManagementForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(StudentManagementForm.class.getName());

    public StudentManagementForm() {
        initComponents();
        loadStudents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtStudentId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnAdd = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnViewAll = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtStudentList = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24));
        jLabel1.setText("Student Management");

        jLabel2.setText("Student ID");
        jLabel3.setText("Name");
        jLabel4.setText("Username");
        jLabel5.setText("Password");

        btnAdd.setText("Add");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setText("Delete");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnViewAll.setText("View All");
        btnViewAll.addActionListener(this::btnViewAllActionPerformed);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        txtStudentList.setEditable(false);
        txtStudentList.setColumns(20);
        txtStudentList.setRows(5);
        jScrollPane1.setViewportView(txtStudentList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStudentId)
                            .addComponent(txtName)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(475, 475, 475)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAdd)
                            .addComponent(btnUpdate)
                            .addComponent(btnClear)
                            .addComponent(btnViewAll))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSearch)
                            .addComponent(btnDelete)
                            .addComponent(btnBack))))
                .addGap(72, 72, 72)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnSearch))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear)
                            .addComponent(btnBack))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewAll))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(262, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        String studentId = txtStudentId.getText().trim();
        String name = txtName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (studentId.isEmpty() || name.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        boolean added = new StudentService()
                .addStudent(studentId, name, username, password);

        if (added) {
            JOptionPane.showMessageDialog(this, "Student Added Successfully!");
            loadStudents();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Student could not be added.");
        }
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String studentId = txtStudentId.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Student ID.");
            return;
        }

        for (User user : new FileManager().readUsers()) {
            if (user.getRole().equals("Student")
                    && user.getUserId().equals(studentId)) {
                txtName.setText(user.getName());
                txtUsername.setText(user.getUsername());
                txtPassword.setText(user.getPassword());
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Student not found.");
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        String studentId = txtStudentId.getText().trim();
        String name = txtName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (studentId.isEmpty() || name.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        boolean updated = new StudentService()
                .updateStudent(studentId, name, username, password);

        if (updated) {
            JOptionPane.showMessageDialog(this, "Student Updated.");
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.");
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        String studentId = txtStudentId.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Student ID.");
            return;
        }

        boolean deleted = new StudentService().deleteStudent(studentId);

        if (deleted) {
            JOptionPane.showMessageDialog(this, "Student Deleted.");
            loadStudents();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.");
        }
    }

    private void btnViewAllActionPerformed(java.awt.event.ActionEvent evt) {
        loadStudents();
    }

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        clearFields();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        new AdminMenuForm().setVisible(true);
        this.dispose();
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void loadStudents() {
        ArrayList<User> users = new FileManager().readUsers();
        txtStudentList.setText("");

        for (User user : users) {
            if (user.getRole().equals("Student")) {
                txtStudentList.append(
                        user.getUserId() + " | "
                        + user.getName() + " | "
                        + user.getUsername() + "\n");
            }
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(
                () -> new StudentManagementForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnViewAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtStudentId;
    private javax.swing.JTextArea txtStudentList;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
