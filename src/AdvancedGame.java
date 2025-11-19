public class AdvancedGame extends Jatek {
    private int turnsUntilCheck;

    public AdvancedGame(int decks) {
        super(decks);
        resetCheckTimer();
    }

    @Override
    public void drawNext() {
        super.drawNext();
        turnsUntilCheck--;
    }

    public boolean shouldAskUser() {
        return turnsUntilCheck <= 0;
    }

    public void resetCheckTimer() {
        // Randomly set next check between 4 and 5 turns
        turnsUntilCheck = 4 + (int)(Math.random() * 2); 
    }
}