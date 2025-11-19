import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        // Ensure GUI runs on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kártyaszámoló Program");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null); // Center on screen

            // Main container with CardLayout
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);
            
            GamePanel gamePanel = new GamePanel(mainPanel, cardLayout);
            // Create the Menu Panel
            MenuPanel menuPanel = new MenuPanel(mainPanel, cardLayout) {
                @Override
                protected void onGameStart(String mode, int decks) {
                    if (mode.equals("GAME_LEARNING")) {
                        // Configure GamePanel for Learning (Show count, no quiz)
                        gamePanel.startLearningGame(decks);
                        // Switch view to the game
                        cardLayout.show(mainPanel, "GAME_PANEL");
                    }
                    else if (mode.equals("GAME_BEGINNER")) {
                        // Configure GamePanel for Beginner (Hide count, enable quiz, 3 lives)
                        gamePanel.startBeginnerGame(decks);
                        // Switch view to the game
                        cardLayout.show(mainPanel, "GAME_PANEL");
                    } 
                    else if (mode.equals("GAME_ADVANCED")) {
                        // Advanced mode is not yet implemented in GamePanel
                        // Future: gamePanel.startAdvancedGame(decks);
                        cardLayout.show(mainPanel, "GAME_ADVANCED");
                    }
                }
            };
            // --- Placeholders for Future Panels ---
            // In the future, you will replace these JPanels with your actual GamePanel classes
            
            // 3. Advanced Mode Placeholder
            JPanel advancedPanel = createPlaceholderPanel("Haladó Mód", Color.decode("#E57373"), mainPanel, cardLayout);

            // 4. Scoreboard Placeholder
            JPanel scorePanel = createPlaceholderPanel("Pontozás Tábla", Color.LIGHT_GRAY, mainPanel, cardLayout);


            // Add everything to CardLayout
            mainPanel.add(menuPanel, "MENU");
            mainPanel.add(gamePanel, "GAME_PANEL");
            mainPanel.add(advancedPanel, "GAME_ADVANCED");
            mainPanel.add(scorePanel, "SCORES");

            // Add main panel to frame
            frame.add(mainPanel);
            
            // Show the window
            frame.setVisible(true);
        });
    }

    // Helper to create temporary panels so you can test navigation
    private static JPanel createPlaceholderPanel(String text, Color color, JPanel mainContainer, CardLayout cardLayout) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.CENTER);

        JButton backBtn = new JButton("Vissza a menübe");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        backBtn.addActionListener(e -> cardLayout.show(mainContainer, "MENU"));
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(backBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }
}