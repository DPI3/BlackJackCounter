import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    protected JPanel mainContainer;
    protected CardLayout cardLayout;

    public MenuPanel(JPanel mainContainer, CardLayout cardLayout) {
        this.mainContainer = mainContainer;
        this.cardLayout = cardLayout;

        setLayout(new GridBagLayout());
        setBackground(new Color(50, 100, 50));

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        buttonPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Kártyaszámoló", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        buttonPanel.add(titleLabel);

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

    protected void onGameStart(String mode, int decks) {
        cardLayout.show(mainContainer, mode);
    }

    private void addButton(JPanel panel, String text, String targetCardName) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(e -> {
            if(targetCardName.startsWith("GAME")) {
                String decksStr = JOptionPane.showInputDialog(panel, "Hány paklival szeretnél játszani?", "2");
                if(decksStr != null && !decksStr.isEmpty()) {
                    try {
                        int decks = Integer.parseInt(decksStr);
                        onGameStart(targetCardName, decks);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Kérlek számot adj meg!");
                    }
                }
            } else {
                cardLayout.show(mainContainer, targetCardName);
            }
        });
        panel.add(button);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
    }
}