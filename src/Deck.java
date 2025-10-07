import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards;
    int num; //number of the decks
    
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
    }

    public void RemoveCard(int index){
        cards.remove(index);
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    public Card getRandomCard(){
        int random = (int)(Math.random() * ((num*52) + 1));
        return cards.get(random);
    }

    public void PrintAll(){
        for(int i = 1; i <= num; i++){        
            for(int j = 0; j < 52; j++){
                cards.get(i*j).printcard();
                System.out.println();
            }
        }
    }
}
