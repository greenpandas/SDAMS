package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ReportsForm extends JFrame {

    public ReportsForm() {
        UiTheme.applyDefaults();
        initComponents();
        setTitle("SDAMS - Reports");
        setSize(900, 560);
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CONTENT);
        root.add(UiTheme.createHeader("Reports",
                "Operational summaries for the academy"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 2, 18, 18));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        cards.add(UiTheme.createStatCard("Total Revenue",
                String.format("$%.2f",
                        UiTheme.readPaymentRevenue("data/payments.txt")),
                "From payment records"));
        cards.add(UiTheme.createStatCard("Attendance Records",
                String.valueOf(UiTheme.countLines("data/attendance.txt")),
                "Recorded entries"));
        root.add(cards, BorderLayout.CENTER);
        setContentPane(root);
    }
}
