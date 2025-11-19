import java.util.Random;

public class Beginer extends Jatek {
    private int lives;
    private int cardsSinceLastCheck;
    private int checkInterval;
    private Random random;

    public Beginer(int decks) {
        super(decks);
        this.lives = 3;
        this.cardsSinceLastCheck = 0;
        this.random = new Random();
        setNextCheckInterval();
    }

    private void setNextCheckInterval() {
        // Randomly choose 4 or 5 cards
        this.checkInterval = 4 + random.nextInt(2); 
        this.cardsSinceLastCheck = 0;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        cardsSinceLastCheck++;
    }

    public boolean shouldAskUser() {
        return cardsSinceLastCheck >= checkInterval;
    }

    public void resetCheck() {
        setNextCheckInterval();
    }
    
    public boolean checkAnswer(int playerGuess) {
        boolean correct = (playerGuess == getCount());
        if (!correct) {
            lives--;
        }
        return correct;
    }

    public int getLives() {
        return lives;
    }

    public boolean isAlive() {
        return lives > 0;
    }
}