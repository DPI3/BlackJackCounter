public abstract class Jatek {
    Deck deck;
    int count;
    Jatek(int n){
        count = 0;
        deck = new Deck(n);
    }

    private int getCount(){
        return count;
    }

    private void modifyCount(int nextCard){
        if(nextCard >= 10)
            count--;
        if(nextCard <= 6)
            count++;
    }

    public void consoleDisplayCard(){
        deck.printAll();
        System.out.println();
        int n = deck.getNumberOfCards();
        for(int i = 0; i < n; i++){
            Card random = deck.getRandomCard();
            System.out.print(i+1 + ". ");
            random.printcard();
            deck.removeCard(random);
            modifyCount(random.getNumber());
            System.out.print("   " + count);
            System.out.println();
        }
    }
}
