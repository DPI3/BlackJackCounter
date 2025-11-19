public class Card {
    short number;
    /*
     * The number can be an integer between 2 and 14
     * 11 means jack
     * 12 means queen
     * 13 means king
     * 14 means ace
     */
    String symbol(short num){
        if(num == 11)
            return "Jack";
        if(num == 12)
            return "Queen";
        if(num == 13)
            return "King";
        if(num == 14)
            return "Ace";
        return String.valueOf(num);
    }
    suits suit;
    Card(short n, suits s){
        number = n;
        suit = s;
    }
    public void printcard(){
        System.out.print(suit + " ");
        System.out.print(symbol(number));
    }
    public int getNumber(){
        return number;
    }
}
