import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GamePanel extends JPanel {
    private Jatek gameLogic;
    
    // --- UI Components ---
    private JLabel cardLabel;      // Display card value/suit
    private JLabel countLabel;     // Display current count (Hidden in Beginner)
    private JLabel remainingLabel; // Display remaining cards
    private JLabel messageLabel;   // Feedback area (e.g., "Correct!", "Wrong!")
    private JLabel livesLabel;     // Display lives (Only for Beginner)
    
    // Panels for switching between controls
    private JPanel bottomContainer; 
    private JPanel controlPanel;    // Standard "Next Card" button
    private JPanel quizPanel;       // Quiz buttons
    private JButton[] choiceButtons;
    
    private JPanel mainContainer;
    private CardLayout mainCardLayout; // To switch back to Menu
    private boolean showCount; 

    public GamePanel(JPanel mainContainer, CardLayout mainCardLayout) {
        this.mainContainer = mainContainer;
        this.mainCardLayout = mainCardLayout;
        
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40)); // Dark background

        // --------------------------
        // 1. Top Stats Bar
        // --------------------------
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statsPanel.setOpaque(false);
        
        countLabel = new JLabel("Számláló: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 24));
        countLabel.setForeground(Color.CYAN);
        
        remainingLabel = new JLabel("Kártyák: 0");
        remainingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        remainingLabel.setForeground(Color.LIGHT_GRAY);

        livesLabel = new JLabel("Életek: 3");
        livesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        livesLabel.setForeground(Color.RED);
        livesLabel.setVisible(false); // Hidden by default
        
        statsPanel.add(countLabel);
        statsPanel.add(remainingLabel);
        statsPanel.add(livesLabel);
        add(statsPanel, BorderLayout.NORTH);

        // --------------------------
        // 2. Center Card Display
        // --------------------------
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        
        cardLabel = new JLabel("Kezdés", SwingConstants.CENTER);
        cardLabel.setFont(new Font("Serif", Font.BOLD, 60));
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        cardLabel.setPreferredSize(new Dimension(220, 320));
        centerPanel.add(cardLabel, gbc);

        // Message Label (Below card)
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0); // Top margin
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 22));
        messageLabel.setForeground(Color.YELLOW);
        centerPanel.add(messageLabel, gbc);
        
        add(centerPanel, BorderLayout.CENTER);

        // --------------------------
        // 3. Bottom Controls
        // --------------------------
        bottomContainer = new JPanel(new CardLayout());
        bottomContainer.setOpaque(false);
        bottomContainer.setPreferredSize(new Dimension(100, 100));

        // State A: Normal Game (Next Button)
        controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        
        JButton nextButton = new JButton("Következő Kártya");
        styleButton(nextButton);
        nextButton.addActionListener(e -> handleNextTurn());
        
        JButton exitButton = new JButton("Kilépés");
        styleButton(exitButton);
        exitButton.setBackground(new Color(200, 80, 80));
        exitButton.addActionListener(e -> exitGame());

        controlPanel.add(nextButton);
        controlPanel.add(exitButton);

        // State B: Quiz (Choice Buttons)
        quizPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        quizPanel.setOpaque(false);
        choiceButtons = new JButton[3];
        
        for(int i=0; i<3; i++) {
            JButton btn = new JButton("?");
            styleButton(btn);
            btn.setPreferredSize(new Dimension(80, 50));
            btn.addActionListener(e -> handleQuizGuess(btn.getText()));
            choiceButtons[i] = btn;
            quizPanel.add(btn);
        }

        bottomContainer.add(controlPanel, "CONTROL");
        bottomContainer.add(quizPanel, "QUIZ");
        add(bottomContainer, BorderLayout.SOUTH);
    }

    // --- Initialization Methods ---

    public void startLearningGame(int deckCount) {
        this.gameLogic = new LearningGame(deckCount);
        this.showCount = true;
        this.livesLabel.setVisible(false);
        resetUI();
    }

    public void startBeginnerGame(int deckCount) {
        this.gameLogic = new Beginer(deckCount);
        this.showCount = false; // Requirement: Hide count
        this.livesLabel.setVisible(true);
        resetUI();
    }

    private void resetUI() {
        cardLabel.setText("Start");
        cardLabel.setForeground(Color.WHITE);
        messageLabel.setText(" ");
        countLabel.setVisible(showCount); 
        updateStats();
        switchBottomPanel("CONTROL");
    }

    // --- Game Loop Logic ---

    private void handleNextTurn() {
        if (gameLogic.isGameOver()) {
            JOptionPane.showMessageDialog(this, "A pakli elfogyott! Játék vége.\nVégső szám: " + gameLogic.getCount());
            exitGame();
            return;
        }

        // Specific Logic for Beginner Mode
        if (gameLogic instanceof Beginer) {
            Beginer bg = (Beginer) gameLogic;
            // Check if we hit the 4-5 card limit
            if (bg.shouldAskUser()) {
                showQuiz(); 
                return; // Stop here, wait for user input
            }
        }

        // Normal flow: Draw card
        gameLogic.nextTurn();
        updateCardDisplay();
        updateStats();
    }

    private void showQuiz() {
        int correct = gameLogic.getCount();
        ArrayList<Integer> options = new ArrayList<>();
        options.add(correct);
        
        // Generate 2 unique fake answers close to the real one
        Random r = new Random();
        while(options.size() < 3) {
            int fake = correct + (r.nextInt(7) - 3); // Range: correct +/- 3
            if(!options.contains(fake)) {
                options.add(fake);
            }
        }
        Collections.shuffle(options);

        // Assign numbers to buttons
        for(int i=0; i<3; i++) {
            choiceButtons[i].setText(String.valueOf(options.get(i)));
        }
        
        messageLabel.setText("Mennyi a számláló?");
        switchBottomPanel("QUIZ");
    }

    private void handleQuizGuess(String text) {
        int guess = Integer.parseInt(text);
        Beginer bg = (Beginer) gameLogic; // Safe cast because quiz only happens in Beginner
        
        boolean isCorrect = bg.checkAnswer(guess);

        if (isCorrect) {
            messageLabel.setText("Helyes!");
            messageLabel.setForeground(Color.GREEN);
            bg.resetCheck(); // Reset the 4-5 card timer
            
            // Switch back to game controls
            switchBottomPanel("CONTROL");
        } else {
            if (!bg.isAlive()) {
                JOptionPane.showMessageDialog(this, "Vége a játéknak! Túl sok rossz válasz.\nVégső szám: " + bg.getCount());
                exitGame();
            } else {
                messageLabel.setText("Rossz válasz!");
                messageLabel.setForeground(Color.RED);
                updateStats(); // Update lives display
            }
        }
    }

    // --- Helper Methods ---

    private void updateCardDisplay() {
        Card card = gameLogic.getCurrentCard();
        if (card != null) {
            String suitSymbol = getSuitSymbol(card.suit);
            cardLabel.setText("<html><div style='text-align: center;'>" + 
                              card.getNumber() + "<br>" + 
                              "<span style='font-size: 40px;'>" + suitSymbol + "</span>" + 
                              "</div></html>");
            
            if (card.suit == suits.HEARTS || card.suit == suits.DIAMONDS) {
                cardLabel.setForeground(new Color(255, 80, 80));
            } else {
                cardLabel.setForeground(Color.WHITE);
            }
        }
    }
    
    private String getSuitSymbol(suits s) {
        if (s == null) return "";
        switch(s) {
            case SPADES: return "♠";
            case HEARTS: return "♥";
            case CLUBS: return "♣";
            case DIAMONDS: return "♦";
            default: return "";
        }
    }

    private void updateStats() {
        if(showCount) {
            countLabel.setText("Számláló: " + gameLogic.getCount());
        }
        remainingLabel.setText("Lapok: " + gameLogic.getRemainingCards());
        
        if(gameLogic instanceof Beginer) {
            livesLabel.setText("Életek: " + ((Beginer)gameLogic).getLives());
        }
    }

    private void switchBottomPanel(String name) {
        CardLayout cl = (CardLayout) bottomContainer.getLayout();
        cl.show(bottomContainer, name);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void exitGame() {
        mainCardLayout.show(mainContainer, "MENU");
    }
}