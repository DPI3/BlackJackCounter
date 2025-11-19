import java.util.Random;

public class AdvancedGame extends Jatek {
    private int turnsUntilCheck;
    private boolean isAlive;
    private Random random;

    public AdvancedGame(int decks) {
        super(decks);
        this.isAlive = true;
        this.random = new Random();
        resetCheckTimer();
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        turnsUntilCheck--;
    }

    public boolean shouldAskUser() {
        return turnsUntilCheck <= 0;
    }

    public void resetCheckTimer() {
        // Requirement: 4-5 cards interval
        turnsUntilCheck = 4 + random.nextInt(2); 
    }

    public boolean checkAnswer(int playerGuess) {
        boolean correct = (playerGuess == getCount());
        if (!correct) {
            isAlive = false; // Instant Game Over
        }
        return correct;
    }

    public boolean isAlive() {
        return isAlive;
    }
}