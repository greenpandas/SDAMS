package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.User;
import service.UserService;

public class LoginForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(LoginForm.class.getName());

    private JLabel Username;
    private JLabel Password;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;

    private static final Color BACKGROUND = new Color(15, 23, 42);
    private static final Color CARD = new Color(30, 41, 59);
    private static final Color FIELD = new Color(51, 65, 85);
    private static final Color BORDER = new Color(71, 85, 105);
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DANGER = new Color(239, 68, 68);

    public LoginForm() {
        initComponents();
        setTitle("SDAMS - Login");
        setSize(850, 650);
        setResizable(false);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setContentPane(createLoginContent());
    }

    private JPanel createLoginContent() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BACKGROUND);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(440, 580));
        card.setMaximumSize(new Dimension(440, 580));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(38, 44, 38, 44)));

        JLabel logo = new JLabel("SD");
        logo.setPreferredSize(new Dimension(72, 72));
        logo.setMinimumSize(new Dimension(72, 72));
        logo.setOpaque(true);
        logo.setBackground(PRIMARY);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);

        JLabel title = new JLabel("SDAMS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitle = new JLabel("Self Defence Academy Management System");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(148, 163, 184));
        subtitle.setHorizontalAlignment(JLabel.CENTER);

        JLabel welcome = new JLabel("Welcome back");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcome.setForeground(Color.WHITE);
        welcome.setHorizontalAlignment(JLabel.CENTER);

        JLabel prompt = new JLabel("Sign in to your account");
        prompt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        prompt.setForeground(new Color(148, 163, 184));
        prompt.setHorizontalAlignment(JLabel.CENTER);

        Username = new JLabel("Username");
        Username.setFont(new Font("Segoe UI", Font.BOLD, 13));
        Username.setForeground(new Color(226, 232, 240));

        Password = new JLabel("Password");
        Password.setFont(new Font("Segoe UI", Font.BOLD, 13));
        Password.setForeground(new Color(226, 232, 240));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        styleField(txtUsername);
        styleField(txtPassword);
        txtUsername.setPreferredSize(new Dimension(350, 44));
        txtPassword.setPreferredSize(new Dimension(350, 44));

        btnLogin = createButton("Login", PRIMARY);
        btnExit = createButton("Exit", DANGER);
        btnLogin.setPreferredSize(new Dimension(350, 44));
        btnExit.setPreferredSize(new Dimension(350, 44));

        txtUsername.addActionListener(this::txtUsernameActionPerformed);
        txtPassword.addActionListener(this::txtPasswordActionPerformed);
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        btnExit.addActionListener(this::btnExitActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(card);
        card.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(logo)
                        .addComponent(title)
                        .addComponent(subtitle)
                        .addComponent(welcome)
                        .addComponent(prompt)
                        .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
                                javax.swing.GroupLayout.PREFERRED_SIZE));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(logo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(title)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subtitle)
                        .addGap(22)
                        .addComponent(welcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prompt)
                        .addGap(24)
                        .addComponent(Username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsername, 44, 44, 44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Password)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword, 44, 44, 44)
                        .addGap(18)
                        .addComponent(btnLogin, 44, 44, 44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExit, 44, 44, 44));

        root.add(card, new GridBagConstraints());
        return root;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBackground(FIELD);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {
        txtPassword.requestFocus();
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.");
            return;
        }

        UserService userService = new UserService();
        User user = userService.login(username, password);

        if (user != null) {
            if (user.getRole().equals("Manager")) {
                new ManagerMenuForm().setVisible(true);
            } else if (user.getRole().equals("Administrator")) {
                new AdminMenuForm().setVisible(true);
            } else if (user.getRole().equals("Student")) {
                new StudentMenuForm().setVisible(true);
            } else if (user.getRole().equals("Instructor")) {
                new InstructorMenuForm().setVisible(true);
            } else if (user.getRole().equals("AttendanceOfficer")) {
                new AttendanceOfficerDashboard().setVisible(true);
            } else if (user.getRole().equals("FacilitiesOfficer")) {
                new FacilitiesMenuForm().setVisible(true);
            }

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password");
            txtPassword.setText("");
        }
    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {
        btnLoginActionPerformed(evt);
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

        java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
