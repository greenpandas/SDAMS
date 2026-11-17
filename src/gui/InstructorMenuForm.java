package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import service.InstructorService;

public class InstructorMenuForm extends JFrame {

    private final InstructorService instructorService = new InstructorService();

    public InstructorMenuForm() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        heading = new JLabel();
        btnCreateSchedule = new JButton();
        btnViewSchedule = new JButton();
        btnViewRatings = new JButton();
        btnReportIssue = new JButton();
        btnLogout = new JButton();

        setTitle("Instructor Dashboard");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        heading.setText("Instructor Dashboard");
        heading.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 22));

        btnCreateSchedule.setText("Create Schedule");
        btnCreateSchedule.addActionListener(this::btnCreateScheduleActionPerformed);

        btnViewSchedule.setText("View Schedule");
        btnViewSchedule.addActionListener(this::btnViewScheduleActionPerformed);

        btnViewRatings.setText("View Ratings");
        btnViewRatings.addActionListener(this::btnViewRatingsActionPerformed);

        btnReportIssue.setText("Report Issue");
        btnReportIssue.addActionListener(this::btnReportIssueActionPerformed);

        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(36)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(heading)
                .addComponent(btnCreateSchedule, 240, 240, Short.MAX_VALUE)
                .addComponent(btnViewSchedule, 240, 240, Short.MAX_VALUE)
                .addComponent(btnViewRatings, 240, 240, Short.MAX_VALUE)
                .addComponent(btnReportIssue, 240, 240, Short.MAX_VALUE)
                .addComponent(btnLogout, 240, 240, Short.MAX_VALUE))
            .addGap(36));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGap(28)
            .addComponent(heading)
            .addGap(20)
            .addComponent(btnCreateSchedule, 36, 36, 36)
            .addGap(8)
            .addComponent(btnViewSchedule, 36, 36, 36)
            .addGap(8)
            .addComponent(btnViewRatings, 36, 36, 36)
            .addGap(8)
            .addComponent(btnReportIssue, 36, 36, 36)
            .addGap(8)
            .addComponent(btnLogout, 36, 36, 36)
            .addGap(28));
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateScheduleActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCreateScheduleActionPerformed
        String[] fields = promptForFields("Create Schedule", "Class ID", "Class Name", "Date", "Time");
        if (fields != null) {
            boolean saved = instructorService.createSchedule(fields[0], fields[1], fields[2], fields[3]);
            showSaveResult(saved, "Schedule created.", "Could not save the schedule.");
        }
    }//GEN-LAST:event_btnCreateScheduleActionPerformed

    private void btnViewScheduleActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnViewScheduleActionPerformed
        try {
            showRecords("Class Schedule", "Class ID, Class Name, Date, Time",
                    instructorService.readSchedules(), "No scheduled classes.");
        } catch (IOException ex) {
            showError("Could not read the class schedule.");
        }
    }//GEN-LAST:event_btnViewScheduleActionPerformed

    private void btnViewRatingsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnViewRatingsActionPerformed
        try {
            showRecords("Ratings", "Student ID, Instructor ID, Rating",
                    instructorService.readRatings(), "No ratings submitted.");
        } catch (IOException ex) {
            showError("Could not read the ratings.");
        }
    }//GEN-LAST:event_btnViewRatingsActionPerformed

    private void btnReportIssueActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnReportIssueActionPerformed
        String[] fields = promptForFields("Report Issue", "Issue ID", "Issue Description");
        if (fields != null) {
            boolean saved = instructorService.reportIssue(fields[0], fields[1]);
            showSaveResult(saved, "Issue reported.", "Could not save the issue.");
        }
    }//GEN-LAST:event_btnReportIssueActionPerformed

    private void btnLogoutActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        new LoginForm().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private String[] promptForFields(String title, String... labels) {
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 8));
        JTextField[] inputs = new JTextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            inputs[i] = new JTextField(20);
            JLabel label = new JLabel(labels[i]);
            label.setLabelFor(inputs[i]);
            form.add(label);
            form.add(inputs[i]);
        }

        while (JOptionPane.showConfirmDialog(this, form, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            String[] values = new String[inputs.length];
            boolean valid = true;
            for (int i = 0; i < inputs.length; i++) {
                values[i] = inputs[i].getText().trim();
                valid &= !values[i].isEmpty() && !values[i].contains(",");
            }
            if (valid) {
                return values;
            }
            showError("Complete every field and remove any commas.");
        }
        return null;
    }

    private void showRecords(String title, String columns, List<String> records, String emptyMessage) {
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, emptyMessage, title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextArea contents = new JTextArea(String.join("\n", records), 10, 42);
        contents.setEditable(false);
        contents.setCaretPosition(0);
        Object[] message = {new JLabel(columns), new JScrollPane(contents)};
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void showSaveResult(boolean saved, String successMessage, String errorMessage) {
        if (saved) {
            JOptionPane.showMessageDialog(this, successMessage);
        } else {
            showError(errorMessage);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Instructor Dashboard", JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnCreateSchedule;
    private JButton btnViewSchedule;
    private JButton btnViewRatings;
    private JButton btnReportIssue;
    private JButton btnLogout;
    private JLabel heading;
    // End of variables declaration//GEN-END:variables
}
