public abstract class Jatek {
    Deck deck;
    int count;
    Jatek(int n){
        deck = new Deck(n);
    }

    public void consoleDisplayCard(){
        Card random = deck.getRandomCard();
        random.printcard();
    }
}
