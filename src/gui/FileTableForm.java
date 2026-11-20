package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class FileTableForm extends JFrame {

    private final String filePath;
    private final String[] columns;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtSearch = new JTextField();
    private final JLabel lblCount = new JLabel();
    private final TableRowSorter<DefaultTableModel> sorter;
    private final String filterStudentId;

    public FileTableForm(String title, String filePath, String[] columns) {
        this(title, filePath, columns, null);
    }

    public FileTableForm(String title, String filePath, String[] columns,
            String filterStudentId) {
        this.filePath = filePath;
        this.columns = columns;
        this.filterStudentId = filterStudentId;
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
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UiTheme.CONTENT);
        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        content.add(UiTheme.createHeader(title, "Records from file-based storage"),
                BorderLayout.NORTH);

        UiTheme.styleTable(table);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        JPanel searchRow = new JPanel(new BorderLayout(10, 10));
        searchRow.setOpaque(false);
        searchRow.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        JLabel searchLabel = UiTheme.createFieldLabel("Search");
        UiTheme.styleField(txtSearch);
        lblCount.setForeground(UiTheme.MUTED);
        searchRow.add(searchLabel, BorderLayout.WEST);
        searchRow.add(txtSearch, BorderLayout.CENTER);
        searchRow.add(lblCount, BorderLayout.EAST);
        content.add(searchRow, BorderLayout.SOUTH);
        txtSearch.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        filter();
                    }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        filter();
                    }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        filter();
                    }
                });
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
                if (filterStudentId != null
                        && (data.length == 0
                        || !data[0].trim().equalsIgnoreCase(filterStudentId))) {
                    continue;
                }
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
        updateCount();
    }

    private void filter() {
        String query = txtSearch.getText().trim();
        if (query.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(
                    "(?i)" + java.util.regex.Pattern.quote(query)));
        }
        updateCount();
    }

    private void updateCount() {
        int count = table.getRowCount();
        lblCount.setText(count + " record" + (count == 1 ? "" : "s"));
    }
}
