import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class App {
    public static void main(String[] args) {
        // Ensure all GUI tasks run on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kártyaszámoló Program");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null); // Center on screen

            // -------------------------------------------------------------
            // 1. Layout Manager
            // -------------------------------------------------------------
            // CardLayout allows us to switch between screens (Menu <-> Game)
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);
            // 1. Initialize Data Layer
            // We need this manager to pass to both the Game (to save) and the Scoreboard (to load)
            ScoreManager scoreManager = new ScoreManager();

            // -------------------------------------------------------------
            // 2. The Game Panel (The "View" for the game)
            // -------------------------------------------------------------
            // We create ONE instance of GamePanel. We will reset its state
            // (Mode, Deck count, UI style) whenever a new game starts.
            GamePanel gamePanel = new GamePanel(mainPanel, cardLayout, scoreManager);

            // -------------------------------------------------------------
            // 3. The Menu Panel (The Controller for starting games)
            // -------------------------------------------------------------
            // We override 'onGameStart' to define what happens when a button is clicked.
            MenuPanel menuPanel = new MenuPanel(mainPanel, cardLayout) {
                @Override
                protected void onGameStart(String mode, int decks) {
                    switch (mode) {
                        case "GAME_LEARNING":
                            // Initialize Learning Logic and UI
                            gamePanel.startLearningGame(decks);
                            cardLayout.show(mainPanel, "GAME_PANEL");
                            break;
                            
                        case "GAME_BEGINNER":
                            // Initialize Beginner Logic (Hidden count, Quiz, Lives)
                            gamePanel.startBeginnerGame(decks);
                            cardLayout.show(mainPanel, "GAME_PANEL");
                            break;
                            
                        case "GAME_ADVANCED":
                            // Initialize Advanced Logic (Hidden count, Text Input, Instant Death)
                            gamePanel.startAdvancedGame(decks);
                            cardLayout.show(mainPanel, "GAME_PANEL");
                            break;
                        case "SCORES":
                            cardLayout.show(mainPanel, "SCORES");
                            break;
                    }
                }
            };

            // 4. Initialize Score Panel (The Real Implementation)
            // Replaces the old createPlaceholderPanel method
            ScorePanel scorePanel = new ScorePanel(mainPanel, cardLayout, scoreManager);
            
            // Add a listener: Refresh the table every time the user navigates to this screen
            scorePanel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    scorePanel.refreshScores();
                }
            });

            // -------------------------------------------------------------
            // 5. Register Screens to Layout
            // -------------------------------------------------------------
            mainPanel.add(menuPanel, "MENU");
            mainPanel.add(gamePanel, "GAME_PANEL"); // Shared by all 3 game modes
            mainPanel.add(scorePanel, "SCORES");

            // -------------------------------------------------------------
            // 6. Show the App
            // -------------------------------------------------------------
            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    // --- Helper for Placeholder Screens ---
    private static JPanel createPlaceholderPanel(String text, Color color, JPanel mainContainer, CardLayout cardLayout) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.CENTER);

        JButton backBtn = new JButton("Vissza a menübe");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> cardLayout.show(mainContainer, "MENU"));
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bottomPanel.add(backBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }
}