import java.io.Serializable;

/**
 * Absztrakt osztály, amely egy kártyajáték alapvető logikáját valósítja meg.
 * Támogatja az objektum szerializációját a játékállás mentéséhez.
 */
public abstract class Jatek implements Serializable {
    /**
     * A játékban használt kártyapakli.
     */
    protected Deck deck;

    /**
     * A kártyaszámolás aktuális értéke (running count).
     * Alacsony lapoknál nő, magas lapoknál csökken.
     */
    protected int count = 0;

    /**
     * Az éppen aktuális, legutóbb húzott kártya.
     */
    protected Card currentCard;

    /**
     * A játék során felhúzott lapok teljes száma.
     */
    protected int cardsDrawn = 0;

    /**
     * A kijátszott lapok száma, amely a pontszám alapját képezi.
     */
    protected int cardsPlayed;

    /**
     * Létrehoz egy új játékot a megadott számú paklival.
     *
     * @param numberOfDecks A játékhoz használt paklik száma.
     */
    public Jatek(int numberOfDecks) {
        this.deck = new Deck(numberOfDecks);
    }

    /**
     * A következő kör lebonyolítása.
     * Húz egy lapot a pakliból, frissíti a számolási értéket,
     * és növeli a kijátszott lapok számát (cardsPlayed).
     */
    public void nextTurn() {
        this.currentCard = this.deck.drawCard();
        if (this.currentCard != null) {
            this.updateCount(this.currentCard.getNumber());
            ++this.cardsPlayed;
        }

    }

    /**
     * Húz egy következő lapot.
     * Hasonló a nextTurn metódushoz, de ez a felhúzott lapok
     * számát (cardsDrawn) növeli a kijátszott lapok helyett.
     */
    public void drawNext() {
        this.currentCard = this.deck.drawCard();
        if (this.currentCard != null) {
            this.updateCount(this.currentCard.getNumber());
            ++this.cardsDrawn;
        }

    }

    /**
     * Frissíti a számolási értéket (count) a húzott kártya értéke alapján.
     * Hi-Lo stratégia szerint:
     * - Érték >= 10: -1 a számlálóhoz.
     * - Érték <= 6: +1 a számlálóhoz.
     *
     * @param cardValue A kártya numerikus értéke.
     */
    private void updateCount(int cardValue) {
        if (cardValue >= 10) {
            --this.count;
        } else if (cardValue <= 6) {
            ++this.count;
        }

    }

    /**
     * Visszaadja az aktuális számolási értéket.
     *
     * @return A 'count' változó jelenlegi értéke.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Visszaadja a jelenlegi kártyát.
     *
     * @return A legutóbb húzott Card objektum, vagy null, ha még nem húztak.
     */
    public Card getCurrentCard() {
        return this.currentCard;
    }

    /**
     * Ellenőrzi, hogy véget ért-e a játék.
     *
     * @return Igaz, ha a pakliban nem maradt több kártya, egyébként hamis.
     */
    public boolean isGameOver() {
        return this.deck.getRemainingCards() == 0;
    }

    /**
     * Lekérdezi a pakliban maradt kártyák számát.
     *
     * @return A hátralévő kártyák száma.
     */
    public int getRemainingCards() {
        return this.deck.getRemainingCards();
    }

    /**
     * Visszaadja a játékos aktuális pontszámát.
     * Ebben a megvalósításban a pontszám megegyezik a kijátszott lapok számával.
     *
     * @return A kijátszott lapok száma.
     */
    public int getScore() {
        return this.cardsPlayed;
    }
}