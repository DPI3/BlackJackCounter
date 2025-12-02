import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GamePanel extends JPanel {
    private Jatek gameLogic;
    private ScoreManager scoreManager;
    private String currentModeName;    // Tanuló, Kezdő, Haladó
    
    // UI Komponensek
    private JLabel cardLabel;
    private JLabel countLabel;
    private JLabel remainingLabel;
    private JLabel messageLabel;
    private JLabel livesLabel; 
    

    private JPanel bottomContainer; 
    private JPanel controlPanel;
    private JPanel quizPanel;
    private JButton[] choiceButtons;
    private JPanel inputPanel;
    private JTextField answerField;
    private JButton submitButton;
    
    private JPanel mainContainer;
    private CardLayout mainCardLayout;
    private boolean showCount; 

    // Konstruktor
    public GamePanel(JPanel mainContainer, CardLayout mainCardLayout, ScoreManager scoreManager) {
        this.mainContainer = mainContainer;
        this.mainCardLayout = mainCardLayout;
        this.scoreManager = scoreManager;
        
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40)); // Dark background

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
        livesLabel.setVisible(false); 
        
        statsPanel.add(countLabel);
        statsPanel.add(remainingLabel);
        statsPanel.add(livesLabel);
        add(statsPanel, BorderLayout.NORTH);

        // Center Card Area
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        
        // Card Display
        cardLabel = new JLabel("Kezdés", SwingConstants.CENTER);
        cardLabel.setFont(new Font("Serif", Font.BOLD, 60));
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        cardLabel.setPreferredSize(new Dimension(220, 320));
        centerPanel.add(cardLabel, gbc);

        //Rossz vagy jo
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 22));
        messageLabel.setForeground(Color.YELLOW);
        centerPanel.add(messageLabel, gbc);
        
        add(centerPanel, BorderLayout.CENTER);

        bottomContainer = new JPanel(new CardLayout());
        bottomContainer.setOpaque(false);
        bottomContainer.setPreferredSize(new Dimension(100, 100));

        createControlPanel();
        createQuizPanel();
        createInputPanel();

        bottomContainer.add(controlPanel, "CONTROL");
        bottomContainer.add(quizPanel, "QUIZ");
        bottomContainer.add(inputPanel, "INPUT");
        
        add(bottomContainer, BorderLayout.SOUTH);
    }

    // Helpers

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        
        JButton nextButton = new JButton("Következő Kártya");
        styleButton(nextButton);
        nextButton.addActionListener(e -> handleNextTurn());
        
        JButton exitButton = new JButton("Kilépés és Mentés");
        styleButton(exitButton);
        exitButton.setBackground(new Color(200, 80, 80));
        exitButton.addActionListener(e -> exitGame());

        controlPanel.add(nextButton);
        controlPanel.add(exitButton);
    }

    private void createQuizPanel() {
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
    }

    private void createInputPanel() {
        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputPanel.setOpaque(false);

        JLabel prompt = new JLabel("Írd be a számot: ");
        prompt.setForeground(Color.WHITE);
        prompt.setFont(new Font("Arial", Font.BOLD, 18));

        answerField = new JTextField(5);
        answerField.setFont(new Font("Arial", Font.BOLD, 18));

        answerField.addActionListener(e -> handleTextGuess());
        
        submitButton = new JButton("Küldés");
        styleButton(submitButton);
        submitButton.addActionListener(e -> handleTextGuess());

        inputPanel.add(prompt);
        inputPanel.add(answerField);
        inputPanel.add(submitButton);
    }


    public void startLearningGame(int deckCount) {
        this.gameLogic = new LearningGame(deckCount);
        this.currentModeName = "Tanuló";
        this.showCount = true;
        this.livesLabel.setVisible(false);
        resetUI();
    }

    public void startBeginnerGame(int deckCount) {
        this.gameLogic = new Beginer(deckCount);
        this.currentModeName = "Kezdő";
        this.showCount = false;
        this.livesLabel.setVisible(true);
        resetUI();
    }

    public void startAdvancedGame(int deckCount) {
        this.gameLogic = new AdvancedGame(deckCount);
        this.currentModeName = "Haladó";
        this.showCount = false;
        this.livesLabel.setVisible(false);
        resetUI();
    }

    private void resetUI() {
        cardLabel.setText("Start");
        cardLabel.setForeground(Color.WHITE);
        
        //idk miert de valamiert megoldja a kartya elcsuszas bugot
        String filename = "cards/1B.png";
        ImageIcon icon = new ImageIcon(filename);
        Image img = icon.getImage().getScaledInstance(220, 320, Image.SCALE_SMOOTH);

        messageLabel.setText(" ");
        countLabel.setVisible(showCount); 
        updateStats();
        switchBottomPanel("CONTROL");
    }

    // Main game loop

    private void handleNextTurn() {
        // ha vege van
        if (gameLogic.isGameOver()) {
            saveAndEndGame("A pakli elfogyott! Gratulálok!");
            return;
        }

        if (gameLogic instanceof Beginer) {
            Beginer bg = (Beginer) gameLogic;
            if (bg.shouldAskUser()) { 
                showQuiz(); 
                return; 
            }
        }

        else if (gameLogic instanceof AdvancedGame) {
            AdvancedGame ag = (AdvancedGame) gameLogic;
            if (ag.shouldAskUser()) { 
                showTextInput(); 
                return; 
            }
        }

        // Draw card
        gameLogic.nextTurn();
        updateCardDisplay();
        updateStats();
    }

    private void showQuiz() {
        int correct = gameLogic.getCount();
        ArrayList<Integer> options = new ArrayList<>();
        options.add(correct);
        
        Random r = new Random();
        while(options.size() < 3) {
            int fake = correct + (r.nextInt(7) - 3);
            if(!options.contains(fake)) options.add(fake);
        }
        Collections.shuffle(options);
        
        for(int i=0; i<3; i++) {
            choiceButtons[i].setText(String.valueOf(options.get(i)));
        }
        
        messageLabel.setText("Mennyi a számláló?");
        switchBottomPanel("QUIZ");
    }

    private void showTextInput() {
        messageLabel.setText("Írd be a helyes számot!");
        answerField.setText("");
        switchBottomPanel("INPUT");
        SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());
    }

    private void handleQuizGuess(String text) {
        int guess = Integer.parseInt(text);
        Beginer bg = (Beginer) gameLogic;
        
        if (bg.checkAnswer(guess)) {
            messageLabel.setText("Helyes!");
            messageLabel.setForeground(Color.GREEN);
            bg.resetCheck(); 
            switchBottomPanel("CONTROL");
        } else {
            if (!bg.isAlive()) {
                saveAndEndGame("Vége a játéknak! Túl sok rossz válasz.");
            } else {
                messageLabel.setText("Rossz válasz!");
                messageLabel.setForeground(Color.RED);
                updateStats();
            }
        }
    }

    private void handleTextGuess() {
        try {
            String text = answerField.getText().trim();
            if (text.isEmpty()) return;
            
            int guess = Integer.parseInt(text);
            AdvancedGame ag = (AdvancedGame) gameLogic;

            if (ag.checkAnswer(guess)) {
                messageLabel.setText("Helyes!");
                messageLabel.setForeground(Color.GREEN);
                ag.resetCheckTimer();
                switchBottomPanel("CONTROL");
            } else {
                saveAndEndGame("Rossz válasz! A játéknak vége.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kérlek érvényes számot írj be!");
        }
    }

    // Save es Exit

    private void saveAndEndGame(String message) {
        int finalScore = gameLogic.getScore();
        
        if (finalScore > 0) {
            scoreManager.addScore(new Score(currentModeName, finalScore));
            message += "\nVégső pontszám (lapok): " + finalScore + "\nEredmény mentve!";
        } else {
            message += "\nVégső pontszám: 0";
        }

        JOptionPane.showMessageDialog(this, message);
        mainCardLayout.show(mainContainer, "MENU");
    }

    private void exitGame() {
        if (gameLogic != null && gameLogic.getScore() > 0) {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Szeretnéd menteni az eredményt?", "Kilépés", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                scoreManager.addScore(new Score(currentModeName, gameLogic.getScore()));
            }
        }
        mainCardLayout.show(mainContainer, "MENU");
    }

    // UI Helpers

    private void updateCardDisplay() {
        Card card = gameLogic.getCurrentCard();
        if (card != null) {
            String filename = getCardFilename(card);
            ImageIcon icon = new ImageIcon(filename);

        if (icon.getIconWidth() > 0) {
                Image img = icon.getImage().getScaledInstance(220, 320, Image.SCALE_SMOOTH);
                cardLabel.setIcon(new ImageIcon(img));

                cardLabel.setText("");
                cardLabel.setBorder(null);
            } else {
                // Ha nem találja a képet
                cardLabel.setIcon(null);
                cardLabel.setText(filename);
                cardLabel.setForeground(Color.RED);
            }
        }
    }

    // file name
    private String getCardFilename(Card card) {
        String rank = "";
        
        // Szamok atalakitasa (2-9, T, J, Q, K, A)
        switch (card.getNumber()) {
            case 10: 
                rank = "T"; // 10 helyett T a fájlnévben
                break;
            case 11:
                rank = "J";
                break;
            case 12:
                rank = "Q";
                break;
            case 13:
                rank = "K";
                break;
            case 14:
                rank = "A";
                break;
            default:
                rank = String.valueOf(card.getNumber()); // 2-9
                break;
        }

        String suit = "";
        // Szinek (C, D, H, S)
        switch (card.suit) {
            case CLUBS:
                suit = "C";
                break;
            case DIAMONDS:
                suit = "D";
                break;
            case HEARTS:
                suit = "H";
                break;
            case SPADES:
                suit = "S";
                break;
        }

        return "cards/" + rank + suit + ".png";
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
}