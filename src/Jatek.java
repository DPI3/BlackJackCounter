import java.io.Serializable;

// Implements Serializable to handle the "Unexpected Shutdown" requirement
public abstract class Jatek implements Serializable {
    protected Deck deck;
    protected int count;
    protected Card currentCard;
    protected int cardsDrawn;
    protected int cardsPlayed;

    public Jatek(int numberOfDecks){
        this.count = 0;
        this.cardsDrawn = 0;
        this.deck = new Deck(numberOfDecks);
    }

    // Core logic: Draw card and update count
    public void nextTurn() {
        currentCard = deck.drawCard();
        if (currentCard != null) {
            updateCount(currentCard.getNumber());
            cardsPlayed++;
        }
    }

    // Logic to process the next move
    public void drawNext(){
        this.currentCard = deck.drawCard();
        if(this.currentCard != null){
            updateCount(this.currentCard.getNumber());
            cardsDrawn++;
        }
    }

    private void updateCount(int cardValue){
        if(cardValue >= 10) count--;
        else if(cardValue <= 6) count++;
    }

    public int getCount() { return count; }
    public Card getCurrentCard() { return currentCard; }
    public boolean isGameOver() { return deck.getRemainingCards() == 0; }
    public int getRemainingCards() { return deck.getRemainingCards(); }
    public int getScore() { return cardsPlayed; } 
}