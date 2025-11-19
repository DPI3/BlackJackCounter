import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    
    public Deck(int numberOfDecks){
        cards = new ArrayList<>(numberOfDecks * 52);
        for(int i = 0; i < numberOfDecks; i++){
            cards.addAll(createDeck());
        }
        // Requirement: Use Collections.shuffle()
        Collections.shuffle(cards); 
    }

    private ArrayList<Card> createDeck(){
        ArrayList<Card> newDeck = new ArrayList<>(52);
        for(suits s: suits.values()){
            for(short j = 2; j <= 14; j++){
                newDeck.add(new Card(j, s));
            }
        }
        return newDeck;
    }

    public Card drawCard(){
        if(cards.isEmpty()) return null;
        return cards.remove(0);
    }

    public int getRemainingCards(){
        return cards.size();
    }
}