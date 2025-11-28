import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Az alkalmazás fő belépési pontja (Main osztály).
 * <p>
 * Ez az osztály felelős a grafikus felhasználói felület (GUI) alapjainak létrehozásáért.
 * Felépíti a főablakot (JFrame), inicializálja az adatkezelőt (ScoreManager),
 * és beállítja a {@link CardLayout} alapú navigációt a különböző nézetek
 * (Menü, Játék, Eredménytábla) között.
 * </p>
 */
public class App {

    /**
     * A program indító metódusa.
     * <p>
     * Biztosítja, hogy a GUI létrehozása és módosítása az Event Dispatch Thread-en (EDT)
     * történjen a {@link SwingUtilities#invokeLater} használatával.
     * Létrehozza és összekapcsolja a következő komponenseket:
     * <ul>
     * <li>{@link GamePanel}: A játék megjelenítése és logika futtatása.</li>
     * <li>{@link MenuPanel}: A főmenü és játékindítás vezérlése.</li>
     * <li>{@link ScorePanel}: Az eredménytábla megjelenítése.</li>
     * </ul>
     *
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kártyaszámoló Program");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null); // Középen

            // Layout Manager
            // CardLayout arra szolgál hogy a képernyők között váltogassunk (Menu <-> Game)
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);
            // Initialize Data Layer
            // We need this manager to pass to both the Game (to save) and the Scoreboard (to load)
            ScoreManager scoreManager = new ScoreManager();

            // The Game Panel (The "View" for the game)
            // We create ONE instance of GamePanel. We will reset its state
            // (Mode, Deck count, UI style) whenever a new game starts.
            GamePanel gamePanel = new GamePanel(mainPanel, cardLayout, scoreManager);

            // The Menu Panel (The Controller for starting games)
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


            // 5. Register Screens to Layout

            mainPanel.add(menuPanel, "MENU");
            mainPanel.add(gamePanel, "GAME_PANEL"); // Shared by all 3 game modes
            mainPanel.add(scorePanel, "SCORES");


            // 6. Show the App

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}