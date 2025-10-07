public abstract class Jatek {
    Deck deck;
    int count;
    Jatek(int n){
        deck = new Deck(n);
    }

    public void consoleDisplayCard(){
        deck.PrintAll();
        System.out.println();
        int n = deck.getNumberOfCards();
        for(int i = 0; i < n; i++){
            Card random = deck.getRandomCard();
            System.out.print(i+1 + ". ");
            random.printcard();
            System.out.println();
            deck.removeCard(random);
        }
    }
}
