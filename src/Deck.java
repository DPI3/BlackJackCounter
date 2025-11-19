import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards;
    int num; //number of the decks
    int numCard; //number of cards
    
    ArrayList<Card> createDeck(){
        ArrayList<Card> card = new ArrayList<Card>(52);
        for(suits s: suits.values()){
            for(short j = 2; j <= 14; j++){
                card.add(new Card(j, s));
            }
        }
        return card;
    }

    /*
     * n - the number of decks
     */
    Deck(int n){
        num = n;
        cards = new ArrayList<Card>(num*52); // 52 - size of a singe deck
        for(int i = 0; i < num; i++){
            cards.addAll(createDeck());
        }
        numCard = cards.size();
    }

    public void removeCard(Card card){
        numCard--;
        cards.remove(card);
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    public Card getRandomCard(){
        int random = (int)(Math.random() * cards.size());
        return cards.get(random);
    }

    public int getNumberOfCards(){
        return cards.size();
    }

    public void printAll(){
        System.out.println("Pakli");
        for(int i = 0; i < cards.size(); i++){
            cards.get(i).printcard();
            System.out.println();
        }
    }
}
