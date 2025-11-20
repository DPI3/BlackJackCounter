import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ScorePanel extends JPanel {
    private ScoreManager scoreManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainContainer;

    public ScorePanel(JPanel mainContainer, CardLayout cardLayout, ScoreManager manager) {
        this.mainContainer = mainContainer;
        this.cardLayout = cardLayout;
        this.scoreManager = manager;

        setLayout(new BorderLayout());
        setBackground(new Color(50, 100, 50)); // Match Menu theme

        // --- Title ---
        JLabel title = new JLabel("Eredménytábla", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // --- Table ---
        String[] columnNames = {"Játékmód", "Pontszám (Tanulás esetében lapok)", "Dátum"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override // Make cells uneditable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(240, 240, 240));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        add(scrollPane, BorderLayout.CENTER);

        // --- Back Button ---
        JButton backBtn = new JButton("Vissza a Menübe");
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backBtn.addActionListener(e -> cardLayout.show(mainContainer, "MENU"));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    // Called whenever this panel is shown to refresh data
    public void refreshScores() {
        tableModel.setRowCount(0); // Clear existing
        List<Score> scores = scoreManager.getScores();
        
        for (Score s : scores) {
            Object[] row = {
                s.getGameMode(),
                s.getScoreValue(),
                s.getFormattedDate()
            };
            tableModel.addRow(row);
        }
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(40, 40, 40));
        table.getTableHeader().setForeground(Color.WHITE);
        
        // Center align columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}