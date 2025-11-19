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
            
            // 1. Learning Mode
            GamePanel learningPanel = new GamePanel(mainPanel, cardLayout, true);
            // Create the Menu Panel
            MenuPanel menuPanel = new MenuPanel(mainPanel, cardLayout){
                @Override
                protected void onGameStart(String mode, int decks) {
                    if (mode.equals("GAME_LEARNING")) {
                        // Initialize the logic for Learning Mode with the user's deck count
                        learningPanel.startNewGame(decks);
                        cardLayout.show(mainContainer, "GAME_LEARNING");
                    }
                    // Logic for other modes (Beginner/Advanced) will go here later
                    else if (mode.equals("GAME_BEGINNER")) {
                        // Future: beginnerPanel.startNewGame(decks);
                        cardLayout.show(mainContainer, "GAME_BEGINNER");
                    } 
                    else if (mode.equals("GAME_ADVANCED")) {
                        // Future: advancedPanel.startNewGame(decks);
                        cardLayout.show(mainContainer, "GAME_ADVANCED");
                    }
                }
            };
            // --- Placeholders for Future Panels ---
            // In the future, you will replace these JPanels with your actual GamePanel classes
            
            // 2. Beginner Mode Placeholder
            JPanel beginnerPanel = createPlaceholderPanel("Kezdő Mód", Color.decode("#64B5F6"), mainPanel, cardLayout);
            
            // 3. Advanced Mode Placeholder
            JPanel advancedPanel = createPlaceholderPanel("Haladó Mód", Color.decode("#E57373"), mainPanel, cardLayout);

            // 4. Scoreboard Placeholder
            JPanel scorePanel = createPlaceholderPanel("Pontozás Tábla", Color.LIGHT_GRAY, mainPanel, cardLayout);


            // Add everything to CardLayout
            mainPanel.add(menuPanel, "MENU");
            mainPanel.add(learningPanel, "GAME_LEARNING");
            mainPanel.add(beginnerPanel, "GAME_BEGINNER");
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