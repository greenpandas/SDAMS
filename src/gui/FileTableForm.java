package gui;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FileTableForm extends JFrame {

    private final String filePath;
    private final String[] columns;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public FileTableForm(String title, String filePath, String[] columns) {
        this.filePath = filePath;
        this.columns = columns;
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        setTitle("SDAMS - " + title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        content.add(UiTheme.createHeader(title, "Records from file-based storage"),
                BorderLayout.NORTH);

        UiTheme.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UiTheme.BORDER));
        content.add(scrollPane, BorderLayout.CENTER);

        setContentPane(content);
        loadRows();
    }

    private void loadRows() {
        tableModel.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                Object[] row = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    row[i] = i < data.length ? data[i].trim() : "";
                }
                tableModel.addRow(row);
            }
        } catch (Exception ignored) {
            Object[] emptyRow = new Object[columns.length];
            emptyRow[0] = "No records available";
            tableModel.addRow(emptyRow);
        }
    }
}
