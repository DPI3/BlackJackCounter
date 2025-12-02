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
            // kepernyok kozott
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);

            ScoreManager scoreManager = new ScoreManager();

            GamePanel gamePanel = new GamePanel(mainPanel, cardLayout, scoreManager);

            MenuPanel menuPanel = new MenuPanel(mainPanel, cardLayout) {
                @Override
                protected void onGameStart(String mode, int decks) {
                    switch (mode) {
                        case "GAME_LEARNING":
                            // inicializalas
                            gamePanel.startLearningGame(decks);
                            cardLayout.show(mainPanel, "GAME_PANEL");
                            break;

                        case "GAME_BEGINNER":
                            gamePanel.startBeginnerGame(decks);
                            cardLayout.show(mainPanel, "GAME_PANEL");
                            break;

                        case "GAME_ADVANCED":
                            gamePanel.startAdvancedGame(decks);
                            cardLayout.show(mainPanel, "GAME_PANEL");
                            break;
                        case "SCORES":
                            cardLayout.show(mainPanel, "SCORES");
                            break;
                    }
                }
            };

            ScorePanel scorePanel = new ScorePanel(mainPanel, cardLayout, scoreManager);

            //listener
            scorePanel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    scorePanel.refreshScores();
                }
            });

            mainPanel.add(menuPanel, "MENU");
            mainPanel.add(gamePanel, "GAME_PANEL"); //midharom ezt hasznalja
            mainPanel.add(scorePanel, "SCORES");

            // vegso megjelenites
            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}