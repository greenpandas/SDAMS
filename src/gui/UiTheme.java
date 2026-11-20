package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public final class UiTheme {

    public static final Color SIDEBAR = new Color(30, 41, 59);
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color SUCCESS = new Color(34, 197, 94);
    public static final Color WARNING = new Color(245, 158, 11);
    public static final Color DANGER = new Color(239, 68, 68);
    public static final Color CONTENT = new Color(248, 250, 252);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(15, 23, 42);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color BORDER = new Color(203, 213, 225);

    public static final Font SEGOU_UI = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SEGOU_UI_BOLD = new Font("Segoe UI", Font.BOLD, 14);

    private UiTheme() {
    }

    public static void applyDefaults() {
        javax.swing.UIManager.put("Label.font", SEGOU_UI);
        javax.swing.UIManager.put("Button.font", SEGOU_UI_BOLD);
        javax.swing.UIManager.put("TextField.font", SEGOU_UI);
        javax.swing.UIManager.put("PasswordField.font", SEGOU_UI);
        javax.swing.UIManager.put("Table.font", SEGOU_UI);
        javax.swing.UIManager.put("TableHeader.font", SEGOU_UI_BOLD);
    }

    public static JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(SEGOU_UI_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(8, 16, 8, 16));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    public static JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(SEGOU_UI_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(SIDEBAR);
        button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return button;
    }

    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return card;
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(SEGOU_UI_BOLD);
        return label;
    }

    public static void styleField(JTextField field) {
        field.setPreferredSize(new Dimension(260, 38));
        field.setFont(SEGOU_UI);
        field.setForeground(TEXT);
        field.setBackground(CARD);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

    public static JScrollPane createTableScroll(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.setPreferredSize(new Dimension(590, 0));
        return scrollPane;
    }

    public static JPanel createSidebar(String role) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(230, 0));

        JPanel brand = new JPanel();
        brand.setLayout(new BorderLayout());
        brand.setBackground(SIDEBAR);
        brand.setBorder(BorderFactory.createEmptyBorder(24, 20, 24, 20));

        JLabel logo = new JLabel("SD");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(SIDEBAR);
        logo.setOpaque(true);
        logo.setBackground(Color.WHITE);
        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(44, 44));
        logo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel brandText = new JPanel();
        brandText.setOpaque(false);
        brandText.setLayout(new BorderLayout());
        JLabel academy = new JLabel("SDAMS");
        academy.setFont(new Font("Segoe UI", Font.BOLD, 18));
        academy.setForeground(Color.WHITE);
        JLabel roleLabel = new JLabel(role);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(new Color(148, 163, 184));
        brandText.add(academy, BorderLayout.NORTH);
        brandText.add(roleLabel, BorderLayout.CENTER);

        JPanel brandRow = new JPanel(new BorderLayout(12, 0));
        brandRow.setOpaque(false);
        brandRow.add(logo, BorderLayout.WEST);
        brandRow.add(brandText, BorderLayout.CENTER);
        brand.add(brandRow, BorderLayout.CENTER);
        sidebar.add(brand, BorderLayout.NORTH);

        return sidebar;
    }

    public static JPanel createHeader(String title, String subtitle) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(18, 28, 18, 28)));

        JPanel titles = new JPanel(new BorderLayout());
        titles.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT);
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(SEGOU_UI);
        subtitleLabel.setForeground(MUTED);
        titles.add(titleLabel, BorderLayout.NORTH);
        titles.add(subtitleLabel, BorderLayout.CENTER);
        header.add(titles, BorderLayout.WEST);

        return header;
    }

    public static JPanel createStatCard(String title, String value, String accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0,
                        PRIMARY),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SEGOU_UI_BOLD);
        titleLabel.setForeground(MUTED);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(TEXT);

        JLabel accentLabel = new JLabel(accent);
        accentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        accentLabel.setForeground(MUTED);

        JPanel cardText = new JPanel(new BorderLayout());
        cardText.setOpaque(false);
        cardText.add(titleLabel, BorderLayout.NORTH);
        cardText.add(valueLabel, BorderLayout.CENTER);
        cardText.add(accentLabel, BorderLayout.SOUTH);
        card.add(cardText, BorderLayout.CENTER);

        return card;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(38);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(226, 232, 240));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(SEGOU_UI_BOLD);
        header.setForeground(TEXT);
        header.setBackground(new Color(241, 245, 249));
        header.setPreferredSize(new Dimension(0, 42));

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        table.setDefaultRenderer(Object.class, renderer);
    }

    public static int countLines(String relativePath) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(relativePath))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (Exception ignored) {
            return 0;
        }
        return count;
    }

    public static JPanel createRecentTablePanel(String title, String relativePath,
            String[] columns) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 24, 24, 24));

        JLabel heading = new JLabel(title);
        heading.setFont(SEGOU_UI_BOLD);
        heading.setForeground(TEXT);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(heading, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        styleTable(table);
        try (BufferedReader reader = new BufferedReader(
                new FileReader(relativePath))) {
            String line;
            int added = 0;
            java.util.ArrayList<String[]> rows = new java.util.ArrayList<>();
            while ((line = reader.readLine()) != null && added < 8) {
                String[] data = line.split(",", -1);
                Object[] row = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    row[i] = i < data.length ? data[i].trim() : "";
                }
                rows.add(0, row);
                added++;
            }
            for (Object[] row : rows) {
                model.addRow(row);
            }
        } catch (Exception ignored) {
        }
        if (model.getRowCount() == 0) {
            model.addRow(new Object[]{"No recent activity"});
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.setPreferredSize(new Dimension(0, 220));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    public static double readPaymentRevenue(String relativePath) {
        double total = 0;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(relativePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    try {
                        total += Double.parseDouble(data[1].trim());
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
            return 0;
        }
        return total;
    }

    public static java.math.BigDecimal readPaymentRevenueExact(String relativePath) {
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(relativePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    try {
                        total = total.add(new java.math.BigDecimal(data[1].trim()));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
            return java.math.BigDecimal.ZERO;
        }
        return total.setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
