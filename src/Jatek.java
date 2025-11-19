import java.io.Serializable;

// Implements Serializable to handle the "Unexpected Shutdown" requirement
public abstract class Jatek implements Serializable {
    protected Deck deck;
    protected int currentCount;
    protected Card currentCard;
    protected int cardsDrawn;

    public Jatek(int numberOfDecks){
        this.currentCount = 0;
        this.cardsDrawn = 0;
        this.deck = new Deck(numberOfDecks);
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
        if(cardValue >= 10) currentCount--;
        else if(cardValue <= 6) currentCount++;
    }

    public int getCount() { return currentCount; }
    public Card getCurrentCard() { return currentCard; }
    public boolean isGameOver() { return deck.getRemainingCards() == 0; }
}