import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Jatek gameLogic;
    private JLabel cardLabel; // Display card info
    private JLabel countLabel; // Display current count
    private JLabel remainingLabel;
    private JButton nextButton;
    private JPanel mainContainer;
    private CardLayout cardLayout;
    private boolean showCount; // Toggle for different modes

    public GamePanel(JPanel mainContainer, CardLayout cardLayout, boolean showCount) {
        this.mainContainer = mainContainer;
        this.cardLayout = cardLayout;
        this.showCount = showCount;
        
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40)); // Dark background

        // --- Top: Stats ---
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statsPanel.setOpaque(false);
        
        countLabel = new JLabel("Számláló: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 24));
        countLabel.setForeground(Color.CYAN);
        
        remainingLabel = new JLabel("Kártyák: 0");
        remainingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        remainingLabel.setForeground(Color.LIGHT_GRAY);

        if (showCount) {
            statsPanel.add(countLabel);
        }
        statsPanel.add(remainingLabel);
        add(statsPanel, BorderLayout.NORTH);

        // --- Center: The Card ---
        cardLabel = new JLabel("Kezdéshez nyomj a gombra", SwingConstants.CENTER);
        cardLabel.setFont(new Font("Serif", Font.BOLD, 36));
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        cardLabel.setPreferredSize(new Dimension(200, 300));
        
        JPanel centerWrapper = new JPanel(new GridBagLayout()); // Center the card box
        centerWrapper.setOpaque(false);
        centerWrapper.add(cardLabel);
        add(centerWrapper, BorderLayout.CENTER);

        // --- Bottom: Controls ---
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        
        nextButton = new JButton("Következő Kártya");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.addActionListener(e -> drawNextCard());
        
        JButton exitButton = new JButton("Kilépés");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(e -> exitGame());

        controlPanel.add(nextButton);
        controlPanel.add(exitButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void startNewGame(int deckCount) {
        // Initialize Learning Game Logic
        this.gameLogic = new LearningGame(deckCount);
        updateUIState();
    }

    private void drawNextCard() {
        if (gameLogic.isGameOver()) {
            JOptionPane.showMessageDialog(this, "A pakli elfogyott! Vége a játéknak.");
            exitGame();
            return;
        }

        gameLogic.nextTurn();
        updateUIState();
    }

    private void updateUIState() {
        Card card = gameLogic.getCurrentCard();
        
        // Update Card Display
        if (card != null) {
            // In a real app, load image here: new ImageIcon(card.toString() + ".png")
            // For now, we use text and color to simulate visual cards
            cardLabel.setText("<html><center>" + card.getNumber() + "<br>" + card.suit + "</center></html>");
            
            if (card.suit == suits.HEARTS || card.suit == suits.DIAMONDS) {
                cardLabel.setForeground(Color.RED);
            } else {
                cardLabel.setForeground(Color.WHITE);
            }
        }

        // Update Labels
        if (showCount) {
            countLabel.setText("Számláló: " + gameLogic.getCount());
        }
        remainingLabel.setText("Maradék: " + gameLogic.getRemainingCards());
    }

    private void exitGame() {
        // Save score would go here
        cardLayout.show(mainContainer, "MENU");
    }
}