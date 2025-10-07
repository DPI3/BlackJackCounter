public class App {
    public static void main(String[] args) throws Exception {
        Deck pakli = new Deck(1);
        pakli.PrintAll();
        System.out.println();
        Beginer proba = new Beginer();
        proba.consoleDisplayCard();
        System.out.println();
    }
}
