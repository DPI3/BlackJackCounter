import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private JPanel mainContainer;
    private CardLayout cardLayout;

    public MenuPanel(JPanel mainContainer, CardLayout cardLayout) {
        this.mainContainer = mainContainer;
        this.cardLayout = cardLayout;

        // Layout setup
        setLayout(new GridBagLayout()); // GridBag for centering
        setBackground(new Color(50, 100, 50)); // Dark green background like a card table

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 rows (Title + 5 buttons), 10px gap
        buttonPanel.setOpaque(false);

        // Title
        JLabel titleLabel = new JLabel("Kártyaszámoló", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        buttonPanel.add(titleLabel);

        // Buttons
        addButton(buttonPanel, "Tanuló", "GAME_LEARNING");
        addButton(buttonPanel, "Kezdő", "GAME_BEGINNER");
        addButton(buttonPanel, "Haladó", "GAME_ADVANCED");
        addButton(buttonPanel, "Pontozás", "SCORES");
        
        JButton exitBtn = new JButton("Kilépés");
        styleButton(exitBtn);
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        add(buttonPanel);
    }

    private void addButton(JPanel panel, String text, String targetCardName) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For game modes, we might want to ask for deck count first
                if(targetCardName.startsWith("GAME")) {
                    String decksStr = JOptionPane.showInputDialog(panel, "Hány paklival szeretnél játszani?", "2");
                    if(decksStr != null && !decksStr.isEmpty()) {
                        try {
                            int decks = Integer.parseInt(decksStr);
                            // In a real implementation, you would pass 'decks' to the GamePanel here
                            System.out.println("Starting " + text + " with " + decks + " decks.");
                            cardLayout.show(mainContainer, targetCardName);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(panel, "Kérlek számot adj meg!");
                        }
                    }
                } else {
                    // Just switch (for Scores)
                    cardLayout.show(mainContainer, targetCardName);
                }
            }
        });
        panel.add(button);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
    }
}